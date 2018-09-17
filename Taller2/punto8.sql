SELECT nombre_bebedor, ciudad, numBaresFrecuentados
FROM(
    SELECT bebedores.nombre nombre_bebedor, ciudad, id_bebedor,horario, COUNT(*)numBaresFrecuentados
    FROM PARRANDEROS.BEBEDORES bebedores, PARRANDEROS.FRECUENTAN frec
    WHERE bebedores.id = frec.id_bebedor AND horario = 'nocturno'
    GROUP BY bebedores.nombre, ciudad, id_bebedor,horario
)
WHERE numBaresFrecuentados BETWEEN 3 AND 8
ORDER BY ciudad, nombre_bebedor, numBaresFrecuentados
