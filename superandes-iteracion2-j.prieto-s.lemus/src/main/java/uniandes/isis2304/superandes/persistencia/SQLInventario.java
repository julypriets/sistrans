package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto INVENTARIO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLInventario {

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
	public SQLInventario (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un INVENTARIO
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto almacenado en una bodega
	 * @param idBodega - El identificador de la bodega que almacena el producto
	 * @return El número de tuplas insertadas
	 */
	public long registrarInventario(PersistenceManager pm, String idProducto, long idBodega) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaInventario() + " VALUES (?, ?)");
		q.setParameters(idProducto, idBodega);
		return (long) q.executeUnique();
	}
	
}
