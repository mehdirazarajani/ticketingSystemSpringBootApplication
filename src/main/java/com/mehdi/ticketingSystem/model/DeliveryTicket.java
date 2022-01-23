package com.mehdi.ticketingSystem.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "delivery_ticket")
@Entity
@Data
@Accessors(chain = true)
public class DeliveryTicket {

    public static final String TABLE_NAME = "delivery_ticket";
    public static final String TICKET_ID = "ticket_id";
    public static final String DELIVERY_ID = "delivery_id";

    @Id
    @Column(nullable = false)
    int ticketId;

    @Column(nullable = false)
    private int deliveryId;

    @OneToOne(mappedBy = "delivery_detail")
    private DeliveryDetails deliveryDetails;

    public DeliveryTicket(int ticketId, int deliveryId) {
        this.ticketId = ticketId;
        this.deliveryId = deliveryId;
    }
}
