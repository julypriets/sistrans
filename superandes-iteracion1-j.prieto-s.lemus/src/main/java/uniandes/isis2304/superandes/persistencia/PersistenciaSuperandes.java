/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superandes.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.negocio.Bebedor;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Compra;
import uniandes.isis2304.superandes.negocio.ComprasPorPromocion;
import uniandes.isis2304.superandes.negocio.Empresa;
import uniandes.isis2304.superandes.negocio.PersonaNatural;
import uniandes.isis2304.superandes.negocio.Producto;


/**
 * Clase para el manejador de persistencia del proyecto Superandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * 
 */
public class PersistenciaSuperandes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaSuperandes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";
	
	/**
	 * Cada una de las siguientes constantes hace referencia a todos los 
	 * nombres de las tablas existentes en Superandes
	 */
	public static final String SUCURSAL = "SUCURSAL";
	public static final String PRODUCTO = "PRODUCTO";
	public static final String CATEGORÍA = "CATEGORÍA";
	public static final String PERECEDERO = "PERECEDERO";
	public static final String ORDEN = "ORDEN";
	public static final String ESTANTE = "ESTANTE";
	public static final String BODEGA = "BODEGA";
	public static final String PEDIDO_BODEGA = "PEDIDO_BODEGA";
	public static final String PROVEEDOR = "PROVEEDOR";
	public static final String CLIENTE = "CLIENTE";
	public static final String PERSONA_NATURAL = "PERSONA_NATURAL";
	public static final String EMPRESA = "EMPRESA";
	public static final String CAJERO = "CAJERO";
	public static final String FACTURA = "FACTURA";
	public static final String PROMOCION = "PROMOCION";
	public static final String USUARIO = "USUARIO";
	public static final String ABASTECIMIENTO_ESTANTE = "ABASTECIMIENTO_ESTANTE";
	public static final String SURTIDO_ESTANTE = "SURTIDO_ESTANTE";
	public static final String PRODUCTOS_ABASTECIMIENTO = "PRODUCTOS_ABASTECIMIENTO";
	public static final String INVENTARIO = "INVENTARIO";
	public static final String REORDEN_SUCURSAL = "REORDEN_SUCURSAL";
	public static final String DETALLE_PEDIDO = "DETALLE_PEDIDO";
	public static final String PEDIDO = "PEDIDO";
	public static final String COMPRA_FACTURA = "COMPRA";
	public static final String PRODUCTO_PROMOCION = "PRODUCTO_PROMOCION";
	public static final String SUPA_SEQ = "Superandes_sequence";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaSuperandes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, Sucursal, Producto, Categoría, Perecedero, Orden, Estante, Bodega,
	 * Pedido_Bodega, Proveedor, Cliente, Persona_Natural, Empresa, Cajero, Factura, Promocion, Usuario,
	 * Abastecimiento_Estante, Surtido_Estante, Productos_Abastecimiento, Inventario, Reorden_Sucursal,
	 * Detalle_Pedido, Pedido, Compra_Factura, Producto_Promocion.
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaSuperandes
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla PRODUCTO
	 */
	private SQLProducto sqlProducto;
	
	/**
	 * Atributo para el acceso a la tabla CLIENTE, EMPRESA y PERSONA_NATURAL
	 */
	private SQLCliente sqlCliente; 
	
	/**
	 * Atributo para el acceso a la tabla PROMOCION
	 */
	private SQLPromocion sqlPromocion;
	
	/**
	 * Atributo para el acceso a la tabla COMPRA
	 */
	private SQLCompra sqlCompra; 
	
	/**
	 * Atributo para el acceso a la tabla COMPRA
	 */
	private SQLCompra sqlOrden; 

	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaSuperandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Superandes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Superandes_sequence");
		tablas.add ("SUCURSAL");
		tablas.add ("PRODUCTO");
		tablas.add ("CATEGORÍA");
		tablas.add ("PERECEDERO");
		tablas.add ("ORDEN");
		tablas.add ("ESTANTE");
		tablas.add ("BODEGA");
		tablas.add ("PEDIDO_BODEGA");
		tablas.add ("PROVEEDOR");
		tablas.add ("CLIENTE");
		tablas.add ("PERSONA_NATURAL");
		tablas.add ("EMPRESA");
		tablas.add ("CAJERO");
		tablas.add ("FACTURA");
		tablas.add ("PROMOCION");
		tablas.add ("USUARIO");
		tablas.add ("ABASTECIMIENTO_ESTANTE");
		tablas.add ("SURTIDO_ESTANTE");
		tablas.add ("PRODUCTOS_ABASTECIMIENTO");
		tablas.add ("INVENTARIO");
		tablas.add ("REORDEN_SUCURSAL");
		tablas.add ("DETALLE_PEDIDO");
		tablas.add ("COMPRA_FACTURA");
		tablas.add ("PRODUCTO_PROMOCION");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperandes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaSuperandes existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	public String darTablaCliente() {
		return tablas.get(10);
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{	
		sqlProducto = new SQLProducto(this);
		sqlCliente = new SQLCliente(this);
		sqlPromocion = new SQLPromocion(this);
		sqlCompra = new SQLCompra(this);
		sqlUtil = new SQLUtil(this);
	}

	
	/**
	 * Transacción para el generador de secuencia de Superandes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Superandes
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}


	/* ****************************************************************
	 * 			Métodos para manejar los PRODUCTOS
	 *****************************************************************/

	/**
	 * Se encarga de adicionar un producto según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param nombre
	 * @param marca
	 * @param precioUnitario
	 * @param presentacion
	 * @param precioUnidadMedida
	 * @param unidadMedida
	 * @param empacado
	 * @param codigoBarras
	 * @param nivelReorden
	 * @param existencias
	 * @param idCategoria
	 * @param idSucursal
	 * @return
	 */
	public Producto adicionarProducto(String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idProducto = nextval ();
            long tuplasInsertadas = sqlProducto.adicionarProducto(pm, nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, nivelReorden, existencias, idCategoria, idSucursal);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Producto(nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, nivelReorden, existencias, idCategoria, idSucursal);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Retorna todos los productos existentes
	 * @return
	 */
	public List<Producto> darProductos(){
		return sqlProducto.darProductos(pmf.getPersistenceManager());
	}
	
	public Producto darProductoPorId(long idProducto) {
		return sqlProducto.darProductoPorId(pmf.getPersistenceManager(), idProducto);
	}

	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/
	
	/**
	 * Se encarga de adicionar una empresa según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param id
	 * @param nombre
	 * @param correo
	 * @param nit
	 * @param direccion
	 * @return
	 */
	public Cliente adicionarEmpresa(String nombre, String correo, String nit, String direccion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCliente = nextval ();
            long tuplasInsertadas = sqlCliente.adicionarEmpresa(pm, idCliente, nombre, correo, nit, direccion);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Empresa(idCliente, nombre, correo, nit, direccion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Se encarga de adicionar una persona natural según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param id
	 * @param nombre
	 * @param correo
	 * @param identificacion
	 * @return
	 */
	public Cliente adicionarPersonaNatural(String nombre, String correo, String identificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCliente = nextval ();
            long tuplasInsertadas = sqlCliente.adicionarPersonaNatural(pm, idCliente, nombre, correo, identificacion);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new PersonaNatural(idCliente, nombre, correo, identificacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public List<Cliente> darClientes(){
		return sqlCliente.darClientes(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/
	
	/**
	 * Finaliza una promoción para todos los productos asociados
	 * @param idPromocion
	 * @return Número de tuplas modificadas 
	 */
	public long finalizarPromocion(Date fechaActual) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlPromocion.finalizarPromocion(pm, fechaActual);
			tx.commit();
			return resp;
			
		} catch (Exception e) {
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
		} finally {
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();

		}
	}
	
	/**
	 * Retorna las 20 promociones más populares entre las que no están finalizadas
	 * por tiempo o por bajas existencias
	 * @return id de la promoción y su cantidad de compras 
	 */
	public List<ComprasPorPromocion> dar20PromocionesMasPopulares() {
		return sqlPromocion.dar20PromocionesMasPopulares(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las COMPRAS
	 *****************************************************************/
	
	/**
	 * Retorna el número de compras realizadas para un producto
	 * @param idProducto
	 * @return
	 */
	public long darNumeroDeComprasPorProducto(long idProducto) {
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlCompra.darNumeroDeComprasPorProducto(pm, idProducto);
	}
	
	/**
	 * Registra una nueva compra dada la factura y el producto
	 * @param idProducto
	 * @param idFactura
	 * @return
	 */
	public Compra adicionarCompra(long idProducto, long idFactura) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlCompra.adicionarCompra(pm, idProducto, idFactura);
            tx.commit();
            
            log.trace ("Inserción de compra con producto:  " + idProducto + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Compra(idProducto, idFactura);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las ÓRDENES
	 *****************************************************************/
	
	
 }
