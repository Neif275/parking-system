create table entry_exit (
    id                bigint not null auto_increment,
    plate             varchar(20) not null,
    entry_time        datetime not null,
    exit_time         datetime,
    parking_space_id  bigint not null,
    tariff_id         bigint not null,
    status            varchar(20) not null,
    primary key (id)
);

insert into entry_exit (plate, entry_time, exit_time, parking_space_id, tariff_id, status) values
    ('RPSP29', '2026-05-17 08:00:00', '2026-05-17 10:30:00', 1, 1, 'COMPLETADO'),
    ('SFKD95', '2026-05-17 09:15:00', null,                  2, 2, 'ACTIVO');
