package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.sql.Timestamp;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto FACTURA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLFactura {

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
	public SQLFactura (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una FACTURA 
	 * @param pm - El manejador de persistencia
	 * @param id - el identificador de la factura
	 * @param fecha - fecha de creación de la factura
	 * @param precioTotal - precio total de la factura
	 * @param idCajero - id del cajero que atendió la compra
	 * @param idPersona - id de la persona que realizó la compra
	 * @param idEmpresa - id de la empresa que realizó la compra
	 * @param cliente - tipp de cliente
	 * @param comprada - si la compra se realizó o no
	 * @return El número de tuplas insertadas
	 */
	public long registarFactura(PersistenceManager pm, long id, Timestamp fecha, double precioTotal, long idCajero, 
			long idPersona, long idEmpresa, String cliente, int comprada) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaFactura() + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(id, fecha, precioTotal, idCajero, idPersona, idEmpresa, cliente, comprada);
		return (long) q.executeUnique();
	}
}
