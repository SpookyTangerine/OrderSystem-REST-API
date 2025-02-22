package de.hs_bremen.mkss.rest_service.entities;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass 
public abstract class Item {

    private String name;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getPrice();
    public abstract void print();
}

