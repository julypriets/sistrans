SELECT a.table_name NombreTabla, a.numCols NumColumnas, c.NumColsPK, coalesce(b.numColsNotNull, 0) NumColsNotNull, coalesce(d.countfk, 0) NumColsFKs
FROM (
    SELECT table_name, COUNT(*) numCols
    FROM all_tab_columns
    WHERE owner = 'PARRANDEROS'
    GROUP BY table_name
)a LEFT OUTER JOIN (
    SELECT table_name, COUNT(*) numColsNotNull
    FROM all_tab_columns
    WHERE owner = 'PARRANDEROS' AND nullable = 'N'
    GROUP BY table_name
)b ON a.table_name = b.table_name INNER JOIN (
    SELECT table_name, COUNT(*) numColsPK
    FROM all_cons_columns NATURAL INNER JOIN (
        SELECT constraint_name
        FROM all_constraints
        WHERE owner = 'PARRANDEROS' AND constraint_type = 'P'
    )
    WHERE owner = 'PARRANDEROS'
    GROUP BY table_name
)c ON a.table_name = c.table_name LEFT OUTER JOIN (
    SELECT table_name, COUNT(*) countFK
    FROM all_cons_columns NATURAL INNER JOIN (
        SELECT constraint_name
        FROM all_constraints
        WHERE owner = 'PARRANDEROS' AND constraint_type = 'R'
    )
    WHERE owner = 'PARRANDEROS'
    GROUP BY table_name
) d ON a.table_name = d.table_name
ORDER BY NombreTabla DESC
