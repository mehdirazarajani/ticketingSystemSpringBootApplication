CREATE TABLE IF NOT EXISTS USERS(
    user_id serial primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email text not null,
    password text not null
);

CREATE TABLE IF NOT EXISTS DELIVERY_DETAILS(
    delivery_id serial primary key,
    customer_type varchar(20) not null,
    delivery_status varchar(20) not null,
    expected_delivery_time timestamp not null,
    current_distance_from_destination_in_meters int not null,
    time_to_reach_destination timestamp not null
);

CREATE TABLE IF NOT EXISTS DELIVERY_TICKET(
    ticket_id int primary key,
    delivery_id int not null,

   CONSTRAINT fk_delivery_id_in_delivery_ticket
      FOREIGN KEY (delivery_id)
	  REFERENCES DELIVERY_DETAILS(delivery_id)
	  ON DELETE CASCADE
);