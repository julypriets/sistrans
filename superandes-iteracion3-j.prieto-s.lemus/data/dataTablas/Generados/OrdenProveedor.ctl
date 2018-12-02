load data 
infile '/Users/usuario/Documents/July.mac/Académico/Uniandes/Sistrans/sistrans/superandes-iteracion3-j.prieto-s.lemus/data/dataTablas/csv/OrdenProveedor.csv' "str '\r\n'"
append
into table ORDEN_PROVEEDOR
fields terminated by ';'
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID_ORDEN,
             ID_PROVEEDOR CHAR(255),
             CALIFICACION
           )
