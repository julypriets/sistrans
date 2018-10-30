package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class Superandes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Superandes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperandes ps;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Superandes ()
	{
		ps = PersistenciaSuperandes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Superandes (JsonObject tableConfig)
	{
		ps = PersistenciaSuperandes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia ();
	}
	
	
	/*
	 * REQUERIMIENTOS FUNCIONALES
	 * 
	 *  Registrar proveedores
	 *  
	 *  Registrar productos
	 *  
	 *  Registrar clientes
	 *  
	 *  Registar una sucursal
	 *  
	 *  Registrar una bodega a una sucursal
	 *  
	 *  Registrar un estante en una sucursal
	 *  
	 *  Registrar una promoción
	 *  
	 *  Finalizar una promoción
	 *  
	 *  Registrar un pedido de un producto a un proveedor para una sucursal
	 *  
	 *  Registrar la llegada de un pedido de un producto a una sucursal
	 *  
	 *  Regsistrar una venta de un producto en una sucursal
	 *  
	 */

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Superandes
	 * @return Un arreglo con 24 números que indican el número de tuplas borradas en las tablas: 
	 * SUCURSAL, CATEGORIA, PRODUCTO, ORDEN, ESTANTE, BODEGA, ABASTECIMIENTO, PROVEEDOR, CLIENTE, PERSONA,
	 * EMPRESA, CAJERO, FACTURA, PROMOCION, USUARIO, CARRITO, ABASTECIMIENTO_BODEGA, SURTIDO, PRODUCTO_ABASTECIMIENTO, 
	 * INVENTARIO, PRODUCTO_ORDEN, CATALOGO, ORDEN_PROVEEDOR, COMPRA, PRODUCTO_PROMOCION, FACTURA_PROMOCION y CARRITO_PRODUCTO
	 * respectivamente.
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Superandes");
        long [] borrrados = ps.limpiarSuperandes();	
        log.info ("Limpiando la BD de Superanes: Listo!");
        return borrrados;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las PRODUCTOS
	 *****************************************************************/
	
	/**
	 * (RF1)
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
	 * @param fechaVencimiento
	 * @return La instancia de producto de acuerdo a los parámetros asignados
	 * @throws Exception si alguno de los valores no son válidos
	 */
	public Producto registrarProducto(String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal, String fechaVencimiento) 
			throws Exception
	{
		if(precioUnitario < 0 || precioUnidadMedida < 0 || nivelReorden < 0 || existencias < 0){
			throw new Exception("Los valores del precio, existencias o nivel de reorden son inválidos");
		}
		
		if(ps.darSucursalPorId(idSucursal) == null){
			throw new Exception("El id de la sucursal ingresado no es válido");
		}
		
		if(ps.darCategoriaPorId(idCategoria) == null){
			throw new Exception("El id de la categoría ingresado no es válido");
		}
		
		Timestamp fechaVencimientoFormateada = (Timestamp) new Date();
		
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date parsedDate = dateFormat.parse(fechaVencimiento);
			fechaVencimientoFormateada = new Timestamp(parsedDate.getTime());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("Adicionando el producto: " + nombre + "con código de barras: " + codigoBarras);
		Producto p = ps.registrarProducto(nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, nivelReorden, existencias, idCategoria, idSucursal, fechaVencimientoFormateada);
		log.info("Adicionando el producto: " + p.toString());
		return p;
	}
	/**
	 * 
	 * @return Una lista de todos los productos disponibles
	 */
	public List<Producto> darProductos(){
		return ps.darProductos();
	}


	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/
	
	/**
	 * (RF8) Finaliza las promociones que ya se vencieron o cuyos productos
	 * asociados hayan agotado sus existencias
	 * @param fechaActual
	 * @return número de tuplas eliminadas
	 */
	public long finalizarPromocion(Date fechaActual) {
		return ps.finalizarPromocion(fechaActual);
	}
	
	/**
	 * (RFC2)
	 * @return las 20 promociones con más compras
	 */
	public List<ComprasPorPromocion> dar20PromocionesMasPopulares(){
		return ps.dar20PromocionesMasPopulares();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROVEEDORES
	 *****************************************************************/
	
	public Proveedor registrarProveedor(String nit, String nombre, double calificacion) throws Exception{
		if (calificacion < 0 || calificacion > 5) {
			throw new Exception("La calificación ingresada no es válida");
		}
		
		log.info("Adicionando el proveedor: " + nombre + "con nit: " + nit);
		Proveedor p = ps.registrarProveedor(nit, nombre, calificacion);
		log.info("Adicionando el proveedor: " + p.toString());
		return p;
	}
	
}
