package uniandes.isis2304.superandes.negocio;

public interface VOCarritoProducto {

	/**
	 * Obtiene el identificador del carrito que contiene el producto
	 * @return - el identificador del carrito que contiene el producto
	 */
	public long getIdCarrito();
	
	/**
	 * Obtiene el identificador del producto contenido en el carrito
	 * @return - el identificador del producto contenido en el carrito
	 */
	public String getIdProducto();
}
