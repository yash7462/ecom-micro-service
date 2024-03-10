package com.product.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

    @JsonProperty(value = "status")
    private boolean status;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "response")
    private Object response;

}
