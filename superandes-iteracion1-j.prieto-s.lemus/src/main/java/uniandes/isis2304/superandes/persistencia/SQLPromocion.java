package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.superandes.negocio.ComprasPorPromocion;

public class SQLPromocion {

	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;
	
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes pa;

	/**
	 * Constructor
	 * @param pa - Manejador de persistencia de la aplicación
	 */
	public SQLPromocion(PersistenciaSuperandes pa) {
		this.pa = pa;
	}
	
	
	/**
	 * Retorna las promociones que tuvieron más compras. Ya que constantemente se están 
	 * deshabilitando las promociones que deben finalizar, solo se comparan las promociones 
	 * disponibles actualmente
	 * @return
	 */
	public List<ComprasPorPromocion> dar20PromocionesMasPopulares(PersistenceManager pm) {
		String select = "SELECT prom.id idPromocion, COUNT(*) numCompras " + 
				"FROM PRODUCTO_PROMOCION prodprom, COMPRA compra, PROMOCION prom " + 
				"WHERE " + 
				"    prom.id = prodprom.id_promocion AND" + 
				"    compra.id_producto = prodprom.id_producto " + 
				"GROUP BY prom.id " + 
				"ORDER BY numCompras DESC " + 
				"FETCH FIRST 20 ROWS ONLY";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(ComprasPorPromocion.class);
		return (List<ComprasPorPromocion>) q.execute();
	}
	
	/**
	 * Finaliza una promoción para todos los productos asociados(remueve la relación) 
	 * si las existencias del producto se acabaron o si finalizó la fecha 
	 * @param pm - PersistenceManager
	 * @param idPromocion
	 * @return
	 */
	public long finalizarPromocion(PersistenceManager pm, Date fechaActual) {
		String delete = "DELETE " + 
				"FROM PRODUCTO_PROMOCION "+ 
				"WHERE id_producto IN ( " + 
				"    SELECT prom.id_producto " + 
				"    FROM PROMOCION prom, PRODUCTO_PROMOCION prodprom, Producto prod " + 
				"    WHERE " + 
				"        prom.id_producto = prodprom.id_promocion AND " + 
				"        prod.codigo_barras = prodprom.id_producto AND " + 
				"        prod.existencias = 0 OR " +
				"        prom.fecha_fin < fechaActual " +
				") ";
 
		Query q = pm.newQuery(SQL, delete);
		return (long) q.execute();
	}
	
}
