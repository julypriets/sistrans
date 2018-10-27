package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de PRODUCTO_ABASTECIMIENTO
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOProductoAbastecimiento {
	
	/* ----------------- Métodos ----------------------------------- */

	/**
	 * Retorna el id del abastecimiento
	 * @return - id del abastecimiento
	 */
	public long getIdAbastecimiento();
	
	/**
	 * Retorna el id del producto
	 * @return - id del producto
	 */
	public String getIdProducto();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();

}
