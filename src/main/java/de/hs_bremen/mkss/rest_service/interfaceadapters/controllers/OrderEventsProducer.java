package de.hs_bremen.mkss.rest_service.interfaceadapters.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.hs_bremen.mkss.rest_service.entities.Order;
import de.hs_bremen.mkss.events.Event;
import de.hs_bremen.mkss.events.EventWithPayload;

@Component
public class OrderEventsProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${my.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${my.rabbitmq.routing.key}")
    private String routingKey;

    public OrderEventsProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void emitOrderEvent(Order order) {
        EventWithPayload<Order> event = EventWithPayload.<Order>builder()
            .type(Event.EventType.CREATED)
            .payload(order)
            .build();

        amqpTemplate.convertAndSend(exchangeName, routingKey, event);
        System.out.println("Sent event: " + event);
    }
}