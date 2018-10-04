package uniandes.isis2304.superandes.persistencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Producto;

public class SQLCliente {

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
	public SQLCliente(PersistenciaSuperandes pa) {
		this.pa = pa;
	}
	
	/**
	 * Adiciona una nueva empresa como cliente a la base de datos
	 * @param pm - PersistenceManager
	 * @param id
	 * @param nombre
	 * @param correo
	 * @param nit
	 * @param direccion
	 * @return el número de tuplas adicionadas
	 */
	public long adicionarEmpresa(PersistenceManager pm, long id, String nombre, String correo, String nit, String direccion) {
        // insertar en la tabla clientes
		Query q1 = pm.newQuery(SQL, "INSERT INTO " + pa.CLIENTE + "(id, nombre, correo) "
//				+ "values (?, ?, ?)");
        		+ "values ( " + id +", " + "'" + nombre + "', " + "'" + correo + "')");
//		Map<String, Object> params = new HashMap<>();
//		params.put("idEm", id);
//		params.put("nombreEm", nombre);
//		params.put("correoEm", correo);
//		
		//q1.setNamedParameters(params);
		
        //q1.setParameters(id, nombre, correo);
        //q1.compile();
        long num1 = (long) q1.execute();
        //return num1;
        
        // insertar en la tabla empresa
        Query q2 = pm.newQuery(SQL, "INSERT INTO " + pa.EMPRESA + "(id_cliente, nit, direccion) "
        		+ "values (?, ?, ?)");
        //q2.setParameters(id, nit, direccion);
        //long num = (long) q2.executeUnique();
        return (long) q2.execute(id, nit, direccion);
	}
	
	/**
	 * Adiciona una nueva persona natural como cliente a la base de datos
	 * @param pm - PersistenceManager
	 * @param id
	 * @param nombre
	 * @param correo
	 * @param identificacion
	 * @return el número de tuplas adicionadas
	 */
	public Long adicionarPersonaNatural(PersistenceManager pm, Long id, String nombre, String correo, String identificacion) {
        // insertar en la tabla clientes
		Query q1 = pm.newQuery(SQL, "INSERT INTO " + pa.CLIENTE + "(id, nombre, correo) "
        		+ "values (?, ?, ?)");
        q1.setParameters(id, nombre, correo);
        
        // insertar en la tabla persona_natural
        Query q2 = pm.newQuery(SQL, "INSERT INTO " + pa.PERSONA_NATURAL + "(id, identificacion) "
        		+ "values (?, ?)");
        q2.setParameters(id, identificacion);
        return (long) q2.executeUnique();
	}
	
	public List<Cliente> darClientes(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.CLIENTE);
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
}
