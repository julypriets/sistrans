package uniandes.isis2304.superandes.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Empresa;
import uniandes.isis2304.superandes.negocio.FacturaCliente;
import uniandes.isis2304.superandes.negocio.Persona;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.ProductoPorSemana;

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
	public Long registrarPersonaNatural(PersistenceManager pm, Long id, String nombre, String correo, long identificacion) {
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
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @return Todos los clientes registrados
	 */
	public List<Cliente> darClientes(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT id, nombre, correo FROM " + ps.darTablaCliente());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @param identificacion
	 * @return La persona natural correspondiente al documento de identificación
	 */
	public Cliente darClientePersonaPorId(PersistenceManager pm, long identificacion){
		String select = "SELECT id, nombre, correo, identificacion " +
						"FROM CLIENTE c, PERSONA p " +
						"WHERE " +
						    "c.id = p.id_cliente AND " +
						    "p.identificacion = " + identificacion;
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(Persona.class);
		return (Persona) q.executeUnique();
		// ((List<Persona>) q.executeList()).get(0)
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @param nit
	 * @return La empresa correspondiente al nit
	 */
	public Cliente darClienteEmpresaPorId(PersistenceManager pm, long nit){
		String select = "SELECT id, nombre, correo, nit, direccion " +
				"FROM CLIENTE c, EMPRESA e " +
				"WHERE " +
				    "c.id = e.id_cliente AND " +
				    "e.nit = ? ";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(Empresa.class);
		q.setParameters(nit);
		return (Empresa) q.executeUnique();
	}

	
	/* ****************************************************************
	 * 			Métodos consulta para Iteración 3
	 *****************************************************************/
	
	/**
	 * Retorna la información de todos los clientes que han comprado por lo menos una vez un determinado producto 
	 * en un rango de fechas especificado
	 * @param pm
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param criterioOrdenamiento
	 * @param nombreProducto
	 * @return Una colección de la información de los clientes y su compra
	 */
	public List<FacturaCliente> clientesQueCompraronElProductoPorRangoFecha(
			PersistenceManager pm, Date fechaInicial, Date fechaFinal, String criterioOrdenamiento, String nombreProducto, long idSucursal) {
		
		String select1 = "SELECT f.id \"id\", fecha \"fecha\", f.precio_total \"precioTotal\", f.id_cliente \"idCliente\", c.nombre \"nombre\", c.correo \"correo\", p.nombre \"nombreProducto\", COUNT (f.id_cliente) \"cantidad\" \n" + 
				"FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p, CAJERO cj\n" + 
				"WHERE\n" + 
				"    f.id_cliente = c.id AND\n" + 
				"    f.id = cp.id_factura AND\n" + 
				"    p.codigo_barras = cp.id_producto AND\n" + 
				"    p.nombre = 'Beans - French' AND\n" + 
				"    cj.id = f.id_cajero AND\n" + 
				"    f.fecha BETWEEN to_date('01-JAN-01') AND to_date('01-NOV-01')\n" + 
				"GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras\n" + 
				"ORDER BY c.nombre";
		
		String select = "SELECT f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre nombreProducto, COUNT (f.id_cliente) cantidad\n" + 
				"FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p, CAJERO cj\n" + 
				"WHERE\n" + 
				"    f.id_cliente = c.id AND\n" + 
				"    f.id = cp.id_factura AND \n" + 
				"    p.codigo_barras = cp.id_producto AND\n" +
				"    cj.id = f.id_cajero \n" +
				"    AND p.nombre = '" + nombreProducto + "'\n" + 
				"    AND f.fecha BETWEEN (timestamp  '" + fechaInicial+ "') AND (timestamp '" + fechaFinal + "')\n";
		
		if(idSucursal > 0) {
			select += "AND cj.id_sucursal = " + idSucursal + "\n";
		}
		
		select += 
				"GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras\n" +
				"ORDER BY " + criterioOrdenamiento;
		
		Query q = pm.newQuery(SQL, select1);
		q.setResultClass(FacturaCliente.class);
		//q.setParameters(fechaInicial, fechaFinal);
		return (List<FacturaCliente>) q.executeList();
	}
	
	/**
	 * Retorna la información de todos los clientes que no han comprado por lo menos una vez un determinado producto 
	 * en un rango de fechas especificado
	 * @param pm
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param criterioOrdenamiento
	 * @param nombreProducto
	 * @return Una colección de la información de los clientes y su compra
	 */
	public List<FacturaCliente> clientesQueNoCompraronElProductoPorRangoFecha(
			PersistenceManager pm, Date fechaInicial, Date fechaFinal, String criterioOrdenamiento, String nombreProducto, long idSucursal){
		String select = "SELECT f.id \"id\", fecha \"fecha\", f.precio_total \"precioTotal\", f.id_cliente \"idCliente\", c.nombre \"nombre\", c.correo \"correo\", p.nombre \"nombreProducto\", cp.cantidad \"cantidad\"\n" + 
				"FROM FACTURA f, CLIENTE c, COMPRA cp, Producto p, CAJERO cj \n" + 
				"WHERE\n" + 
				"    f.id_cliente = c.id AND\n" + 
				"    f.id = cp.id_factura AND \n" + 
				"    p.codigo_barras = cp.id_producto AND\n" +
				"    cj.id = f.id_cajero AND\n" +
				"    c.id IN (\n" + 
				"        SELECT id FROM CLIENTE\n" + 
				"        MINUS\n" + 
				"        (SELECT id_cliente FROM FACTURA WHERE p.nombre = '" + nombreProducto + "' AND f.fecha BETWEEN (timestamp  '" + fechaInicial+ "') AND (timestamp '" + fechaFinal + "'))\n" + 
				"    )\n";
		
		if(idSucursal > 0) {
			select += "AND cj.id_sucursal = " + idSucursal + "\n";
		}
		
		select += 
				"GROUP BY f.id, f.fecha, f.precio_total, f.id_cliente, c.nombre, c.correo, p.nombre, p.codigo_barras, cp.cantidad\n" + 
				"ORDER BY " + criterioOrdenamiento;
		
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(FacturaCliente.class);
		//q.setParameters(fechaInicial, fechaFinal);
		return (List<FacturaCliente>) q.executeList();
	}
	
}
