package com.prasanta.parking;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class MGSamples {

    public static JsonNode sendSimpleMessage() throws UnirestException {
        String apiKey = System.getenv("API_KEY");
        if (apiKey == null) {
            apiKey = "998b99aa350351ee213cb7917cd42fa4-a1dad75f-d83f9001";
        }

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/sandbox457389b3505c495dbf752fbe8c2245be.mailgun.org/messages")
                .basicAuth("api", apiKey)
                .field("from", "Mailgun Sandbox <postmaster@sandbox457389b3505c495dbf752fbe8c2245be.mailgun.org>")
                .field("to", "PRASANTA KUMAR PARIDA <prasantaparida2@gmail.com>")
                .field("subject", "Hello PRASANTA KUMAR PARIDA")
                .field("text", "Congratulations PRASANTA KUMAR PARIDA, you just sent an email with Mailgun using Unirest 3!")
                .asJson();

        return response.getBody();
    }

    public static void main(String[] args) {
        try {
            JsonNode response = sendSimpleMessage();
            System.out.println("Mailgun response: " + response.toPrettyString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}