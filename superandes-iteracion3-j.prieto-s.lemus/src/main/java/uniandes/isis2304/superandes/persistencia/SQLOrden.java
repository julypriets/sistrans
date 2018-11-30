package uniandes.isis2304.superandes.persistencia;

import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;
import uniandes.isis2304.superandes.negocio.Orden;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
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
	 * @param idSucursal - Identificador de la sucursal que solicitó la orden
	 * @param fechaLlegada - fecha de llegada de la orden
	 * @param idProveedor - identificador del proveedor que realizó la orden
	 * @param calificacion - calificación del servicio
	 * @return - El número de tuplas modificadas
	 */
	public long[] registrarLlegadaOrden( PersistenceManager pm, long idOrden, long idSucursal, Timestamp fechaLlegada, String idProveedor, double calificacion){
		//Registro la orden
		Query q1 = pm.newQuery(SQL,"UPDATE " + ps.darTablaOrden() + " SET fecha_llegada = ?, estado = 'ENTREGADA' WHERE id = ?");
		q1.setParameters(fechaLlegada, idOrden);
		
		//Califico al proveedor
		Query q2 = pm.newQuery(SQL, "UPDATE " + ps.darTablaOrdenProveedor() + " SET calificacion = ? WHERE id_orden = ? AND id_proveedor = ?"); 
		q2.setParameters(calificacion, idOrden, idProveedor);
		
		//Selecciono los ids y la cantidad de productos 
		String sql = "SELECT producto_orden.id_producto, cantidad, id_sucursal FROM " + ps.darTablaProductoOrden() + " WHERE orden_id = ?";
		Query q3 = pm.newQuery(SQL, sql);
		q3.setParameters(idOrden);
		
		String sql2 = "SELECT id FROM " + ps.darTablaBodega() + " WHERE id_sucursal = ?";
		Query q4 = pm.newQuery(SQL, sql2);
		q4.setParameters(idSucursal);
		//Lista con las bodegas de dicha sucursal
		List<Object> list2 = q4.executeList();
		
		//Lista con codigos de producto y cantidad
		List<Object> list = q3.executeList();
		
		long[] resp = new long[100];
		
		long lq1 = (long) q1.executeUnique();
		long lq2 = (long) q2.executeUnique();
		
		resp[1] = lq1;
		resp[2] = lq2;
		
		for(Object tupla : list){
			int i = 3;
			Object[] datos = (Object[]) tupla;
			long idP = ((BigDecimal)datos[0]).longValue();
			int cant = ((BigDecimal)datos[1]).intValue();
			for(Object bodegas : list2) {
				Object[] bodega = (Object[])bodegas;
				//Id de la bodega actual
				long idBod = ((BigDecimal)bodega[0]).longValue();
				//Aumento la cantidad de productos de ese producto en la bodega correspondiente
				Query q5= pm.newQuery(SQL, "UPDATE " + ps.darTablaInventario() + " SET cantidad = cantidad + ? WHERE id_producto = ? AND id_bodega = ?" );
				q5.setParameters((cant), idP, idBod);
				long lq5 = (long)q5.executeUnique();
				resp[i] = lq5;
				i++;
			}
		}
		return resp;
	}
	
	
}
