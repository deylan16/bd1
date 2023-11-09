drop database if exists ImportacionesBD;
create database if not exists ImportacionesBD;
use ImportacionesBD;
drop table if exists COMPANIA;
drop table if exists DONDECOMPRAR;
drop table if exists PROVEEDOR;
drop table if exists ORDENESDEPEDIDO;
drop table if exists LINEADEPEDIDO;
drop table if exists ARTICULO;
drop table if exists GANANCIA;
drop table if exists SEGMENTODECLIENTES;

CREATE TABLE COMPANIA (
    Codigo int primary key auto_increment not null,
    Nombre varchar(50) not null,
    Direccion varchar(50),
    NumeroPatronal int not null,
    Cedula int(9) not null,
    Presidente varchar(50) not null,
    CedulaCompania int not null,
    Gerente varchar(50)
);
CREATE TABLE PROVEEDOR (
    Codigo int primary key auto_increment not null,
    Nombre varchar(50) not null,
    Cedula int(9) not null,
    Tipo ENUM('granos', 'licores', 'lácteos', 'equipo de oficina', 'etc'),
    Telefono int(8) not null,
    Direccion varchar(50),
    Contacto varchar(50)
);
CREATE TABLE DONDECOMPRAR (
	PROVEEDOR_CODIGO int  not null,
    COMPANIA_CODIGO int not null,
    CONSTRAINT PK_DONDECOMPRAR PRIMARY KEY (PROVEEDOR_CODIGO, COMPANIA_CODIGO),
    CONSTRAINT PROVEEDOR_CODIGO_positive_value CHECK (PROVEEDOR_CODIGO > 0),
    CONSTRAINT PROVEEDOR_CODIGO_id_fk FOREIGN KEY (PROVEEDOR_CODIGO) REFERENCES PROVEEDOR (Codigo),
    CONSTRAINT COMPANIA_CODIGO_positive_value CHECK (COMPANIA_CODIGO > 0),
    CONSTRAINT COMPANIA_CODIGO_id_fk FOREIGN KEY (COMPANIA_CODIGO) REFERENCES COMPANIA (Codigo)
);

CREATE TABLE ORDENESDEPEDIDO (
    NumeroOrden int primary key auto_increment not null,
    FechaOrden timestamp not null,
    Comprador varchar(50) not null,
    Aprobador varchar(50) not null,
    MontoTotalOrden float,
    PROVEEDOR_CODIGO int  not null,
    CONSTRAINT PROVEEDOR_CODIGO_positive_value2 CHECK (PROVEEDOR_CODIGO > 0),
    CONSTRAINT PROVEEDOR_CODIGO_id_fk2 FOREIGN KEY (PROVEEDOR_CODIGO) REFERENCES PROVEEDOR (Codigo)
);
CREATE TABLE ARTICULO (
    Codigo int primary key auto_increment not null,
    Descripcion varchar(50),
    Tipo ENUM('granos', 'licores', 'lácteos', 'equipo de oficina', 'etc'),
    PrecioUnitario float,
    PrecioVenta float,
    FechaUltimaCompra timestamp not null,
    Stock int,
    FechaUltimoInventario timestamp not null
);
CREATE TABLE LINEADEPEDIDO (
    NumeroLinea int primary key auto_increment not null,
    Cantidad int,
    Precio float,
    TotalLinea float,
    NumeroOrden_id int  not null,
    Articulo_codigo int not null,
    CONSTRAINT NumeroOrden_id_positive_value CHECK (NumeroOrden_id > 0),
    CONSTRAINT NumeroOrden_id_fk FOREIGN KEY (NumeroOrden_id) REFERENCES ORDENESDEPEDIDO (NumeroOrden),
    CONSTRAINT Articulo_codigo_positive_value CHECK (Articulo_codigo > 0),
    CONSTRAINT Articulo_codigo_id_fk FOREIGN KEY (Articulo_codigo) REFERENCES ARTICULO (Codigo)
);
CREATE TABLE SEGMENTODECLIENTES (
    Codigo int primary key auto_increment not null,
    Descripcion varchar(50),
    Gerente varchar(50),
    UltimaVisita timestamp not null,
	COMPANIA_CODIGO int not null,
    CONSTRAINT COMPANIA_CODIGO_positive_value3 CHECK (COMPANIA_CODIGO > 0),
    CONSTRAINT COMPANIA_CODIGO_id_fk3 FOREIGN KEY (COMPANIA_CODIGO) REFERENCES COMPANIA (Codigo)
);
CREATE TABLE GANANCIA (
    ARTICULO_CODIGO int not null,
    SEGMENTODECLIENTES_CODIGO int not null,
    Precio float not null,
    CONSTRAINT PK_GANANCIA PRIMARY KEY (ARTICULO_CODIGO, SEGMENTODECLIENTES_CODIGO),
    CONSTRAINT ARTICULO_CODIGO_positive_value2 CHECK (ARTICULO_CODIGO > 0),
    CONSTRAINT ARTICULO_CODIGO_id_fk2 FOREIGN KEY (ARTICULO_CODIGO) REFERENCES ARTICULO (Codigo),
    CONSTRAINT SEGMENTODECLIENTES_CODIGO_positive_value CHECK (SEGMENTODECLIENTES_CODIGO > 0),
    CONSTRAINT SEGMENTODECLIENTES_CODIGO_id_fk FOREIGN KEY (SEGMENTODECLIENTES_CODIGO) REFERENCES SEGMENTODECLIENTES (Codigo)
);

