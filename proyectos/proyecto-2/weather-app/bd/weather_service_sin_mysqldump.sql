-- drop database if exists weather_service;
create database if not exists weather_service;
use weather_service;

drop USER 'weatherappuser'@'localhost';
CREATE USER 'weatherappuser'@'localhost' IDENTIFIED BY 'weatherapppass';
GRANT ALL PRIVILEGES ON weather_service.* TO 'weatherappuser'@'localhost';
FLUSH PRIVILEGES;



drop table if exists forecast;
drop table if exists city;
drop table if exists state;
drop table if exists country;
drop table if exists forecast_log;

-- Crear la tabla "country" para almacenar países por código y nombre del país
CREATE TABLE if not exists country (
    country_code int PRIMARY KEY auto_increment,
    country_name VARCHAR(100) NOT NULL unique
);

-- Crear la tabla "state" para almacenar estados por código y nombre

CREATE TABLE if not exists state (
    state_code int PRIMARY KEY auto_increment,
    state_name VARCHAR(100) NOT NULL,
    country_code int NOT NULL,
    FOREIGN KEY (country_code) REFERENCES country(country_code)
);

-- Crear la tabla "city" para almacenar ciudades por código, nombre y código postal (zip code)
CREATE TABLE if not exists city (
    city_code INT PRIMARY KEY auto_increment,
    city_name VARCHAR(100) NOT NULL,
    zip_code VARCHAR(10) NOT NULL unique,
    state_code int NOT NULL,
    FOREIGN KEY (state_code) REFERENCES state(state_code)
);

-- Crear la tabla "forecast" para almacenar los pronósticos del tiempo
CREATE TABLE if not exists forecast (
    forecast_code int PRIMARY KEY auto_increment,
    city_code INT NOT NULL,
    zip_code int NOT NULL,
    temperature_celsius DECIMAL(5, 2) NOT NULL,
    forecast_date DATE NOT NULL,
    FOREIGN KEY (city_code) REFERENCES city(city_code),
    INDEX zip_index (zip_code)
);

-- Crear la tabla "forecast_log" para almacenar los pronósticos del tiempo
CREATE TABLE if not exists forecast_log (
    id int PRIMARY KEY auto_increment,
    last_modified_on DATE NOT NULL,
    entry_text VARCHAR(100) NOT NULL,
    forecast_code INT NOT NULL
);


-- procedimientos
drop procedure if exists create_country;
drop procedure if exists find_all_countries;
drop procedure if exists delete_country;
drop procedure if exists update_country;
drop procedure if exists find_all_states;
drop procedure if exists create_state;
drop procedure if exists delete_state;
drop procedure if exists update_state;
drop procedure if exists find_all_citys;
drop procedure if exists create_city;
drop procedure if exists delete_city;
drop procedure if exists update_city;
drop procedure if exists find_all_forecasts;
drop procedure if exists find_all_forecasts_date;
drop procedure if exists find_all_forecasts_zip;
drop procedure if exists create_forecast;
drop procedure if exists delete_forecast;
drop procedure if exists update_forecast;
drop procedure if exists find_forecasts_log;


-- Country==========================================================================
DELIMITER $$
CREATE PROCEDURE create_country(
	IN p_country_name VARCHAR(100),
    OUT p_new_country_id INT
    )
BEGIN
	-- Agregar Bloque transaccional
    START TRANSACTION;
    INSERT INTO country(country_name) values (p_country_name);
    select last_insert_id() into p_new_country_id;
    COMMIT;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE find_all_countries(in p_country_id int)
BEGIN
	if(p_country_id <= 0) then	 
	-- Agregar Bloque transaccional
		select country_code,country_name from country order by country_code asc;
	else 
		select * from country where country_code = p_country_id;
	end if;

END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE delete_country(in p_country_id int)
BEGIN

	-- Agregar Bloque transaccional
    START TRANSACTION;
    DELETE FROM country WHERE country_code = p_country_id;
    commit;


END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_country(
	IN p_country_id int,
	IN p_country_name VARCHAR(100)
    )
BEGIN
	-- Agregar Bloque transaccional
    START TRANSACTION;
    update country set country_name = p_country_name where p_country_id = country_code;
    commit;
END$$
DELIMITER ;


-- State ==========================================================================
DELIMITER $$
CREATE PROCEDURE create_state(
	IN p_state_name VARCHAR(100),
    IN p_country_code int,
    OUT p_new_state_id INT
    )
BEGIN
	-- Agregar Bloque transaccional
    START TRANSACTION;
    INSERT INTO state(state_name,country_code) values (p_state_name,p_country_code);
    select last_insert_id() into p_new_state_id;
    commit;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE find_all_states(in p_state_id int)
