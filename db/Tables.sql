drop database if exists padel;
create database padel;
use padel;

create table pistas(
	id_pista int(2) primary key not null 
);

create table socio(
	id_socio int(2) primary key not null auto_increment,
	user varchar(15) not null unique,
	pass varchar(15) not null,
	fecha_nacimiento date not null,
	dni varchar(9) not null unique 
);

alter table socio
alter id_socio set default 1;

create table reserva(
	id_reserva int(2) primary key not null auto_increment,
	id_pista int(2) not null,
	id_socio int(2) not null,
	fecha date,
	franja enum('10','11','12','13','14','15','16','17','18','19','20') not null,
	
	foreign key (id_socio) references socio (id_socio),
	foreign key (id_pista) references pistas (id_pista)
);

alter table reserva
alter id_reserva set default 1;
alter table reserva 
add constraint reserva_unico unique(fecha,franja,id_pista);

create table pareja(
	id_pareja int(2) primary key not null auto_increment,
	id_socio_A int(2) not null,
	id_socio_B int(2) not null,
	foreign key (id_socio_A) references socio (id_socio),
	foreign key (id_socio_B) references socio (id_socio)
);
alter table pareja
alter id_pareja set default 1;
alter table pareja
add constraint pk_idSocio unique(id_socio_A,id_socio_B);

create table partido(
	id_partido int(3) primary key not null auto_increment,
	id_liga int(2) unsigned not null,
	id_pareja_A int(2) not null,
	id_pareja_B int(2) not null,
	fase_liga int(1) not null,
	finalizado bool not null default false,
	ganador int(2) , -- pareja ganadora 
	constraint chk_faseLiga check (fase_liga > 0 and fase_liga < 4),
	foreign key (id_pareja_A) references Pareja (id_pareja),
	foreign key (id_pareja_B) references Pareja (id_pareja),
	foreign key (ganador) references pareja (id_pareja)
);

alter table partido
alter id_partido set default 1;


create table detalles_partido(
	id_detalle int(3) primary key not null auto_increment,
	id_partido int(3) not null ,
	puntos_oro_A int(2) default 0,
	puntos_oro_B int(2) default 0,
	smash_ganadores_A int(2) default 0,
	smash_ganadores_B int(2) default 0,
	errores_no_forzados_A int(2) default 0,
	errores_no_forzados_B int(2) default 0,
	puntuacion varchar(20) not null default "(0-0)(0-0)(0-0)",
	foreign key (id_partido) references partido (id_partido)
);

alter table detalles_partido
alter id_detalle set default 1;

create table liga(
	id_liga int(2) primary key not null auto_increment,
	Nombre varchar(50) not null,
	create_year date default now(),
	Categoria varchar(50),
	ganador int(2) unique,
	foreign key (ganador) references pareja (id_pareja)
);
-- Darle un valor por defecto 
alter table liga
alter id_liga set default 1;

create table admin(
	usuario varchar(20) primary key, 
	pass varchar(20)
);
