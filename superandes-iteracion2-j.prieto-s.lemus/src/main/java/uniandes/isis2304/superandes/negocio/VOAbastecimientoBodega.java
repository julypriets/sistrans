package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de ABASTECIMIENTO_BODEGA
 * Sirve para proteger la información del ngocio de posibles manipulaciones desde la interfaz.
 * 
 * @author j.prieto
 */

public interface VOAbastecimientoBodega {
	
	/* ----------------- Métodos ----------------------------------- */

	/**
	 * Retorna el id del abastecimiento
	 * @return - id del abastecimiento
	 */
	public long getIdAbastecimiento();
	
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
