package de.hs_bremen.mkss.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import de.hs_bremen.mkss.rest_service.entities.ItemFactory;
import de.hs_bremen.mkss.rest_service.entities.LineItem;
import de.hs_bremen.mkss.rest_service.entities.Order;
import de.hs_bremen.mkss.rest_service.entities.domain.OrderStorageInterface;
import de.hs_bremen.mkss.rest_service.exceptions.InvalidOrderState;
import de.hs_bremen.mkss.rest_service.exceptions.OrderNotFound;

@Service
public class OrderInteractor {

    private final OrderStorageInterface orderRepository;
    private final ItemFactory itemFactory;

    public OrderInteractor(OrderStorageInterface orderRepository, ItemFactory itemFactory) {
        this.orderRepository = orderRepository;
        this.itemFactory = itemFactory;
    }


    public OrderOutputData createNewOrder(String customerName) {
        Order newOrder = new Order(customerName); 
        orderRepository.save(newOrder);      
        return new OrderOutputData(newOrder.getId(), newOrder.getCustomerName());
    }
    

    public List<OrderOutputData> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderOutputData) 
                .toList();
    }


    public OrderOutputData getOrderById(Long id) {
        return orderRepository.findById(id).map(this::mapToOrderOutputData).orElseThrow(() ->
        new IllegalArgumentException("Order not found"));
    }

    public void addItemToOrder(Long orderId, String name, int quantity, int unitPrice) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() ->
            new IllegalArgumentException("Order not found"));

        LineItem item = itemFactory.createItem(name, unitPrice, quantity, order);
        order.addItem(item);
        orderRepository.save(order);
    
    }
    public List<LineItem> getLineItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> 
            new IllegalArgumentException("Order not found"));

        return order.getItems();
    }
    

        public boolean removeLineItemFromOrder(Long orderId, String itemName) {
            return orderRepository.findById(orderId).map(order -> {
                order.removeLineItemFromOrder(itemName);
                orderRepository.save(order);
                return true;
            }).orElse(false);
        }
        
        public boolean deleteOrder(Long id) {
            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);
                return true;
            }
            return false;
        }

    private OrderOutputData mapToOrderOutputData(Order order) {
        return new OrderOutputData(
                order.getId(),
                order.getCustomerName(),
                order.getItems(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getCheckoutDate()
        );
    }

    public void commitOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFound(orderId));
    
        if ("EMPTY".equals(order.getOrderStatus())) {
            throw new InvalidOrderState("Cannot commit an empty order.");
        }
        if ("COMMITTED".equals(order.getOrderStatus())) {
            throw new InvalidOrderState("Order has already been committed.");
        }
    
        order.commitOrder(); // Set status to COMMITTED and checkout date
        orderRepository.save(order); // Persist changes
    }

    public void processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound(orderId));
        if ("ACCEPTED".equals(order.getOrderStatus())) {
            throw new InvalidOrderState("Can purchase only committed orders.");
        }

        order.processOrder();
        orderRepository.save(order);
    }
}


