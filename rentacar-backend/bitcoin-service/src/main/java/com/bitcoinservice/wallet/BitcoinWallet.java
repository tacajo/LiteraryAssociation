package com.bitcoinservice.wallet;

import com.bitcoinservice.dto.PaymentResponse;
import com.bitcoinservice.model.Payment;
import com.bitcoinservice.repository.PaymentRepository;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BitcoinWallet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WalletAppKit walletAppKit;

    @Value("${bitcoin.network}")
    private String network;

    @Value("${bitcoin.file-prefix}")
    private String filePrefix;

    @Value("${bitcoin.file-location}")
    private String btcFileLocation;

    @Autowired
    private NetworkParameters networkParameters;

    @Autowired
    private PaymentRepository paymentRepository;

    public ResponseEntity<?> send(String value, String to, String uuid) {
        try {
            Address toAddress = LegacyAddress.fromBase58(networkParameters, to);
            SendRequest sendRequest = SendRequest.to(toAddress, Coin.parseCoin(value));
            sendRequest.feePerKb = Coin.parseCoin("0.0005");
            Wallet.SendResult sendResult = walletAppKit.wallet().sendCoins(walletAppKit.peerGroup(), sendRequest);
            sendResult.broadcastComplete.addListener(() ->
                            System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getTxId()),
                    MoreExecutors.directExecutor());

            System.out.println("Estimated balance: " + walletAppKit.wallet().getBalance(Wallet.BalanceType.ESTIMATED).toPlainString());

            Payment paymentDB = paymentRepository.findByUuid(uuid);
            paymentDB.setCurrency("BTC");
            paymentDB.setDetails(sendResult.tx.getTxId().toString());
            paymentDB.setState("completed");
            paymentDB.setTotal(value);
            paymentDB = paymentRepository.save(paymentDB);
            logger.info(String.format("Successful payment with id: %s", paymentDB.getId().toString()));
            return new ResponseEntity<>(new PaymentResponse("Successful payment!", paymentDB.getId()), HttpStatus.OK);

        } catch (InsufficientMoneyException e) {
            System.out.println(e);
            Payment paymentDB = paymentRepository.findByUuid(uuid);
            paymentDB.setCurrency("BTC");
            paymentDB.setDetails("You don't have enough money!");
            paymentDB.setState("failed");
            paymentDB.setTotal(value);
            paymentDB = paymentRepository.save(paymentDB);
            logger.error(String.format("Failed payment with id: %s", paymentDB.getId().toString()));
            return new ResponseEntity<>(new PaymentResponse("You don't have enough money!", paymentDB.getId()), HttpStatus.BAD_REQUEST);

        }
    }

}