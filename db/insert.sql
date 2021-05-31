
insert into socio (usuario,pass,nombre,fecha_nacimiento,dni)
values
("administrador","qwerty2021","Administrador","1996-06-15","923451X"),
("juand98","qwerty2021","David velasco","1996-06-15","123545X"),
("david12","qwety2021","davidu velasco", "1996-06-15","234567Y"),
("david15","qwety2021","dady velasco", "1996-06-15","234587Y"),
("Arriola23","qwerty2021","Jose Arriola", "1992-03-13","423712K"),
("Mariota12","qwerty2021","Maria Flores","1990-05-15","4542124Z"),
("liz21","qwerty2021","Lizeth andre","1990-04-13","1243321A"),
("lala41","qwerty2021","Laura lanuda","1991-05-03","4312343B"),
("Lauro21","qwerty2021","Lautaro","1991-02-11","9543312L"),
("Byivan19","qwerty2021","Tal ivan","1991-05-13","4832910B"),
("Mariola21","qwerty2021","Mario lavin","1991-06-23","5483132P"),
("erew26","qwerty2021","Eren jagger","1990-05-16","4129531O"),
("lizd13","qwerty2021","Liza dentin","1992-04-21","5738921P"),
("peper","qwerty2021","Mr peper","1994-06-25","9543214X"),
("linlin","qwerty2021","ling ling ","1999-05-26","1235621Q"),
("camila31","qwerty2021","camila restrepo","1994-03-01","8473231P"),
("linklink","qwerty2021","link ping ","1999-05-26","1235321Q"),
("taniaRes","qwerty2021","tania restrepo","1994-03-01","8465543P");

update socio 
set administrador = true 
where id_socio =1;


insert into pareja (id_socio_A,id_socio_B)
values
(3,4),
(5,6),
(7,8),
(9,10),
(11,12),
(13,14),
(15,16),
(17,18);

insert into pistas
values
(1),
(2),
(3),
(4),
(5);

insert into liga (nombre,create_year,fecha_inicio ,Categoria,ganador)
values
('Copa Piston 2', '2021-02-18','2021-05-23', 2,null);

insert into participantes_liga (id_liga,participante)
values 
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8);

insert into partido (id_liga,id_pareja_A,id_pareja_B,fase_liga,ganador)
values
(1,1,2,1,2),
(1,3,4,1,3),
(1,5,6,1,6),
(1,7,8,1,7);

insert into detalles_partido (id_partido,puntos_oro_A,puntos_oro_B,smash_ganadores_A,smash_ganadores_B,errores_no_forzados_A,errores_no_forzados_B,puntuacion,ganador)
values
(1,5,4,9,10,15,2,"(4,6)(6,2)(4,6)",2),
(2,6,6,5,12,14,4,"(6,3)(4,3)(6,2)",3),
(3,7,6,7,14,16,5,"(1,6)(6,2)(5,7)",6),
(4,8,4,6,10,23,3,"(6,1)(6,3)(0,0)",7);


insert into partido (id_liga,id_pareja_A,id_pareja_B,fase_liga,ganador)
values
(1,2,3,2,3),
(1,6,7,2,6);

insert into detalles_partido
values
(5,5,5,4,9,10,15,2,"(4,6)(6,2)(4,6)",3),
(6,6,6,6,5,12,14,4,"(6,3)(4,3)(6,2)",6);

insert into partido (id_liga,id_pareja_A,id_pareja_B,fase_liga,ganador)
values
(1,3,6,3,3);

insert into detalles_partido
values
(7,7,5,4,9,10,15,2,"(6,3)(4,6)(6,2)",3);


select * from socio r ;


select id_pista,fecha,franja from reserva where id_socio = 1 and fecha >= now()