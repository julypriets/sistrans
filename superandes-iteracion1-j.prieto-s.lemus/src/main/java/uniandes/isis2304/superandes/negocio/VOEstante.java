package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de ESTANTE 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOEstante {

	/**
	 * 
	 * @return el id de estante
	 */
	public Long getId();

	/**
	 * 
	 * @return la capacidad de peso del estante
	 */
	public Double getCapacidadPeso();

	/**
	 * 
	 * @return la capacidad de volumen del estante
	 */
	public Double getCapacidadVolumen();

	/**
	 * 
	 * @return el nivel de abastecimiento del estante
	 */
	public Integer getNivelAbastecimiento();
	
	/**
	 * 
	 * @return el id de la sucursal
	 */
	public Long getIdSucursal();

	/**
	 * Retorna una cadena de caractéres con la información básica
	 * del estante
	 */
	@Override
	public String toString();
}
