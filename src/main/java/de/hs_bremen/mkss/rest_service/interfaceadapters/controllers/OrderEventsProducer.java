package de.hs_bremen.mkss.rest_service.interfaceadapters.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.hs_bremen.mkss.events.Event;
import de.hs_bremen.mkss.events.EventWithPayload;
import de.hs_bremen.mkss.usecases.OrderOutputData;

@Service
public class OrderEventsProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${my.rabbitmq.exchange}")
    private String exchange;

    @Value("${my.rabbitmq.routing.key}")
    private String routingKey;

    public OrderEventsProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void emitOrderEvent(OrderOutputData order) {
        EventWithPayload<OrderOutputData> event = EventWithPayload.<OrderOutputData>builder()
            .type(Event.EventType.CREATED)
            .payload(order)
            .build();

            rabbitTemplate.convertAndSend(exchange, routingKey, event);
        System.out.println("Sent event: " + event);
    }
}





