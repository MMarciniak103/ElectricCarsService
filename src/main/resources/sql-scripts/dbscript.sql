use vozilla;


drop table if exists rented;
drop table if exists vehicles;
drop table if exists clients;

create table vehicles(
    id int auto_increment primary key,
    name varchar(255),
    plates_number varchar(255),
    range_km int,
    battery_lvl_pct int,
    address varchar(255),
    latitude double,
    longitude double,
    status varchar(255),
    insurance_period date,
    battery_distance int,
    damage_description varchar(500)
);



create table clients(
    id int auto_increment primary key,
    phone_number varchar(255)
);


create table rented(
    data date,
    client_id int,
    car_id int,
    completed varchar(255),
    foreign key(client_id) references clients(id),
    foreign key(car_id) references vehicles(id)
);


delete from vehicles;


select * from vehicles;

create table users(
    id int auto_increment primary key,
    login varchar(255),
    pswd varchar(255),
    role enum('ADMIN','NORMAL')
);

insert into users(
    login,
    pswd,
    role
)
values(
    'admin123',
    SHA1('admin123'),
    'ADMIN'
 );

insert into users(
    login,
    pswd,
    role
)
values(
    'user123',
    SHA1('user123'),
    'NORMAL'
);


select * from users;