package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de BODEGA 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOBodega {

	/**
	 * 
	 * @return id de la bodega
	 */
	public Long getId();
	/**
	 * 
	 * @return capacidad de la bodega
	 */
	public Double getCapacidadPeso();
	
	/**
	 * 
	 * @return capacidad de volumen de la bodega
	 */
	public Double getCapacidadVolumen();
	
	/**
	 * Retorna una cadena de caractéres con la información básica
	 * de la bodega
	 */
	@Override
	public String toString();
}
