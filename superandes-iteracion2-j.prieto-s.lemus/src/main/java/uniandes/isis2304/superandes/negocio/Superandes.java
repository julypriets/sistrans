package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
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
	 * SUCURSAL, CATEGORIA, PRODUCTO, ORDEN, ESTANTE, BODEGA, ABASTECIMIENTO, PROVEEDOR, PERSONA,
	 * EMPRESA, CAJERO, FACTURA, PROMOCION, USUARIO, ABASTECIMIENTO_BODEGA, SURTIDO, PRODUCTO_ABASTECIMIENTO, 
	 * INVENTARIO, PRODUCTO_ORDEN, CATALOGO, ORDEN_PROVEEDOR, COMPRA, PRODUCTO_PROMOCION y FACTURA_PROMOCION,
	 * respectivamente.
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = ps.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
}
