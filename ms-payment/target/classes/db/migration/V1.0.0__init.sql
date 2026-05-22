create table payment (
    id               bigint not null auto_increment,
    entry_exit_id    bigint not null,
    amount           decimal(10, 2) not null,
    payment_method   varchar(30) not null,
    payment_time     datetime not null,
    status           varchar(20) not null,
    primary key (id)
);

insert into payment (entry_exit_id, amount, payment_method, payment_time, status) values
    (1, 3700.00, 'EFECTIVO',    '2026-05-17 10:31:00', 'PAGADO'),
    (2, 1500.00, 'TARJETA',     '2026-05-17 11:00:00', 'PENDIENTE');
