package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CLIENTE de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLCliente {
	
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
	public SQLCliente (PersistenciaSuperandes ps) {
		this.ps = ps;
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
	public long registarEmpresa(PersistenceManager pm, long id, String nombre, String correo, String nit, String direccion) {
        // insertar en la tabla clientes
		Query q1 = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCliente() + "(id, nombre, correo) "
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
        Query q2 = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaEmpresa() + "(id_cliente, nit, direccion) "
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
	public Long registrarPersonaNatural(PersistenceManager pm, Long id, String nombre, String correo, String identificacion) {
        // insertar en la tabla clientes
		Query q1 = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCliente() + "(id, nombre, correo) "
        		+ "values (?, ?, ?)");
        q1.setParameters(id, nombre, correo);
        
        // insertar en la tabla persona_natural
        Query q2 = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaPersona() + "(id, identificacion) "
        		+ "values (?, ?)");
        q2.setParameters(id, identificacion);
        return (long) q2.executeUnique();
	}
	
	public List<Cliente> darClientes(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ps.darTablaCliente());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}

}
