package uniandes.isis2304.superandes.persistencia;

import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;
import uniandes.isis2304.superandes.negocio.Orden;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.sql.Timestamp;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ORDEN de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLOrden {
	
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
	public SQLOrden (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una ORDEN 
	 * @param pm - El manejador de persistencia
	 * @param id - identificador de la orden
	 * @param precio - precio de la orden
	 * @param fechaEsperada - fecha esperada de la orden
	 * @param fechaLlegada - fecha de llegada de la orden
	 * @param estado - estado de la orden
	 * @param idSucursal - identificador de la sucursal que solicitó la orden.
	 * @return - El número de tuplas insertadas
	 */
	public long registrarOrden( PersistenceManager pm, long id, double precio, Timestamp fechaEsperada, Timestamp fechaLlegada, String estado, long idSucursal ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaOrden() + " VALUES (?, ?, ?, ?, ?, ?)");
		q.setParameters(id, precio, fechaEsperada, fechaLlegada, estado, idSucursal);
		return (long) q.executeUnique();
	}
	
	/**
	 * Registra la llegada de una orden
	 * @param pm - El manejador de persistencia
	 * @param idOrden - identificador de la orden
	 * @param fechaLlegada - fecha de llegada de la orden
	 * @param estado - estado de la orden
	 * @param idProveedor - identificador del proveedor que realizó la orden
	 * @param calificacion - calificación del servicio
	 * @return - El número de tuplas modificadas
	 */
	public long[] registrarLlegadaOrden( PersistenceManager pm, long idOrden, Timestamp fechaLlegada, String estado, String idProveedor, double calificacion){
		Query q1 = pm.newQuery(SQL,"UPDATE " + ps.darTablaOrden() + " SET fecha_llegada = ?, estado = ? WHERE id = ?");
		q1.setParameters(fechaLlegada, estado, idOrden);
		
		Query q2 = pm.newQuery(SQL, "UPDATE " + ps.darTablaOrdenProveedor() + " SET calificacion = ? WHERE id_orden = ? AND id_proveedor = ?"); 
		q2.setParameters(calificacion, idOrden, idProveedor);
		
		long lq1 = (long) q1.executeUnique();
		long lq2 = (long) q2.executeUnique();
		
		return new long[] {lq1, lq2};
	}
	
}
