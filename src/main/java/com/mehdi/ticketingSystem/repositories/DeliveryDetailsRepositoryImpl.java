package com.mehdi.ticketingSystem.repositories;

import com.mehdi.ticketingSystem.comparator.DeliveryDetailComparator;
import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryDetails;
import com.mehdi.ticketingSystem.model.DeliveryStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeliveryDetailsRepositoryImpl implements DeliveryDetailsRepository {

    public static final String SELECT_ALL = "SELECT " + DeliveryDetails.DELIVERY_ID + ", " + DeliveryDetails.CUSTOMER_TYPE + ", " + DeliveryDetails.DELIVERY_STATUS + ", " + DeliveryDetails.EXPECTED_DELIVERY_TIME + ", " + DeliveryDetails.CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS + ", " + DeliveryDetails.TIME_TO_REACH_DESTINATION + " " +
            "FROM " + DeliveryDetails.TABLE_NAME;
    public static final String SQL_INCOMPLETE_DETAILS = SELECT_ALL + " WHERE " + DeliveryDetails.DELIVERY_STATUS + " IN (" + "'" + DeliveryStatus.ORDER_PICKED + "', '" + DeliveryStatus.ORDER_PREPARING + "'')";

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
    public List<DeliveryDetails> getAllUncompletedDeliveryDetails() {
        List<DeliveryDetails> deliveryDetailsList = jdbcTemplate.query(SQL_INCOMPLETE_DETAILS, deliveryDetailsRowMapper);
        deliveryDetailsList.sort(comparator);
        return deliveryDetailsList;
    }

}
