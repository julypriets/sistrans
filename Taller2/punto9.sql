SELECT *
FROM (
    SELECT nombre, tipo, COUNT(*) numBebedoresGustan
    FROM PARRANDEROS.GUSTAN, PARRANDEROS.BEBIDAS
    GROUP BY id_bebida, nombre, tipo
    ORDER BY numBebedoresGustan
)
OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY
