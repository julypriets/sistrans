
-- RFC2
SELECT prom.id idPromocion, COUNT(*) numCompras
				FROM PRODUCTO_PROMOCION prodprom, COMPRA compra, PROMOCION prom
				WHERE
				    prom.id = prodprom.id_promocion AND
				    compra.id_producto = prodprom.id_producto
				GROUP BY prom.id
				ORDER BY numCompras DESC
				FETCH FIRST 20 ROWS ONLY

-- RFC4


-- RFC7
