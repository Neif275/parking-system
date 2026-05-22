create table brand (
    id int not null auto_increment,
    name varchar(100) not null,
    primary key (id)
);

create table vehicle_model (
    id int not null auto_increment,
    name varchar(100) not null,
    brand_id int not null,
    primary key (id),
    foreign key (brand_id) references brand(id)
);

create table vehicle_category (
    id int not null auto_increment,
    name varchar(50) not null,
    description varchar(200),
    primary key (id)
);

create table vehicle (
    id bigint not null auto_increment,
    plate varchar(20) not null unique,
    color varchar(50),
    year varchar(10),
    model_id int not null,
    category_id int not null,
    owner_user_id bigint,
    primary key (id),
    foreign key (model_id) references vehicle_model(id),
    foreign key (category_id) references vehicle_category(id)
);

insert into brand (name) values
    ('Toyota'), ('Chevrolet'), ('Renault'), ('Mazda'), ('Ford'), ('Honda'), ('Nissan'), ('Volkswagen'), ('Hyundai'), ('Kia'),
    ('Peugeot'), ('Fiat'), ('Subaru'), ('Mitsubishi'), ('Suzuki'), ('Dodge'), ('Jeep'), ('Ram'), ('GMC'), ('Cadillac'),
    ('Yamaha'), ('Harley-Davidson'), ('Ducati'), ('Kawasaki'), ('BMW Motorrad'), ('Triumph'), ('Aprilia'), ('Vespa'),
    ('Piaggio'), ('Royal Enfield'), ('Bajaj'), ('Hero MotoCorp'), ('TVS Motor Company'), ('KTM'), ('Husqvarna Motorcycles'),
    ('Moto Guzzi'), ('Indian Motorcycle'), ('Victory Motorcycles');

insert into vehicle_category (name, description) values
    ('AUTO', 'Auto'),
    ('MOTO', 'Motocicleta'),
    ('CAMIONETA', 'Camioneta');

insert into vehicle_model (name, brand_id) values
    ('Corolla', 1), ('Corsa', 2), ('Cleo', 3), ('Mazda3', 4), ('Focus', 5),
    ('Civic', 6), ('Sentra', 7), ('Golf', 8), ('Elantra', 9), ('Sportage', 10),
    ('208', 11), ('Punto', 12), ('Impreza', 13), ('Lancer', 14), ('Swift', 15),
    ('Charger', 16), ('Wrangler', 17), ('1500', 18), ('Sierra', 19), ('Escalade', 20),
    ('YZF-R3', 21), ('Street Glide', 22), ('Panigale V4', 23), ('Ninja ZX-6R', 24), ('R1200GS', 25),
    ('Bonneville T120', 26), ('RSV4 RF', 27), ('GTS SuperTech 300 HPE', 28), ('Vespa GTS SuperTech 300 HPE', 29),
    ('Classic 350', 30), ('Pulsar NS200', 31), ('Splendor Plus', 32), ('Apache RTR 160', 33),
    ('KTM Duke 390', 34), ('Husqvarna Svartpilen 401', 35), ('V85 TT Travel Edition', 36),
    ('Chief Dark Horse', 37), ('XR 250', 6), ('XR 400', 6), ('XR 650L', 6),
    ('KLR 250', 24), ('KLR 650', 24);
