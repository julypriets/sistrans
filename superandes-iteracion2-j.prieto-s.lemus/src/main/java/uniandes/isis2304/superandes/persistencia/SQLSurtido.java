package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Producto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SURTIDO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLSurtido {
	
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
	public SQLSurtido (PersistenciaSuperandes ps) {
		this.ps = ps;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para registrar un SURTIDO
	 * @param pm - El manejador de persistencia
	 * @param idEstante - El identificador del estante surtido
	 * @param idProducto - El identificador del producto que surte el estante 
	 * @return El número de tuplas insertadas
	 */
	public long registrarSurtido(PersistenceManager pm, long idEstante, String idProducto) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaSurtido() + " VALUES (?, ?)");
		q.setParameters(idEstante, idProducto);
		return (long) q.executeUnique();
	}
	
	/**
	 * Remueve una cantidad determinada de un producto en un estante
	 * @param pm - Persistence Manager 
	 * @param idEstante
	 * @param idProducto
	 * @return El número de tuplas actualizadas
	 */
	public long removerProductoDeEstante(PersistenceManager pm, long idEstante, String idProducto, long cantidadARemover){
		Query q = pm.newQuery(SQL, "UPDATE SURTIDO SET cantidad = cantidad - " + cantidadARemover + " WHERE id_estante = "+ idEstante +" AND id_producto = " + "'"+ idProducto+"'");
		return (long) q.executeUnique();
	}
	
	/**
	 * Adiciona una cantidad determinada de un producto al estante
	 * correspondiente
	 * @param pm - Persistence Manager 
	 * @param idEstante
	 * @param idProducto
	 * @param cantidadAAdicionar
	 * @return
	 */
	public long insertarProductoEnEstante(PersistenceManager pm, long idEstante, String idProducto, long cantidadAAdicionar){
		Query q = pm.newQuery(SQL, "UPDATE SURTIDO SET cantidad = cantidad + " + cantidadAAdicionar + " WHERE id_estante = "+ idEstante +" AND id_producto = " + "'"+ idProducto+"'");
		return (long) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param nombre
	 * @param idEstante
	 * @return El producto correspondiente al nombre y el estante
	 */
	public Producto darProductoPorNombreYEstante(PersistenceManager pm, String nombre, long idEstante){
		String select = "SELECT nombre, marca, precio_unitario precioUnitario, presentacion, precio_unidadmedida precioUnidadMedida, empacado, codigo_barras codigobarras, id_categoria idCategoria, nivel_reorden nivelReorden, existencias, fecha_vencimiento fechaVencimiento " +
						"FROM SURTIDO s, Producto p " +
						"WHERE " +
						    "s.id_producto = p.codigo_barras AND " +
						    "s.id_Estante = " + idEstante +" AND " +
						    "p.nombre = ?";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(Producto.class);
		q.setParameters(nombre);
		return (Producto) q.executeUnique();
	}
	
	public int darCantidadProductoPorEstante(PersistenceManager pm, String idProducto, long idEstante){
		String select = "SELECT cantidad " +
				"FROM SURTIDO s, Producto p " +
				"WHERE " +
				    "s.id_producto = p.codigo_barras AND " +
				    "s.id_Estante = " + idEstante +" AND " +
				    "p.codigo_barras = ?";
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(Integer.class);
		q.setParameters(idProducto);
		return (int) q.executeUnique();
	}
}
