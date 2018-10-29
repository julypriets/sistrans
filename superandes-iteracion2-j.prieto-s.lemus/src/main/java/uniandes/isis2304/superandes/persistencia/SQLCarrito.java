package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CARRITO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLCarrito {
	
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
	public SQLCarrito (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un CARRITO
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del carrito
	 * @param idCliente - El identificador del cliente que tiene el carrito
	 * @param estado - El estado del carrito
	 * @return El número de tuplas insertadas
	 */
	public long registrarCarrito(PersistenceManager pm, long id, long idCliente, String estado) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCarrito() + "VALUES (?, ?, ?)");
		q.setParameters(id, idCliente, estado);
		return (long) q.executeUnique();
	}

}
