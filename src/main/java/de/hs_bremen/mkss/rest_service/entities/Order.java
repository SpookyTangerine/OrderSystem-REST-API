package de.hs_bremen.mkss.rest_service.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders_table")
public class Order {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineItem> items = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "order_status", nullable = false)
    private String orderStatus = "EMPTY";

    @Column(name = "checkout_date")
    private LocalDateTime checkoutDate;

    public Order(){

    }

    public Order(String customerName){
        this.customerName = customerName;
    }

    private Order(Long id, String customerName, String orderStatus, LocalDateTime checkoutDate, List<LineItem> items) {
        this.id = id;
        this.customerName = customerName;
        this.orderStatus = orderStatus;
        this.checkoutDate = checkoutDate;
        if (items != null) {
            this.items = items;
        }
    }


    public static class OrderBuilder {
        private Long id;
        private String customerName;
        private String orderStatus = "EMPTY";
        private LocalDateTime checkoutDate;
        private List<LineItem> items = new ArrayList<>();

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public OrderBuilder orderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderBuilder checkoutDate(LocalDateTime checkoutDate) {
            this.checkoutDate = checkoutDate;
            return this;
        }

        public OrderBuilder items(List<LineItem> items) {
            this.items = items;
            return this;
        }

        public Order build() {
            return new Order(id, customerName, orderStatus, checkoutDate, items);
        }
    }


    public static OrderBuilder builder() {
        return new OrderBuilder();
    }


    public String getCustomerName() {  
        return customerName;
    }

    public Long getId() {
        return id;
    }

    // The orders are still not protected, should be changed 
    public List<LineItem> getItems() {
        return items;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }




    public void removeLineItemFromOrder(String itemName) {
        if ("COMMITTED".equals(this.orderStatus)) {
            throw new IllegalStateException("Cannot remove items from a committed order.");
        }
        for (LineItem item : items) {
            if (item.getName().equals(itemName)) {
                items.remove(item); 
                if (items.isEmpty()) {
                    this.orderStatus = "EMPTY";
                }
                break; 
            }
        }
    }


    public void addItem(LineItem item) {
        if ("COMMITTED".equals(this.orderStatus)) {
            throw new IllegalStateException("Cannot add items to a committed order.");
        }
        items.add(item);
        this.orderStatus = "IN PREPARATION";
    }


    public int getTotalPrice() {
        int total = 0;
        for (LineItem item : items) {
            total += item.getPrice();
        }
        return total;
    }


    public void clear() {
        items.clear();
        this.orderStatus = "EMPTY";
        //checkoutDateTime = null;
    }


    public void commitOrder() {
        if (!"IN PREPARATION".equals(this.orderStatus)) {
            throw new IllegalStateException("Only orders in IN PREPARATION status can be committed.");
        }
        this.orderStatus = "COMMITTED";
        this.checkoutDate = LocalDateTime.now();
    }

    // Processes the order with random acceptance/rejection
    public void processOrder() {
        if (!"COMMITTED".equals(this.orderStatus)) {
            throw new IllegalStateException("Only orders in COMMITTED status can be processed.");
        } else {
            Random random = new Random();
            int randomBinary = random.nextInt(2);
            if (randomBinary == 0) {
                this.orderStatus = "REJECTED";
            } else {
                this.orderStatus = "ACCEPTED";
            }
        }
    }
}
