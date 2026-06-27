-- Setup completo de MySQL para prode-api.
-- Ejecutar todo este archivo en MySQL Workbench con un usuario administrador, por ejemplo root.
-- Crea la base prode_db, el usuario usado por application.properties y datos iniciales limpios.

CREATE DATABASE IF NOT EXISTS prode_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'prode_user'@'localhost'
IDENTIFIED BY 'prode_pass';

ALTER USER 'prode_user'@'localhost'
IDENTIFIED BY 'prode_pass';

GRANT ALL PRIVILEGES ON prode_db.* TO 'prode_user'@'localhost';
FLUSH PRIVILEGES;

USE prode_db;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS predicciones;
DROP TABLE IF EXISTS miembros_grupo;
DROP TABLE IF EXISTS partidos;
DROP TABLE IF EXISTS grupos;
DROP TABLE IF EXISTS jornadas;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS equipos;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE equipos (
  id_equipo bigint NOT NULL AUTO_INCREMENT,
  esta_activo bit(1) NOT NULL,
  nombre varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (id_equipo),
  UNIQUE KEY UK_equipos_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
  id_usuario bigint NOT NULL AUTO_INCREMENT,
  apellido varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  contrasena varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  correo varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  nombre varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  puntos int NOT NULL,
  rol enum('USUARIO','ADMIN') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (id_usuario),
  UNIQUE KEY UK_usuarios_correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE jornadas (
  id_jornada bigint NOT NULL AUTO_INCREMENT,
  estado_jornada enum('PROGRAMADA','EN_JUEGO','FINALIZADA') COLLATE utf8mb4_unicode_ci NOT NULL,
  fecha_fin date NOT NULL,
  fecha_inicio date NOT NULL,
  nombre varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (id_jornada)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE grupos (
  id_grupo bigint NOT NULL AUTO_INCREMENT,
  codigo_invitacion varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  descripcion varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  nombre varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  creador_id bigint NOT NULL,
  PRIMARY KEY (id_grupo),
  UNIQUE KEY UK_grupos_codigo_invitacion (codigo_invitacion),
  KEY FK_grupos_creador (creador_id),
  CONSTRAINT FK_grupos_creador FOREIGN KEY (creador_id) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE partidos (
  id_partido bigint NOT NULL AUTO_INCREMENT,
  estado_partido enum('PROGRAMADO','EN_JUEGO','FINALIZADO') COLLATE utf8mb4_unicode_ci NOT NULL,
  goles_local int NOT NULL,
  goles_visitante int NOT NULL,
  hora_inicio datetime(6) NOT NULL,
  resultado_final enum('LOCAL','VISITANTE','EMPATE') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  equipo_local_id bigint NOT NULL,
  equipo_visitante_id bigint NOT NULL,
  jornada_id bigint NOT NULL,
  PRIMARY KEY (id_partido),
  KEY FK_partidos_equipo_local (equipo_local_id),
  KEY FK_partidos_equipo_visitante (equipo_visitante_id),
  KEY FK_partidos_jornada (jornada_id),
  CONSTRAINT FK_partidos_equipo_local FOREIGN KEY (equipo_local_id) REFERENCES equipos (id_equipo),
  CONSTRAINT FK_partidos_equipo_visitante FOREIGN KEY (equipo_visitante_id) REFERENCES equipos (id_equipo),
  CONSTRAINT FK_partidos_jornada FOREIGN KEY (jornada_id) REFERENCES jornadas (id_jornada)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE miembros_grupo (
  id_miembro bigint NOT NULL AUTO_INCREMENT,
  fecha_ingreso datetime(6) NOT NULL,
  grupo_id bigint NOT NULL,
  usuario_id bigint NOT NULL,
  PRIMARY KEY (id_miembro),
  UNIQUE KEY UK_miembros_grupo_usuario (grupo_id, usuario_id),
  KEY FK_miembros_usuario (usuario_id),
  CONSTRAINT FK_miembros_grupo FOREIGN KEY (grupo_id) REFERENCES grupos (id_grupo),
  CONSTRAINT FK_miembros_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE predicciones (
  id_prediccion bigint NOT NULL AUTO_INCREMENT,
  fecha_creacion datetime(6) NOT NULL,
  goles_local int NOT NULL,
  goles_visitante int NOT NULL,
  puntos_obtenidos int NOT NULL,
  resultado_pronosticado enum('LOCAL','VISITANTE','EMPATE') COLLATE utf8mb4_unicode_ci NOT NULL,
  partido_id bigint NOT NULL,
  usuario_id bigint NOT NULL,
  PRIMARY KEY (id_prediccion),
  UNIQUE KEY UK_predicciones_usuario_partido (usuario_id, partido_id),
  KEY FK_predicciones_partido (partido_id),
  CONSTRAINT FK_predicciones_partido FOREIGN KEY (partido_id) REFERENCES partidos (id_partido),
  CONSTRAINT FK_predicciones_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO usuarios (id_usuario, apellido, contrasena, correo, nombre, puntos, rol) VALUES
(1, 'Sistema', '$argon2id$v=19$m=16384,t=2,p=1$CWH0i0EqJVMIwxSu2vFarA$bbbcpgM8ktuEmybxEOtNJ9pcCgA4XJDmgxtoqJCCSGM', 'admin@prode.com', 'Admin', 0, 'ADMIN'),
(2, 'Demo', '$argon2id$v=19$m=16384,t=2,p=1$pSOPAOftLtyu0QmRJYUKBw$bkplXKtFsa+syN3VAH1H+G1tkeQ81OLTIdGGdtVH0Lc', 'usuario@prode.com', 'Usuario', 0, 'USUARIO');

INSERT INTO equipos (id_equipo, esta_activo, nombre) VALUES
(1, b'1', 'Club Atletico Saca Chispas'),
(2, b'1', 'Generacion Zoe FC'),
(3, b'1', 'River Plate'),
(4, b'1', 'Boca Juniors'),
(5, b'1', 'Racing Club'),
(6, b'1', 'Independiente'),
(7, b'1', 'San Lorenzo'),
(8, b'1', 'Huracan'),
(9, b'1', 'Lanus'),
(10, b'1', 'Estudiantes'),
(11, b'1', 'Velez Sarsfield'),
(12, b'1', 'Atletico Tucuman'),
(13, b'1', 'Deportivo Alem');

SELECT 'Base prode_db inicializada correctamente' AS estado;
SELECT id_usuario, nombre, apellido, correo, puntos, rol FROM usuarios ORDER BY id_usuario;
SELECT id_equipo, nombre, esta_activo FROM equipos ORDER BY id_equipo;
