package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clsae que encapsula los métodos que hacen acceso a la base de datos para el concepto ORDEN de SuperAndes.
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia.
 * @author j.prieto
 *
 */
public class SQLOrden {

	/*----------- CONSTANTES ------------------------------------*/
	private final static String SQL = PersistenciaSuperandes.SQL;
	
	
	/*----------- ATRIBUTOS ------------------------------------*/
	private PersistenciaSuperandes pa;
	
	
	/*----------- MÉTODOS ------------------------------------*/
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
