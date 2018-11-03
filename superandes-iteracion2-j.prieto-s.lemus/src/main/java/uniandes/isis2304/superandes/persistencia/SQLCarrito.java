package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Carrito;
import uniandes.isis2304.superandes.negocio.Producto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CARRITO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLCarrito {
	
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
	public SQLCarrito (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un CARRITO
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del carrito
	 * @param idCliente - El identificador del cliente que tiene el carrito
	 * @param estado - El estado del carrito
	 * @return El número de tuplas insertadas
	 */
	public long registrarCarrito(PersistenceManager pm, long id, long idCliente, String estado) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaCarrito() + "VALUES (?, ?, ?)");
		q.setParameters(id, idCliente, estado);
		return (long) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param id
	 * @return El carro correspondiente a su id
	 */
	public Carrito darCarroPorId(PersistenceManager pm, long id){
		Query q = pm.newQuery(SQL, "SELECT id, id_cliente idcliente, estado FROM CARRITO WHERE id = " + id);
		q.setResultClass(Carrito.class);
		return (Carrito) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idCliente
	 * @return El carro correspondiente al id del cliente dueño
	 */
	public Carrito darCarroPorIdCliente(PersistenceManager pm, long idCliente){
		Query q = pm.newQuery(SQL, "SELECT id, id_cliente idcliente, estado FROM CARRITO WHERE id_cliente = " + idCliente);
		q.setResultClass(Carrito.class);
		return (Carrito) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @return Todos los carros disponibles en el supermercado
	 */
	public List<Carrito> darCarros(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT id, id_cliente idcliente, estado FROM CARRITO");
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}
	
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @return Una colección con los carros de compra con estado DESOCUPADO
	 */
	public List<Carrito> darCarrosDesocupados(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT id, id_cliente idcliente, estado FROM CARRITO WHERE estado = 'DESOCUPADO'");
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @return Una colección con los carros de compra con estado ABANDONADO
	 */
	public List<Carrito> darCarrosAbandonados(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT id, id_cliente idcliente, estado FROM CARRITO WHERE estado = 'ABANDONADO'");
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}

	/**
	 * Método usado cuando un cliente solicita un carro de compras disponible.
	 * Se actualiza el id del cliente dueño, y el estado a OCUPADO
	 * @param pm - Persistence Manager
	 * @param id
	 * @param idCliente
	 * @return El número de tuplas actualizadas
	 */
	public long solicitarCarro(PersistenceManager pm, long id, long idCliente){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO SET id_cliente = " + idCliente + " , estado = 'OCUPADO' WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	/**
	 * Método usado cuando un cliente abandona su carro de compras.
	 * Actualiza el estado del carro a ABANDONADO
	 * @param pm - Persistence Manager
	 * @param id
	 * @return El número de tuplas acualizadas
	 */
	public long abandonarCarro(PersistenceManager pm, long id){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO SET id_cliente = NULL , estado = 'ABANDONADO' WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	/**
	 * Inserta un producto y su cantidad respectiva en el carro de compras
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @param idProducto
	 * @param cantidadAnadida
	 * @return El número de tuplas actualizadas
	 */
	public long insertarProductoAlCarro(PersistenceManager pm, long idCarrito, String idProducto, long cantidadAnadida){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO_PRODUCTO SET cantidad = cantidad + " + cantidadAnadida + " WHERE id_carrito = "+ idCarrito +" AND id_producto = " + "'"+ idProducto+"'");
		return (long) q.executeUnique();
	}
	
	/**
	 * Remueve el producto y su cantidad respectiva en el carro de compras
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @param idProducto
	 * @param cantidadRemovida
	 * @return El número de tuplas actualizadas
	 */
	public long removerProductoDelCarro(PersistenceManager pm, long idCarrito, String idProducto, long cantidadRemovida){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO_PRODUCTO SET cantidad = cantidad - " + cantidadRemovida + " WHERE id_carrito = "+ idCarrito +" AND id_producto = " + "'"+ idProducto+"'");
		return (long) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @return Una colección de todos los productos insertados en el carro de compras
	 */
	public List<Producto> darProductosEnCarro(PersistenceManager pm, long idCarrito){
		String select = "SELECT nombre, marca, precio_unitario precioUnitario, presentacion, precio_unidadmedida precioUnidadMedida, empacado, codigo_barras codigobarras, id_categoria idCategoria, nivel_reorden nivelReorden, existencias, fecha_vencimiento fechaVencimiento " + 
							"FROM CARRITO_PRODUCTO c, PRODUCTO p " +
							"WHERE c.id_producto = p.codigo_barras AND c.id_carrito = " + idCarrito;
		Query q = pm.newQuery(SQL, select);
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
	public int darCantidadDeProducto (PersistenceManager pm, long idCarrito, String idProducto){
		Query q = pm.newQuery(SQL, "SELECT cantidad FROM CARRITO_PRODUCTO WHERE id_carrito = " + idCarrito + " AND id_producto = " + "'"+ idProducto+"'");
		q.setResultClass(Integer.class);
		return (int) q.executeUnique();
	}
}
