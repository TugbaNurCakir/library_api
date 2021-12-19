package com.example.demo.Exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.xml.ws.http.HTTPException;

@Data
public class ApiException extends RuntimeException{
    private HttpStatus httpStatus;

    public ApiException(String message, HttpStatus status) {
        super(message);
        httpStatus = status;
    }
}
