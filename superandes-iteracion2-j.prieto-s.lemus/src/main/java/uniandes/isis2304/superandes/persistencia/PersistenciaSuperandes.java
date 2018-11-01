package uniandes.isis2304.superandes.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import uniandes.isis2304.superandes.negocio.Bodega;
import uniandes.isis2304.superandes.negocio.Categoria;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.ComprasPorPromocion;
import uniandes.isis2304.superandes.negocio.Empresa;
import uniandes.isis2304.superandes.negocio.Estante;
import uniandes.isis2304.superandes.negocio.Persona;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.Promocion;
import uniandes.isis2304.superandes.negocio.Proveedor;
import uniandes.isis2304.superandes.negocio.Sucursal;

/**
 * Clase para el manejador de persistencia del proyecto Superandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases:
 * SQLSucursal, SQLCategoria SQLProducto, SQLOrden, SQLEstante, SQLBodega, SQLAbastecimiento,
 * SQLProveedor, SQLCliente, SQLPersona, SQLEmpresa, SQLCajero, SQLFactura, SQLPromocion, SQLUsuario, 
 * SQLCarrito, SQLAbastecimientoBodega, SQLSurtido, SQLProductoAbastecimiento, SQLInventario,
 * SQLProductoOrden, SQLCatalogo, SQLOrdenProveedor, SQLCompra, SQLProductoPromocion, SQLFacturaPromocion y SQLCarritoProducto
 * que son las que realizan el acceso a la base de datos
 * 
 */
public class PersistenciaSuperandes {

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
	 * Secuenciador, sucursal, categoria, producto, orden, estante, bodega, abastecimiento, 
	 * proveedor, persona, empresa, cajero, factura, promocion, usuario, abastecimiento_bodega,
	 * surtido, producto_abastecimiento, inventario, producto_orden, catalogo, orden_proveedor,
	 * compra, producto_promocion, factura promocion.
	 */
	private ArrayList<String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaSuperandes
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
	 */
	private SQLSucursal sqlSucursal;

	/**
	 * Atributo para el acceso a la tabla CATEGORIA de la base de datos
	 */
	private SQLCategoria sqlCategoria;

	/**
	 * Atributo para el acceso a la tabla PRODUCTO de la base de datos
	 */
	private SQLProducto sqlProducto;

	/**
	 * Atributo para el acceso a la tabla ORDEN de la base de datos
	 */
	private SQLOrden sqlOrden;

	/**
	 * Atributo para el acceso a la tabla ESTANTE de la base de datos
	 */
	private SQLEstante sqlEstante;

	/**
	 * Atributo para el acceso a la tabla BODEGA de la base de datos
	 */
	private SQLBodega sqlBodega;

	/**
	 * Atributo para el acceso a la tabla ABASTECIMIENTO de la base de datos
	 */
	private SQLAbastecimiento sqlAbastecimiento;

	/**
	 * Atributo para el acceso a la tabla PROVEEDOR de la base de datos
	 */
	private SQLProveedor sqlProveedor;

	/**
	 * Atributo para el acceso a la tabla CLIENTE de la base de datos
	 */
	private SQLCliente sqlCliente;
	
	/**
	 * Atributo para el acceso a la tabla PERSONA de la base de datos
	 */
	private SQLPersona sqlPersona;

	/**
	 * Atributo para el acceso a la tabla EMPRESA de la base de datos
	 */
	private SQLEmpresa sqlEmpresa;

	/**
	 * Atributo para el acceso a la tabla CAJERO de la base de datos
	 */
	private SQLCajero sqlCajero;

	/**
	 * Atributo para el acceso a la tabla FACTURA de la base de datos
	 */
	private SQLFactura sqlFactura;

	/**
	 * Atributo para el acceso a la tabla PROMOCION de la base de datos
	 */
	private SQLPromocion sqlPromocion;

	/**
	 * Atributo para el acceso a la tabla USUARIO de la base de datos
	 */
	private SQLUsuario sqlUsuario;

	/**
	 * Atributo para el acceso a la tabla CARRITO de la base de datos
	 */
	private SQLCarrito sqlCarrito;
	
	/**
	 * Atributo para el acceso a la tabla ABASTECIMIENTO_BODEGA de la base de datos
	 */
	private SQLAbastecimientoBodega sqlAbastecimientoBodega;

	/**
	 * Atributo para el acceso a la tabla SURTIDO de la base de datos
	 */
	private SQLSurtido sqlSurtido;

	/**
	 * Atributo para el acceso a la tabla PRODUCTO_ABASTECIMIENTO de la base de datos
	 */
	private SQLProductoAbastecimiento sqlProductoAbastecimiento;

