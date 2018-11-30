package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.ProveedorPorSemana;


/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROVEEDOR de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLProveedor {

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
	public SQLProveedor (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un PROVEEDOR
	 * @param pm - El manejador de persistencia
	 * @param nit - identificador del proveedor
	 * @param nombre - nombre del proveedor
	 * @param calificacion - calificación del servicio del proveedor
	 * @return El número de tuplas insertadas
	 */
	public long registarProveedor( PersistenceManager pm, String nit, String nombre, double calificacion) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaProveedor() + " VALUES(?, ?, ?)");
		q.setParameters(nit, nombre, calificacion);
		return (long) q.executeUnique();
	}
	
	/* ****************************************************************
	 * 			Métodos consulta para Iteración 3
	 *****************************************************************/
	
	/**
	 * Retorna la información de los proveedores con solicitudes máximas por semana
	 * @param pm
	 * @return Colección con la información de los proveedores con órdenes máximas por semana
	 */
	public List<ProveedorPorSemana> proveedoresConSolicitudesMaximasPorSemana(PersistenceManager pm){
		String select = "SELECT fechaSemana, nombre, nit,\n" + 
				"    MAX(solicitudes) OVER (PARTITION BY fechaSemana) AS solicitudesMaximas\n" +  
				"FROM\n" + 
				"(SELECT TRUNC(fecha_esperada, 'WW') fechaSemana, p.nombre, p.nit, COUNT(p.nit) solicitudes\n" + 
				"FROM ORDEN o, PROVEEDOR p, ORDEN_PROVEEDOR op\n" + 
				"WHERE\n" + 
				"    o.id = op.id_orden AND\n" + 
				"    p.nit = op.id_proveedor\n" + 
				"GROUP BY TRUNC(fecha_esperada, 'WW'), p.nombre, p.nit\n" + 
				"ORDER BY TRUNC(fecha_esperada, 'WW')\n" + 
				")";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(ProveedorPorSemana.class);
		return (List<ProveedorPorSemana>) q.executeList();
	}
	
	/**
	 * Retorna la información de los proveedores con solicitudes con ventas mínimas por semana
	 * @param pm
	 * @return Colección con la información de los proveedores con órdenes mínimas por semana
	 */
	public List<ProveedorPorSemana> proveedoresConSolicitudesMinimasPorSemana(PersistenceManager pm){
		String select = "SELECT fechaSemana, nombre, nit,\n" + 
				"    MIN(solicitudes) OVER (PARTITION BY fechaSemana) AS solicitudesMinimas \n" + 
				"FROM\n" + 
				"(SELECT TRUNC(fecha_esperada, 'WW') fechaSemana, p.nombre, p.nit, COUNT(p.nit) solicitudes\n" + 
				"FROM ORDEN o, PROVEEDOR p, ORDEN_PROVEEDOR op\n" + 
				"WHERE\n" + 
				"    o.id = op.id_orden AND\n" + 
				"    p.nit = op.id_proveedor\n" + 
				"GROUP BY TRUNC(fecha_esperada, 'WW'), p.nombre, p.nit\n" + 
				"ORDER BY TRUNC(fecha_esperada, 'WW')\n" + 
				")";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(ProveedorPorSemana.class);
		return (List<ProveedorPorSemana>) q.executeList();
	}
	
	
	
	
}