INSERT INTO COMPANIA (Nombre, Direccion, NumeroPatronal, Cedula, Presidente, CedulaCompania, Gerente)
VALUES
    ('Compañía A', 'Dirección A', 12345, 123456789, 'Presidente A', 987654321, 'Gerente A'),
    ('Compañía B', 'Dirección B', 67890, 987654321, 'Presidente B', 123456789, 'Gerente B'),
    ('Compañía C', 'Dirección C', 24680, 111111111, 'Presidente C', 222222222, 'Gerente C'),
    ('Compañía D', 'Dirección D', 13579, 333333333, 'Presidente D', 444444444, 'Gerente D'),
    ('Compañía E', 'Dirección E', 98765, 555555555, 'Presidente E', 666666666, 'Gerente E'),
    ('Compañía F', 'Dirección F', 55555, 777777777, 'Presidente F', 888888888, 'Gerente F'),
    ('Compañía G', 'Dirección G', 77777, 999999999, 'Presidente G', 101010101, 'Gerente G'),
    ('Compañía H', 'Dirección H', 88888, 121212121, 'Presidente H', 131313131, 'Gerente H'),
    ('Compañía I', 'Dirección I', 99999, 141414141, 'Presidente I', 151515151, 'Gerente I'),
    ('Compañía J', 'Dirección J', 12345, 161616161, 'Presidente J', 171717171, 'Gerente J');
    
INSERT INTO PROVEEDOR (Nombre, Cedula, Tipo, Telefono, Direccion, Contacto)
VALUES
    ('Proveedor 1', 111111111, 'granos', 12345678, 'Dirección 1', 'Contacto 1'),
    ('Proveedor 2', 222222222, 'licores', 23456789, 'Dirección 2', 'Contacto 2'),
    ('Proveedor 3', 333333333, 'lácteos', 34567890, 'Dirección 3', 'Contacto 3'),
    ('Proveedor 4', 444444444, 'equipo de oficina', 45678901, 'Dirección 4', 'Contacto 4'),
    ('Proveedor 5', 555555555, 'etc', 56789012, 'Dirección 5', 'Contacto 5'),
    ('Proveedor 6', 666666666, 'granos', 67890123, 'Dirección 6', 'Contacto 6'),
    ('Proveedor 7', 777777777, 'licores', 78901234, 'Dirección 7', 'Contacto 7'),
    ('Proveedor 8', 888888888, 'lácteos', 89012345, 'Dirección 8', 'Contacto 8'),
    ('Proveedor 9', 999999999, 'equipo de oficina', 90123456, 'Dirección 9', 'Contacto 9'),
    ('Proveedor 10', 101010101, 'etc', 12345678, 'Dirección 10', 'Contacto 10');


INSERT INTO DONDECOMPRAR (PROVEEDOR_CODIGO, COMPANIA_CODIGO)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);

INSERT INTO ORDENESDEPEDIDO (FechaOrden, Comprador, Aprobador, MontoTotalOrden, PROVEEDOR_CODIGO)
VALUES
    ('2023-10-01 08:00:00', 'Comprador 1', 'Aprobador 1', 1000.00, 1),
    ('2023-10-02 09:00:00', 'Comprador 2', 'Aprobador 2', 2000.00, 2),
    ('2023-10-03 10:00:00', 'Comprador 3', 'Aprobador 3', 1500.00, 3),
    ('2023-10-04 11:00:00', 'Comprador 4', 'Aprobador 4', 3000.00, 4),
    ('2023-10-05 12:00:00', 'Comprador 5', 'Aprobador 5', 2500.00, 5),
    ('2023-10-06 13:00:00', 'Comprador 6', 'Aprobador 6', 1800.00, 6),
    ('2023-10-07 14:00:00', 'Comprador 7', 'Aprobador 7', 2200.00, 7),
    ('2023-10-08 15:00:00', 'Comprador 8', 'Aprobador 8', 2800.00, 8),
    ('2023-10-09 16:00:00', 'Comprador 9', 'Aprobador 9', 3200.00, 9),
    ('2023-10-10 17:00:00', 'Comprador 10', 'Aprobador 10', 2100.00, 10);

