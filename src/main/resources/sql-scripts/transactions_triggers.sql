delimiter $$
create trigger after_renting_car
  before insert
  on transactions for each row
begin
  update vehicles set status = "RESERVED" where id = new.car_id;
end $$
delimiter ;