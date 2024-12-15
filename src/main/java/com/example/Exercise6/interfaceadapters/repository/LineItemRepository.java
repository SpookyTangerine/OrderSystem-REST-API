package com.example.Exercise6.interfaceadapters.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Exercise6.entities.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
    // Custom queries can be added here if needed
}
