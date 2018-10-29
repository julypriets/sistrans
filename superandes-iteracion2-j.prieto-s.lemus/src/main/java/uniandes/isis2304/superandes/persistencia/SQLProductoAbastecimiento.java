package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SUCURSAL de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLProductoAbastecimiento {

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
	public SQLProductoAbastecimiento (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un PRODUCTO ABASTECIMIENTO
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto que hace parte del pedido a bodega (abastecimiento)
	 * @param idAbastecimiento - el identificador del pedido a bodega (abastecimiento)
	 * @return El número de tuplas insertadas
	 */
	public long registrarProductoAbastecimiento( PersistenceManager pm, String idProducto, long idAbastecimiento ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaProductoAbastecimiento() + " VALUES (?, ?)");
		q.setParameters(idProducto, idAbastecimiento);
		return (long) q.executeUnique();
	}
	
}
