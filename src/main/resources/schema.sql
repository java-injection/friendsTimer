create database if not exists timer;
use timer;

create table if not exists user(
    id int primary key auto_increment,
    name varchar(50) not null,
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

