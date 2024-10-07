-- Creación de tablas

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    telefono VARCHAR(20)
);

CREATE TABLE Categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100)
);

CREATE TABLE Ingrediente (
    id_ingrediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DECIMAL(10, 2),
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

CREATE TABLE ProductoIngrediente (
    id_producto INT,
    id_ingrediente INT,
    PRIMARY KEY (id_producto, id_ingrediente),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto),
    FOREIGN KEY (id_ingrediente) REFERENCES Ingrediente(id_ingrediente)
);

CREATE TABLE LocalDeComida (
    id_local INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE OrdenDeCompra (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(50),
    total DECIMAL(10, 2),
    fecha_hora DATETIME,
    id_usuario INT,
    id_local INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_local) REFERENCES LocalDeComida(id_local)
);

CREATE TABLE DetalleOrden (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT,
    precio DECIMAL(10, 2),
    id_orden INT,
    id_producto INT,
    FOREIGN KEY (id_orden) REFERENCES OrdenDeCompra(id_orden),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

-- Inserción de datos

INSERT INTO Categoria (nombre_categoria) VALUES ('Pizzas'), ('Hamburguesas'), ('Sushi');

INSERT INTO Ingrediente (nombre) VALUES ('Queso'), ('Tomate'), ('Pepperoni'), ('Pan'), ('Cheddar'), ('Panceta');

INSERT INTO Producto (nombre, precio, id_categoria) VALUES 
('Pizza Margherita', 800.00, 1),
('Pizza Pepperoni', 850.00, 1),
('Hamburguesa Completa', 500.00, 2),
('Hamburguesa Cheddar', 600.00, 2),
('Sushi Roll', 700.00, 3);

INSERT INTO ProductoIngrediente (id_producto, id_ingrediente) VALUES 
(1, 1), -- Pizza Margherita con Queso
(1, 2), -- Pizza Margherita con Tomate
(2, 1), -- Pizza Pepperoni con Queso
(2, 3), -- Pizza Pepperoni con Pepperoni
(3, 4), -- Hamburguesa Completa con Pan
(3, 1), -- Hamburguesa Completa con Queso
(4, 4), -- Hamburguesa Cheddar con Pan
(4, 5), -- Hamburguesa Cheddar con Cheddar
(4, 6); -- Hamburguesa Cheddar con Panceta

INSERT INTO LocalDeComida (nombre, direccion, telefono) VALUES 
('Pizza Express', 'Calle Falsa 123', '123456789'),
('Burger King', 'Calle Real 456', '987654321');

INSERT INTO Usuario (nombre, email, telefono) VALUES 
('Juan Pérez', 'juan@example.com', '111222333'),
('Ana López', 'ana@example.com', '444555666'),
('Carlos García', 'carlos@example.com', '777888999');

-- Órdenes del día de hoy
INSERT INTO OrdenDeCompra (estado, total, fecha_hora, id_usuario, id_local) VALUES 
('En preparación', 1350.00, NOW(), 1, 1), -- Pedido de hoy de Juan Pérez en Pizza Express
('Completado', 600.00, NOW(), 2, 2); -- Pedido de hoy de Ana López en Burger King

-- Órdenes anteriores
INSERT INTO OrdenDeCompra (estado, total, fecha_hora, id_usuario, id_local) VALUES 
('Completado', 850.00, '2024-10-03 14:30:00', 1, 1), -- Pedido anterior de Juan Pérez en Pizza Express
('Completado', 500.00, '2024-10-01 12:00:00', 2, 2), -- Pedido anterior de Ana López en Burger King
('Completado', 1200.00, '2024-09-30 18:00:00', 3, 2); -- Pedido anterior de Carlos García en Burger King

-- Detalles para los pedidos de hoy
INSERT INTO DetalleOrden (cantidad, precio, id_orden, id_producto) VALUES 
(1, 800.00, 1, 1),  -- Pizza Margherita para el pedido de Juan Pérez
(1, 550.00, 1, 4),  -- Hamburguesa Cheddar para el pedido de Juan Pérez
(1, 600.00, 2, 4);  -- Hamburguesa Cheddar para el pedido de Ana López

-- Detalles para pedidos anteriores
INSERT INTO DetalleOrden (cantidad, precio, id_orden, id_producto) VALUES 
(1, 850.00, 3, 2),  -- Pizza Pepperoni para el pedido anterior de Juan Pérez
(1, 500.00, 4, 3),  -- Hamburguesa Completa para el pedido anterior de Ana López
(2, 600.00, 5, 4);  -- 2 Hamburguesas Cheddar para Carlos García

-- Consultas de ejemplo

-- 1. Ver todos los pedidos del día de hoy (desde el punto de vista del local)
SELECT o.id_orden, o.estado, o.total, o.fecha_hora, u.nombre AS Usuario
FROM OrdenDeCompra o
JOIN Usuario u ON o.id_usuario = u.id_usuario
WHERE DATE(o.fecha_hora) = CURDATE();

-- 2. Ver mis últimos 10 pedidos (desde el punto de vista del usuario)
SELECT o.id_orden, o.estado, o.total, o.fecha_hora, l.nombre AS Local
FROM OrdenDeCompra o
JOIN LocalDeComida l ON o.id_local = l.id_local
WHERE o.id_usuario = 1  -- ID del usuario Juan Pérez
ORDER BY o.fecha_hora DESC
LIMIT 10;

-- 3. Ver todas las hamburguesas que tengan cheddar y panceta
SELECT p.nombre AS Producto, c.nombre_categoria AS Categoria
FROM Producto p
JOIN Categoria c ON p.id_categoria = c.id_categoria
JOIN ProductoIngrediente pi ON p.id_producto = pi.id_producto
WHERE p.id_categoria = 2  -- ID de la categoría "Hamburguesas"
AND pi.id_ingrediente IN (5, 6)  -- Cheddar = 5, Panceta = 6
GROUP BY p.nombre, c.nombre_categoria
HAVING COUNT(DISTINCT pi.id_ingrediente) = 2;