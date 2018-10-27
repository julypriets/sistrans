package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de INVENTARIO
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOInventario {

	/* ----------------- Métodos ----------------------------------- */

	/**
	 * Retorna el id del producto
	 * @return - id del producto
	 */
	public String getIdProducto();
	
	/**
	 * Retorna el id de la bodega
	 * @return - id de la bodega
	 */
	public long getIdBodega();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
}
