package de.hs_bremen.mkss.rest_service.entities;


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

    public Order(){

    }

    public Order(String customerName){
        this.customerName = customerName;
    }
    
    
    public Order(Long id, String customerName, String orderStatus){

        this.id = id;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.orderStatus = orderStatus;
        
    }

    public String getCustomerName() {  
        return customerName;
    }

    public Long getId(){
        return id;
    }

    public void removeLineItemFromOrder(String itemName) {
        if ("COMMITTED".equals(this.orderStatus)) {
            throw new IllegalStateException("Cannot remove items from a committed order.");
        }
        for (LineItem item : items) {
            if (item.getName().equals(itemName)) {
                items.remove(item); 
                break; 
            }
        }
        if (items.isEmpty()) {
            this.orderStatus = "EMPTY";
        }
    }

    //the orders are still not protected, should be changed 
    public List<LineItem> getItems() {
        return items;
    }

    public void addItem( LineItem item){
        if ("COMMITTED".equals(this.orderStatus)) {
            throw new IllegalStateException("Cannot add items to a committed order.");
        }
        items.add(item);
        this.orderStatus = "IN PREPARATION";
    }

    public int getTotalPrice(){
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

    public String getOrderStatus(){
        return orderStatus;
    }

    //  public LocalDateTime getCheckoutDateTime(){
    //      return this.checkoutDateTime;
    //  }


    public void commitOrder() {
        if (!"IN PREPARATION".equals(this.orderStatus)) {
            throw new IllegalStateException("Only orders in IN_PREPARATION can be commited");
            }
        this.orderStatus = "COMMITTED";
    }

    public void processOrder(){
        if (!"COMMITTED".equals(this.orderStatus)){
            throw new IllegalStateException("Only orders in COMMITTED can be processed");
        }
        else{
            Random random = new Random();
            int randomBinary = random.nextInt(2);
            if (randomBinary == 0){
                this.orderStatus = "REJECTED";
            }
            else{
                this.orderStatus = "ACCEPTED";
            }
        }
    }
}




