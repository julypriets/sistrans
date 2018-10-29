package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto FACTURA_PROMOCION de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLFacturaPromocion {

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
	public SQLFacturaPromocion (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una FACTURA_PROMOCION
	 * @param pm - El manejador de persistencia
	 * @param idFactura - El identificador de la factura que contiene la promoción
	 * @param idPromocion - El identificador de la promoción contenida en la factura
	 * @return El número de tuplas insertadas
	 */
	public long registrarFacturaPromocion(PersistenceManager pm, long idFactura, long idPromocion) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaFacturaPromocion() + " VALUES (?, ?)");
		q.setParameters(idFactura, idPromocion);
		return (long) q.executeUnique();
	}
}
