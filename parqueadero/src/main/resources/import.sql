-- Insertar los roles
INSERT INTO rol (nombre) VALUES ('ADMIN');
INSERT INTO rol (nombre) VALUES ('SOCIO');

-- Insertar el usuario administrador
INSERT INTO usuario (nombre, correo, contrasena, rol_id, fecha_registro) VALUES ('Admin', 'admin@mail.com', '$2a$12$9E0k3gwv2Omn6bivgsXFveprf8TKmZmp.EmnZF/89jKAIXocd1PeS', 1, current_timestamp);
