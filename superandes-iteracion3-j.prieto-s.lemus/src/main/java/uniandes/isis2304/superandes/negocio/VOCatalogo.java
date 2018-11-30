package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de CATALOGO
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOCatalogo {
	
	/**
	 * Retorna el id del proveedor
	 * @return - id del proveedor
	 */
	public String getIdProveedor();
	
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
