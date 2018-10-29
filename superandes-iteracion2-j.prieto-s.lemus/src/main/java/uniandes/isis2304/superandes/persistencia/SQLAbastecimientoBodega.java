package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ABASTECIMIENTO_BODEGA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLAbastecimientoBodega {

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
	public SQLAbastecimientoBodega (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registar un ABASTECIMIENTO_BODEGA
	 * @param pm - El manejador de persistencia
	 * @param idAbastecimiento - El id del abastecimiento
	 * @param idBodega - El id de la bodega
	 * @return El número de tuplas insertadas
	 */
	public long registrarAbastecimientoBodega(PersistenceManager pm, long idAbastecimiento, long idBodega) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaAbastecimientoBodega() + " VALUES (?, ?)");
		q.setParameters(idAbastecimiento, idBodega);
		return (long) q.executeUnique();
	}
	
}
