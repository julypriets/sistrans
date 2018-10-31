package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.ComprasPorPromocion;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROMOCION de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLPromocion {

	/* ----------------- Constantes ----------------------------------- */

	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;


	/* ----------------- Atributos ----------------------------------- */

	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes ps;


	/* ----------------- Métodos ----------------------------------- */

	/**
	 * Constructor
	 * @param ps - El Manejador de persistencia de la aplicación
	 */
	public SQLPromocion (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una PROMOCION 
	 * @param pm - El manejador de persistencia
	 * @param id - identificador de la promoción 
	 * @param tipo - tipo de promoción
	 * @param precio - precio de la promoción
	 * @param fechaInicio - fecha de inicio de la promoción
	 * @param fechaFin - fecha fin de la promoción
	 * @param idSucursal - identificador de la sucursal que realiza la promoción
	 * @param idProducto - identificador del producto que se promociona
	 * @param cantidad1 - cantidad 1 promocionada del producto
	 * @param cantidad2 - cantidad 2 promocionada del producto
	 * @param descuento - descuento del producto
	 * @return El número de tuplas insertadas
	 */
	public long registrarPromocion (PersistenceManager pm, long id, int tipo, double precio, Timestamp fechaInicio, 
			Timestamp fechaFin, long idSucursal, String idProducto, int cantidad1, int cantidad2, double descuento ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPromocion() + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(id, tipo, precio, fechaInicio, fechaFin, idSucursal, idProducto, cantidad1, cantidad2, descuento);
		return (long) q.executeUnique();
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