	/**
	 * Atributo para el acceso a la tabla INVENTARIO de la base de datos
	 */
	private SQLInventario sqlInventario;

	/**
	 * Atributo para el acceso a la tabla PRODUCTO_ORDEN de la base de datos
	 */
	private SQLProductoOrden sqlProductoOrden;

	/**
	 * Atributo para el acceso a la tabla CATALOGO de la base de datos
	 */
	private SQLCatalogo sqlCatalogo;

	/**
	 * Atributo para el acceso a la tabla ORDEN_PROVEEDOR de la base de datos
	 */
	private SQLOrdenProveedor sqlOrdenProveedor;

	/**
	 * Atributo para el acceso a la tabla COMPRA de la base de datos
	 */
	private SQLCompra sqlCompra;

	/**
	 * Atributo para el acceso a la tabla PRODUCTO_PROMOCION de la base de datos
	 */
	private SQLProductoPromocion sqlProductoPromocion;

	/**
	 * Atributo para el acceso a la tabla FACTURA_PROMOCION de la base de datos
	 */
	private SQLFacturaPromocion sqlFacturaPromocion;
	
	/**
	 * Atributo para el acceso a la tabla CARRITO_PRODUCTO de la base de datos
	 */
	private SQLCarritoProducto sqlCarritoProducto;


	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/
	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaSuperandes()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Superandes");
		crearClasesSQL();

