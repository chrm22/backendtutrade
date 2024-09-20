-- Roles del sistema
INSERT INTO db_tutrade.public.roles(nombre)
VALUES ('ROL_USUARIO'), ('ROL_ADMIN');

-- Usuarios del sistema

-- Usuario: "admin", Contraseña: "password"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado)
VALUES (
        'admin',
        '$2a$12$YM6vvmdxapoTx.j20rEAV.eti1dzpTF/zxwYm1Vtz3byCcw1HHd8W',
        '2024-09-19 18:30:00',
        'activo'
       );

-- Usuario: "testUser1", Contraseña: "password1"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado)
VALUES (
           'testUser1',
           '$2a$12$lGXjBgCHtat9Pr1SuE2sDOSrW5AbyqjrWKOARkNpm1.ZgwIYcPrKG',
           '2024-09-19 18:30:00',
           'activo'
       );

-- Usuario: "testUser2", Contraseña: "password2"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado)
VALUES (
           'testUser2',
           '$2a$12$e.3djq1EkZf.EcIB7ABYzOBvZ3aDbTjdY8Swxky9yxXR/crBhAzHG',
           '2024-09-19 18:30:00',
           'activo'
       );

-- Usuario: "testUser3", Contraseña: "password3"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado)
VALUES (
           'testUser3',
           '$2a$12$zXF1G2vD9uUooLczYXMpNOct1aZj7WiqsSJCrKmefkuANSsiYwTfi',
           '2024-09-20 18:30:00',
           'activo'
       );

-- Asignación de roles para cada usuario
INSERT INTO db_tutrade.public.roles_usuarios(rol_id, usuario_id)
VALUES(2, 1), (1, 1), (1, 2), (1, 3);

-- Asignación de información para cada usuario
INSERT INTO db_tutrade.public.informacion_usuarios
    (usuario_id, nombre, apellido, dni, fecha_nacimiento, email,
     telefono, ciudad, pais, cantidad_intercambios, verificado)
VALUES (2, 'Lucía', 'Gómez', '12345678', '1995-05-15', 'lucia.gomez@test.com',
        '912345678', 'Cusco', 'Perú', 0, false),
       (3, 'Fernando', 'Martínez', '87654321', '1988-08-20', 'fernando.m@test.com',
        '923456789', 'Arequipa', 'Perú', 0, false),
       (4, 'Sofía', 'Vásquez', '11223344', '1992-11-30', 'sofia.vazquez@test.com',
        '934567890', 'Callao', 'Perú', 0, false);

-- Inserción de artículos
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado)
VALUES
    (2, 'Televisor Samsung 55"', 'Televisor en excelente estado, poco uso.', true, 'disponible'),
    (2, 'Consola PlayStation 5', 'Consola en perfectas condiciones, incluye dos controles.', true, 'disponible'),
    (2, 'Teclado Mecánico Razer', 'Teclado mecánico RGB en perfectas condiciones.', false, 'disponible'),
    (3, 'Impresora HP DeskJet', 'Impresora multifuncional, ideal para oficina en casa.', true, 'disponible'),
    (3, 'Smartwatch Garmin', 'Smartwatch en excelente estado, incluye cargador.', true, 'disponible'),
    (3, 'Cámara Canon EOS', 'Cámara en buenas condiciones, incluye lente.', false, 'disponible'),
    (4, 'Tablet Samsung Galaxy', 'Tablet en buen estado, con cargador original.', true, 'disponible'),
    (4, 'Proyector Epson', 'Proyector en excelentes condiciones, ideal para presentaciones.', true, 'disponible'),
    (4, 'Parlantes JBL', 'Parlantes portátiles, en muy buen estado.', false, 'disponible');

-- Inserción de etiquetas por artículo
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES
    (1, 'televisor'),       (6, 'Samsung'),
    (2, 'consola'),         (7, 'PlayStation'),
    (3, 'teclado'),         (8, 'mecánico'),
    (4, 'impresora'),       (9, 'HP'),
    (5, 'smartwatch'),     (10, 'Garmin'),
    (6, 'cámara'),         (11, 'Canon'),
    (7, 'tablet'),         (12, 'Samsung'),
    (8, 'proyector'),      (13, 'Epson'),
    (9, 'parlantes'),      (14, 'JBL');

-- Inserción de imágenes
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES
    (1, 1, 'https://imgurl.com/abc131', 'Televisor encendido'),
    (1, 2, 'https://imgurl.com/abc131', 'Televisor apagado'),
    (2, 1, 'https://imgurl.com/abc132', ''),
    (3, 1, 'https://imgurl.com/abc133', 'Teclado iluminado'),
    (4, 1, 'https://imgurl.com/abc134', ''),
    (5, 1, 'https://imgurl.com/abc135', ''),
    (6, 1, 'https://imgurl.com/abc136', 'Cámara con lente'),
    (7, 1, 'https://imgurl.com/abc137', ''),
    (8, 1, 'https://imgurl.com/abc138', 'Proyector en acción'),
    (9, 1, 'https://imgurl.com/abc139', ''),
    (9, 2, 'https://imgurl.com/abc140', 'Parlantes en uso');

-- Inserción de pedidos de prueba
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES
(1, 5, '2024-09-19 17:15:10.123456', 'pendiente'),
(2, 6, '2024-09-19 17:20:30.654321', 'pendiente'),
(2, 9, '2024-09-19 17:25:40.789012', 'pendiente'),
(2, 4, '2024-09-19 17:30:50.111213', 'pendiente'),
(5, 9, '2024-09-19 17:35:00.141516', 'pendiente'),
(6, 1, '2024-09-19 17:40:20.171819', 'pendiente');