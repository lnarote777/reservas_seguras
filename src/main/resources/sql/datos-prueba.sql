-- Insertar usuarios en la tabla 'usuarios'
INSERT INTO usuarios (nombre, password) VALUES ('juanperez', 'password123'), ('maria_gomez', 'securepass'), ('carlos_lopez', 'mypassword');
-- Insertar reservas en la tabla 'reservas'
INSERT INTO reservas (destino, origen, fecha_ida, fecha_regreso, id_usuario) VALUES ('Cancún', 'Ciudad de México', '2024-12-01 08:00:00', '2024-12-07 20:00:00', 1), ('Madrid', 'Barcelona', '2025-01-15 10:30:00', '2025-01-25 18:45:00', 2), ('París', 'Lyon', '2025-02-10 06:00:00', '2025-02-20 22:00:00', 3), ('Nueva York', 'Los Ángeles', '2024-11-25 15:00:00', '2024-11-30 20:00:00', 1), ('Tokio', 'Osaka', '2024-12-10 12:00:00', '2024-12-20 21:00:00', 2);
