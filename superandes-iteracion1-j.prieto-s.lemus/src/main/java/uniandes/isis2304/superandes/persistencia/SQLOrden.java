package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.sun.jmx.snmp.Timestamp;

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
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una ORDEN a la base de datos
	 * @param pm
	 * @param id
	 * @param precio
	 * @param fechaEsperada
	 * @param fechaLlegada
	 * @param estado
	 * @param idSucursal
	 * @return
	 */
	public long adicionarOrden(PersistenceManager pm, long id, double precio, Timestamp fechaEsperada, Timestamp fechaLlegada,
			String estado, long idSucursal) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pa.darTablaOrden() + "(ID, PRECIO, FECHA_ESPERADA, FECHA_LLEGADA, ESTADO, ID_SUCURSAL) VALUES (?, ?, ?, ?, ?, ?)");
		q.setParameters(id, precio, fechaEsperada, fechaLlegada);
		return (long) q.executeUnique();
	}
}
