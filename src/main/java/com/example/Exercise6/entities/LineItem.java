package com.example.Exercise6.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "line_items")
public class LineItem extends Item {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Teraz to tutaj będzie 'id'
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    @Column(name = "unit_price", nullable = false)
    private int unitPrice;



    public LineItem() {
        super();
    }

    public LineItem(String name, int quantity, int unitPrice, Order order) {
        super(name);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.order = order;
    }

    @Override
    public int getPrice() {
        return unitPrice * quantity;
    }

    @Override
    public void print() {
        System.out.println(quantity + " * " + getName() + " at " + unitPrice + " cents each");
    }
}
