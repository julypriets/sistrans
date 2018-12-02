package uniandes.isis2304.superandes.persistencia;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;
import uniandes.isis2304.superandes.negocio.FacturaCliente;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.ProductoPorSemana;
import uniandes.isis2304.superandes.negocio.Sucursal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.sql.Timestamp;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PRODUCTO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLProducto {
	
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
	public SQLProducto (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un PRODUCTO
	 * @param pm - El manejador de persistencia
	 * @param nombre - nombre del producto
	 * @param marca - marca del producto
	 * @param precioUnitario - precio unitario del producto
	 * @param presentacion - presentacion del producto
	 * @param precioUnidadMedida - precio por unidad de medida del producto
	 * @param unidadMedida - unidad de medida del producto
	 * @param empacado - empacado del producto
	 * @param codigoBarras - codigo de barras del producto
	 * @param idCategoria - identificador de la categoria del producto
	 * @param nivelReorden - nivel de reorden del producto
	 * @param existencias - existencias del producto
	 * @param fechaVencimiento - fecha de vencimiento del producto
	 * @return - El número de tupolas insertadas
	 */
	public long registarProducto( PersistenceManager pm, String nombre, String marca, double precioUnitario, String presentacion, 
			double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, long idCategoria, int nivelReorden,
			int existencias, Timestamp fechaVencimiento) {
		Query q = pm.newQuery(SQL, "INSERT INTO" + ps.darTablaProducto() + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, 
				idCategoria, nivelReorden, existencias, fechaVencimiento);
		return (long) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @return todos los productos disponibles
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT nombre, marca, precio_unitario precioUnitario, presentacion, precio_unidadmedida precioUnidadMedida, empacado, codigo_barras codigobarras, id_categoria idCategoria, nivel_reorden nivelReorden, existencias, fecha_vencimiento fechaVencimiento FROM PRODUCTO");
		q.setResultClass(Producto.class); 
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @param idProducto 
	 * @return El producto correspondiente al id
	 */
	public Producto darProductoPorId(PersistenceManager pm, String idProducto) {
		Query q = pm.newQuery(SQL, "SELECT nombre, marca, precio_unitario precioUnitario, presentacion, precio_unidadmedida precioUnidadMedida, empacado, codigo_barras codigobarras, id_categoria idCategoria, nivel_reorden nivelReorden, existencias, fecha_vencimiento fechaVencimiento "
				+ " FROM " + ps.darTablaProducto() + " WHERE codigo_barras = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idProducto);
		return (Producto) q.executeUnique();
		// ((List<Producto>) q.executeList()).get(0)
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @param nombre
	 * @return todos los productos asociados al nombre 
	 */
	public List<Producto> darProductosPorNombre (PersistenceManager pm, String nombre){
		Query q = pm.newQuery(SQL, "SELECT nombre, marca, precio_unitario precioUnitario, presentacion, precio_unidadmedida precioUnidadMedida, empacado, codigo_barras codigobarras, id_categoria idCategoria, nivel_reorden nivelReorden, existencias, fecha_vencimiento fechaVencimiento FROM PRODUCTO "
				+ "WHERE nombre = ?");
		q.setResultClass(Producto.class);
		q.setParameters(nombre);
		return (List<Producto>) q.executeList();
	}
	
	/* ****************************************************************
	 * 			Métodos consulta para Iteración 3
	 *****************************************************************/	
	
	/**
	 * Retorna la información de los productos con ventas máximas por semana
	 * @param pm
	 * @return Colección con la información de los productos con ventas máximas por semana
	 */
	public List<ProductoPorSemana> productosConVentasMaximasPorSemana(PersistenceManager pm){
		String select = "SELECT fechaSemana, nombre, codigo_barras, ventasMaximas\n" + 
				"FROM\n" + 
				"    (SELECT fechaSemana, nombre, codigo_barras, ventas,\n" + 
				"        MAX(ventas) OVER (PARTITION BY fechaSemana) AS ventasMaximas\n" +  
				"    FROM\n" + 
				"        (SELECT TRUNC(fecha, 'WW') fechaSemana, nombre, codigo_barras, (COUNT (nombre)* SUM(cantidad)) ventas\n" + 
				"        FROM PRODUCTO p, COMPRA cp, FACTURA f\n" + 
				"        WHERE\n" + 
				"            p.codigo_barras = cp.id_producto AND\n" + 
				"            f.id = cp.id_factura\n" + 
				"        GROUP BY TRUNC(fecha, 'WW'), nombre, codigo_barras\n" + 
				"        ORDER BY TRUNC(fecha, 'WW')\n" + 
				"        )\n" + 
				"    )\n" + 
				"WHERE ventas = ventasMaximas;";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(ProductoPorSemana.class);
		return (List<ProductoPorSemana>) q.executeList();
	}
	
	/**
	 * Retorna la información de los productos con ventas mínimas por semana
	 * @param pm
	 * @return Colección con la información de los productos con ventas mínimas por semana
	 */
	public List<ProductoPorSemana> productosConVentasMinimasPorSemana(PersistenceManager pm){
		String select = "SELECT fechaSemana, nombre, codigo_barras, ventasMaximas\n" + 
				"FROM\n" + 
				"    (SELECT fechaSemana, nombre, codigo_barras, ventas,\n" +  
				"        MIN(ventas) OVER (PARTITION BY fechaSemana) AS ventasMinimas  \n" + 
				"    FROM\n" + 
				"        (SELECT TRUNC(fecha, 'WW') fechaSemana, nombre, codigo_barras, (COUNT (nombre)* SUM(cantidad)) ventas\n" + 
				"        FROM PRODUCTO p, COMPRA cp, FACTURA f\n" + 
				"        WHERE\n" + 
				"            p.codigo_barras = cp.id_producto AND\n" + 
				"            f.id = cp.id_factura\n" + 
				"        GROUP BY TRUNC(fecha, 'WW'), nombre, codigo_barras\n" + 
				"        ORDER BY TRUNC(fecha, 'WW')\n" + 
				"        )\n" + 
				"    )\n" + 
				"WHERE ventas = ventasMaximas;";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(ProductoPorSemana.class);
		return (List<ProductoPorSemana>) q.executeList();
	}
}
