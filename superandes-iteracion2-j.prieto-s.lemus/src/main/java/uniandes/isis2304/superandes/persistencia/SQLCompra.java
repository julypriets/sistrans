package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto COMPRA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLCompra {
	
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
	public SQLCompra (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una COMPRA
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto comprado
	 * @param idFactura - El identificador de la factura que contiene el producto
	 * @return El número de tuplas insertadas
	 */
	public long registrarCompra(PersistenceManager pm, String idProducto, long idFactura) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCompra() + " VALUES (?, ?)");
		q.setParameters(idProducto, idFactura);
		return (long) q.executeUnique();
	}
	
}
