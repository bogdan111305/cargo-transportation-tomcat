package com.example.cargo_transportation.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ResponseError {
    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ResponseError(String message, String debugMessage){
        this.message=message;
        this.debugMessage=debugMessage;
    }

    public ResponseError(String message, String debugMessage, List errors){
        this.message=message;
        this.debugMessage=debugMessage;
        this.errors = errors;
    }
}

