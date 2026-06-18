-- Setup local de MySQL para prode-api.
-- Ejecutar este archivo en MySQL Workbench con un usuario administrador, por ejemplo root.

CREATE DATABASE IF NOT EXISTS prode_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'prode_user'@'localhost'
IDENTIFIED BY 'prode_pass';

GRANT ALL PRIVILEGES ON prode_db.* TO 'prode_user'@'localhost';
FLUSH PRIVILEGES;

USE prode_db;

SHOW TABLES;
