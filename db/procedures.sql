/** 
-- por si acaso se quiere eliminar
 drop procedure partidos_ganados;
-- ver cuantos partidos ha ganado una pareja en total por su id
**/
create procedure partidos_ganados (ele_pareja int)
begin
select count(*) from partido 
	inner join liga on partido.id_liga = liga.id_liga 
	where partido.ganador = id_pareja_ele;
end;

-- actualizar liga para definir el ganador
create procedure ganador_liga (ele_liga int)
begin
	update liga 
	set ganador = (
	select ganador from partido 
	where partido.fase_liga = '3' and
	partido.id_liga = ele_liga
	) 
	where liga.id_liga = ele_liga;
end;

-- TRIGGER PARA ACTUALIZAR EL GANADOR DE LA LIGA
create trigger definir_ganador_liga
after update on partido 
for each row 
begin 
	if (new.ganador is not null) then 
		call ganador_liga(new.id_liga);
	end if;
end;


/* porcentaje de victorias de una pareja */

create function porcentaje_victorias(fecha_inicial date, fecha_final date, id_pareja_ele int) 
returns float
begin
	declare porcentaje float default 0;
	
	set porcentaje = (
	(select count(*) from partido 
	inner join liga on partido.id_liga = liga.id_liga 
	where partido.ganador = id_pareja_ele and (liga.create_year >= fecha_inicial and liga.create_year <= fecha_final))
	/
	(select count(*) from partido 
	inner join liga on partido.id_liga = liga.id_liga 
	where (id_pareja_a = id_pareja_ele or id_pareja_b = id_pareja_ele)  and (liga.create_year >= fecha_inicial and liga.create_year <= fecha_final) 
	)) ;

	return porcentaje;
end;



/** 
 * encontrar si una pareja es participante de una liga
 * **/

create function Encuentra_liga (pareja_buscada int,liga_buscada int)
returns smallint
begin
	
	declare encuentra smallint default 0;

	select count(*) into encuentra from participantes_liga 
	where participantes_liga.participante = pareja_buscada
	and participantes_liga.id_liga = liga_buscada;

	return encuentra;

end;

/** Trigger que se ejecuta si se intenta ingresar a un participante que no este en la liga **/

create trigger restriccion_partido
before insert on partido
for each row
begin
	
	if (encuentra_liga(new.id_pareja_a,new.id_liga) = 0) then
		signal sqlstate '45000' set message_text = "Participante no se encuentra en la liga";
	elseif(encuentra_liga(new.id_pareja_b,new.id_liga) = 0) then
		signal sqlstate '45000' set message_text = "Participante no se encuentra en la liga";
	end if;

end; 

	

/** Reservar una pista para la liga**/

create procedure resrvar_pista_liga (numero_pista int,id_socio_ingresado int,fecha_ingresada date,hora_inicio char(2), hora_final char(2))
begin
	
	if (
	(select count(*) from reserva
	where numero_pista = id_pista and 
	(fecha_ingresada = reserva.fecha) and 
	(hora_final <= hora_fin and hora_inicio >= franja) > 0 )
	) then 

	delete from reserva 
	where (numero_pista = id_pista) and 
	(fecha_ingresada = reserva.fecha) and 
	(hora_final <= hora_fin and hora_inicio >= franja);

	end if;

	insert into reserva (id_pista, id_socio, fecha, franja, hora_fin)
	values
	(numero_pista,id_socio_ingresado,fecha_ingresada,hora_inicio,hora_final);
	
end;

/** trigger para cancelar las reservas que necesita una liga y reservar para la liga **/
drop trigger reservar_pista;
create trigger reservar_pista
before insert on liga
for each row 
begin 
	call resrvar_pista_liga (2,1,new.fecha_inicio,'10','13');
	call resrvar_pista_liga (3,1,new.fecha_inicio,'10','13');
	call resrvar_pista_liga (4,1,new.fecha_inicio,'10','13');
	call resrvar_pista_liga (5,1,new.fecha_inicio,'10','13');
	call resrvar_pista_liga (3,1,date_add(new.fecha_inicio,interval 1 day),'10','13');
	call resrvar_pista_liga (4,1,date_add(new.fecha_inicio,interval 1 day),'10','13');
	call resrvar_pista_liga (3,1,date_add(new.fecha_inicio,interval 2 day),'10','13');

