package com.example.Exercise6.entities;

import org.springframework.stereotype.Component;

@Component
public class SimpleItemFactory implements ItemFactory {

    @Override
    public LineItem createItem(String name, int unitPrice, int quantity, Order order){
        return new LineItem(name, quantity, unitPrice, order);  // 4 parameters constructor
    }
    

}