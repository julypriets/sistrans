package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de ORDEN_PROVEEDOR
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOOrdenProveedor {
	
	/**
	 * Retorna el id de la orden.
	 * @return - id de la orden
	 */
	public long getIdOrden();
	
	/**
	 * Retorna el id del proveedor
	 * @return - id del proveedor
	 */
	public String getIdProveedor();
	
	/**
	 * Retorna la calificación
	 * @return - calificación
	 */
	public double getCalificacion();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();

}
