SELECT a.table_name NombreTabla, a.column_name NombreCol, a.constraint_name NombreConstraitColumna, b.table_name NombreTablaRefFK
FROM (
    SELECT *
    FROM all_cons_columns NATURAL INNER JOIN all_constraints
) a LEFT OUTER JOIN (
    SELECT *
    FROM all_cons_columns NATURAL INNER JOIN all_constraints
) b ON b.R_constraint_name = a.constraint_name
WHERE a.owner = 'PARRANDEROS'
ORDER BY a.table_name, a.column_name, a.constraint_name
