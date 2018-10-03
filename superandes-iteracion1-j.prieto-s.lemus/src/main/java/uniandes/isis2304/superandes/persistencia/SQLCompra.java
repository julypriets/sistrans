package uniandes.isis2304.superandes.persistencia;

import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLCompra {
	
	
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
	public SQLCompra(PersistenciaSuperandes pa) {
		this.pa = pa;
	}
	
	/**
	 * Retorna el número de compras realizada para un producto concreto
	 * @param pm
	 * @param idProducto
	 * @return
	 */
	public long darNumeroDeComprasPorProducto(PersistenceManager pm, long idProducto) {
		String sql = "SELECT COUNT (*) numfacturas FROM" + pa.COMPRA_FACTURA
				+ "WHERE id_producto = ?";
		Query q = pm.newQuery(SQL, sql);
		return ((BigDecimal)q.execute(idProducto)).longValue();
	}
	
	/**
	 * Se añade el registro de que se realizó una nueva compra
	 * @param pm
	 * @param idProducto
	 * @param idFactura
	 * @return
	 */
	public long adicionarCompra(PersistenceManager pm, long idProducto, long idFactura) {
		String insert = "INSERT INTO " + pa.COMPRA_FACTURA + "(id_producto ,id_factura)"
				+ "values (?, ?)";
		Query q = pm.newQuery(SQL, insert);
		long numAd = (long) q.execute(idProducto, idFactura);
		
		String update = "UPDATE " + pa.PRODUCTO 
				+ "SET existencias = existencias - 1"
				+ "WHERE codigo_barras = " + idProducto;
		Query q1 = pm.newQuery(SQL, update);
		q1.execute();
		return numAd;
	}
}
