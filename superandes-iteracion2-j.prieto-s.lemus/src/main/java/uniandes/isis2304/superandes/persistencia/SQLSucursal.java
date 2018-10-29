package uniandes.isis2304.superandes.persistencia;

import uniandes.isis2304.superandes.negocio.Sucursal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SUCURSAL de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLSucursal {
	
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
	public SQLSucursal (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una SUCURSAL 
	 * @param pm - El manejador de persistencia
	 * @param idSucursal - El identificador de la sucursal
	 * @param nombre - El nombre de la sucursal
	 * @param ciudad - La ciudad de la sucursal
	 * @param direccion - La dirección de la sucursal
	 * @return El número de tuplas insertadas
	 */
	public long registrarSucursal (PersistenceManager pm, long idSucursal, String nombre, String ciudad, String direccion ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaSucursal() + " VALUES (?, ?, ?, ?)");
		q.setParameters(idSucursal, nombre, ciudad, direccion);
		return (long) q.executeUnique();
	}

}
