package de.hs_bremen.mkss.rest_service.interfaceadapters.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hs_bremen.mkss.usecases.ItemInputData;
import de.hs_bremen.mkss.usecases.OrderInteractor;
import de.hs_bremen.mkss.usecases.OrderOutputData;

@RestController
@RequestMapping("/orders") 
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

    @PostMapping
    public ResponseEntity<OrderOutputData> createNewOrder(@RequestBody CreateOrderRequest request) {
        OrderOutputData newOrder = orderInteractor.createNewOrder(request.getCustomerName());
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    
    @GetMapping
    public ResponseEntity<List<OrderOutputData>> getAllOrders() {
        List<OrderOutputData> orders = orderInteractor.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputData> getOrderById(@PathVariable Long id) {
        OrderOutputData order = orderInteractor.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/line-items")
    public ResponseEntity<OrderOutputData> addItemToOrder(
            @PathVariable Long id, @RequestBody ItemInputData itemData) {
        orderInteractor.addItemToOrder(id, itemData.getName(), itemData.getQuantity(), itemData.getPrice());
        return ResponseEntity.ok(orderInteractor.getOrderById(id));
    }

    @DeleteMapping("/{id}/line-items/{itemName}")
    public ResponseEntity<Void> removeItemFromOrder(@PathVariable Long id, @PathVariable String itemName) {
        boolean success = orderInteractor.removeLineItemFromOrder(id, itemName);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean success = orderInteractor.deleteOrder(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/commit")
    public ResponseEntity<OrderOutputData> commitOrder(@PathVariable Long id) {
        orderInteractor.commitOrder(id);
        return ResponseEntity.ok(orderInteractor.getOrderById(id));
    }


    @PatchMapping("/{id}/process")
    public ResponseEntity<OrderOutputData> processOrder(@PathVariable Long id) {
        try {
            orderInteractor.processOrder(id);
            return ResponseEntity.ok(orderInteractor.getOrderById(id));
        } catch (IllegalArgumentException | IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }
        
    }
}



