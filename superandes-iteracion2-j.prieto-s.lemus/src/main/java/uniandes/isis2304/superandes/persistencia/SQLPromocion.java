package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.sql.Timestamp;

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
			Timestamp fechaFin, long idSucursal, String idProducto, int cantidad1, int cantidad2, int descuento ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPromocion() + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(id, tipo, precio, fechaInicio, fechaFin, idSucursal, idProducto, cantidad1, cantidad2, descuento);
		return (long) q.executeUnique();
	}
}
