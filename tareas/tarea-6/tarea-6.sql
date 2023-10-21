drop database if exists tarea6;
create database if not exists tarea6;
use tarea6;
drop table if exists PEDIDOS;
drop table if exists CLIENTES;
drop table if exists LIBROS;

CREATE TABLE LIBROS (
    LibroID INT PRIMARY KEY,
    Titulo VARCHAR(255) NOT NULL,
    Autor VARCHAR(255) NOT NULL,
    Genero VARCHAR(50),
    Precio DECIMAL(10, 2) NOT NULL,
    Stock INT NOT NULL
);
CREATE INDEX idx_Titulo ON Libros(Titulo);

CREATE TABLE CLIENTES (
    ClienteID INT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    CorreoElectronico VARCHAR(255) NOT NULL
);
CREATE INDEX idx_CorreoElectronico ON Clientes(CorreoElectronico);

CREATE TABLE PEDIDOS (
    PedidoID INT PRIMARY KEY,
    ClienteID INT NOT NULL,
    LibroID INT,
    FechaPedido DATE NOT NULL,
    FOREIGN KEY (ClienteID) REFERENCES Clientes(ClienteID),
    FOREIGN KEY (LibroID) REFERENCES Libros(LibroID)
);
CREATE INDEX idx_ClienteID ON Pedidos(ClienteID);


-- Procedimiento Almacenado para Agregar Libros:
DELIMITER //
CREATE PROCEDURE AgregarLibro(
    IN p_Titulo VARCHAR(255),
    IN p_Autor VARCHAR(255),
    IN p_Genero VARCHAR(50),
    IN p_Precio DECIMAL(10, 2),
    IN p_Stock INT
)
BEGIN
    INSERT INTO LIBROS (Titulo, Autor, Genero, Precio, Stock) VALUES (p_Titulo, p_Autor, p_Genero, p_Precio, p_Stock);
END;
//
DELIMITER ;

-- Procedimiento Almacenado para Realizar Pedidos:
DELIMITER //
CREATE PROCEDURE RealizarPedido(
    IN p_ClienteID INT,
    IN p_LibroID INT
)
BEGIN
    -- Insertar un nuevo pedido en la tabla Pedidos
    INSERT INTO PEDIDOS (ClienteID, FechaPedido,LibroID) VALUES (p_ClienteID, CURDATE(),p_LibroID);
    
    -- Disminuir el stock del libro
    UPDATE LIBROS
    SET Stock = Stock - 1
    WHERE LibroID = p_LibroID;
END;
//
DELIMITER ;

-- Función para Calcular el Total del Pedido:
DELIMITER //
CREATE FUNCTION CalcularTotalPedido(p_PedidoID INT)
RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
    DECLARE Total DECIMAL(10, 2);
    SELECT SUM(LIBROS.Precio)
    INTO Total
    FROM Pedidos
    JOIN LIBROS ON Pedidos.PedidoID = p_PedidoID AND LIBROS.LibroID = Pedidos.LibroID;
    RETURN Total;
END;
//
DELIMITER ;




--  Disparador para Actualizar el Stock de Libros:
DELIMITER //
CREATE TRIGGER ActualizarStockDespuesDePedido
AFTER INSERT ON PEDIDOS
FOR EACH ROW
BEGIN
    -- Disminuir el stock del libro vendido
    UPDATE LIBROS
    SET Stock = Stock - 1
    WHERE LibroID = NEW.LibroID;
END;
//
DELIMITER ;

