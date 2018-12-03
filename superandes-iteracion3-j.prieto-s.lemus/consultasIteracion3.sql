-- Ejemplo base RFC10
SELECT f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre nombreProducto, p.codigo_barras, COUNT (f.id_cliente) cantidad
FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p, CAJERO cj
WHERE
    f.id_cliente = c.id AND
    f.id = cp.id_factura AND
    p.codigo_barras = cp.id_producto AND
    p.nombre = 'Beans - French' AND
    cj.id = f.id_cajero AND
    f.fecha BETWEEN to_date('01-JAN-01') AND to_date('01-NOV-01')
    --AND cj.id_sucursal = 1
GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras
ORDER BY nombre

-- Ejemplo base RFC11
SELECT f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre nombreProducto, p.codigo_barras, cp.cantidad
FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p, CAJERO cj
WHERE
    f.id_cliente = c.id AND
    f.id = cp.id_factura AND
    p.codigo_barras = cp.id_producto AND
    cj.id = f.id_cajero AND
    c.id IN (
        SELECT id FROM CLIENTE
        MINUS
        (SELECT id_cliente FROM FACTURA WHERE p.nombre = 'Beans - French' AND fecha BETWEEN to_date('15-JAN-10') AND to_date('17-JAN-10'))
    ) AND
    cj.id_sucursal = 1
GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras, cp.cantidad
ORDER BY nombre

-- Ejemplo base RFC12

-- Máximas y mínimas ventas de los productos por semana
SELECT fechaSemana, nombre, codigo_barras, ventasMaximas
FROM
    (SELECT fechaSemana, nombre, codigo_barras, ventas,
        MAX(ventas) OVER (PARTITION BY fechaSemana) AS ventasMaximas
    --    ,MIN(ventas) OVER (PARTITION BY fechaSemana) AS ventasMinimas  // en otra consulta similar para sacar los valores mínimos
    FROM
        (SELECT TRUNC(fecha, 'WW') fechaSemana, nombre, codigo_barras, (COUNT (nombre)* SUM(cantidad)) ventas
        FROM PRODUCTO p, COMPRA cp, FACTURA f
        WHERE
            p.codigo_barras = cp.id_producto AND
            f.id = cp.id_factura
        GROUP BY TRUNC(fecha, 'WW'), nombre, codigo_barras
        ORDER BY TRUNC(fecha, 'WW')
        )
    )
WHERE ventas = ventasMaximas;

-- Máximas y mínimas órdenes a proveedores por semana
SELECT fechaSemana, nombre, nit, solicitudesMaximas
FROM
    (SELECT fechaSemana, nombre, nit, solicitudes,
        MAX(solicitudes) OVER (PARTITION BY fechaSemana) AS solicitudesMaximas
    --    ,MIN(solicitudes) OVER (PARTITION BY fechaSemana) AS solicitudesMinimas // en otra consulta similar para sacar los valores mínimos
    FROM
        (SELECT TRUNC(fecha_esperada, 'WW') fechaSemana, p.nombre, p.nit, COUNT(p.nombre) solicitudes
        FROM ORDEN o, PROVEEDOR p, ORDEN_PROVEEDOR op
        WHERE
            o.id = op.id_orden AND
            p.nit = op.id_proveedor
        GROUP BY TRUNC(fecha_esperada, 'WW'), p.nombre, p.nit
        ORDER BY TRUNC(fecha_esperada, 'WW')
        )
    )
WHERE solicitudes = solicitudesMaximas
ORDER BY solicitudesMaximas DESC

-- Ejemplo base RFC13
