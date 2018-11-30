-- Ejemplo base RFC10
SELECT f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre nombreProducto, p.codigo_barras, COUNT (f.id_cliente) cantidad
FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p
WHERE
    f.id_cliente = c.id AND
    f.id = cp.id_factura AND
    p.codigo_barras = cp.id_producto
    AND p.nombre = 'Beans - French'
    AND f.fecha BETWEEN to_date('15-JAN-10') AND to_date('17-JAN-10')
GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras
ORDER BY nombre

-- Ejemplo base RFC11
SELECT f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre nombreProducto, p.codigo_barras, cp.cantidad
FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p
WHERE
    f.id_cliente = c.id AND
    f.id = cp.id_factura AND
    p.codigo_barras = cp.id_producto AND
    c.id IN (
        SELECT id FROM CLIENTE
        MINUS
        (SELECT id_cliente FROM FACTURA WHERE p.nombre = 'Beans - French' AND fecha BETWEEN to_date('15-JAN-10') AND to_date('17-JAN-10'))
    )
GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras, cp.cantidad
ORDER BY nombre

-- Ejemplo base RFC12

-- Máximas y mínimas ventas de los productos por semana
SELECT fechaSemana, nombre, codigo_barras,
    MAX(ventas) OVER (PARTITION BY fechaSemana) AS ventasMaximo,
--    MIN(ventas) OVER (PARTITION BY fechaSemana) AS ventasMinimo  // en otra consulta similar para sacar los valores mínimos
FROM
(SELECT TRUNC(fecha, 'WW') fechaSemana, nombre, codigo_barras, COUNT (codigo_barras) ventas
FROM PRODUCTO p, FACTURA_PRODUCTO fp, FACTURA f
WHERE
    p.codigo_barras = fp.id_producto AND
    f.id = fp.id_factura
GROUP BY TRUNC(fecha, 'WW'), nombre, codigo_barras
ORDER BY TRUNC(fecha, 'WW')
)

-- Máximas y mínimas órdenes a proveedores por semana
SELECT fechaSemana, nombre, nit,
    MAX(solicitudes) OVER (PARTITION BY fechaSemana) AS solicitudesMaximo,
--    MIN(solicitudes) OVER (PARTITION BY fechaSemana) AS solicitudesMinimo // en otra consulta similar para sacar los valores mínimos
FROM
(SELECT TRUNC(fecha_esperada, 'WW') fechaSemana, p.nombre, p.nit, COUNT(p.nit) solicitudes
FROM ORDEN o, PROVEEDOR p, ORDEN_PROVEEDOR op
WHERE
    o.id = op.id_orden AND
    p.nit = op.id_proveedor
GROUP BY TRUNC(fecha_esperada, 'WW'), p.nombre, p.nit
ORDER BY TRUNC(fecha_esperada, 'WW')
)

-- Ejemplo base RFC13