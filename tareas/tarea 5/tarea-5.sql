-- esquema 
drop database if exists blockbuster;
create database if not exists blockbuster;
use blockbuster;

drop table if exists cliente;
drop table if exists categoria;
drop table if exists pelicula;
drop table if exists prestamo;


create table if not exists cliente (
	id int primary key auto_increment, -- cedula
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    telefono varchar(15) not null,
    direccion varchar(200) default 'N/A'
);

create table if not exists categoria(
 id int primary key auto_increment,
 titulo_categoria varchar (30) not null
);

create table  if not exists pelicula (
	id int primary key auto_increment, -- codigo
    titulo varchar(100) not null,
    fecha_lanzamineto timestamp not null,
    categoria_id int not null,
    constraint category_id_positive_value check (categoria_id > 0),
    constraint movie_category_id_fk foreign key (categoria_id) references categoria (id)
);

create table if not exists prestamo (
	id int not null auto_increment unique,
    cliente_id int not null,
    pelicula_id int not null unique,
    fecha_prestamo timestamp not null,
    fecha_devolucion timestamp,
    primary key (cliente_id, pelicula_id),
    constraint customer_id_positive_value check (cliente_id > 0),
    constraint movie_id_positive_value check (pelicula_id > 0), 
	constraint rental_customer_id_fk foreign key (cliente_id) references cliente (id),
	constraint rental_movie_id_fk foreign key (pelicula_id) references pelicula(id)
);


-- Datos
insert into cliente(nombre, apellido, telefono) values ('Juan', 'Perez', '1111111');
insert into cliente(nombre, apellido, telefono) values ('Maria', 'Rodríguez', '2222222');
insert into cliente(nombre, apellido, telefono, direccion) values ('Raquel', 'Blanco', '3333333', 'Limon');
insert into cliente(nombre, apellido, telefono) values ('Erick', 'Madrigal', '4444444');
insert into cliente(nombre, apellido, telefono) values ('Sebastian', 'Quesada', '555555');
insert into cliente(nombre, apellido, telefono, direccion) values ('Maria', 'Arguello', '6666666', 'Cartago');
insert into cliente(nombre, apellido, telefono) values ('Sergio', 'Rios', '777777');
insert into cliente(nombre, apellido, telefono) values ('Celeste', 'Rodriguez', '888888');
insert into cliente(nombre, apellido, telefono, direccion) values ('Mauro', 'Campos', '999999', 'San Jose');
insert into cliente(nombre, apellido, telefono) values ('Helen', 'Sanhez', '0000000');

INSERT INTO categoria (titulo_categoria) VALUES ('Comedia');
INSERT INTO categoria (titulo_categoria) VALUES ('Drama');
INSERT INTO categoria (titulo_categoria) VALUES ('Sci-Fi');
INSERT INTO categoria (titulo_categoria) VALUES ('Terror');
INSERT INTO categoria (titulo_categoria) VALUES ('Acción');
INSERT INTO categoria (titulo_categoria) VALUES ('Romance');
INSERT INTO categoria (titulo_categoria) VALUES ('Aventura');
INSERT INTO categoria (titulo_categoria) VALUES ('Fantasía');
INSERT INTO categoria (titulo_categoria) VALUES ('Documental');
INSERT INTO categoria (titulo_categoria) VALUES ('Misterio');



INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id) VALUES ('Una Noche en la Comedia', '2000-05-20 12:00:00', 1); -- Comedia
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id) VALUES ('El Drama de la Vida', '2015-02-10 12:00:00', 2); -- Drama
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('Aventuras en el Espacio', '2008-11-30 12:00:00', 3); -- Sci-Fi
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('Noche de Pesadillas', '2012-10-15 12:00:00', 4); -- Terror
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('Amenaza en la Ciudad', '2011-07-05 12:00:00', 5); -- Acción
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('Amor Eterno', '2018-03-25 12:00:00', 6); -- Romance
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('El Tesoro Perdido', '2010-09-18 12:00:00', 7); -- Aventura
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('Mundos Fantásticos', '2014-06-08 12:00:00', 8); -- Fantasía
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('El polo norte', '2020-06-08 12:00:00', 8); -- Documental
INSERT INTO pelicula (titulo, fecha_lanzamineto, categoria_id)  VALUES ('El Misterio Sin Resolver', '2007-04-30 12:00:00', 10); -- Misterio
select * from pelicula;
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (1, 1, '2023-09-22 10:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (2, 2, '2023-08-22 11:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (3, 3, '2023-09-15 12:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (4, 4, '2023-07-31 13:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (5, 5, '2023-01-06 14:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (3, 6, '2023-05-29 15:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (4, 7, '2023-04-09 16:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (3, 8, '2023-03-05 17:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (5, 9, '2023-12-25 18:00:00');
INSERT INTO prestamo (cliente_id, pelicula_id, fecha_prestamo) VALUES (3, 10, '2023-11-21 19:00:00');

-- Mostrar la Cédula, Nombre, Apellido y Teléfono Celular de los clientes que han pedido prestada una película al video, no importa si el préstamo está activo o no.

select 
c.id, c.nombre, c.apellido, c.telefono, r.pelicula_id, m.titulo
from 
cliente c 
inner join prestamo r on c.id = r.cliente_id
left join pelicula m on r.pelicula_id = m.id;

-- Mostrar la cantidad de préstamos activos por cédula.
select 
c.id, 
r.fecha_prestamo,
m.titulo,
datediff(now(), r.fecha_prestamo) as dias_en_rental
from 
prestamo as r
inner join cliente c on r.cliente_id = c.id
inner join pelicula m on r.pelicula_id = m.id
where 
datediff(now(), r.fecha_prestamo) <= 3;

-- Mostrar la cantidad de préstamos inactivos existentes por cédula.
select 
c.id, 
r.fecha_prestamo,
m.titulo,
datediff(now(), r.fecha_prestamo) as dias_en_rental
from 
prestamo as r
inner join cliente c on r.cliente_id = c.id
inner join pelicula m on r.pelicula_id = m.id
where 
datediff(now(), r.fecha_prestamo) > 3;

-- Mostrar el total de préstamos inactivos existentes .
select 
count(*)
from 
prestamo r 
where
datediff(now(), r.fecha_prestamo) > 3;

-- Mostrar a todos aquellos clientes que nunca han realizado un préstamo.
select 
c.id, 
c.nombre, 
c.apellido, 
c.telefono, 
r.id, 
r.pelicula_id
from 
cliente c 
left join prestamo r on c.id = r.cliente_id 
where r.id is null;

-- Actualizar el campo Dirección y poner Guanacaste, del cliente con cédula 10.
UPDATE cliente
SET direccion = 'Guanacaste'
WHERE id = 10;
-- Mostrar la cédula, nombre, apellido de los clientes que tienen entre 1 y 3 préstamos activos.
select
c.id,
concat(c.nombre, ' ', c.apellido) as cliente,
count(*) as numero_rentas
from 
cliente c
inner join prestamo r on c.id = r.cliente_id
group by 
r.cliente_id
having
numero_rentas > 0 and numero_rentas <= 3;



-- Listar todas las películas de la categoría “Comedia”.
select
* 
from 
pelicula as m inner join categoria as c on m.categoria_id = c.id 
where c.titulo_categoria = 'Comedia';

-- Listas todas las películas prestadas de la categoría “Comedia”.
select
* 
from 
prestamo as r inner join pelicula m on r.pelicula_id = m.id 
inner join categoria as c on m.categoria_id = c.id 
where c.titulo_categoria = 'Comedia';
 

-- Listar cuántas películas existen por categoría, por ejemplo: 3 de Comedia, 2 de Drama, 4 de Ciencia Ficción, etc.
SELECT c.titulo_categoria AS Categoria, COUNT(p.id) AS Cantidad_Peliculas
FROM categoria c
LEFT JOIN pelicula p ON c.id = p.categoria_id
GROUP BY c.titulo_categoria;










-- insert into pelicula(titulo, fecha_lanzamiento, categoria) values ('James Bond 007', now(), 'Accion');
-- insert into pelicula(titulo, fecha_lanzamiento, categoria) values ('Matrix', now(), 'Ciencia Ficcion');
-- insert into pelicula(titulo, fecha_lanzamiento, categoria) values ('Avatar', now(), 'Accion');
-- insert into pelicula(titulo, fecha_lanzamiento, categoria) values ('Spider Man', now(), 'Accion');

-- -- INSERT
-- -- SELECTS


-- drop procedure if exists add_course;
-- delimiter $$
-- create procedure add_course(in course_id int, in course_name varchar(50), in course_credits int, in course_dept varchar(50)) 
-- begin
-- 	insert into curso(id, nombre, creditos, departamento) values(course_id, course_name, course_credits, course_dept);
-- end
-- $$
-- delimiter ;


-- drop procedure if exists delete_movie;
-- delimiter $$
-- create procedure delete_movie(in movie_id int) 
-- begin
-- 	start transaction;
-- 		-- region critica
--         delete from pelicula where codigo = movie_id;
--         
-- 	commit;
-- end
-- $$
-- delimiter ;

-- select * from pelicula where titulo like 'Spider Man';



-- use blockbuster;


-- create table  if not exists movie (  id int primary key auto_increment,     title varchar(100) not null,     release_date timestamp not null,     category_id int not null,     constraint category_id_positive_value check (value > 0),     constraint movie_category_id_fk foreign key (category_id) references movie_category (id) )