INSERT INTO ARTICULO (Descripcion, Tipo, PrecioUnitario, PrecioVenta, FechaUltimaCompra, Stock, FechaUltimoInventario)
VALUES
    ('Artículo 1', 'granos', 10.00, 15.00, '2023-09-01 14:30:00', 100, '2023-09-30 18:45:00'),
    ('Artículo 2', 'licores', 20.00, 30.00, '2023-09-02 15:45:00', 80, '2023-09-29 16:30:00'),
    ('Artículo 3', 'lácteos', 15.00, 25.00, '2023-09-03 12:15:00', 120, '2023-09-28 20:15:00'),
    ('Artículo 4', 'equipo de oficina', 25.00, 35.00, '2023-09-04 09:30:00', 50, '2023-09-27 22:00:00'),
    ('Artículo 5', 'etc', 12.00, 18.00, '2023-09-05 08:00:00', 70, '2023-09-26 19:00:00'),
    ('Artículo 6', 'granos', 11.00, 16.00, '2023-09-06 10:30:00', 90, '2023-09-25 21:30:00'),
    ('Artículo 7', 'licores', 21.00, 31.00, '2023-09-07 13:45:00', 60, '2023-09-24 17:45:00'),
    ('Artículo 8', 'lácteos', 16.00, 26.00, '2023-09-08 11:00:00', 110, '2023-09-23 15:15:00'),
    ('Artículo 9', 'equipo de oficina', 26.00, 36.00, '2023-09-09 16:15:00', 40, '2023-09-22 23:30:00'),
    ('Artículo 10', 'etc', 13.00, 19.00, '2023-09-10 19:30:00', 75, '2023-09-21 12:00:00');

INSERT INTO LINEADEPEDIDO (Cantidad, Precio, TotalLinea, NumeroOrden_id, Articulo_codigo)
VALUES
    (5, 10.00, 50.00, 1, 1),
    (4, 20.00, 80.00, 2, 2),
    (7, 15.00, 105.00, 3, 3),
    (3, 25.00, 75.00, 4, 4),
    (6, 12.00, 72.00, 5, 5),
    (8, 11.00, 88.00, 6, 6),
    (4, 21.00, 84.00, 7, 7),
    (7, 16.00, 112.00, 8, 8),
    (5, 26.00, 130.00, 9, 9),
    (9, 13.00, 117.00, 10, 10);

INSERT INTO SEGMENTODECLIENTES (Descripcion, Gerente, UltimaVisita, COMPANIA_CODIGO)
VALUES
    ('Segmento 1', 'Gerente Segmento 1', '2023-09-15 09:30:00', 1),
    ('Segmento 2', 'Gerente Segmento 2', '2023-09-16 10:45:00', 2),
    ('Segmento 3', 'Gerente Segmento 3', '2023-09-17 08:15:00', 3),
    ('Segmento 4', 'Gerente Segmento 4', '2023-09-18 11:30:00', 4),
    ('Segmento 5', 'Gerente Segmento 5', '2023-09-19 12:45:00', 5),
    ('Segmento 6', 'Gerente Segmento 6', '2023-09-20 14:00:00', 6),
    ('Segmento 7', 'Gerente Segmento 7', '2023-09-21 16:15:00', 7),
    ('Segmento 8', 'Gerente Segmento 8', '2023-09-22 18:30:00', 8),
    ('Segmento 9', 'Gerente Segmento 9', '2023-09-23 20:45:00', 9),
    ('Segmento 10', 'Gerente Segmento 10', '2023-09-24 22:00:00', 10);

INSERT INTO GANANCIA (ARTICULO_CODIGO, SEGMENTODECLIENTES_CODIGO, Precio)
VALUES
    (1, 1, 5.00),
    (2, 2, 8.00),
    (3, 3, 6.50),
    (4, 4, 10.00),
    (5, 5, 4.50),
    (6, 6, 4.00),
    (7, 7, 8.50),
    (8, 8, 6.75),
    (9, 9, 11.00),
    (10, 10, 5.25);
