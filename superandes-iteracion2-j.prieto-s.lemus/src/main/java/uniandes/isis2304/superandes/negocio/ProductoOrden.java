package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación PRODUCTO_ORDEN del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Un producto que está incluído en una orden para un proveedor.
 *  - Una orden para un proveedor que contiene un producto.
 *  Debe existir un producto con el identificador dado.
 *  Debe existir una orden con el identificador dado.
 * 
 * @author j.prieto
 */

public class ProductoOrden implements VOProductoOrden{
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador del producto
	 */
	private String idProducto;
	
	/**
	 * Identificador de la orden
	 */
	private long idOrden;
	
	/**
	 * Cantidad del producto
	 */
	private int cantidad;
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public ProductoOrden(){
		this.idProducto = "";
		this.idOrden = 0;
		this.cantidad = 0;
	}
	
	/**
	 * Constructor con valores.
	 * @param idProducto - El identificador del producto. Debe existir un producto con dicho identificador.
	 * @param idOrden - El identificador de la orden. Debe existir una orden con dicho identificador.
	 * @param cantidad - Cantidad del producto
	 */
	public ProductoOrden( String idProducto, long idOrden, int cantidad) {
		this.idProducto = idProducto;
		this.idOrden = idOrden;
		this.cantidad = cantidad;
	}

	/**
	 * Retorna el identificador del producto
	 * @return - el identificador del producto
	 */
	public String getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el identificador del producto
	 * @param idProducto - el identificador del producto
	 */
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Retorna el identificador de la orden.
	 * @return - el identificador de la orden.
	 */
	public long getIdOrden() {
		return idOrden;
	}

	/**
	 * Modifica el identificador de la orden.
	 * @param idOrden - el identificador de la orden.
	 */
	public void setIdOrden(long idOrden) {
		this.idOrden = idOrden;
	}
	
	/**
	 * Retorna la cantidad del producto
	 * @return - cantidad del producto
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Modifica la cantidad del producto
	 * @param cantidad - nueva cantidad del producto
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "ProductoOrden [idProducto=" + idProducto + ", idOrden=" + idOrden + ", cantidad=" + cantidad + "]";
	}
	
}
