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

package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
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
	private PersistenciaSuperandes pa;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Superandes ()
	{
		pa = PersistenciaSuperandes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Superandes (JsonObject tableConfig)
	{
		pa = PersistenciaSuperandes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pa.cerrarUnidadPersistencia ();
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
	 * @return La representación del producto
	 */
	public Producto adicionarProducto(String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal) {
		log.info("Adicionando el producto: " + nombre + "con código de barras: " + codigoBarras);
		Producto p = pa.adicionarProducto(nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, nivelReorden, existencias, idCategoria, idSucursal);
		log.info("Adicionando el producto: " + p.toString());
		return p;
	}
	
	
	public List<Producto> darProductos(){
		return pa.darProductos();
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
	public Cliente adicionarEmpresa(String nombre, String correo, String nit, String direccion) {
		log.info("Adicionando la empresa: " + nombre + "con nit: " + nit);
		Cliente e = pa.adicionarEmpresa(nombre, correo, nit, direccion);
		log.info("Adicionando la empresa: " + e.toString());
		return e;
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
	public Cliente adicionarPersonaNatural(String nombre, String correo, String identificacion) {
		log.info("Adicionando la persona: " + nombre + "con documento de identificación: " + identificacion);
		Cliente p = pa.adicionarPersonaNatural(nombre, correo, identificacion);
		log.info("Adicionando el producto: " + p.toString());
		return p;
	} 
	
	/**
	 * Retorna todos los clientes existentes
	 * @return
	 */
	public List<Cliente> darClientes(){
		return pa.darClientes();
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
		return pa.finalizarPromocion(fechaActual);
	}
	
	/**
	 * (RFC2)
	 * @return las 20 promociones con más compras
	 */
	public List<ComprasPorPromocion> dar20PromocionesMasPopulares(){
		return pa.dar20PromocionesMasPopulares();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las ORDENES
	 *****************************************************************/
	
	/** (RF10)
	 * Registra una orden en la base de datos
	 * @param precio
	 * @param fechaEsperada
	 * @param fechaLlegada
	 * @param estado
	 * @param calificacion
	 * @param idSucursal
	 * @param idProveedor
	 * @return
	 */
	public Orden adicionarOrden(Double precio, Timestamp fechaEsperada, Timestamp fechaLlegada, String estado,
			Double calificacion, long idSucursal, long idProveedor) {
		return pa.adicionarOrden(precio, fechaEsperada, fechaLlegada, estado, calificacion, idSucursal, idProveedor);
		
	}
	
//	/**
//	 * Elimina todas las tuplas de todas las tablas de la base de datos 
//	 */
//	public long [] limpiarParranderos ()
//	{
//        log.info ("Limpiando la BD de Parranderos");
//        long [] borrrados = pp.limpiarParranderos();	
//        log.info ("Limpiando la BD de Parranderos: Listo!");
//        return borrrados;
//	}
}
