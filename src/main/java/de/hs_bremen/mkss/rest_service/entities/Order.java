package de.hs_bremen.mkss.rest_service.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    //private LocalDateTime checkoutDateTime;  //<-zakomentowalam wszystko z checkouttime

    public Order(){

    }

    public Order(String customerName){
        this.customerName = customerName;
    }
    
    
    public Order(Long id, String customerName){

        this.id = id;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        
    }

    public String getCustomerName() {  
        return customerName;
    }

    public Long getId(){
        return id;
    }

    public void removeLineItemFromOrder(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)) {
                items.remove(i);
                break; 
            }
        }
    }

    //the orders are still not protected, should be changed 
    public List<LineItem> getItems() {
        return items;
    }

    public void addItem( LineItem item){
        items.add(item);
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
        //checkoutDateTime = null;
    }

    //  public LocalDateTime getCheckoutDateTime(){
    //      return this.checkoutDateTime;
    //  }

    public void finishOrder(){
        //this.checkoutDateTime = LocalDateTime.now();
    }


}

