-- Sentencias SQL para la creación del esquema de SuperAndes

-- Creación del secuenciador
create sequence superandes_sequence;

-- Creación de la tabla SUCURSAL y especificación de sus restricciones
CREATE TABLE SUCURSAL (
    ID NUMBER NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    CIUDAD VARCHAR2(255 BYTE) NOT NULL,
    DIRECCION VARCHAR2(255 BYTE) NOT NULL
);

-- Creación de la tabla CATEGORIA y especificación de sus restricciones
CREATE TABLE CATEGORIA(
    ID NUMBER NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL
);

-- Creación de la tabla PRODUCTO y especificación de sus restricciones
CREATE TABLE PRODUCTO (
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    MARCA VARCHAR2(255 BYTE) NOT NULL,
    PRECIO_UNITARIO NUMBER NOT NULL CHECK(PRECIO_UNITARIO > 0),
    PRESENTACION VARCHAR2(255 BYTE) NOT NULL,
    PRECIO_UNIDADMEDIDA NUMBER(18,4) NOT NULL CHECK(PRECIO_UNIDADMEDIDA > 0),
    UNIDAD_MEDIDA VARCHAR2(255 BYTE) NOT NULL,
    EMPACADO VARCHAR2(255 BYTE) NOT NULL,
    CODIGO_BARRAS VARCHAR2(255 BYTE) NOT NULL PRIMARY KEY,
    ID_CATEGORIA NUMBER NOT NULL,
    CONSTRAINT FK_CATEGORIA FOREIGN KEY (ID_CATEGORIA)
    REFERENCES CATEGORIA(ID),
    NIVEL_REORDEN NUMBER NOT NULL,
    EXISTENCIAS NUMBER NOT NULL,
    FECHA_VENCIMIENTO DATE
);

-- Creación de la tabla ORDEN y especificación de sus restricciones
CREATE TABLE ORDEN(
    ID NUMBER NOT NULL PRIMARY KEY,
    PRECIO NUMBER(18,4) NOT NULL CHECK(PRECIO>0),
    FECHA_ESPERADA DATE NOT NULL,
    FECHA_LLEGADA DATE NOT NULL,
    ESTADO VARCHAR2(255 BYTE) NOT NULL CHECK(ESTADO IN( 'PENDIENTE', 'ENTREGADA')),
    ID_SUCURSAL NUMBER NOT NULL,
    CONSTRAINT FK_ORDEN_SUCURSAL FOREIGN KEY (ID_SUCURSAL)
    REFERENCES SUCURSAL(ID)
);

-- Creación de la tabla ESTANTE y especificación de sus restricciones
CREATE TABLE ESTANTE (
    ID NUMBER NOT NULL PRIMARY KEY,
    ID_CATEGORIA NUMBER NOT NULL,
    CONSTRAINT FK_CATEGORIA_ESTANTE FOREIGN KEY (ID_CATEGORIA)
    REFERENCES CATEGORIA(ID),
    CAPACIDAD_PESO NUMBER(18,4) NOT NULL CHECK (CAPACIDAD_PESO>0),
    CAPACIDAD_VOLUMEN NUMBER(18,4) NOT NULL CHECK (CAPACIDAD_VOLUMEN>0),
    ID_SUCURSAL NUMBER NOT NULL,
    CONSTRAINT FK_SUCURSAL_ESTANTE FOREIGN KEY (ID_SUCURSAL)
    REFERENCES SUCURSAL(ID)
);

-- Creación de la tabla BODEGA y especificación de sus restricciones
CREATE TABLE BODEGA(
    ID NUMBER NOT NULL PRIMARY KEY,
    ID_CATEGORIA NUMBER NOT NULL,
    CONSTRAINT FK_CATEGORIA_BODEGA FOREIGN KEY (ID_CATEGORIA)
    REFERENCES CATEGORIA(ID),
    CAPACIDAD_PESO NUMBER(18,4) NOT NULL,
    CAPACIDAD_VOLUMEN NUMBER(18,4) NOT NULL,
    ID_SUCURSAL NUMBER NOT NULL,
    CONSTRAINT FK_SUCURSAL_BODEGA FOREIGN KEY (ID_SUCURSAL)
    REFERENCES SUCURSAL(ID)
);

