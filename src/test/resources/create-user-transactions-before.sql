delete from user_transactions;


insert into user_transactions(id, operation_type, amount, user_from_id, user_to_id, transaction_time) values
(1,'TOP_UP_MY',70.00,2,2,'2021-06-29 14:54:33.179000'),
(2,'TOP_UP_SOMEONES',11.00,2,1,'2021-06-29 21:58:32.879000'),
(3,'MONEY_TRANSFER',50.00,2,3,'2021-06-29 14:56:29.688000'),
(4,'WITHDRAW_MONEY',44.00,2,null,'2021-06-29 21:40:05.538000');


alter sequence hibernate_sequence restart with 10;