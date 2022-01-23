package com.mehdi.ticketingSystem.repositories;

import com.mehdi.ticketingSystem.model.DeliveryDetails;
import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTicket;
import com.mehdi.ticketingSystem.repositories.interfaces.DeliveryTicketRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeliveryTicketRepositoryImpl implements DeliveryTicketRepository {

    public static final String SELECT_ALL = "SELECT " + DeliveryTicket.TICKET_ID + ", " + DeliveryTicket.DELIVERY_ID + " FROM " + DeliveryTicket.TABLE_NAME;
    private static final String DELETE_ALL = "DELETE FROM " + DeliveryTicket.TABLE_NAME;
    private static final String SQL_CREATE = "INSERT INTO " + DeliveryTicket.TABLE_NAME + " (" + DeliveryTicket.TICKET_ID + ", " + DeliveryTicket.DELIVERY_ID + ") " +
            "VALUES(?, ?)";

    JdbcTemplate jdbcTemplate;

    public DeliveryTicketRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public void insertAll(DeliveryDetailsList deliveryDetailsList) {
        int id = 0;
        for (var deliveryDetail : deliveryDetailsList) {
            singleInsert(id++, deliveryDetail);
        }
    }

    @Override
    public List<DeliveryTicket> getAll() {
        var list = jdbcTemplate.queryForList(SELECT_ALL);
        List<DeliveryTicket> tickets = new ArrayList<>();
        for (var element : list) {
            tickets.add(
                    new DeliveryTicket(
                            (int) element.getOrDefault(DeliveryTicket.TICKET_ID, 0),
                            (int) element.getOrDefault(DeliveryTicket.DELIVERY_ID, 0)
                    )
            );
        }
        return tickets;
    }

    private void singleInsert(int ticketId, DeliveryDetails details) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticketId);
            ps.setInt(2, details.getDeliveryId());
            return ps;
        }, keyHolder);
    }
}
