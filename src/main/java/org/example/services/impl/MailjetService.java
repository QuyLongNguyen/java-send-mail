package org.example.services.impl;

import com.google.gson.Gson;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.example.dto.EmailDto;
import org.example.services.EmailService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class MailjetService implements EmailService {

    private final String apiKey = ""; //your api key

    private final String secretKey = ""; //your secret key

    private final String mailJetVersion = "v3.1"; //mailjet api version



    @Override
    public boolean sendEmail(EmailDto emailDto) {
        MailjetClient client = new MailjetClient(apiKey, secretKey, new ClientOptions(mailJetVersion));
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject(new Gson().toJson(emailDto))));
        try{
            MailjetResponse response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
