package com.mehdi.ticketingSystem.repositories;

import com.mehdi.ticketingSystem.comparator.DeliveryDetailComparator;
import com.mehdi.ticketingSystem.exceptions.AuthException;
import com.mehdi.ticketingSystem.model.*;
import com.mehdi.ticketingSystem.repositories.interfaces.DeliveryDetailsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;

@Repository
public class DeliveryDetailsRepositoryImpl implements DeliveryDetailsRepository {

    private static final String SELECT_ALL = "SELECT " + DeliveryDetails.DELIVERY_ID + ", " + DeliveryDetails.CUSTOMER_TYPE + ", " + DeliveryDetails.DELIVERY_STATUS + ", " + DeliveryDetails.EXPECTED_DELIVERY_TIME + ", " + DeliveryDetails.CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS + ", " + DeliveryDetails.TIME_TO_REACH_DESTINATION + " " +
            "FROM " + DeliveryDetails.TABLE_NAME;
    private static final String SQL_INCOMPLETE_DETAILS = SELECT_ALL + " WHERE " + DeliveryDetails.DELIVERY_STATUS + " IN (" + "'" + DeliveryStatus.ORDER_RECEIVED + "', '" + DeliveryStatus.ORDER_PREPARING + "')";
    private static final String SQL_CREATE = "INSERT INTO " + DeliveryDetails.TABLE_NAME + " (" + DeliveryDetails.DELIVERY_ID + ", "  + DeliveryDetails.CUSTOMER_TYPE + ", " + DeliveryDetails.DELIVERY_STATUS + ", " + DeliveryDetails.EXPECTED_DELIVERY_TIME + ", " + DeliveryDetails.CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS + ", " + DeliveryDetails.TIME_TO_REACH_DESTINATION + ") " +
            "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ALL = "DELETE FROM " + DeliveryDetails.TABLE_NAME;

    private final RowMapper<DeliveryDetails> deliveryDetailsRowMapper = ((rs, rowNum) -> new DeliveryDetails(
            rs.getInt(DeliveryDetails.DELIVERY_ID),
            CustomerType.getByName(rs.getString(DeliveryDetails.CUSTOMER_TYPE)),
            DeliveryStatus.getByName(rs.getString(DeliveryDetails.DELIVERY_STATUS)),
            rs.getTimestamp(DeliveryDetails.EXPECTED_DELIVERY_TIME),
            rs.getInt(DeliveryDetails.CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS),
            rs.getTimestamp(DeliveryDetails.TIME_TO_REACH_DESTINATION)
    ));

    JdbcTemplate jdbcTemplate;
    DeliveryDetailComparator comparator;

    public DeliveryDetailsRepositoryImpl(JdbcTemplate jdbcTemplate, DeliveryDetailComparator comparator) {
        this.jdbcTemplate = jdbcTemplate;
        this.comparator = comparator;
    }

    @Override
    public DeliveryDetailsList getAllUncompletedDeliveryDetails() {
        DeliveryDetailsList deliveryDetailsList = new DeliveryDetailsList(
                jdbcTemplate.query(SQL_INCOMPLETE_DETAILS, deliveryDetailsRowMapper),
                comparator
        );
        deliveryDetailsList.getDeliveryInPriority();
        return deliveryDetailsList;
    }

    @Override
    public boolean create(int deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus, long expectedDeliveryTime, int currentDistanceFromDestinationInMeters, long timeToReachDestination) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, deliveryId);
                ps.setString(2, customerType.toString());
                ps.setString(3, deliveryStatus.toString());
                ps.setTimestamp(4, new Timestamp(expectedDeliveryTime));
                ps.setInt(5, currentDistanceFromDestinationInMeters);
                ps.setTimestamp(6, new Timestamp(timeToReachDestination));
                return ps;
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            if (keys == null)
                throw new Exception("Invalid details. Failed to DeliveryDetails");
            return true;
        } catch (Exception e) {
            throw new AuthException("Invalid details. Failed to DeliveryDetails");
        }
    }

    @Override
    public boolean deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
        return true;
    }

}
