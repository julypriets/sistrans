package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BODEGA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */
public class SQLBodega {

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
	public SQLBodega (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una BODEGA 
	 * @param pm - El manejador de persistencia
	 * @param id - identificador de la bodega
	 * @param idCategoria - identificador de la categoría de la bodega
	 * @param capacidadPeso - peso que puede almacenar la bodega 
	 * @param capacidadVolumen - volumen que puede almacenar la bodega
	 * @param idSucursal - identificador de la sucursal a la que pertenece la bodega
	 * @return El número de tuplas insertadas
	 */
	public long registrarEstante(PersistenceManager pm, long id, long idCategoria, double capacidadPeso, double capacidadVolumen, long idSucursal ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaBodega() + " VALUES (?, ?, ?, ?, ?)");
		q.setParameters(id, idCategoria, capacidadPeso, capacidadVolumen, idSucursal);
		return (long) q.executeUnique();
	}

}
