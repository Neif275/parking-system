create table reservation (
    id                  bigint not null auto_increment,
    plate               varchar(20) not null,
    owner_user_id       bigint not null,
    parking_space_id    bigint not null,
    reservation_time    datetime not null,
    expiration_time     datetime not null,
    status              varchar(20) not null,
    primary key (id)
);

insert into reservation (plate, owner_user_id, parking_space_id, reservation_time, expiration_time, status) values
    ('RPSP29', 1, 1, '2026-05-18 08:00:00', '2026-05-18 09:00:00', 'ACTIVA'),
    ('SFKD95', 2, 3, '2026-05-18 10:00:00', '2026-05-18 11:00:00', 'CANCELADA');
