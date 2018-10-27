package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de PRODUCTO_ORDEN
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOProductoOrden {

	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Retorna el id del producto 
	 * @return - id del producto
	 */
	public String getIdProducto();
	
	/**
	 * Retorna el id de la orden
	 * @return - id de la orden
	 */
	public long getIdOrden();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
	
}
