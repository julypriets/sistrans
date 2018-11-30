package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de FACTURA_PROMOCION
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */
public interface VOFacturaPromocion {
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Retorna el id de la factura
	 * @return - id de la factura
	 */
	public long getIdFactura();
	
	/**
	 * Retorna el id de la promoción
	 * @return - id de la promoción
	 */
	public long getIdPromocion();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();

}
