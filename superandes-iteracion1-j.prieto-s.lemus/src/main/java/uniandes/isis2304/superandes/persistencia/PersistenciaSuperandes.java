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
	public static final String COMPRA_FACTURA = "COMPRA_FACTURA";
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
	 * Atributo para el acceso a la tabla CLIENTE
	 */
	private SQLCliente sqlCliente; 
	

	
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
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{	
		sqlProducto = new SQLProducto(this);
		sqlCliente = new SQLCliente(this);
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
	public Cliente adicionarEmpresa(Long id, String nombre, String correo, String nit, String direccion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCliente = nextval ();
            long tuplasInsertadas = sqlCliente.adicionarEmpresa(pm, id, nombre, correo, nit, direccion);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Empresa(id, nombre, correo, nit, direccion);
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
	public Cliente adicionarPersonaNatural(Long id, String nombre, String correo, String identificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCliente = nextval ();
            long tuplasInsertadas = sqlCliente.adicionarPersonaNatural(pm, id, nombre, correo, identificacion);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new PersonaNatural(id, nombre, correo, identificacion);
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
	
	
//	/**
//	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
//	 * Adiciona entradas al log de la aplicación
//	 * @param nombre - El nombre del bebedor
//	 * @param ciudad - La ciudad del bebedor
//	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
//	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
//	 */
//	public Bebedor adicionarBebedor(String nombre, String ciudad, String presupuesto) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long idBebedor = nextval ();
//            long tuplasInsertadas = sqlBebedor.adicionarBebedor(pmf.getPersistenceManager(), idBebedor, nombre, ciudad, presupuesto);
//            tx.commit();
//
//            log.trace ("Inserción de bebedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
//            
//            return new Bebedor (idBebedor, nombre, ciudad, presupuesto);
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//        	return null;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el nombre del bebedor
//	 * Adiciona entradas al log de la aplicación
//	 * @param nombre - El nombre del bebedor
//	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
//	 */
//	public long eliminarBebedorPorNombre(String nombre) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long resp = sqlBebedor.eliminarBebedorPorNombre (pm, nombre);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//            return -1;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
//	 * Adiciona entradas al log de la aplicación
//	 * @param idBebedor - El identificador del bebedor
//	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
//	 */
//	public long eliminarBebedorPorId (long idBebedor) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long resp = sqlBebedor.eliminarBebedorPorId (pm, idBebedor);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//            return -1;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el nombre dado
//	 * @param nombreBebedor - El nombre del bebedor
//	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
//	 */
//	public List<Bebedor> darBebedoresPorNombre (String nombreBebedor) 
//	{
//		return sqlBebedor.darBebedoresPorNombre (pmf.getPersistenceManager(), nombreBebedor);
//	}
//
//	/**
//	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
//	 * @param idBebedor - El identificador del bebedor
//	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
//	 */
//	public Bebedor darBebedorPorId (long idBebedor) 
//	{
//		return (Bebedor) sqlBebedor.darBebedorPorId (pmf.getPersistenceManager(), idBebedor);
//	}
//
//	/**
//	 * Método que consulta TODA LA INFORMACIÓN DE UN BEBEDOR con el identificador dado. Incluye la información básica del bebedor,
//	 * las visitas realizadas y las bebidas que le gustan.
//	 * @param idBebedor - El identificador del bebedor
//	 * @return El objeto BEBEDOR, construido con base en las tuplas de la tablas BEBEDOR, VISITAN, BARES, GUSTAN, BEBIDAS y TIPOBEBIDA,
//	 * relacionadas con el identificador de bebedor dado
//	 */
//	public Bebedor darBebedorCompleto (long idBebedor) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Bebedor bebedor = (Bebedor) sqlBebedor.darBebedorPorId (pm, idBebedor);
//		bebedor.setVisitasRealizadas(armarVisitasBebedor (sqlBebedor.darVisitasRealizadas (pm, idBebedor)));
//		bebedor.setBebidasQueLeGustan(armarGustanBebedor (sqlBebedor.darBebidasQueLeGustan (pm, idBebedor)));
//		return bebedor;
//	}
//
//	/**
//	 * Método que consulta todas las tuplas en la tabla BEBEDOR
//	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
//	 */
//	public List<Bebedor> darBebedores ()
//	{
//		return sqlBebedor.darBebedores (pmf.getPersistenceManager());
//	}
// 
//	/**
//	 * Método que consulta los bebedores y el número de visitas que ha realizado
//	 * @return La lista de parejas de objetos, construidos con base en las tuplas de la tabla BEBEDOR y VISITAN. 
//	 * El primer elemento de la pareja es un bebedor; 
//	 * el segundo elemento es el número de visitas de ese bebedor (0 en el caso que no haya realizado visitas)
//	 */
//	public List<Object []> darBebedoresYNumVisitasRealizadas ()
//	{
//		List<Object []> respuesta = new LinkedList <Object []> ();
//		List<Object> tuplas = sqlBebedor.darBebedoresYNumVisitasRealizadas (pmf.getPersistenceManager());
//        for ( Object tupla : tuplas)
//        {
//			Object [] datos = (Object []) tupla;
//			long idBebedor = ((BigDecimal) datos [0]).longValue ();
//			String nombreBebedor = (String) datos [1];
//			String ciudadBebedor = (String) datos [2];
//			String presupuesto = (String) datos [3];
//			int numBares = ((BigDecimal) datos [4]).intValue ();
//
//			Object [] resp = new Object [2];
//			resp [0] = new Bebedor (idBebedor, nombreBebedor, ciudadBebedor, presupuesto);
//			resp [1] = numBares;	
//			
//			respuesta.add(resp);
//        }
//
//		return respuesta;
//	}
// 
//	/**
//	 * Método que consulta CUÁNTOS BEBEDORES DE UNA CIUDAD VISITAN BARES
//	 * @param ciudad - La ciudad que se quiere consultar
//	 * @return El número de bebedores de la ciudad dada que son referenciados en VISITAN
//	 */
//	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
//	{
//		return sqlBebedor.darCantidadBebedoresCiudadVisitanBares (pmf.getPersistenceManager(), ciudad);
//	}
//	
//	/**
//	 * Método que actualiza, de manera transaccional, la ciudad de un  BEBEDOR
//	 * @param idBebedor - El identificador del bebedor
//	 * @param ciudad - La nueva ciudad del bebedor
//	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
//	 */
//	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long resp = sqlBebedor.cambiarCiudadBebedor (pm, idBebedor, ciudad);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//            return -1;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
//	 * Si el bebedor está referenciado en alguna otra relación, no se borra ni el bebedor NI las visitas
//	 * @param idBebedor - El identificador del bebedor
//	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
//	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
//	 */
//	public long []  eliminarBebedorYVisitas_v1 (long idBebedor)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long [] resp = sqlBebedor.eliminarBebedorYVisitas_v1 (pm, idBebedor);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//        	return new long[] {-1, -1};
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//	
//	/**
//	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
//	 * Si el bebedor está referenciado en alguna otra relación, no se puede borrar, SIN EMBARGO SÍ SE BORRAN TODAS SUS VISITAS
//	 * @param idBebedor - El identificador del bebedor
//	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
//	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
//	 */
//	public long [] eliminarBebedorYVisitas_v2 (long idBebedor)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long visitasEliminadas = eliminarVisitanPorIdBebedor(idBebedor);
//            long bebedorEliminado = eliminarBebedorPorId (idBebedor);
//            tx.commit();
//            return new long [] {bebedorEliminado, visitasEliminadas};
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//        	return new long [] {-1, -1};
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}	
//
//	/**
//	 * Método privado para generar las información completa de las visitas realizadas por un bebedor: 
//	 * La información básica del bar visitado, la fecha y el horario, en el formato esperado por los objetos BEBEDOR
//	 * @param tuplas - Una lista de arreglos de 7 objetos, con la información del bar y de la visita realizada, en el siguiente orden:
//	 *   bar.id, bar.nombre, bar.ciudad, bar.presupuesto, bar.cantsedes, vis.fechavisita, vis.horario
//	 * @return Una lista de arreglos de 3 objetos. El primero es un objeto BAR, el segundo corresponde a la fecha de la visita y
//	 * el tercero corresponde al horaario de la visita
//	 */
//	private List<Object []> armarVisitasBebedor (List<Object []> tuplas)
//	{
//		List<Object []> visitas = new LinkedList <Object []> ();
//		for (Object [] tupla : tuplas)
//		{
//			long idBar = ((BigDecimal) tupla [0]).longValue ();
//			String nombreBar = (String) tupla [1];
//			String ciudadBar = (String) tupla [2];
//			String presupuestoBar = (String) tupla [3];
//			int sedesBar = ((BigDecimal) tupla [4]).intValue ();
//			Timestamp fechaVisita = (Timestamp) tupla [5];
//			String horarioVisita = (String) tupla [6];
//			
//			Object [] visita = new Object [3];
//			visita [0] = new Bar (idBar, nombreBar, ciudadBar, presupuestoBar, sedesBar);
//			visita [1] = fechaVisita;
//			visita [2] = horarioVisita;
//
//			visitas.add (visita);
//		}
//		return visitas;
//	}
//	
//	/**
//	 * Método privado para generar las información completa de las bebidas que le gustan a un bebedor: 
//	 * La información básica de la bebida, especificando también el nombre de la bebida, en el formato esperado por los objetos BEBEDOR
//	 * @param tuplas - Una lista de arreglos de 5 objetos, con la información de la bebida y del tipo de bebida, en el siguiente orden:
//	 * 	 beb.id, beb.nombre, beb.idtipobebida, beb.gradoalcohol, tipobebida.nombre
//	 * @return Una lista de arreglos de 2 objetos. El primero es un objeto BEBIDA, el segundo corresponde al nombre del tipo de bebida
//	 */
//	private List<Object []> armarGustanBebedor (List<Object []> tuplas)
//	{
//		List<Object []> gustan = new LinkedList <Object []> ();
//		for (Object [] tupla : tuplas)
//		{			
//			long idBebida = ((BigDecimal) tupla [0]).longValue ();
//			String nombreBebida = (String) tupla [1];
//			long idTipoBebida = ((BigDecimal) tupla [2]).longValue ();
//			int gradoAlcohol = ((BigDecimal) tupla [3]).intValue ();
//			String nombreTipo = (String) tupla [4];
//
//			Object [] gusta = new Object [2];
//			gusta [0] = new Bebida (idBebida, nombreBebida, idTipoBebida, gradoAlcohol);
//			gusta [1] = nombreTipo;	
//			
//			gustan.add(gusta);
//		}
//		return gustan;
//	}

 }
