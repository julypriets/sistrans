SELECT a.table_name NombreTabla, a.data_type TipoDatoCol , numCols NumRestriccionesColsTipoDeDato, coalesce(numConst, 0) NumColsConEseTipoDeDato
FROM (
    SELECT table_name, data_type, COUNT(*) numCols
    FROM all_tab_columns
    WHERE owner = 'PARRANDEROS'
    GROUP BY table_name, data_type
) a LEFT OUTER JOIN (
    SELECT a.table_name, b.data_type, COUNT(*) numConst
    FROM all_cons_columns a INNER JOIN all_tab_columns b ON a.table_name = b.table_name AND a.column_name = b.column_name
    WHERE a.owner = 'PARRANDEROS'
    GROUP BY a.table_name, b.data_type
) b ON a.table_name = b.table_name AND a.data_type = b.data_type
ORDER BY a.table_name, a.data_type
