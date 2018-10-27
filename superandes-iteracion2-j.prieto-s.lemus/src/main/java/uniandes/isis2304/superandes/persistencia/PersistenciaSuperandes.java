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

/**
 * Clase para el manejador de persistencia del proyecto Superandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases:
 * SQLSucursal, SQLCategoria SQLProducto, SQLOrden, SQLEstante, SQLBodega, SQLAbastecimiento,
 * SQLProveedor, SQLPersona, SQLEmpresa, SQLCajero, SQLFactura, SQLPromocion, SQLUsuario, 
 * SQLAbastecimientoBodega, SQLSurtido, SQLProductoAbastecimiento, SQLInventario,
 * SQLProductoOrden, SQLCatalogo, SQLOrdenProveedor, SQLCompra, SQLProductoPromocion y SQLFacturaPromocion, 
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
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;
	
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

}
