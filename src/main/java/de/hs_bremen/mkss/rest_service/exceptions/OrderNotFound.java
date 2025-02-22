package de.hs_bremen.mkss.rest_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.NOT_FOUND)
public class OrderNotFound extends RuntimeException {
    public OrderNotFound(Long id) {
        super("Order with ID " + id + " not found.");
    }
}
