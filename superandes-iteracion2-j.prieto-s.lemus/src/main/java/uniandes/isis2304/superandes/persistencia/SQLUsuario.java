package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto USUARIO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLUsuario {

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
	public SQLUsuario (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un USUARIO
	 * @param pm - El manejador de persistencia
	 * @param username - El nombre de usuario
	 * @param password - La constraseña del usuario
	 * @param tipo - El tipo de usuario
	 * @param nombre - El nombre del ususario
	 * @param idSucursal - El identificador de la sucursal a la que pertenece el usuario
	 * @return El número de tuplas insertadas
	 */
	public long registrarUsuario( PersistenceManager pm, String username, String password, String tipo, String nombre, long idSucursal) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaUsuario() + " VALUES (?, ?, ?, ?, ?)");
		q.setParameters(username, password, tipo, nombre, idSucursal);
		return (long) q.executeUnique();
	}
	
}
