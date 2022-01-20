CREATE TABLE IF NOT EXISTS users(
    user_id serial primary key not null,
    username varchar(50) not null,
    password text not null
);

CREATE TABLE IF NOT EXISTS DELIVERY_DETAILS(
    delivery_id serial primary key not null,
    customer_type varchar(20) not null,
    delivery_status varchar(20) not null,
    expected_delivery_time timestamp not null,
    current_distance_from_destination_in_meters int not null,
    time_to_reach_destination timestamp not null
);