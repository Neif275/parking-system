create table tariff (
    id               bigint not null auto_increment,
    name             varchar(100) not null,
    description      varchar(200),
    price_per_minute decimal(10, 2) not null,
    vehicle_type     varchar(20) not null,
    primary key (id)
);

insert into tariff (name, description, price_per_minute, vehicle_type) values
    ('Tarifa Auto Estándar',      'Tarifa por minuto para autos',      37.00, 'AUTO'),
    ('Tarifa Moto Estándar',      'Tarifa por minuto para motos',      37.00, 'MOTO'),
    ('Tarifa Camioneta Estándar', 'Tarifa por minuto para camionetas', 37.00, 'CAMIONETA'),
    ('Tarifa Auto Nocturna',      'Tarifa nocturna para autos',        50.00, 'AUTO'),
    ('Tarifa Moto Nocturna',      'Tarifa nocturna para motos',        40.00, 'MOTO'),
    ('Tarifa Camioneta Nocturna', 'Tarifa nocturna para camionetas',   50.00, 'CAMIONETA');