		//Define los nombres por defecto de las tablas de la base de datos
		tablas = new ArrayList<String>();
		tablas.add("Superandes_sequence");
		tablas.add("SUCURSAL");
		tablas.add("CATEGORIA");
		tablas.add("PRODUCTO");
		tablas.add("ORDEN");
		tablas.add("ESTANTE");
		tablas.add("BODEGA");
		tablas.add("ABASTECIMIENTO");
		tablas.add("PROVEEDOR");
		tablas.add("CLIENTE");
		tablas.add("PERSONA");
		tablas.add("EMPRESA");
		tablas.add("CAJERO");
		tablas.add("FACTURA");
		tablas.add("PROMOCION");
		tablas.add("USUARIO");
		tablas.add("CARRITO");
		tablas.add("ABSATECIMIENTO_BODEGA");
		tablas.add("SURTIDO");
		tablas.add("PRODUCTO_ABASTECIMIENTO");
		tablas.add("INVENTARIO");
		tablas.add("PRODUCTO_ORDEN");
		tablas.add("CATALOGO");
		tablas.add("ORDEN_PROVEEDOR");
		tablas.add("COMPRA");
		tablas.add("PRODUCTO_PROMOCION");
		tablas.add("FACTURA_PROMOCION");
		tablas.add("CARRITO_PRODUCTO");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperandes (JsonObject tableConfig){
		crearClasesSQL();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaSuperandes existente - Patrón SINGLETON
	 */
	public static PersistenciaSuperandes getInstance (){
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
	public static PersistenciaSuperandes getInstance (JsonObject tableConfig){
		if (instance == null)
		{
			instance = new PersistenciaSuperandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia (){
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private ArrayList<String> leerNombresTablas (JsonObject tableConfig){
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		ArrayList<String> resp = new ArrayList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL() {
		sqlSucursal = new SQLSucursal(this);
		sqlCategoria = new SQLCategoria(this);
		sqlProducto = new SQLProducto(this);
		sqlOrden = new SQLOrden(this);
		sqlEstante = new SQLEstante(this);
		sqlBodega = new SQLBodega(this);
		sqlAbastecimiento = new SQLAbastecimiento(this);
		sqlProveedor = new SQLProveedor(this);
		sqlCliente = new SQLCliente(this);
		sqlPersona = new SQLPersona(this);
		sqlEmpresa = new SQLEmpresa(this);
		sqlCajero = new SQLCajero(this);
		sqlFactura = new SQLFactura(this);
		sqlPromocion = new SQLPromocion(this);
		sqlUsuario = new SQLUsuario(this);
		sqlCarrito = new SQLCarrito(this);
		sqlAbastecimientoBodega = new SQLAbastecimientoBodega(this);
		sqlSurtido = new SQLSurtido(this);
		sqlProductoAbastecimiento = new SQLProductoAbastecimiento(this);
		sqlInventario = new SQLInventario(this);
		sqlProductoOrden = new SQLProductoOrden(this);
		sqlCatalogo = new SQLCatalogo(this);
		sqlOrdenProveedor = new SQLOrdenProveedor(this);
		sqlCompra = new SQLCompra(this);
		sqlProductoPromocion = new SQLProductoPromocion(this);
		sqlFacturaPromocion = new SQLFacturaPromocion(this);
		sqlCarritoProducto = new SQLCarritoProducto(this);
		sqlUtil = new SQLUtil(this);

	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de Superandes
	 */
	public String darSeqSuperandes(){
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sucursal de Superandes
	 */
	public String darTablaSucursal(){
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Categoria de Superandes
	 */
	public String darTablaCategoria(){
		return tablas.get(2);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla Producto de Superandes
	 */
	public String darTablaProducto() {
		return tablas.get(3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Orden de Superandes
	 */
	public String darTablaOrden() {
		return tablas.get(4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Estante de Superandes
	 */
	public String darTablaEstante() {
		return tablas.get(5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Bodega de Superandes
	 */
	public String darTablaBodega() {
		return tablas.get(6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Abastecimiento de Superandes
	 */
	public String darTablaAbastecimiento() {
		return tablas.get(7);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Proveedor de Superandes
	 */
	public String darTablaProveedor() {
		return tablas.get(8);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Cliente de Superandes
	 */
	public String darTablaCliente() {
		return tablas.get(9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Persona de Superandes
	 */
	public String darTablaPersona() {
		return tablas.get(10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Empresa de Superandes
	 */
	public String darTablaEmpresa() {
		return tablas.get(11);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Cajero de Superandes
	 */
	public String darTablaCajero() {
		return tablas.get(12);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Factura de Superandes
	 */
	public String darTablaFactura() {
		return tablas.get(13);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Promocion de Superandes
	 */
	public String darTablaPromocion() {
		return tablas.get(14);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Usuario de Superandes
	 */
	public String darTablaUsuario() {
		return tablas.get(15);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Carrito de Superandes
	 */
	public String darTablaCarrito() {
		return tablas.get(16);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla AbastecimientoBodega de Superandes
	 */
	public String darTablaAbastecimientoBodega() {
		return tablas.get(17);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Surtido de Superandes
	 */
	public String darTablaSurtido() {
		return tablas.get(18);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ProductoAbastecimiento de Superandes
	 */
	public String darTablaProductoAbastecimiento() {
		return tablas.get(19);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Inventario de Superandes
	 */
	public String darTablaInventario() {
		return tablas.get(20);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ProductoOrden de Superandes
	 */
	public String darTablaProductoOrden() {
		return tablas.get(21);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Catalogo de Superandes
	 */
	public String darTablaCatalogo() {
		return tablas.get(22);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla OrdenProveedor de Superandes
	 */
	public String darTablaOrdenProveedor() {
		return tablas.get(23);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla Compra de Superandes
	 */
	public String darTablaCompra() {
		return tablas.get(24);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ProductoPromocion de Superandes
	 */
	public String darTablaProductoPromocion() {
		return tablas.get(25);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla FacturaPromocion de Superandes
	 */
	public String darTablaFacturaPromocion() {
		return tablas.get(26);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla CarritoProducto de Superandes
	 */
	public String darTablaCarritoProducto() {
		return tablas.get(27);
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
	
	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarSuperandes()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarSuperandes (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        			-1, -1, -1, -1, -1, -1, -1};
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
	public Producto registrarProducto(String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal, Timestamp fechaVencimiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idProducto = nextval ();
            long tuplasInsertadas = sqlProducto.registarProducto(pm, nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, idCategoria, nivelReorden, existencias, fechaVencimiento);
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Producto(nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, nivelReorden, existencias, idCategoria, idSucursal, fechaVencimiento);
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
	

	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/
	/**
	 * 
	 * @param tipo
	 * @param precio
	 * @param fechaInicio
	 * @param fechaFin
	 * @param idSucursal
	 * @param idProducto
	 * @param cantidad1
	 * @param cantidad2
	 * @param descuento
	 * @return La promoción registrada
	 */
	public Promocion registrarPromocion (int tipo, double precio, Timestamp fechaInicio, 
			Timestamp fechaFin, long idSucursal, String idProducto, int cantidad1, int cantidad2, double descuento){
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idPromocion = nextval();
            long tuplasInsertadas = sqlPromocion.registrarPromocion(pm, idPromocion, tipo, precio, fechaInicio, fechaFin, idSucursal, idProducto, cantidad1, cantidad2, descuento);
            tx.commit();
            
            log.trace ("Inserción de promoción: " + tipo + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Promocion(idPromocion, tipo, precio, fechaInicio, fechaFin, cantidad1, cantidad2, descuento, idSucursal, idProducto);
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
	 * 			Métodos para manejar las CATEGORÍAS
	 *****************************************************************/
	
	/**
	 * 
	 * @param idCategoria
	 * @return La categoría correspondiente al id
	 */
	public Categoria darCategoriaPorId(long idCategoria){
		return sqlCategoria.darCategoriaPorId(pmf.getPersistenceManager(), idCategoria);
	}
	
	/**
	 * 
	 * @param nombreCategoria
	 * @return La categoría correspondiente al nombre
	 */
	public Categoria darCategoriaPorNombre(String nombreCategoria){
		return sqlCategoria.darCategoriaPorNombre(pmf.getPersistenceManager(), nombreCategoria);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las SUCURSALES
	 *****************************************************************/
	
	/**
	 * 
	 * @param nombre
	 * @param ciudad
	 * @param direccion
	 * @return la sucursal registrada 
	 */
	public Sucursal registrarSucursal(String nombre, String ciudad, String direccion){
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idSucursal = nextval();
            long tuplasInsertadas = sqlSucursal.registrarSucursal(pm, idSucursal, nombre, ciudad, direccion);
            tx.commit();
            
            log.trace ("Inserción de sucursal: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Sucursal(idSucursal, nombre, ciudad, direccion);
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
	 * 
	 * @param idSucursal
	 * @return La sucursal correspondiente al id
	 */
	public Sucursal darSucursalPorId(long idSucursal){
		return sqlSucursal.darSucursalPorId(pmf.getPersistenceManager(), idSucursal);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las BODEGAS
	 *****************************************************************/
	
	/**
	 * 
	 * @param idCategoria
	 * @param capacidadPeso
	 * @param capacidadVolumen
	 * @param idSucursal
	 * @return La bodega registrada
	 */
	public Bodega registrarBodega(long idCategoria, double capacidadPeso, double capacidadVolumen, long idSucursal){
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idBodega = nextval();
            long tuplasInsertadas = sqlBodega.registrarBodega(pm, idBodega, idCategoria, capacidadPeso, capacidadVolumen, idSucursal);
            tx.commit();
            
            log.trace ("Inserción de bodega: " + idBodega + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Bodega(idBodega, capacidadPeso, capacidadVolumen, idCategoria, idSucursal);
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
	 * 			Métodos para manejar los ESTANTES
	 *****************************************************************/
	
	public Estante registrarEstante(long idCategoria, double capacidadPeso, double capacidadVolumen, long idSucursal){
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idEstante = nextval();
            long tuplasInsertadas = sqlEstante.registrarEstante(pm, idEstante, idCategoria, capacidadPeso, capacidadVolumen, idSucursal);
            tx.commit();
            
            log.trace ("Inserción de estante: " + idEstante + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Estante(idEstante, capacidadPeso, capacidadVolumen, idCategoria, idSucursal);
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
	 * 			Métodos para manejar los PROVEEDORES
	 *****************************************************************/
	
	/**
	 * 
	 * @param nit
	 * @param nombre
	 * @param calificacion
	 * @return El proveedor registrado
	 */
	public Proveedor registrarProveedor(String nit, String nombre, double calificacion){
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlProveedor.registarProveedor(pm, nit, nombre, calificacion);
            tx.commit();
            
            log.trace ("Inserción de proveedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Proveedor(nit, nombre, calificacion);
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
	public Cliente registrarEmpresa(String nombre, String correo, String nit, String direccion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCliente = nextval ();
            long tuplasInsertadas = sqlCliente.registarEmpresa(pm, idCliente, nombre, correo, nit, direccion);
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
	public Cliente registrarPersona(String nombre, String correo, String identificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCliente = nextval ();
            long tuplasInsertadas = sqlCliente.registrarPersonaNatural(pm, idCliente, nombre, correo, identificacion);
     
            tx.commit();
            
            log.trace ("Inserción de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Persona(idCliente, nombre, correo, identificacion);
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
	 * 
	 * @return todos los clientes registrados
	 */
	public List<Cliente> darClientes(){
		return sqlCliente.darClientes(pmf.getPersistenceManager());
	}
	
	/**
	 * 
	 * @param identificacion
	 * @return La persona natural correspondiente al documento de identificación
	 */
	public Cliente darClientePersonaPorId(long identificacion){
		return sqlCliente.darClientePersonaPorId(pmf.getPersistenceManager(), identificacion);
	}
	
	/**
	 * 
	 * @param nit
	 * @return La empresa correspondiente al nit 
	 */
	public Cliente darClienteEmpresaPorId(long nit){
		return sqlCliente.darClienteEmpresaPorId(pmf.getPersistenceManager(), nit);
	}
}
