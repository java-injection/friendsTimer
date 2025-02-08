drop database if exists timer;
create database if not exists timer;
use timer;

create table if not exists user(
    id int primary key auto_increment,
    name varchar(50) not null unique,
    password varchar(100) not null
);

create table if not exists timer(
    id int primary key auto_increment,
    name varchar(50) not null,
    expiration_date datetime
);

create table if not exists user_timer(
    id_user int not null,
    id_timer int not null,
    primary key(id_user, id_timer),
    foreign key(id_user) references user(id),
    foreign key(id_timer) references timer(id)
);


insert into timer (name, expiration_date)
values ("studio", '2021-12-31 23:59:59');


insert into user (name, password)
values ("admin", "{noop}admin");

insert into user (name, password)
values ("topone", "{noop}topogigio");

insert into user_timer (id_user, id_timer)
values (1, 1);

select tim.name, expiration_date
from user t
join user_timer on t.id = user_timer.id_user
join timer tim on user_timer.id_timer = tim.id
where t.name = "admin";

select * from user;