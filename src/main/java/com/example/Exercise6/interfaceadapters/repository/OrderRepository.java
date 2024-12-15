package com.example.Exercise6.interfaceadapters.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Exercise6.entities.LineItem;
import com.example.Exercise6.entities.Order;
import com.example.Exercise6.entities.domain.OrderStorageInterface;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderStorageInterface {

    @Override
    default List<Order> getAllOrders() {
        return findAll();
    }

    @Override
    default Order findOrderById(Long id) {
        return findById(id).orElse(null);
    }

    

    @Override
    default void addLineItemToOrder(Long orderId, LineItem item) {
        Order order = findOrderById(orderId);
        if (order != null) {
            order.addItem(item);
            save(order);
        }
    }
    @Query("SELECT li FROM LineItem li WHERE li.order.id = :orderId")
    List<LineItem> findLineItemsByOrderId(@Param("orderId") Long orderId);

    @Override
    default List<LineItem> getLineItemsByOrderId(Long orderId) {
        return findLineItemsByOrderId(orderId);
    }

    @Override
    default boolean removeLineItemFromOrder(Long orderId, String itemName) {
        Order order = findOrderById(orderId);
        if (order != null) {
            order.removeLineItemFromOrder(itemName);
            save(order);
            return true;
        }
        return false;
    }

    @Override
    default void addOrder(Order order) {
        save(order);
    }

    @Override
    default void deleteOrderById(Long orderId) {
        deleteById(orderId);
    }

    @Override
    default void deleteAllOrders() {
        deleteAll();
    }

    @Override
    default void updateOrder(Order order) {
        save(order);
    }
}