-- Creación de la tabla ABASTECIMIENTO y especificación de sus restricciones
CREATE TABLE ABASTECIMIENTO(
    ID NUMBER NOT NULL PRIMARY KEY,
    ID_CATEGORIA NUMBER,
    CONSTRAINT FK_CATEGORIA_PEDIDO_BODEGA FOREIGN KEY (ID_CATEGORIA)
    REFERENCES CATEGORIA(ID),
    ID_ESTANTE NUMBER,
    CONSTRAINT FK_ESTANTE_PEDIDO_BODEGA FOREIGN KEY (ID_ESTANTE)
    REFERENCES ESTANTE(ID)
);

-- Creación de la tabla PROVEEDOR y especificación de sus restricciones
CREATE TABLE PROVEEDOR(
    NIT VARCHAR2(255 BYTE) NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    CALIFICACION NUMBER CHECK (CALIFICACION>0)
);

-- Creación de la tabla CLIENTE y especificación de sus restricciones
CREATE TABLE CLIENTE(
    ID NUMBER NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    CORREO VARCHAR2(255 BYTE) NOT NULL
);

-- Creación de la tabla PERSONA y especificación de sus restricciones
CREATE TABLE PERSONA(
    ID_CLIENTE NUMBER NOT NULL,
    CONSTRAINT FK_CLIENTE_PERSONA FOREIGN KEY (ID_CLIENTE)
    REFERENCES CLIENTE(ID),
    IDENTIFICACION NUMBER NOT NULL,
    PRIMARY KEY(ID_CLIENTE, IDENTIFICACION)
);

-- Creación de la tabla EMPRESA y especificación de sus restricciones
CREATE TABLE EMPRESA(
    ID_CLIENTE NUMBER,
    CONSTRAINT FK_CLIENTE_EMPRESA FOREIGN KEY (ID_CLIENTE)
    REFERENCES CLIENTE(ID),
    NIT VARCHAR2(255 BYTE),
    DIRECCION VARCHAR2(255 BYTE),
    PRIMARY KEY (ID_CLIENTE, NIT)
);

-- Creación de la tabla CAJERO y especificación de sus restricciones
CREATE TABLE CAJERO(
    ID NUMBER NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(255 BYTE),
    ID_SUCURSAL NUMBER NOT NULL,
    CONSTRAINT FK_SUCURSAL_CAJERO FOREIGN KEY (ID_SUCURSAL)
    REFERENCES SUCURSAL(ID)
);

-- Creación de la tabla FACTURA y especificación de sus restricciones
CREATE TABLE FACTURA(
    ID NUMBER NOT NULL PRIMARY KEY,
    FECHA DATE NOT NULL,
    PRECIO_TOTAL NUMBER(18,4) NOT NULL,
    ID_CAJERO NUMBER NOT NULL,
    CONSTRAINT FK_CAJERO_FACTURA FOREIGN KEY (ID_CAJERO)
    REFERENCES CAJERO(ID),
    ID_CLIENTE NUMBER NOT NULL,
    CONSTRAINT FK_CLIENTE_FACTURA FOREIGN KEY (ID_CLIENTE)
    REFERENCES CLIENTE(ID)
);

-- Creación de la tabla PROMOCION y especificación de sus restricciones
CREATE TABLE PROMOCION(
    ID NUMBER NOT NULL PRIMARY KEY,
    TIPO NUMBER NOT NULL,
    PRECIO NUMBER(18,4) NOT NULL CHECK(PRECIO>0),
    FECHA_INICIO DATE NOT NULL,
    FECHA_FIN DATE NOT NULL,
    ID_SUCURSAL NUMBER NOT NULL,
    CONSTRAINT FK_SUCURSAL_PROMOCION FOREIGN KEY (ID_SUCURSAL)
    REFERENCES SUCURSAL(ID),
    ID_PRODUCTO VARCHAR2(255 BYTE),
    CONSTRAINT FK_PRODUCTO_PROMOCION FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    CANTIDAD1 NUMBER CHECK(CANTIDAD1>0),
    CANTIDAD2 NUMBER CHECK(CANTIDAD2>0),
    DESCUENTO NUMBER CHECK(DESCUENTO>0)
);

