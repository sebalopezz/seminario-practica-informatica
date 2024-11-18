CREATE DATABASE IF NOT EXISTS comidapp;

USE comidapp;

-- Eliminar tablas existentes si ya existen
DROP TABLE IF EXISTS DetalleOrden;
DROP TABLE IF EXISTS OrdenDeCompra;
DROP TABLE IF EXISTS Producto;
DROP TABLE IF EXISTS Categoria;
DROP TABLE IF EXISTS Usuario;

-- Crear tablas
CREATE TABLE Usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         telefono VARCHAR(20)
);

CREATE TABLE Categoria (
                           id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                           nombre_categoria VARCHAR(100) NOT NULL
);

CREATE TABLE Producto (
                          id_producto INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          precio DECIMAL(10, 2) NOT NULL,
                          id_categoria INT NOT NULL,
                          FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

CREATE TABLE OrdenDeCompra (
                               id_orden INT AUTO_INCREMENT PRIMARY KEY,
                               id_usuario INT NOT NULL,
                               total DECIMAL(10, 2) NOT NULL,
                               FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE DetalleOrden (
                              id_detalle INT AUTO_INCREMENT PRIMARY KEY,
                              id_orden INT NOT NULL,
                              id_producto INT NOT NULL,
                              cantidad INT NOT NULL DEFAULT 1,
                              FOREIGN KEY (id_orden) REFERENCES OrdenDeCompra(id_orden),
                              FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

-- Insertar datos iniciales
INSERT INTO Categoria (nombre_categoria) VALUES
                                             ('Hamburguesas'),
                                             ('Pizzas'),
                                             ('Bebidas');

INSERT INTO Producto (nombre, precio, id_categoria) VALUES
                                                        ('Hamburguesa con queso', 10.00, 1),
                                                        ('Pizza Napolitana', 12.50, 2),
                                                        ('Coca-Cola', 2.50, 3);

INSERT INTO Usuario (nombre, email, telefono) VALUES
                                                  ('Juan Pérez', 'juan@example.com', '123456789'),
                                                  ('María García', 'maria@example.com', '987654321');
