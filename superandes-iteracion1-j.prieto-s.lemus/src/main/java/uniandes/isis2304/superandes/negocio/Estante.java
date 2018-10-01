package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de ESTANTE en Superandes.
 * @author s.lemus
 *
 */
public class Estante implements VOEstante{

	/**
	 * Identificador único del Estante
	 */
	private Long id;
	
	/**
	 * Capacidad de peso del Estante
	 */
	private Double capacidadPeso;
	
	/**
	 * Capacidad de volumen del estante
	 */
	private Double capacidadVolumen;
	
	/**
	 * Nivel de abastecimiento del estante
	 */
	private Integer nivelAbastecimiento;

	/**
	 * Constructor del Estante con valores
	 * @param id
	 * @param capacidadPeso
	 * @param capacidadVolumen
	 * @param nivelAbastecimiento
	 */
	public Estante(Long id, Double capacidadPeso, Double capacidadVolumen, Integer nivelAbastecimiento) {
		this.id = id;
		this.capacidadPeso = capacidadPeso;
		this.capacidadVolumen = capacidadVolumen;
		this.nivelAbastecimiento = nivelAbastecimiento;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Estante(){
		this.id = (long) 0;
		this.capacidadPeso = 0.0;
		this.capacidadVolumen = 0.0;
		this.nivelAbastecimiento = 0;
	}

	/**
	 * 
	 * @return el id de estante
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id - el id nuevo del estante
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return la capacidad de peso del estante
	 */
	public Double getCapacidadPeso() {
		return capacidadPeso;
	}

	/**
	 * 
	 * @param capacidadPeso - la nueva capacidad de peso del estante
	 */
	public void setCapacidadPeso(Double capacidadPeso) {
		this.capacidadPeso = capacidadPeso;
	}

	/**
	 * 
	 * @return la capacidad de volumen del estante
	 */
	public Double getCapacidadVolumen() {
		return capacidadVolumen;
	}

	/**
	 * 
	 * @param capacidadVolumen - la nueva capacidad de volumen del estante
	 */
	public void setCapacidadVolumen(Double capacidadVolumen) {
		this.capacidadVolumen = capacidadVolumen;
	}

	/**
	 * 
	 * @return el nivel de abastecimiento del estante
	 */
	public Integer getNivelAbastecimiento() {
		return nivelAbastecimiento;
	}

	/**
	 * 
	 * @param nivelAbastecimiento - el nuevo nivel de abastecimiento del estante
	 */
	public void setNivelAbastecimiento(Integer nivelAbastecimiento) {
		this.nivelAbastecimiento = nivelAbastecimiento;
	}

	/**
	 * Retorna una cadena de caractéres con la información básica
	 * del estante
	 */
	@Override
	public String toString() {
		return "Estante [id=" + id + ", capacidadPeso=" + capacidadPeso + ", capacidadVolumen=" + capacidadVolumen
				+ ", nivelAbastecimiento=" + nivelAbastecimiento + "]";
	}
	
	
	
}
