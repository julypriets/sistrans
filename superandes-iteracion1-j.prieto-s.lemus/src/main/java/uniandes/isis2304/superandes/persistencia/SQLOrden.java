package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLOrden {
	
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;
	
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes pa;

	/**
	 * Constructor
	 * @param pa - Manejador de persistencia de la aplicación
	 */
	public SQLOrden(PersistenciaSuperandes pa) {
		this.pa = pa;
	}
	
	public long adicionarOrden(PersistenceManager pm, long id, Double precio, Timestamp fechaEsperada, Timestamp fechaLlegada, String estado,
			Double calificacion, long idSucursal, long idProveedor) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pa.ORDEN + "(id, precio, fecha_esperada, fecha_llegada, estado, id_sucursal)"
				+ "values (?, ?, ?, ?, ?, ?)");
		q.setParameters(id, precio, fechaEsperada, fechaLlegada, estado, calificacion, idSucursal);
		long numAd = (long) q.executeUnique();
		
		Query q1 = pm.newQuery(SQL, "INSERT INTO " + pa.PEDIDO + "(id_orden, id_proveedor, calificacion)"
				+ "values (?, ?, ?)");
		q1.execute(id, idProveedor, calificacion);
		return numAd;
	}
}
