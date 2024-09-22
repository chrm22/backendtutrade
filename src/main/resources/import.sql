-- Roles del sistema
BEGIN;
INSERT INTO db_tutrade.public.roles(nombre) VALUES ('ROL_USUARIO'), ('ROL_ADMIN'), ('ROL_MODERADOR');
COMMIT;

-- Usuarios del sistema

BEGIN;

-- Usuario: "admin", Contraseña: "password"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado) VALUES ('admin','$2a$12$YM6vvmdxapoTx.j20rEAV.eti1dzpTF/zxwYm1Vtz3byCcw1HHd8W','2024-09-19 18:30:00','activo');

-- Usuario: "testUser1", Contraseña: "password1"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado) VALUES ('testUser1','$2a$12$lGXjBgCHtat9Pr1SuE2sDOSrW5AbyqjrWKOARkNpm1.ZgwIYcPrKG','2024-09-19 18:30:00','activo');

-- Usuario: "testUser2", Contraseña: "password2"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado) VALUES ('testUser2','$2a$12$e.3djq1EkZf.EcIB7ABYzOBvZ3aDbTjdY8Swxky9yxXR/crBhAzHG','2024-09-19 18:30:00','activo');

-- Usuario: "testUser3", Contraseña: "password3"
INSERT INTO db_tutrade.public.usuarios(username, password, fecha_hora_creacion, estado) VALUES ('testUser3','$2a$12$zXF1G2vD9uUooLczYXMpNOct1aZj7WiqsSJCrKmefkuANSsiYwTfi','2024-09-20 18:30:00','activo');

COMMIT;

-- Asignación de roles para cada usuario
BEGIN;
INSERT INTO db_tutrade.public.roles_usuarios(rol_id, usuario_id) VALUES(2, 1), (3, 1), (1, 2), (1, 3), (1, 4);
COMMIT;

-- Asignación de información para cada usuario
BEGIN;
INSERT INTO db_tutrade.public.informacion_usuarios (usuario_id, nombre, apellido, dni, fecha_nacimiento, email, telefono, ciudad, pais, cantidad_intercambios, verificado) VALUES (2, 'Lucía', 'Gómez', '12345678', '1995-05-15', 'lucia.gomez@test.com', '912345678', 'Cusco', 'Perú', 0, false);
INSERT INTO db_tutrade.public.informacion_usuarios (usuario_id, nombre, apellido, dni, fecha_nacimiento, email, telefono, ciudad, pais, cantidad_intercambios, verificado) VALUES (3, 'Fernando', 'Martínez', '87654321', '1988-08-20', 'fernando.m@test.com', '923456789', 'Arequipa', 'Perú', 0, false);
INSERT INTO db_tutrade.public.informacion_usuarios (usuario_id, nombre, apellido, dni, fecha_nacimiento, email, telefono, ciudad, pais, cantidad_intercambios, verificado) VALUES (4, 'Sofía', 'Vásquez', '11223344', '1992-11-30', 'sofia.vazquez@test.com', '934567890', 'Callao', 'Perú', 0, false);
COMMIT;

-- Inserción de artículos
BEGIN;
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (2, 'Televisor Samsung 55"', 'Televisor en excelente estado, poco uso.', true, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (2, 'Consola PlayStation 5', 'Consola en perfectas condiciones, incluye dos controles.', true, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (2, 'Teclado Mecánico Razer', 'Teclado mecánico RGB en perfectas condiciones.', false, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (3, 'Impresora HP DeskJet', 'Impresora multifuncional, ideal para oficina en casa.', true, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (3, 'Smartwatch Garmin', 'Smartwatch en excelente estado, incluye cargador.', true, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (3, 'Cámara Canon EOS', 'Cámara en buenas condiciones, incluye lente.', false, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (4, 'Tablet Samsung Galaxy', 'Tablet en buen estado, con cargador original.', true, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (4, 'Proyector Epson', 'Proyector en excelentes condiciones, ideal para presentaciones.', true, 'disponible');
INSERT INTO db_tutrade.public.articulos (usuario_id, nombre, descripcion, publico, estado) VALUES (4, 'Parlantes JBL', 'Parlantes portátiles, en muy buen estado.', false, 'disponible');
COMMIT;

-- Inserción de etiquetas por artículo

BEGIN;
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (1, 'televisor'),    (1, 'Samsung');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (2, 'consola'),  (2, 'PlayStation');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (3, 'teclado'),  (3, 'mecánico'),    (3, 'nuevo');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (4, 'impresora'),    (4, 'HP'),      (4, 'nuevo');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (5, 'smartwatch'),   (5, 'Garmin'),   (5, 'accesorio');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (6, 'cámara'),   (6, 'Canon'),    (6, 'seminuevo');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (7, 'tablet'),   (7, 'Samsung'),  (7, 'nuevo');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (8, 'proyector'),    (8, 'Epson'),    (8, 'seminuevo');
INSERT INTO db_tutrade.public.etiquetas_articulos (articulo_id, nombre) VALUES (9, 'parlantes'),    (9, 'JBL'),      (9, 'audio');
COMMIT;

-- Inserción de imágenes

BEGIN;
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES (1, 1, 'https://imgurl.com/abc131', 'Televisor encendido');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES (1, 2, 'https://imgurl.com/abc131', 'Televisor apagado');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen) VALUES (2, 1, 'https://imgurl.com/abc132');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES (3, 1, 'https://imgurl.com/abc133', 'Teclado iluminado');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen) VALUES (4, 1, 'https://imgurl.com/abc134');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen) VALUES (5, 1, 'https://imgurl.com/abc135');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES (6, 1, 'https://imgurl.com/abc136', 'Cámara con lente');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen) VALUES (7, 1, 'https://imgurl.com/abc137');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES (8, 1, 'https://imgurl.com/abc138', 'Proyector en acción');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen) VALUES (9, 1, 'https://imgurl.com/abc139');
INSERT INTO db_tutrade.public.imagenes_articulos (articulo_id, nro_imagen_articulo, url_imagen, descripcion) VALUES (9, 2, 'https://imgurl.com/abc140', 'Parlantes en uso');
COMMIT;

-- Inserción de pedidos de prueba

BEGIN;
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES (1, 5, '2024-09-19 17:15:10.123456', 'pendiente');
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES (2, 6, '2024-09-19 17:20:30.654321', 'pendiente');
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES (2, 9, '2024-09-19 17:25:40.789012', 'pendiente');
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES (2, 4, '2024-09-19 17:30:50.111213', 'pendiente');
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES (5, 9, '2024-09-19 17:35:00.141516', 'pendiente');
INSERT INTO db_tutrade.public.pedidos_articulos (articulo_id, articulo_ofrecido_id, fecha_hora_creacion, estado) VALUES (6, 1, '2024-09-19 17:40:20.171819', 'pendiente');
COMMIT;