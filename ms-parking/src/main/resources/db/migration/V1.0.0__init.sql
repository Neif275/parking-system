create table floor (
    id int not null auto_increment,
    number int not null,
    description varchar(200),
    primary key (id)
);

create table zone (
    id int not null auto_increment,
    name varchar(50) not null,
    floor_id int not null,
    primary key (id),
    foreign key (floor_id) references floor(id)
);

create table slot_type (
    id int not null auto_increment,
    name varchar(50) not null,
    description varchar(200),
    primary key (id)
);

create table parking_slot (
    id bigint not null auto_increment,
    slot_number varchar(20) not null unique,
    zone_id int not null,
    slot_type_id int not null,
    is_available boolean default true,
    primary key (id),
    foreign key (zone_id) references zone(id),
    foreign key (slot_type_id) references slot_type(id)
);

insert into floor (number, description)
values (1,'Piso 1'),(2,'Piso 2');
insert into slot_type (name)
values ('NORMAL'),('MOTO'),('DISCAPACITADO'),('RESERVADO');
insert into zone (name, floor_id)
values ('A',1),('B',1),('C',2),('D',2);
insert into parking_slot (slot_number, zone_id, slot_type_id)
values
('A-01',1,1),
('A-02',1,1),
('A-03',1,2),
('B-01',2,1),
('C-01',3,3),
('D-01',4,4);
