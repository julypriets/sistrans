package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ORDEN_PROVEEDOR de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */
public class SQLOrdenProveedor {

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
	public SQLOrdenProveedor (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una ORDEN_PROVEEDOR
	 * @param pm - El manejador de persistencia
	 * @param idOrden - El identificador de 
	 * @param idProveedor
	 * @param calificacion
	 * @return
	 */
	public long registrarOrdenProveedor(PersistenceManager pm, long idOrden, String idProveedor, double calificacion) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaOrdenProveedor() + " VALUES (?, ?, ?)");
		q.setParameters(idOrden, idProveedor, calificacion);
		return (long) q.executeUnique();
	}
	
}
