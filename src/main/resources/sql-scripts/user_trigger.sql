use vozilla;

delimiter $$
create trigger before_user_insert
before insert
on users for each row
begin
    set new.pswd = (select SHA1(new.pswd));
end $$
delimiter ;
