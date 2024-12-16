package de.hs_bremen.mkss.rest_service.entities;

public interface ItemFactory {

    LineItem createItem(String name, int unitPrice, int quantity, Order order);
    

}