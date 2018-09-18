SELECT table_name NombreTabla, constraint_name NombrePK, column_name NombreColPK, data_type TipoDatoColPK, all_cons_columns. position OrdenColPK, NumIndicesColPk
--SELECT *
FROM all_constraints NATURAL INNER JOIN all_cons_columns NATURAL INNER JOIN all_tab_columns NATURAL INNER JOIN (
    SELECT table_name, column_name, COUNT(*) NumIndicesColPk
    FROM all_cons_columns NATURAL INNER JOIN (
        SELECT table_name, column_name
        FROM all_constraints NATURAL INNER JOIN all_cons_columns
        WHERE owner = 'PARRANDEROS' AND constraint_type = 'P'
    )
    GROUP BY table_name, column_name
)
WHERE owner = 'PARRANDEROS' AND constraint_type = 'P'
ORDER BY NombreTabla, NombrePK, column_name, TipoDatoColPK
