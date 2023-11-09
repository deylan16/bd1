drop database if exists Caso2;
create database if not exists Caso2;
use Caso2;
drop table if exists PERSONA;
drop table if exists CUENTA_POR_COBRAR;
drop table if exists ABONO;
drop table if exists FORMA_DE_PAGO;
drop table if exists PERSONA_FISICA;
drop table if exists PERSONA_JURIDICA;


CREATE TABLE PERSONA_FISICA (
    id int primary key auto_increment not null,
    Fecha_Nac timestamp not null
);
CREATE TABLE PERSONA_JURIDICA (
    id int primary key auto_increment not null,
     Nombre_Comercial varchar(50) not null
);
CREATE TABLE PERSONA (
    Cedula int primary key auto_increment not null,
    Tipo ENUM('Persona Fisica', 'Persona Juridica'),
    Nombre varchar(50) not null,
    id_persona_juridica int default null,
    id_persona_fisica int ,
    CONSTRAINT id_persona_juridica_permite CHECK (Tipo = 'Persona Juridica' and id_persona_fisica = null or Tipo = 'Persona Fisica' and id_persona_juridica = null),

    CONSTRAINT id_persona_juridica_positive_value CHECK (id_persona_juridica > 0),
    CONSTRAINT id_persona_juridica_id_fk FOREIGN KEY (id_persona_juridica) REFERENCES PERSONA_JURIDICA (id),
    CONSTRAINT id_persona_fisica_positive_value CHECK (id_persona_fisica > 0),
    CONSTRAINT id_persona_fisica_id_fk FOREIGN KEY (id_persona_fisica) REFERENCES PERSONA_FISICA (id)
);
CREATE TABLE CUENTA_POR_COBRAR (
    Numero int primary key auto_increment not null,
    Monto float not null,
    Fecha_Venc timestamp not null,
    cedula_persona int  not null,
    CONSTRAINT cedula_persona_positive_value CHECK (cedula_persona > 0),
    CONSTRAINT cedula_persona_id_fk FOREIGN KEY (cedula_persona) REFERENCES PERSONA (Cedula)
);

CREATE TABLE FORMA_DE_PAGO (
    Codigo int primary key auto_increment not null,
	Nombre varchar(50) not null
);


CREATE TABLE ABONO (
    Numero_Cuota int primary key auto_increment not null,
    Fecha timestamp not null,
    Monto float not null,
    numero_cuenta_por_pagar int  not null,
    CONSTRAINT numero_cuenta_por_pagar_positive_value CHECK (numero_cuenta_por_pagar > 0),
    CONSTRAINT numero_cuenta_por_pagar_id_fk FOREIGN KEY (numero_cuenta_por_pagar) REFERENCES CUENTA_POR_COBRAR (Numero),
    codigo_forma_de_pago int  not null,
    CONSTRAINT codigo_forma_de_pago_positive_value CHECK (codigo_forma_de_pago > 0),
    CONSTRAINT codigo_forma_de_pago_id_fk FOREIGN KEY (codigo_forma_de_pago) REFERENCES FORMA_DE_PAGO (Codigo)
    
);

INSERT INTO PERSONA_FISICA (Fecha_Nac)
VALUES
    ('1990-05-15'),
    ('1985-08-20'),
    ('1978-03-10'),
    ('1995-11-25'),
    ('1980-07-08'),
    ('1987-02-17'),
    ('1992-09-30'),
    ('1983-12-05'),
    ('1975-06-22'),
    ('1998-04-12');


INSERT INTO PERSONA_JURIDICA (Nombre_Comercial)
VALUES
    ('Empresa A'),
    ('Empresa B'),
    ('Empresa C'),
    ('Empresa D'),
    ('Empresa E'),
    ('Empresa F'),
    ('Empresa G'),
    ('Empresa H'),
    ('Empresa I'),
    ('Empresa J');

INSERT INTO PERSONA (Tipo, Nombre, id_persona_juridica, id_persona_fisica)
VALUES
    ('Persona Fisica', 'Persona Fisica 1', NULL, 1),
    ('Persona Juridica', 'Persona Juridica 1', 1, NULL),
    ('Persona Fisica', 'Persona Fisica 2', NULL, 2),
    ('Persona Juridica', 'Persona Juridica 2', 2, NULL),
    ('Persona Fisica', 'Persona Fisica 3', NULL, 3),
    ('Persona Juridica', 'Persona Juridica 3', 3, NULL),
    ('Persona Fisica', 'Persona Fisica 4', NULL, 4),
    ('Persona Juridica', 'Persona Juridica 4', 4, NULL),
    ('Persona Fisica', 'Persona Fisica 5', NULL, 5),
    ('Persona Juridica', 'Persona Juridica 5', 5, NULL);


INSERT INTO CUENTA_POR_COBRAR (Monto, Fecha_Venc, cedula_persona)
VALUES
    (1000.00, '2023-10-15 10:00:00', 1),
    (2000.00, '2023-10-16 11:00:00', 2),
    (1500.00, '2023-10-17 12:00:00', 3),
    (3000.00, '2023-10-18 13:00:00', 4),
    (2500.00, '2023-10-19 14:00:00', 5),
    (1800.00, '2023-10-20 15:00:00', 6),
    (2200.00, '2023-10-21 16:00:00', 7),
    (2800.00, '2023-10-22 17:00:00', 8),
    (3200.00, '2023-10-23 18:00:00', 9),
    (2100.00, '2023-10-24 19:00:00', 10);


INSERT INTO FORMA_DE_PAGO (Nombre)
VALUES
    ('Efectivo'),
    ('Tarjeta de crédito'),
    ('Transferencia bancaria'),
    ('Cheque'),
    ('Pago en línea'),
    ('Letra de cambio'),
    ('PayPal'),
    ('Criptomoneda'),
    ('Cheque de viajero'),
    ('Otro');

INSERT INTO ABONO (Fecha, Monto, numero_cuenta_por_pagar, codigo_forma_de_pago)
VALUES
    ('2023-10-01 08:00:00', 500.00, 1, 1),
    ('2023-10-02 09:00:00', 800.00, 2, 2),
    ('2023-10-03 10:00:00', 600.00, 3, 3),
    ('2023-10-04 11:00:00', 1000.00, 4, 4),
    ('2023-10-05 12:00:00', 750.00, 5, 5),
    ('2023-10-06 13:00:00', 600.00, 6, 6),
    ('2023-10-07 14:00:00', 850.00, 7, 7),
    ('2023-10-08 15:00:00', 675.00, 8, 8),
    ('2023-10-09 16:00:00', 1100.00, 9, 9),
    ('2023-10-10 17:00:00', 525.00, 10, 10);





