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
	private long id;
	
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
	 * El id de la categoría de los productos del estante
	 */
	private long idCategoria;
	
	/**
	 * El id de la sucursal al que pertenece
	 */
	private long idSucursal;

	/**
	 * Constructor del Estante con valores
	 * @param id
	 * @param capacidadPeso
	 * @param capacidadVolumen
	 * @param nivelAbastecimiento
	 */
	public Estante(long id, Double capacidadPeso, Double capacidadVolumen, Integer nivelAbastecimiento
			, long idCategoria, long idSucursal) {
		this.id = id;
		this.capacidadPeso = capacidadPeso;
		this.capacidadVolumen = capacidadVolumen;
		this.nivelAbastecimiento = nivelAbastecimiento;
		this.idCategoria = idCategoria;
		this.idSucursal = idSucursal;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Estante(){
		this.id = 0;
		this.capacidadPeso = 0.0;
		this.capacidadVolumen = 0.0;
		this.nivelAbastecimiento = 0;
		this.idCategoria = 0;
		this.idSucursal = 0;
	}

	/**
	 * 
	 * @return el id de estante
	 */
	public long getId() {
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
	 * 
	 * @return el id de la categoría 
	 */
	public Long getIdCategoria() {
		return idCategoria;
	}

	/**
	 * 
	 * @param idCategoria - el id de una nueva categoría
	 */
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * 
	 * @return el id de la sucursal
	 */
	public long getIdSucursal() {
		return idSucursal;
	}

	/**
	 * 
	 * @param idSucursal - el id de una nueva sucursal
	 */
	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
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
