package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CATALOGO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLCatalogo {

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
	public SQLCatalogo (PersistenciaSuperandes ps) {
		this.ps = ps;
	}


	/**
	 * Crea y ejecuta la sentencia SQL para registrar un CATALOGO
	 * @param pm - El manejador de persistencia
	 * @param idProveedor - El identificador del proveedor que ofrece el producto
	 * @param idProducto - El identificador del producto ofrecido por el proveedor
	 * @return El número de tuplas insertadas
	 */
	public long registarCatalogo(PersistenceManager pm, long idProveedor, String idProducto) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCatalogo() + " VALUES (?, ?)");
		q.setParameters(idProveedor, idProducto);
		return (long) q.executeUnique();
	}
	
}
