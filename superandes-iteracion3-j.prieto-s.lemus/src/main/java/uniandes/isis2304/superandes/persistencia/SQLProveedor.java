package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROVEEDOR de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLProveedor {

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
	public SQLProveedor (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un PROVEEDOR
	 * @param pm - El manejador de persistencia
	 * @param nit - identificador del proveedor
	 * @param nombre - nombre del proveedor
	 * @param calificacion - calificación del servicio del proveedor
	 * @return El número de tuplas insertadas
	 */
	public long registarProveedor( PersistenceManager pm, String nit, String nombre, double calificacion) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaProveedor() + " VALUES(?, ?, ?)");
		q.setParameters(nit, nombre, calificacion);
		return (long) q.executeUnique();
	}
	
}
