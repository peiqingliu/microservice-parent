package com.liupeiqing.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {


    @Value("${messages.url:http://localhost:8087}/api")
    String messagesUrl;

    @Bean
    OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details){
        return new OAuth2RestTemplate(details,oAuth2ClientContext);
    }

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @GetMapping("/user")
    public Authentication user(Authentication user) {
        return user;
    }

    @RequestMapping("/api")
    String home(Model model) {
        String result = oAuth2RestTemplate.getForObject(messagesUrl + "/2", String.class);
        return result;
    }


}
