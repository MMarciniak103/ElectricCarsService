create database vozilla;
use vozilla;



drop table if exists charging_cars;
drop table if exists transactions;
drop table if exists vehicles;
drop table if exists profits;
drop  table if exists  users;
#--------------------------------TABLES---------------------------------------------------

create table vehicles(
    id int auto_increment primary key,
    name varchar(255),
    plates_number varchar(255),
    range_km int,
    battery_lvl_pct int,
    address varchar(1000),
    latitude double,
    longitude double,
    status varchar(255),
    insurance_period date,
    battery_distance int,
    damage_description varchar(1000)
);



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

create table transactions(
                             id int auto_increment primary key,
                             data date,
                             client_id int,
                             car_id int,
                             completed varchar(255),
                             foreign key(client_id) references users(id),
                             foreign key(car_id) references vehicles(id)
);




create table profits(
                        id int auto_increment primary key,
                        data date,
                        profit double
);


create table charging_cars(
                              id int auto_increment primary key,
                              car_id int,
                              foreign key(car_id) references vehicles(id)
);




#--------------------------------TRIGGERS---------------------------------------------------


delimiter $$
drop trigger if exists after_renting_car;
drop trigger if exists before_user_insert;
drop trigger if exists before_car_update;
delimiter ;

delimiter $$
create trigger after_renting_car
  before insert
  on transactions for each row
begin
  update vehicles set status = "RESERVED" where id = new.car_id;
end $$
delimiter ;




delimiter $$
create trigger before_user_insert
    before insert
    on users for each row
begin
    set new.pswd = (select SHA1(new.pswd));
end $$
delimiter ;



delimiter $$
create trigger before_car_update
  before update
  on vehicles for each row
begin
  declare traveled_dist int;
  set traveled_dist = new.range_km - old.range_km;
  if traveled_dist > 0.9 * old.battery_distance or new.battery_lvl_pct <= 10.0  then
    insert into charging_cars (car_id) values(old.id);
    set new.status = "IS BEING CHARGED";
  end if;
  if traveled_dist > 0 then
    insert into profits (data,profit) values(CURDATE(),traveled_dist*2.50);
  end if;
  if new.battery_lvl_pct = 100 then
    delete from charging_cars where car_id = old.id;
    set new.status = 'AVAILABLE';
  end if;
end $$
delimiter ;
