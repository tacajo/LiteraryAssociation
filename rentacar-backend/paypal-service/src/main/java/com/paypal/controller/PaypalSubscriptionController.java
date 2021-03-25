package com.paypal.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paypal.base.codec.binary.Base64;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.model.SubscriptionDetails;
import com.paypal.repository.SubscriberDataRepository;
import com.paypal.repository.SubscriptionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import com.paypal.model.SubscriberData;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
public class PaypalSubscriptionController {

    @Autowired
    private SubscriberDataRepository subscriberDataRepository;

    @Autowired
    private SubscriptionDetailsRepository subscriptionDetailsRepository;


    private RestTemplate restTemplate;
    @PostMapping(value = "/subscribe")
    public String subscribe(@RequestParam("subsType") String subsType,@RequestParam("uuidLU") String uuidLU,@RequestBody SubscriberData subscriber ) throws UnsupportedEncodingException, PayPalRESTException {

        subscriber= subscriberDataRepository.save(subscriber);
        SubscriptionDetails subscriptionDetails= new SubscriptionDetails();
        subscriptionDetails.setStatus("initiated");
        subscriptionDetails.setSubscriberDataID(subscriber.getId());
        subscriptionDetails.setUuidLU(uuidLU);
        subscriptionDetailsRepository.save(subscriptionDetails);
        String plan_id="";
        if(subsType.equals("gY")) {
            plan_id="P-0SY19461KL4485728L74ZDUI";
        } else  if(subsType.equals("gM")) {
            plan_id="P-9HA71296P2774221FL74ZFLI";
        } else if(subsType.equals("rY")) {
            plan_id="P-6L833645AS237581DL74ZCTI";
        } else {
            plan_id="P-6XX71530Y9035182CL74ZETI";
        };

        RestTemplate restTemplate = new RestTemplate();
        String paypalAPI = "https://api-m.sandbox.paypal.com/v1/billing/subscriptions";
        String defJson = "{ \"plan_id\": \""+ plan_id +"\","+
                "\"start_time\": \"2021-02-10T06:00:00Z\","+
                " \"subscriber\": {"+
                "\"name\": {"+
                "\"given_name\": \""+ subscriber.getFirstName() +"\","+
                "      \"surname\": \""+ subscriber.getLastName() +"\""+
                "},"+
                " \"email_address\": \""+ subscriber.getEmail() +"\""+
                "},"+
                "\"application_context\": {"+
                "\"brand_name\": \"Literary association\","+
                "\"locale\": \"en-US\",\"shipping_preference\": \"SET_PROVIDED_ADDRESS\","+
        "   \"user_action\": \"SUBSCRIBE_NOW\","+
        "           \"payment_method\": {"+
        "       \"payer_selected\": \"PAYPAL\","+
        "               \"payee_preferred\": \"IMMEDIATE_PAYMENT_REQUIRED\""+
                "            } } } ";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAccessToken());
        System.out.println(headers);
        HttpEntity<String> entity = new HttpEntity<String>(defJson, headers);
        System.out.println(defJson);
        String jsonResponse = restTemplate.postForObject(paypalAPI, entity, String.class);
        System.out.println(jsonResponse);

        subscriptionDetails.setStatus("completed");
        subscriptionDetailsRepository.save(subscriptionDetails);
        return jsonResponse;
    }

    public String getAccessToken() throws UnsupportedEncodingException, PayPalRESTException {
        String paypalAPI = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

        String clientId = "AYuXzgpR-2tEiCSbTG2vL35TI-O0gcgzbws3ABs03EVKidFwcm1utYFwtI9gyM0ES9ejhxM99qhXYFty";
        String clientSecret = "EEdlV6XGlZ4WpGtKiE8mdeRKhg64UmyCPJoI2H-kL2t-jsjDSdEy0-xqBfeX0dwq4RkE_mdSQ_7CM748";

        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
        return  context.fetchAccessToken();
    }

}
