package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de SURTIDO
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOSurtido {
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Retorna el id del estante
	 * @return - id del estante
	 */
	public long getIdEstante();
	
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
