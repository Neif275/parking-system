create table user_profile(
    id bigint auto_increment primary key,
    username varchar (100) not null unique,
    email varchar(200) not null unique,
    full_name varchar(200) not null unique,
    phone varchar(15) not null unique,
    role varchar(8) not null
);

insert into user_profile (username, email, full_name, phone, role) values
('Neif', 'dn.reyes@duocuc.com', 'Diego_Reyes', '+56998652028', 'ADMIN'),
('CristoMc', 'cristo@live.cl', 'Cristobal_Figueroa', '+56951158666','OPERADOR'),
('Claudiapup', 'claudiar@gmail.com', 'Claudia_Retamal', '+56912345678','CLIENTE')