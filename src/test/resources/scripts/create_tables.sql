create table customer
(
    id bigint not null
        constraint customer_pkey
            primary key,
    first_name varchar(255),
    last_name varchar(255)
);



create table phone_numbers
(
    customer_id bigint not null
        constraint fkavclw27tw16bpskcto5x6j9qn
            references customer,
    active boolean,
    number varchar(255)
);


