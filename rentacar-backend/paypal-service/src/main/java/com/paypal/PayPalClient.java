package com.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.repository.PaymentRepository;
import com.paypal.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class PayPalClient {

    Logger logger = Logger.getLogger("MyLog");
    String clientId = "AYuXzgpR-2tEiCSbTG2vL35TI-O0gcgzbws3ABs03EVKidFwcm1utYFwtI9gyM0ES9ejhxM99qhXYFty";
    String clientSecret = "EEdlV6XGlZ4WpGtKiE8mdeRKhg64UmyCPJoI2H-kL2t-jsjDSdEy0-xqBfeX0dwq4RkE_mdSQ_7CM748";

    @Autowired
    private PaymentRepository paymentRepository;


    public Map<String, Object> createPayment(String sum){
        //sum je uuid iz baze
        com.paypal.model.Payment paymentDB = paymentRepository.findByUuid(sum);


        Map<String, Object> response = new HashMap<String, Object>();
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

// Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();

// Set payment details
        Details details = new Details();
     /*   details.setShipping("1");
        details.setSubtotal("5");
        details.setTax("1");
*/
// Payment amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
// Total must be equal to sum of shipping, tax and subtotal.
        amount.setTotal(sum);
        amount.setDetails(details);

// Transaction information
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction
                .setDescription("This is the payment transaction description.");

// Add transaction to a list
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

// Add payment details
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);
        // PROMIJENI OVO
        redirectUrls.setCancelUrl("http://localhost:4201/cancel");
        redirectUrls.setReturnUrl("http://localhost:4201/success");
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            createdPayment = payment.create(context);

            System.out.println(createdPayment);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();

                System.out.println(links);
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }

                logger.info("Payment created. Details :" + createdPayment);
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    public Map<String, Object> completePayment(HttpServletRequest req){
        com.paypal.model.Payment paymentDB = paymentRepository.findByUuid(req.getParameter("uuid"));
        Map<String, Object> response = new HashMap();
        Payment payment = new Payment();
        payment.setId(req.getParameter("paymentId"));

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(req.getParameter("PayerID"));
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            if(createdPayment!=null){
                response.put("status", "success");
                response.put("payment", createdPayment);
            }

            System.out.println("THIS PAYMENT HAS BEEN EXECUTED : "+createdPayment);

            logger.info("Payment executed. Details : "+createdPayment);

//            paymentDB.setState(createdPayment.getState());
            paymentDB.setDetails(createdPayment.getCart());
            paymentDB.setPaypalPaymentID(createdPayment.getId());
            paymentDB.setState("completed");
           // payment1.setPayer(createdPayment.getPayer().getPayerInfo().toJSON());
           // payment1.setTransactions(payment.getTransactions().get(0).toJSON());
            paymentRepository.save(paymentDB);

        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return response;

    }
}
