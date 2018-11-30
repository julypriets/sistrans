package uniandes.isis2304.superandes.negocio;

public interface VOCarrito {
	
	/**
	 * Obtiene el identificador del carrito 
	 * @return - el identificador del carrito
	 */
	public long getId();
	
	/**
	 * Obtiene el identificador del cliente que tiene el carrito
	 * @return - identificador del cliente que tiene el carrito
	 */
	public long getIdCliente();
	
	/**
	 * Obtiene el estado del carrito
	 * @return - estado actual del carrito
	 */
	public String getEstado();

}
