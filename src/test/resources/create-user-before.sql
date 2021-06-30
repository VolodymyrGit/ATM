delete from user_roles;
delete from user_transactions;
delete from usr;

insert into usr(id, balance, card_number, pin_code) values
(1, 111.00, '1111222233334444', '$2a$08$koOesTTzSl/xPrWm3e7YSupZgNPhyH10JFOlF.XeIP7Fgf1AOLVni'),
(2, 222.00, '2222333344445555', '$2a$08$qqkATOVYJsXBbGa.eQsNvujP.HGVaB5SQW9Nic3W1TFo9de2DFtZO'),
(3, 333.00, '3333444455556666', '$2a$08$rrQgjNr16Tp1otYlDQJGXuybpHO1ceJQcd6m7YDyEJqS3ZKEytrn.');

insert into user_roles(user_id, roles) values
(1, 'BANK_USER'),
(2, 'BANK_USER'),
(3, 'BANK_USER');

alter sequence hibernate_sequence restart with 10;