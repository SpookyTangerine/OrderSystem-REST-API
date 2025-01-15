package de.hs_bremen.mkss.rest_service.interfaceadapters.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.hs_bremen.mkss.rest_service.entities.Order;
import de.hs_bremen.mkss.usecases.OrderOutputData;
import de.hs_bremen.mkss.events.Event;
import de.hs_bremen.mkss.events.EventWithPayload;
import de.hs_bremen.mkss.usecases.OrderOutputData;

@Component
public class OrderEventsProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${my.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${my.rabbitmq.routing.key}")
    private String routingKey;

    public OrderEventsProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
        System.out.println("HIHIHIHIHI");
    }

    public void emitOrderEvent(OrderOutputData order) {
        EventWithPayload<OrderOutputData> event = EventWithPayload.<OrderOutputData>builder()
            .type(Event.EventType.CREATED)
            .payload(orderOutputData)
            .build();
    
        amqpTemplate.convertAndSend(exchangeName, routingKey, event);
        System.out.println("Sent event: " + event);
    }

}