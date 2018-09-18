SELECT nombre, presupuesto, ciudad, id_bar, frecClientes
FROM(
    SELECT nombre, presupuesto, ciudad, id_bar, frecClientes, MAX(frecClientes) OVER (PARTITION BY ciudad) maxClientes
    FROM(
        SELECT bares.nombre nombre, bares.presupuesto presupuesto, ciudad, id_bar, COUNT(id_bebedor)frecClientes
        FROM PARRANDEROS.FRECUENTAN frec, PARRANDEROS.BARES bares
        WHERE frec.id_bar = bares.id
        GROUP BY bares.nombre, bares.presupuesto, ciudad, id_bar
    )
)
WHERE frecClientes = maxClientes
ORDER BY ciudad, nombre
