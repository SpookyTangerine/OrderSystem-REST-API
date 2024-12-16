package de.hs_bremen.mkss.rest_service.entities.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import de.hs_bremen.mkss.rest_service.entities.Order;

public interface OrderStorageInterface extends JpaRepository<Order, Long>{
    //JpaRepository already provides standard CRUD methods


   /* 
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
    */
    
}


