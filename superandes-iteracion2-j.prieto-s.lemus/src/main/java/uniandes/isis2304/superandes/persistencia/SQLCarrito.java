package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Carrito;
import uniandes.isis2304.superandes.negocio.CarritoProducto;
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
		Query q = pm.newQuery(SQL, "SELECT id, id_Cliente idcliente, estado FROM CARRITO WHERE estado = 'ABANDONADO'");
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
		Query q = pm.newQuery(SQL, "UPDATE CARRITO SET id_cliente = " + idCliente + " , estado = 'OCUPADO' WHERE id = " + id);
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
		Query q = pm.newQuery(SQL, "UPDATE CARRITO SET estado = 'ABANDONADO' WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	/**
	 * Método utilizado para actualizar el estado del carro a DESOCUPADO
	 * @param pm - Persistence Manager
	 * @param id
	 * @return El número de tuplas actualizadas
	 */
	public long desocuparCarro(PersistenceManager pm, long id){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO SET id_cliente = NULL , estado = 'DESOCUPADO' WHERE id = " + id);
		return (long) q.executeUnique();
	}
	
	/**
	 * Adiciona un producto y su cantidad respectiva en el carro de compras
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @param idProducto
	 * @param cantidadAnadida
	 * @return El número de tuplas actualizadas
	 */
	public long adicionarCantidadDeProductoAlCarro(PersistenceManager pm, long idCarrito, String idProducto, long cantidadAnadida){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO_PRODUCTO SET cantidad = cantidad + " + cantidadAnadida + " WHERE id_carrito = "+ idCarrito +" AND id_producto = " + "'"+ idProducto+"'");
		return (long) q.executeUnique();
	}
	
	/**
	 * Inserta un nuevo producto al carro de compras y su cantidad respectiva
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @param idProducto
	 * @return El número de tuplas insertadas
	 */
	public long insertarProductoAlCarro(PersistenceManager pm, long idCarrito, String idProducto, long cantidad){
		Query q = pm.newQuery(SQL, "INSERT INTO CARRITO_PRODUCTO (id_carrito, id_producto, cantidad) VALUES (" + idCarrito +", " + "?" + " , " + cantidad + " ) ");
		q.setParameters(idProducto);
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
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @param idProducto
	 * @return La cantidad del producto del carro respectivo
	 */
	public int darCantidadDeProducto (PersistenceManager pm, long idCarrito, String idProducto){
		try{
			Query q = pm.newQuery(SQL, "SELECT cantidad FROM CARRITO_PRODUCTO WHERE id_carrito = " + idCarrito + " AND id_producto = " + "'"+ idProducto+"'");
			q.setResultClass(Integer.class);
			return (int) q.executeUnique();
		}catch(Exception e){
			return -1;
		}

	}
	
	/**
	 * Actualiza el estado de todos los carros a 'DESOCUPADO' que hayan sido abandonados
	 * @param pm - Persistence Manager
	 * @return El número de tuplas actualizadas
	 */
	public long desocuparCarros(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "UPDATE CARRITO SET id_cliente = NULL, estado = 'DESOCUPADO' WHERE estado = 'ABANDONADO'");
		return (long) q.executeUnique();
	}
	
	/**
	 * Elimina un producto del carro
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProductoDelCarro(PersistenceManager pm, long idCarrito, String idProducto){
		Query q = pm.newQuery(SQL, "DELETE FROM CARRITO_PRODUCTO WHERE id_carrito = " + idCarrito + " AND id_producto = ?");
		q.setParameters(idProducto);
		return (long) q.executeUnique();
	}
	
	/**
	 * Elimina todos los productos del carro
	 * @param pm - Persistence Manager
	 * @param idCarrito
	 * @return Número de tuplas eliminadas
	 */
	public long eliminarProductosDelCarro(PersistenceManager pm, long idCarrito){
		Query q = pm.newQuery(SQL, "DELETE FROM CARRITO_PRODUCTO WHERE id_carrito = " + idCarrito);
		return (long) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @return Una colección de todos los carros y los productos asociados
	 */
	public List<CarritoProducto> darProductosPorCadaCarro(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT id_carrito idCarrito, id_producto idProducto, cantidad FROM CARRITO_PRODUCTO");
		q.setResultClass(CarritoProducto.class);
		return (List<CarritoProducto>) q.executeList();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idCliente
	 * @return El id del carro correspondiente a su dueño
	 */
	public long darCarroPorCliente (PersistenceManager pm, long idCliente){
		try{
			Query q = pm.newQuery(SQL, "SELECT id FROM CARRITO WHERE id_cliente = ?");
			q.setParameters(idCliente);
			q.setResultClass(Long.class);
			return (long)q.executeUnique();
			
		}catch (Exception e){
			return -1;
		}

	}
	
	public long productoTomado (PersistenceManager pm, long idCliente, String idProducto, long idEstante){
		Query q = pm.newQuery(SQL, "INSERT INTO TOMADO_DE (id_cliente, id_producto, id_estante) VALUES ( " + idCliente +", '" + idProducto+ "' , " + idEstante +" )");
		return (long) q.executeUnique();
	}
	
	public long productoDevuelto (PersistenceManager pm, String idProducto, long idEstante, long idCliente){
		Query q = pm.newQuery(SQL, "DELETE FROM TOMADO_DE WHERE id_estante = " + idEstante + " AND id_producto = " + "'" + idProducto + "' AND id_cliente = " +idCliente);
		return (long) q.executeUnique();
	}
	
	public long todosLosProductosDevueltos (PersistenceManager pm, long idCliente){
		Query q = pm.newQuery(SQL, "DELETE FROM TOMADO_DE WHERE id_cliente = " + idCliente);
		return (long) q.executeUnique();
	}
	
	public long productoFueTomadoDe(PersistenceManager pm, String idProducto, long idCliente){
		try{
			Query q = pm.newQuery(SQL, "SELECT id_estante FROM TOMADO_DE WHERE id_producto = " + "'" + idProducto + "' AND id_cliente = " + idCliente);
			q.setResultClass(Long.class);
			return (long) q.executeUnique();
		}catch(Exception e){
			return -1;
		}
	}
}
