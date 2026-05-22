create table notification (
    id           bigint not null auto_increment,
    user_id      bigint not null,
    message      varchar(500) not null,
    type         varchar(20) not null,
    status       varchar(20) not null,
    created_at   datetime not null,
    sent_at      datetime,
    primary key (id)
);

insert into notification (user_id, message, type, status, created_at, sent_at) values
    (1, 'Su vehiculo RPSP29 ha ingresado al estacionamiento.',   'EMAIL', 'ENVIADA',  '2026-05-17 08:00:00', '2026-05-17 08:00:05'),
    (2, 'Su reserva para el espacio B-01 ha sido confirmada.',   'EMAIL', 'ENVIADA',  '2026-05-17 09:15:00', '2026-05-17 09:15:03'),
    (1, 'Su pago de $3700 ha sido procesado exitosamente.',      'EMAIL', 'ENVIADA',  '2026-05-17 10:31:00', '2026-05-17 10:31:02'),
    (2, 'Su reserva expira en 15 minutos.',                      'EMAIL', 'PENDIENTE','2026-05-18 10:45:00', null);
