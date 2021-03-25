package com.bankservice.service.impl;

import com.bankservice.dto.IssuerResponseDTO;
import com.bankservice.model.*;
import com.bankservice.repository.AccountRepository;
import com.bankservice.repository.AcquirerRepository;
import com.bankservice.repository.BankRepository;
import com.bankservice.repository.IssuerRepository;
import com.bankservice.service.AcquirerService;
import com.bankservice.service.IssuerService;
import com.bankservice.service.PaymentService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AcquirerServiceImpl implements AcquirerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AcquirerRepository acquirerRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private IssuerRepository issuerRepository;

    @Autowired
    private IssuerService issuerService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Acquirer save(Acquirer acquirer) {
        return acquirerRepository.save(acquirer);
    }

    public Acquirer addAcquirer(String uuid) {
        logger.info("adding acquirer...");
        Acquirer acquirer = new Acquirer();
        Account account = new Account();
        logger.info("making account for acquirer...");
        LocalDate today = LocalDate.now();
        account.setBank(bankRepository.findById(1L).get());
        account.setPan(passwordEncoder.encode("11111"));
        account.setSecurityCode(passwordEncoder.encode("11111"));
        account.setDate("2022-02-22");
        account.setCardHolderName("Vulkan");
        account.setBalance(10000L);
        account = accountRepository.save(account);
        logger.info("account for acquirer is added...");

        acquirer.setMerchant_id(UUID.randomUUID().toString());
        acquirer.setMerchant_password(UUID.randomUUID().toString());
        acquirer.setLu(uuid);
        acquirer.setAccount(account);
        save(acquirer);
        logger.info("new merchant is added to data base...");
        return acquirer;
    }

    public boolean checkBank(String uuid, Account account) {
        Payment payment = paymentService.findByUuid(uuid);
        Issuer issuer = issuerRepository.findByUsername(payment.getUsername());
        Bank bankIssuer = issuer.getAccount().getBank();
        Acquirer acquirer = acquirerRepository.findByLu(payment.getLuUuid());
        Bank bankAcquirer = acquirer.getAccount().getBank();

        Long acquirerBalance = acquirer.getAccount().getBalance();
        Long issuerBalance = issuer.getAccount().getBalance();

        if (account.getCardHolderName().equals(issuer.getAccount().getCardHolderName()) &&
                BCrypt.checkpw(account.getPan(), issuer.getAccount().getPan()) &&
                BCrypt.checkpw(account.getSecurityCode(), issuer.getAccount().getSecurityCode())) {
            logger.info("valid data for pan and security code...");
            if (bankIssuer.getId() == bankAcquirer.getId()) {
                logger.info("issuer bank and acquirer bank are equal.");
                if (issuer.getAccount().getBalance() >= payment.getAmount()) {
                    logger.info("user has enough balance...");
                    issuer.getAccount().setBalance(issuer.getAccount().getBalance() - payment.getAmount());
                    acquirer.getAccount().setBalance(acquirer.getAccount().getBalance() + payment.getAmount());

                    try {
                        logger.warn("trying to execute transaction...");
                        acquirerRepository.save(acquirer);
                        issuerService.save(issuer);
                        payment.setProcessed(true);
                        payment.setState("successful");
                        logger.info(String.format("Successful payment with id: %s", uuid));
                        paymentService.save(payment);
                        return true;
                    } catch (Exception e) {
                        issuer.getAccount().setBalance(issuerBalance);
                        acquirer.getAccount().setBalance(acquirerBalance);
                        acquirerRepository.save(acquirer);
                        issuerService.save(issuer);
                        payment.setProcessed(false);
                        payment.setState("fail");
                        paymentService.save(payment);
                        logger.error(String.format("Failed payment with id: %s", uuid));
                        return false;
                    }
                } else {
                    logger.error(issuer.getUsername() + " does not have sufficient funds");
                    return false;
                }
            } else {

                String acquirerOrderId = uuid;
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                HttpHeaders headers = new HttpHeaders();

                String url = "http://localhost:8089/pcc/" + acquirerOrderId + "/" + timestamp.toString().split(" ")[0];
                System.out.println(url);
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                ResponseEntity<IssuerResponseDTO> responseEntity = restTemplate.postForEntity(url,
                        account, IssuerResponseDTO.class);
                System.out.println(responseEntity.getBody());

                if (responseEntity.getBody().isProcessed()) {
                    try {
                        acquirer.getAccount().setBalance(acquirer.getAccount().getBalance() + payment.getAmount());
                        acquirerRepository.save(acquirer);
                        logger.info(String.format("Successful payment with id: %s", uuid));
                        return true;
                    } catch (Exception e) {
                        url = "http://localhost:8089/pcc/rollback/" + issuer.getAccount().getId() + "/" + payment.getAmount();
                        ResponseEntity<String> responseEntity2 = restTemplate.getForEntity(url, String.class);
                        logger.error(String.format("Failed payment with id: %s", uuid));
                        return false;
                    }
                }

            }

            //ovde se salje restTemplate KP-u o uspesnosti transakcije
        } else {
            logger.error("Data is not valid");
            return false;
        }

        return false;
    }

    public String getUrl(String orderId, boolean success) {
        logger.info(String.format("getting url for order with id %s", orderId));
        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:8087/request/" + orderId + "/" + success;
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                entity, String.class);

        return responseEntity.getBody();
    }
}
