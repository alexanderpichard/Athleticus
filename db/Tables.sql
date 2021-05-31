drop database if exists padel;
create database padel;
use padel;

create table pistas(
	id_pista int(2) primary key not null 
);

create table socio(
	id_socio int(2) primary key auto_increment,
	usuario varchar(15) not null unique,
	pass varchar(15) not null,
	nombre varchar(50) not null,
	fecha_nacimiento date not null,
	categoria int,
	dni varchar(9) not null unique,
	administrador boolean default false
);
select * from socio;
create table reserva(
	id_reserva int(2) primary key auto_increment,
	id_pista int(2) not null,
	id_socio int(2) not null,
	fecha date,
	franja enum('10','11','12','13','14','15','16','17','18','19','20') not null,
	hora_fin enum('11','12','13','14','15','16','17','18','19','20') not null,
	constraint chk_hora_fin check (hora_fin > franja),
	foreign key (id_socio) references socio (id_socio),
	foreign key (id_pista) references pistas (id_pista)
);

alter table reserva 
add constraint reserva_unico unique(fecha,franja,id_pista);

create table pareja(
	id_pareja int(2) primary key auto_increment,
	id_socio_A int(2) not null,
	id_socio_B int(2) not null,
	foreign key (id_socio_A) references socio (id_socio),
	foreign key (id_socio_B) references socio (id_socio)
);

alter table pareja
add constraint pk_idSocio unique(id_socio_A,id_socio_B);

create table liga(
	id_liga int(2) primary key auto_increment,
	Nombre varchar(50) not null,
	create_year date not null,
	fecha_inicio date not null,
	Categoria int not null,
	ganador int(2) unique null,
	constraint chk_categoria check (categoria > 0 and categoria < 4),
	foreign key (ganador) references pareja (id_pareja)
);

create table participantes_liga(
	id_lista int primary key auto_increment,
	id_liga int not null,
	participante int not null,
	foreign key (id_liga) references liga (id_liga),
	foreign key (participante) references pareja (id_pareja)
);

alter table participantes_liga 
add constraint participantes_unicos unique
(id_liga,participante);

create table partido(
	id_partido int(3) primary key auto_increment,
	id_liga int(2) not null,
	id_pareja_A int(2) not null,
	id_pareja_B int(2) not null,
	fase_liga int(1) not null,
	finalizado bool default false,
	ganador int(2) , -- pareja ganadora 
	constraint chk_faseLiga check (fase_liga > 0 and fase_liga < 4),
	constraint chk_parejas check (id_pareja_A <> id_pareja_B),
	constraint chk_pareja_ganadora check (ganador = id_pareja_A or ganador = id_pareja_B),
	foreign key (id_liga) references liga (id_liga),
	foreign key (id_pareja_A) references Pareja (id_pareja),
	foreign key (id_pareja_B) references Pareja (id_pareja),
	foreign key (ganador) references pareja (id_pareja)
);
alter table partido 
add constraint partidos_liga unique
(id_liga,id_pareja_a,id_pareja_b);


create table detalles_partido(
	id_detalle int(3) primary key auto_increment,
	id_partido int(3) not null unique,
	puntos_oro_A int(2) default 0,
	puntos_oro_B int(2) default 0,
	smash_ganadores_A int(2) default 0,
	smash_ganadores_B int(2) default 0,
	errores_no_forzados_A int(2) default 0,
	errores_no_forzados_B int(2) default 0,
	puntuacion varchar(20) not null default "(0-0)(0-0)(0-0)",
	ganador int not null,
	foreign key (id_partido) references partido (id_partido),
	foreign key (ganador) references pareja (id_pareja)
);

