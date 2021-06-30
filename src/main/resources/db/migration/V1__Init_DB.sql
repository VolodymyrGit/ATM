create sequence hibernate_sequence start 1 increment 1;

create table usr
(
    id          int8         not null primary key,
    card_number varchar(16)  not null unique,
    pin_code    varchar(255) not null,
    balance     numeric(19, 2)
);

create table user_roles
(
    user_id int8 not null,
    roles   varchar(255)
);

create table user_transactions
(
    id               int8 not null primary key,
    operation_type   varchar(255),
    amount           numeric(19, 2),
    user_from_id     int8,
    user_to_id       int8,
    transaction_time timestamp
);

alter table user_roles
    add constraint FK_role_user_id
    foreign key (user_id) references usr;

alter table user_transactions
    add constraint FK_transaction_user_from_id
    foreign key (user_from_id) references usr;

alter table user_transactions
    add constraint FK_transaction_user_to_id
    foreign key (user_to_id) references usr;