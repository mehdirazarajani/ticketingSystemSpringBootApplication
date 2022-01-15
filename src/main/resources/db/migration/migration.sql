--drop database ticketingSystem;
--drop user ticketingSystemUser;
--
--create user ti1cketingSystemUser with password 'password';
--create database ticketingSystem with owner=ticketingSystemUser;
\connect ticketingSystem;

--alter default privileges grant all on tables to ticketingSystemUser;
--alter default privileges grant all on sequences to ticketingSystemUser;

create table users(
    user_id serial primary key not null,
    username varchar(50) not null,
    password text not null
);

create table DELIVERY_DETAILS(
    delivery_id serial primary key not null,
    customer_type varchar(20) not null,
    delivery_status varchar(20) not null,
    expected_delivery_time timestamp not null,
    current_distance_from_destination_in_meters int not null,
    time_to_reach_destination timestamp not null
);