package com.example.Exercise6.entities.domain;

import java.util.List;

import com.example.Exercise6.entities.Item;
import com.example.Exercise6.entities.LineItem;
import com.example.Exercise6.entities.Order;

public interface OrderStorageInterface {

    List<Order> getAllOrders();                       
    //Order findOrderById(Long id);                     
    //List<LineItem> findByOrderId(Long orderId);
    Order findOrderById(Long orderId);

    List<LineItem> getLineItemsByOrderId(Long orderId); 

    void addOrder(Order order);                       
    void addLineItemToOrder(Long orderId, LineItem item); 
    boolean removeLineItemFromOrder(Long orderId, String itemName);

    void deleteOrderById(Long orderId);                
    void deleteAllOrders(); 
    void updateOrder(Order order);

    
}