-- Creación de la tabla USUARIO y especificación de sus restricciones
CREATE TABLE USUARIO (
    USERNAME VARCHAR2(255 BYTE) NOT NULL PRIMARY KEY,
    PASSWORD VARCHAR2(255 BYTE) NOT NULL,
    TIPO VARCHAR2(255 BYTE) NOT NULL,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    ID_SUCURSAL NUMBER,
    CONSTRAINT FK_SUCURSAL_USUARIO FOREIGN KEY (ID_SUCURSAL)
    REFERENCES SUCURSAL(ID)
);

--Creación de la tabla CARRITO
CREATE TABLE CARRITO(
    ID NUMBER NOT NULL PRIMARY KEY,
    ID_CLIENTE NUMBER,
    CONSTRAINT FK_CARRITO_CLIENTE FOREIGN KEY (ID_CLIENTE)
    REFERENCES CLIENTE(ID),
    ESTADO VARCHAR2(255 BYTE) CHECK (ESTADO IN('OCUPADO', 'DESOCUPADO', 'ABANDONADO'))
);

-- Creación de la tabla ABASTECIMIENTO_BODEGA y especificación de sus restricciones
CREATE TABLE ABASTECIMIENTO_BODEGA(
    ID_ABASTECIMIENTO NUMBER NOT NULL,
    CONSTRAINT FK_ABAST_BODEGA FOREIGN KEY (ID_ABASTECIMIENTO)
    REFERENCES ABASTECIMIENTO(ID),
    ID_BODEGA NUMBER NOT NULL,
    CONSTRAINT FK_BODEGA_PEDIDOB FOREIGN KEY (ID_BODEGA)
    REFERENCES BODEGA(ID),
    PRIMARY KEY(ID_ABASTECIMIENTO, ID_BODEGA)
);

-- Creación de la tabla SURTIDO_ESTANTE y especificación de sus restricciones
CREATE TABLE SURTIDO(
    ID_ESTANTE NUMBER NOT NULL,
    CONSTRAINT FK_ESTANTE_SURTIDO FOREIGN KEY (ID_ESTANTE)
    REFERENCES ESTANTE(ID),
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_SURTIDO FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_ESTANTE, ID_PRODUCTO)
);

-- Creación de la tabla PRODUCTOS_ABASTECIMIENTO y especificación de sus restricciones
CREATE TABLE PRODUCTO_ABASTECIMIENTO(
    ID_PRODUCTO VARCHAR2(255 BYTE)NOT NULL,
    CONSTRAINT FK_PRODUCTO_ABAST FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    ID_ABASTECIMIENTO NUMBER NOT NULL,
    CONSTRAINT FK_ABAST_PRODUCTO FOREIGN KEY (ID_ABASTECIMIENTO)
    REFERENCES ABASTECIMIENTO(ID),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_PRODUCTO, ID_ABASTECIMIENTO)
);

-- Creación de la tabla INVENTARIO y especificación de sus restricciones
CREATE TABLE INVENTARIO(
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_INVENTARIO FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    ID_BODEGA NUMBER NOT NULL,
    CONSTRAINT FK_BODEGA_INVENTARIO FOREIGN KEY (ID_BODEGA)
    REFERENCES BODEGA(ID),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_PRODUCTO, ID_BODEGA)
);

-- Creación de la tabla PRODUCTO_ORDEN y especificación de sus restricciones
CREATE TABLE PRODUCTO_ORDEN(
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_ORDEN FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    ID_ORDEN NUMBER NOT NULL,
    CONSTRAINT FK_ORDEN_ORDEN FOREIGN KEY (ID_ORDEN)
    REFERENCES ORDEN(ID),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_PRODUCTO, ID_ORDEN)
);

