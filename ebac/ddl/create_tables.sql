CREATE TABLE proveedor (
    id_proveedor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL
);

CREATE TABLE producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    costo FLOAT NOT NULL,
    id_proveedor BIGINT,
    CONSTRAINT fk_producto_proveedor
        FOREIGN KEY (id_proveedor)
        REFERENCES proveedor(id_proveedor)
);

CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);