SELECT barNombre, bebidaId, bebidaNombre, horario peorHorario, frecuency visitas
FROM(
    SELECT barNombre, horario, bebidaId, bebidaNombre, frecuency,
    MIN(frecuency) OVER (PARTITION BY barId, barNombre, bebidaId, bebidaNombre) minFreq
    FROM (
        SELECT bares.id barId, bares.nombre barNombre, frec.horario, bebidas.id bebidaId, bebidas.nombre bebidaNombre, COUNT(*) frecuency
        FROM PARRANDEROS.BARES bares, PARRANDEROS.FRECUENTAN frec, PARRANDEROS.SIRVEN sirven,
            PARRANDEROS.GUSTAN gustan, PARRANDEROS.BEBEDORES bebedores, PARRANDEROS.BEBIDAS bebidas
        WHERE
            bares.id = frec.id_bar AND
            bares.id = sirven.id_bar AND
            bebedores.id = gustan.id_bebedor AND
            sirven.id_bebida = bebidas.id AND
            bebedores.id = frec.id_bebedor AND
            bares.ciudad = bebedores.ciudad AND
            bebidas.id = gustan.id_bebida AND
            (sirven.horario = 'todos' OR frec.horario = 'todos' OR (sirven.horario = frec.horario))
        GROUP BY bares.id, bares.nombre, frec.horario, bebidas.id, bebidas.nombre
    )
)
WHERE frecuency = minFreq
ORDER BY barNombre
