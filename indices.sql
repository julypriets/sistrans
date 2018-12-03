CREATE INDEX factura_precioTotal_fecha_i ON FACTURA (precio_total, fecha);

CREATE INDEX producto_nombre_i ON PRODUCTO (nombre);

--CREATE INDEX proveedor_id_i ON PROVEEDOR (nit); // innecesario

--CREATE INDEX producto_id_i ON PRODUCTO (codigo_barras); // innecesario

--CREATE INDEX cliente_id_i ON CLIENTE (id); // innecesario
CREATE INDEX orden_fechaEsperada_i ON ORDEN (fecha_esperada);

CREATE INDEX categoria_nombre_i ON CATEGORIA (nombre);

CREATE INDEX carrito_idCliente_i ON CARRITO (id_cliente);