-- Creación de la tabla CATALOGO y especificación de sus restricciones
CREATE TABLE CATALOGO(
    ID_PROVEEDOR VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PROVEEDOR_CATALOGO FOREIGN KEY (ID_PROVEEDOR)
    REFERENCES PROVEEDOR(NIT),
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_CATALOGO FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    PRIMARY KEY (ID_PROVEEDOR, ID_PRODUCTO)
);

-- Creación de la tabla ORDEN_PROVEEDOR y especificación de sus restricciones
CREATE TABLE ORDEN_PROVEEDOR(
    ID_ORDEN NUMBER NOT NULL,
    CONSTRAINT FK_ORDEN_PEDIDO FOREIGN KEY (ID_ORDEN)
    REFERENCES ORDEN(ID),
    ID_PROVEEDOR VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PROVEEDOR_PEDIDO FOREIGN KEY (ID_PROVEEDOR)
    REFERENCES PROVEEDOR(NIT),
    CALIFICACION NUMBER(18,4) NOT NULL CHECK(CALIFICACION>=0),
    PRIMARY KEY (ID_ORDEN, ID_PROVEEDOR)
);

-- Creación de la tabla COMPRA y especificación de sus restricciones
CREATE TABLE COMPRA(
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_COMPRA FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    ID_FACTURA NUMBER NOT NULL,
    CONSTRAINT FK_FACTURA_COMPRA FOREIGN KEY (ID_FACTURA)
    REFERENCES FACTURA(ID),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_PRODUCTO, ID_FACTURA)
);

-- Creación de la tabla PRODUCTO_PROMOCION y especificación de sus restricciones
CREATE TABLE PRODUCTO_PROMOCION(
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_PRODUCTOP FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    ID_PROMOCION NUMBER NOT NULL,
    CONSTRAINT FK_PROMOCION_PRODUCTOP FOREIGN KEY (ID_PROMOCION)
    REFERENCES PROMOCION(ID),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_PRODUCTO, ID_PROMOCION)
);

-- Creación de la tabla SUCURSAL_PRODUCTO y especificación de sus restricciones
CREATE TABLE FACTURA_PROMOCION(
    ID_FACTURA NUMBER NOT NULL,
    CONSTRAINT FK_FACTURA_PROMO FOREIGN KEY (ID_FACTURA)
    REFERENCES FACTURA(ID),
    ID_PROMOCION NUMBER NOT NULL,
    CONSTRAINT FK_PROMO_FACTURA FOREIGN KEY (ID_FACTURA)
    REFERENCES PROMOCION(ID),
    PRIMARY KEY (ID_FACTURA, ID_PROMOCION)
);

-- Creación de la tabla CARRITO_PRODUCTO y especificación de sus restricciones
CREATE TABLE CARRITO_PRODUCTO(
    ID_CARRITO NUMBER NOT NULL,
    CONSTRAINT FK_CARRITO_PRODUCTO FOREIGN KEY (ID_CARRITO)
    REFERENCES CARRITO(ID),
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO_CARRITO FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    CANTIDAD NUMBER NOT NULL,
    PRIMARY KEY (ID_CARRITO, ID_PRODUCTO)
);

CREATE TABLE TOMADO_DE(
    ID_CLIENTE NUMBER NOT NULL,
    CONSTRAINT FK_CLIENTE FOREIGN KEY (ID_CLIENTE)
    REFERENCES CLIENTE(ID),
    ID_PRODUCTO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT FK_PRODUCTO FOREIGN KEY (ID_PRODUCTO)
    REFERENCES PRODUCTO(CODIGO_BARRAS),
    ID_ESTANTE NUMBER NOT NULL,
    CONSTRAINT FK_ID_ESTANTE FOREIGN KEY (ID_ESTANTE)
    REFERENCES ESTANTE(ID)
);

COMMIT;
