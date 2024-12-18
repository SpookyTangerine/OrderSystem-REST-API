package de.hs_bremen.mkss.rest_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) 
public class InvalidOrderState extends RuntimeException{
    public InvalidOrderState(String message) {
        super(message);
    }
}
