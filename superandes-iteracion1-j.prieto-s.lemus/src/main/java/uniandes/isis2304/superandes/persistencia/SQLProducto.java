package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;

public class SQLProducto {

	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;
	
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes pa;

	/**
	 * Constructor
	 * @param pa - Manejador de persistencia de la aplicación
	 */
	public SQLProducto(PersistenciaSuperandes pa) {
		this.pa = pa;
	}
	
	/**
	 * Adiciona un producto a la base de datos de acuerdo a los valores correspondientes
	 * @param pm - PersistenceManager
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
	 * @return el número de tuplas añadidas
	 */
	public long adicionarProducto(PersistenceManager pm, String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pa.PRODUCTO + "(nombre, marca, precio_unitario, presentacion"
        		+ ", precio_unidadMedida, unidad_medida, empacado, codigo_barras"
        		+ ", id_categoria, nivel_reorden, existencias, id_sucursal) "
        		+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters();
        return (long) q.executeUnique();   
	}
	
	/**
	 * Elimina los producto cuyo código de barras sea igual al dado por parámetro
	 * @param pm - PersistenceManager
	 * @param codigoBarras
	 * @return el número de tuplas eliminadas
	 */
	public long eliminarProductoPorId (PersistenceManager pm, String codigoBarras )
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pa.PRODUCTO + " WHERE codigo_barras = ?");
        q.setParameters();
        return (long) q.executeUnique();            
	}
	
	/**
	 * Elimina los producto cuyo nombre de barras sea igual al dado por parámetro
	 * @param pm - PersistenceManager
	 * @param nombreProducto
	 * @return
	 */
	public long eliminarProductoPorNombre (PersistenceManager pm, String nombreProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pa.PRODUCTO + " WHERE nombre = ?");
        q.setParameters(nombreProducto);
        return (long) q.executeUnique();            
	}
	
	/**
	 * 
	 * @param pm - PersistenceManager
	 * @return todos los productos disponibles
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.PRODUCTO);
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
	public Producto darProductoPorId(PersistenceManager pm, long idProducto) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.PRODUCTO + " WHERE codigo_barras = ?");
		q.setResultClass(Producto.class);
		return (Producto) q.execute(idProducto);
	}
	
	/**
	 * Retorna los productos dado un rango de precios unitarios
	 * @param pm
	 * @param p1 - límite inferior
	 * @param p2 - límite superior
	 * @return
	 */
	public List<Producto> darProductosPorRangoDePrecioUnitario(PersistenceManager pm, double p1, double p2) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.PRODUCTO
				+ " WHERE precio_unitario BETWEEN " + p1 + " AND " + p2);
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * Retorna los productos dado un rango de precios por unidad de medida
	 * @param pm
	 * @param p1 - límite superior
	 * @param p2 - límite inferior
	 * @return
	 */
	public List<Producto> darProductosPorRangoDePrecioUM(PersistenceManager pm, double p1, double p2) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pa.PRODUCTO
				+ " WHERE precio_unidadMedida BETWEEN " + p1 + " AND " + p2);
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
}
