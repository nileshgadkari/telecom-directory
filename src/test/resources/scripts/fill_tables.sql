insert into customer (id, first_name, last_name)
values (1, 'Tom', 'C');
insert into customer (id, first_name, last_name)
values (2, 'Elon', 'M');

insert into phone_numbers (customer_id, active, number)
values (1, true, '333-111-8888');
insert into phone_numbers (customer_id, active, number)
values (1, true, '111-222-3333');
insert into phone_numbers (customer_id, active, number)
values (1, false, '100-200-3000');

insert into phone_numbers (customer_id, active, number)
values (2, true, '123-456-7890');
insert into phone_numbers (customer_id, active, number)
values (2, true, '999-888-0000');