end;
/** Contar cuantas fase de liga tiene un partido 
 * la fase 1 solo puede tener 4 partidos
 * la fase 2 solo puede tener 2 partidos
 * la fase 3 solo puede tener 1 partido
 *  **/
create function contador_fase_liga(fase int,liga int)
returns int
begin
	declare cantidad int default 0;
	
	select count(*) into cantidad from partido 
	where partido.fase_liga  = fase 
	and partido.id_liga = liga;
	
	return cantidad;
end;

/**
 * trigger que lanza un error si se superan los limites anteriores.
 *  **/

create trigger limitador_de_fases 
before insert on partido
for each row 
begin 
	if (new.fase_liga = 1) then
		if (contador_fase_liga(new.fase_liga,new.id_liga) >= 4) then 
			signal sqlstate '45000' set message_text = "No pueden haber mas fases de esa liga";
		end if;
	elseif (new.fase_liga = 2) then
		if (contador_fase_liga(new.fase_liga,new.id_liga) >= 2) then 
			signal sqlstate '45000' set message_text = "No pueden haber mas fases de esa liga";
		end if;
	elseif (new.fase_liga = 1) then
		if (contador_fase_liga(new.fase_liga,new.id_liga) >= 1) then 
			signal sqlstate '45000' set message_text = "No pueden haber mas fases de esa liga";
		end if;
	end if;
end;

/** 
 * Pone el estado finalizado de un partido en true y definir el ganador
 * 
 */
create trigger finalizado_partido 
before insert on detalles_partido
for each row 
begin 
	update partido 
	set finalizado = true,
	partido.ganador = new.ganador
	where new.id_partido = partido.id_partido;
	
end;

/** 
 * No se pueden crear 2 ligas en la misma fecha 
 * 
 * **/

create trigger ligas_diferente_fecha
before insert on liga
for each row 
begin 

	if ((select count(*) from liga 
		where liga.fecha_inicio <= new.fecha_inicio 
		and date_add(liga.fecha_inicio,interval 2 day) >= new.fecha_inicio) > 0 ) then -- si existe alguno entre esas fechas dara mas de 0
		signal sqlstate '45000' set message_text = "No pueden haber ligas en las mismas fechas";
	end if;
end;


/** 
 * Procedimientos para dar de alta de forma rapida
 * **/

create procedure reservar_pista(numero_pista int,id_socio_ingresado int,fecha_ingresada date,hora_inicio char(2), hora_final char(2))
begin
	insert into reserva (id_pista, id_socio, fecha, franja, hora_fin)
	values
	(numero_pista,id_socio_ingresado,fecha_ingresada,hora_inicio,hora_final);
	
end;

-- usuario 

create procedure alta_usuario(usu varchar(15), nom varchar(50), pw varchar(15), fecha_n date, dato varchar(9))
begin
	insert into socio (usuario,nombre,pass,fecha_nacimiento,dni)
	values
	(usu,nom,pw,fecha_n,dato);
end;

-- admin

create procedure alta_admin(usu varchar(15), nom varchar(50), pw varchar(15), fecha_n date, dato varchar(9),admin bool)
begin
	insert into socio (usuario,nombre,pass,fecha_nacimiento,dni,administrador)
	values
	(usu,nom,pw,fecha_n,dato,admin);
end;

-- liga

create procedure alta_liga(nom varchar(50),creacion Date, finicial Date, cate int)
begin
	insert into liga(Nombre,create_year,fecha_inicio,Categoria)
	values
	(nom,creacion,finicial,cate);
end;

/** Fin procedimientos de alta **/

