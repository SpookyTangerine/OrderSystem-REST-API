package com.example.Exercise6.entities;

import org.springframework.stereotype.Component;


public interface ItemFactory {

    LineItem createItem(String name, int unitPrice, int quantity, Order order);
    

}