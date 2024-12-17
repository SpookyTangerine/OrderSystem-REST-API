package de.hs_bremen.mkss.rest_service.entities.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import de.hs_bremen.mkss.rest_service.entities.Order;

public interface OrderStorageInterface extends JpaRepository<Order, Long>{

    
}


