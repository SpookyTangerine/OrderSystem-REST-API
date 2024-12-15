package com.example.Exercise6.interfaceadapters.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usecases.ItemInputData;
import com.example.usecases.OrderInteractor;
import com.example.usecases.OrderOutputData;

@RestController
@RequestMapping("/orders") // Wszystkie endpointy zaczynają się od /orders
public class OrderController {

    private final OrderInteractor orderInteractor;

    public OrderController(OrderInteractor orderInteractor) {
        this.orderInteractor = orderInteractor;
    }
    public static class CreateOrderRequest {
        private String customerName;
    
        public String getCustomerName() {
            return customerName;
        }
    
        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
    }
    // // Tworzenie nowego zamówienia
    // @PostMapping
    // public ResponseEntity<OrderOutputData> createNewOrder(@RequestBody CreateOrderRequest request) {
    //     OrderOutputData newOrder = orderInteractor.createNewOrder(request.getCustomerName());
    //     return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    // }

    @PostMapping
    public ResponseEntity<OrderOutputData> createNewOrder(@RequestBody CreateOrderRequest request) {
    // Dodaj logowanie
    System.out.println("Received request: " + request.getCustomerName());
    
    OrderOutputData newOrder = orderInteractor.createNewOrder(request.getCustomerName());
    return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
}



    // Pobranie wszystkich zamówień
    @GetMapping
    public ResponseEntity<List<OrderOutputData>> getAllOrders() {
        List<OrderOutputData> orders = orderInteractor.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Pobranie zamówienia po ID
    public ResponseEntity<OrderOutputData> getOrderById(@PathVariable Long id) {
        OrderOutputData order = orderInteractor.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Dodanie przedmiotu do zamówienia
    @PostMapping("/{id}/line-items")
    public ResponseEntity<OrderOutputData> addItemToOrder(
            @PathVariable Long id, @RequestBody ItemInputData itemData) {
        orderInteractor.addItemToOrder(id, itemData.getName(), itemData.getQuantity(), itemData.getPrice());
        return ResponseEntity.ok(orderInteractor.getOrderById(id));
    }


    // Usunięcie przedmiotu z zamówienia
    @DeleteMapping("/{id}/line-items/{itemName}")
    public ResponseEntity<Void> removeItemFromOrder(@PathVariable Long id, @PathVariable String itemName) {
        boolean success = orderInteractor.removeLineItemFromOrder(id, itemName);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Usunięcie zamówienia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean success = orderInteractor.deleteOrder(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



