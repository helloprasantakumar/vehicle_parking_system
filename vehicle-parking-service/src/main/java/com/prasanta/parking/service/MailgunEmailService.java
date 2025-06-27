//package com.prasanta.parking.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Service
//public class MailgunEmailService {
//
//    @Value("${mailgun.domain}")
//    private String domain;
//
//    @Value("${mailgun.api-key}")
//    private String apiKey;
//
//    @Value("${mailgun.from-email}")
//    private String fromEmail;
//
//    private final WebClient webClient = WebClient.builder()
//            .baseUrl("https://api.mailgun.net/v3")
//            .defaultHeaders(headers ->
//                    headers.setBasicAuth("api", apiKey))
//            .build();
//
//    public void sendEmail(String to, String subject, String text) {
//        webClient.post()
//                .uri("/" + domain + "/messages")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .bodyValue("from=" + fromEmail +
//                        "&to=" + to +
//                        "&subject=" + subject +
//                        "&text=" + text)
//                .retrieve()
//                .bodyToMono(String.class)
//                .doOnNext(response -> System.out.println("Mailgun response: " + response))
//                .subscribe();
//    }
//}
