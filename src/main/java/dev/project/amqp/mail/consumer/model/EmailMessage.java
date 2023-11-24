package dev.project.amqp.mail.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmailMessage(
    @JsonProperty("firstname")
    String firstname,
    @JsonProperty("lastname")
    String lastname,
    @JsonProperty("email")
    String email,
    @JsonProperty("jwt")
    String jwt

) {

}
