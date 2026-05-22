create table report (
    id            bigint not null auto_increment,
    title         varchar(150) not null,
    type          varchar(50) not null,
    generated_by  bigint not null,
    generated_at  datetime not null,
    date_from     datetime not null,
    date_to       datetime not null,
    primary key (id)
);

insert into report (title, type, generated_by, generated_at, date_from, date_to) values
    ('Reporte de ingresos Mayo 2026',         'INGRESOS',      1, '2026-05-17 12:00:00', '2026-05-01 00:00:00', '2026-05-17 23:59:59'),
    ('Reporte de ocupación semanal',          'OCUPACION',     1, '2026-05-17 12:05:00', '2026-05-11 00:00:00', '2026-05-17 23:59:59'),
    ('Reporte de vehículos por tipo',         'VEHICULOS',     1, '2026-05-17 12:10:00', '2026-05-01 00:00:00', '2026-05-17 23:59:59'),
    ('Reporte de pagos pendientes',           'PAGOS',         1, '2026-05-17 12:15:00', '2026-05-01 00:00:00', '2026-05-17 23:59:59');