BEGIN
	if(p_state_id <= 0) then	 
	-- Agregar Bloque transaccional
		select * from state order by state_code asc;
	else 
		select * from state where state_code = p_state_id;
	end if;

END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE delete_state(in p_state_id int)
BEGIN

	-- Agregar Bloque transaccional
    START TRANSACTION;
    DELETE FROM state WHERE state_code = p_state_id;
    commit;


END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_state(
	IN p_state_id int,
    
	IN p_state_name VARCHAR(100),
    IN p_country_code int
    )
BEGIN
	-- Agregar Bloque transaccional
    START TRANSACTION;
    update state set state_name = p_state_name, country_code=p_country_code where p_state_id = state_code;
    commit;
END$$
DELIMITER ;


-- City ==========================================================================
DELIMITER $$
CREATE PROCEDURE create_city(
	IN p_city_name VARCHAR(100),
    IN p_zip_code int,
    IN p_state_code int,
    OUT p_new_city_id INT
    )
BEGIN
	-- Agregar Bloque transaccional
    START TRANSACTION;
    INSERT INTO city(city_name,zip_code,state_code) values (p_city_name,p_zip_code,p_state_code);
    select last_insert_id() into p_new_city_id;
    commit;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE find_all_citys(in p_city_id int)
BEGIN
	if(p_city_id <= 0) then	 
	-- Agregar Bloque transaccional
		select * from city order by city_code asc;
	else 
		select * from city where city_code = p_city_id;
	end if;

END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE delete_city(in p_city_id int)
BEGIN

	-- Agregar Bloque transaccional
    START TRANSACTION;
    DELETE FROM city WHERE city_code = p_city_id;
    commit;


END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_city(
	IN p_city_id int,
    
	IN p_city_name VARCHAR(100),
    IN p_zip_code int,
    IN p_state_code int
    
    )
BEGIN
	-- Agregar Bloque transaccional
    START TRANSACTION;
    update city set city_name = p_city_name, state_code=p_state_code, zip_code = p_zip_code where p_city_id = city_code;
    commit;
END$$
DELIMITER ;


-- Forecast ==========================================================================
DELIMITER $$
CREATE PROCEDURE create_forecast(
	IN p_city_code int,
    IN p_temperature int,
    IN p_date date,
    OUT p_new_forecast_id INT
    )
BEGIN
	
	-- Agregar Bloque transaccional
    DECLARE zipF INT; -- Se declara la variable para almacenar el código postal

    -- Obtener el código postal para la ciudad
    SELECT zip_code INTO zipF FROM city WHERE city_code = p_city_code;
	START TRANSACTION;
    INSERT INTO forecast(city_code,zip_code,temperature_celsius,forecast_date) values (p_city_code,zipF,p_temperature,p_date);
    select last_insert_id() into p_new_forecast_id;
    commit;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE find_all_forecasts(in p_forecast_id int)
BEGIN
	if(p_forecast_id <= 0) then	 
	-- Agregar Bloque transaccional
		select * from forecast order by forecast_code asc;
	else 
		select * from forecast where forecast_code = p_forecast_id;
	end if;

END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE find_all_forecasts_date(in p_date DATE)
BEGIN
	if(p_date <=> null) then	 
	-- Agregar Bloque transaccional
		select * from forecast order by forecast_date asc;
	else 
		select * from forecast where forecast_date = p_date;
	end if;

END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE find_all_forecasts_zip(in p_zip int)
BEGIN
	if(p_zip <= 0) then	 
	-- Agregar Bloque transaccional
		select * from forecast order by zip_code asc;
	else 
		select * from forecast where zip_code = p_zip;
	end if;

END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE delete_forecast(in p_forecast_id int)
BEGIN

	-- Agregar Bloque transaccional
    START TRANSACTION;
    DELETE FROM forecast WHERE forecast_code = p_forecast_id;
    commit;


END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_forecast(
	IN p_forecast_id int,
    IN p_city_code int,
    IN p_temperature int,
    IN p_date date
    )
BEGIN
	-- Agregar Bloque transaccional ,,
    DECLARE zipF INT; -- Se declara la variable para almacenar el código postal

    -- Obtener el código postal para la ciudad
    START TRANSACTION;
    SELECT zip_code INTO zipF FROM city WHERE city_code = p_city_code;
    update forecast set city_code = p_city_code, temperature_celsius=p_temperature,forecast_date = p_date,zip_code = zipF where p_forecast_id = forecast_code;
    commit;
