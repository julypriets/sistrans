SELECT barId, bebedorId, fecha_ultima_visita
FROM(
    SELECT barId, bebedorId, fecha_ultima_visita, MAX(fecha_ultima_visita) OVER (PARTITION BY bebedorId) fechaMasReciente
    FROM(
        SELECT bares.id barId, bebedores.id bebedorId, COUNT(*) numBebidasGustan
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
        GROUP BY bares.id, bebedores.id
    ), PARRANDEROS.FRECUENTAN
    WHERE numBebidasGustan >= 3 AND
        barId = id_bar AND
        bebedorId = id_bebedor
)
WHERE fecha_ultima_visita = fechaMasReciente
