package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SURTIDO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLSurtido {
	
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
	public SQLSurtido (PersistenciaSuperandes ps) {
		this.ps = ps;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para registrar un SURTIDO
	 * @param pm - El manejador de persistencia
	 * @param idEstante - El identificador del estante surtido
	 * @param idProducto - El identificador del producto que surte el estante 
	 * @return El número de tuplas insertadas
	 */
	public long registrarSurtido(PersistenceManager pm, long idEstante, String idProducto) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaSurtido() + " VALUES (?, ?)");
		q.setParameters(idEstante, idProducto);
		return (long) q.executeUnique();
	}
	
}
