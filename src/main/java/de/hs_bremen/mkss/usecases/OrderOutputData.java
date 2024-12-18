package de.hs_bremen.mkss.usecases;

import java.time.LocalDateTime;
import java.util.List;

import de.hs_bremen.mkss.rest_service.entities.LineItem;

public class OrderOutputData {
    private Long orderId;
    private List<LineItem> items;
    private int totalPrice;
    private LocalDateTime checkoutDate;
    private String customerName;
    private String orderstatus;


    // additional constructor that omits checkoutDateTime to represent the situation where the order is being updated with new items
    public OrderOutputData(Long orderId, String customerName, List<LineItem> items, int totalPrice, String orderstatus, LocalDateTime checkoutDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
        this.totalPrice = totalPrice;
        this.orderstatus = orderstatus;
        this.checkoutDate = checkoutDate;
    }

    public OrderOutputData(Long orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;

    }

    public LocalDateTime getCheckoutDate() {
       return checkoutDate;
    }

    public Long getId() {  
        return orderId;
    }

    public String getCustomerName() {  
        return customerName;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public String getOrderStatus(){
        return orderstatus;
    }
}

