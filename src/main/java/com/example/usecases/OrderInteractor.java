package com.example.usecases;



import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Exercise6.entities.ItemFactory;
import com.example.Exercise6.entities.LineItem;
import com.example.Exercise6.entities.Order;
import com.example.Exercise6.entities.domain.OrderStorageInterface;

@Service
public class OrderInteractor {

    private final OrderStorageInterface orderRepository;
    //private int idCounter = 1;
    private final ItemFactory itemFactory;

    public OrderInteractor(OrderStorageInterface orderRepository, ItemFactory itemFactory) {
        this.orderRepository = orderRepository;
        this.itemFactory = itemFactory;
    }


    public OrderOutputData createNewOrder(String customerName) {
        Order newOrder = new Order(customerName); // Tworzenie zamówienia
        orderRepository.addOrder(newOrder);       // Zapisanie zamówienia do bazy danych
        return new OrderOutputData(newOrder.getId(), newOrder.getCustomerName());
    }
    

    public List<OrderOutputData> getAllOrders() {
        return orderRepository.getAllOrders().stream()
                .map(this::mapToOrderOutputData) // Konwertujemy Order na OrderOutputData
                .toList();
    }


    public OrderOutputData getOrderById(Long id) {
        Order order = orderRepository.findOrderById(id);
        return (order != null) ? mapToOrderOutputData(order) : null;
    }

    public void addItemToOrder(Long orderId, String name, int quantity, int unitPrice) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        LineItem item = itemFactory.createItem(name, unitPrice, quantity, order);
        order.addItem(item);
        orderRepository.updateOrder(order);
    
    }
    public List<LineItem> getLineItemsByOrderId(Long orderId) {
        return orderRepository.getLineItemsByOrderId(orderId);
    }
    

        public boolean removeLineItemFromOrder(Long orderId, String itemName) {
            Order order = orderRepository.findOrderById(orderId);
            if (order != null) {
                order.removeLineItemFromOrder(itemName);
                orderRepository.updateOrder(order);
                return true;
            }
            return false;
        }
        
        public boolean deleteOrder(Long id) {
            Order order = orderRepository.findOrderById(id);
            if (order != null) {
                orderRepository.deleteOrderById(id);
                return true;
            }
            return false;
        }

    private OrderOutputData mapToOrderOutputData(Order order) {
        return new OrderOutputData(
                order.getId(),
                order.getCustomerName(),
                order.getItems(),
                order.getTotalPrice()
                //order.getCheckoutDateTime()
        );
    }
}
