package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Categoria;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CATEGORIA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLCategoria {
	
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
	public SQLCategoria (PersistenciaSuperandes ps) {
		this.ps = ps;
	}

	/**
	 * 
	 * @param pm - PersistenceManager
	 * @param idCategoria 
	 * @return La categoría correspondiente al id
	 */
	public Categoria darCategoriaPorId(PersistenceManager pm, long idCategoria) {
		Query q = pm.newQuery(SQL, "SELECT id, nombre"
				+ " FROM " + ps.darTablaCategoria() + " WHERE id = ?");
		q.setResultClass(Categoria.class);
		return (Categoria) q.execute(idCategoria);
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @param nombreCategoria 
	 * @return La categoría correspondiente al nombre
	 */
	public Categoria darCategoriaPorNombre(PersistenceManager pm, String nombreCategoria) {
		Query q = pm.newQuery(SQL, "SELECT id, nombre"
				+ " FROM " + ps.darTablaCategoria() + " WHERE nombre = ?");
		q.setResultClass(Categoria.class);
		return (Categoria) q.execute(nombreCategoria);
	}
}