END$$
DELIMITER ;
-- forecastLog ========================================
DELIMITER $$
CREATE PROCEDURE find_forecasts_log(in p_cantidad int)
BEGIN
	
    if(p_cantidad <= 0) then	 

		SELECT * 
		FROM forecast_log
		ORDER BY last_modified_on DESC, id DESC;
		
	else 
		SELECT * 
		FROM forecast_log
		ORDER BY last_modified_on DESC, id DESC
		LIMIT p_cantidad;
	end if;
	

END$$
DELIMITER ;

-- Triggers======================================================================
drop TRIGGER if exists after_forecast_insert;
drop TRIGGER if exists after_forecast_update;
drop TRIGGER if exists after_forecast_delete;
DELIMITER //

CREATE TRIGGER after_forecast_insert
AFTER INSERT ON forecast
FOR EACH ROW
BEGIN
    INSERT INTO forecast_log (last_modified_on, entry_text, forecast_code)
    VALUES (CURDATE(), CONCAT('New forecast for city: ', (SELECT city_name FROM city WHERE city_code = NEW.city_code)), NEW.forecast_code);
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_forecast_update
AFTER UPDATE ON forecast
FOR EACH ROW
BEGIN
    INSERT INTO forecast_log (last_modified_on, entry_text, forecast_code)
    VALUES (CURDATE(), CONCAT('Update. Old temperature ', OLD.temperature_celsius, '. New value ', NEW.temperature_celsius), NEW.forecast_code);
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_forecast_delete
AFTER DELETE ON forecast
FOR EACH ROW
BEGIN
    INSERT INTO forecast_log (last_modified_on, entry_text, forecast_code)
    VALUES (CURDATE(), 'Forecast deleted', OLD.forecast_code);
END;
//

DELIMITER ;





-- Ejemplos de países y ciudades para tu base de datos
-- País 1: Estados Unidos
CALL create_country('United States', @country_last_id);
CALL create_state('California', @country_last_id, @state_last_id);
CALL create_city('Los Angeles', '90001', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 30, '2023-10-16', @forecast_last_id);
CALL create_forecast(@city_last_id, 37, '2023-10-20', @forecast_last_id);

-- País 2: Reino Unido
CALL create_country('United Kingdom', @country_last_id);
CALL create_state('England', @country_last_id, @state_last_id);
CALL create_city('London', '90002', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 25, '2023-10-16', @forecast_last_id);

-- País 3: Canadá
CALL create_country('Canada', @country_last_id);
CALL create_state('Ontario', @country_last_id, @state_last_id);
CALL create_city('Toronto', '90003', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 20, '2023-10-17', @forecast_last_id);

-- País 4: Australia
CALL create_country('Australia', @country_last_id);
CALL create_state('New South Wales', @country_last_id, @state_last_id);
CALL create_city('Sydney', '90004', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 28, '2023-10-17', @forecast_last_id);

-- País 5: Francia
CALL create_country('France', @country_last_id);
CALL create_state('Île-de-France', @country_last_id, @state_last_id);
CALL create_city('Paris', '90005', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 22, '2023-10-18', @forecast_last_id);

-- País 6: Alemania
CALL create_country('Germany', @country_last_id);
CALL create_state('Berlin', @country_last_id, @state_last_id);
CALL create_city('Berlin', '90006', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 18, '2023-10-18', @forecast_last_id);

-- País 7: Brasil
CALL create_country('Brazil', @country_last_id);
CALL create_state('São Paulo', @country_last_id, @state_last_id);
CALL create_city('São Paulo', '90007', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 25, '2023-10-18', @forecast_last_id);

-- País 8: Japón
CALL create_country('Japan', @country_last_id);
CALL create_state('Tokyo', @country_last_id, @state_last_id);
CALL create_city('Tokyo', '90008', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 24, '2023-10-19', @forecast_last_id);

-- País 9: México
CALL create_country('Mexico', @country_last_id);
CALL create_state('Mexico City', @country_last_id, @state_last_id);
CALL create_city('Mexico City', '90009', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 23, '2023-10-19', @forecast_last_id);

-- País 10: Italia
CALL create_country('Italy', @country_last_id);
CALL create_state('Lazio', @country_last_id, @state_last_id);
CALL create_city('Rome', '90010', @state_last_id, @city_last_id);
CALL create_forecast(@city_last_id, 21, '2023-10-20', @forecast_last_id);



call find_all_countries(0) ;
call find_all_states(0) ;
call find_all_citys(0) ;

call find_all_forecasts(0) ;
call find_forecasts_log(0) ;





