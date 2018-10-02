package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de BODEGA en Superandes.
 * @author s.lemus
 *
 */
public class Bodega implements VOBodega{
	
	/**
	 * Identificador único de la bodega
	 */
	private Long id;
	
	/**
	 * Capacidad de peso de la bodega
	 */
	private Double capacidadPeso;
	
	/**
	 * Capacidad de volumen de la bodega
	 */
	private Double capacidadVolumen;
	
	/**
	 * El id de la categoría de los productos del estante
	 */
	private Long idCategoria;
	
	/**
	 * El id de la sucursal al que pertenece
	 */
	private Long idSucursal;

	/**
	 * Constructor de Bodega con valores
	 * @param id
	 * @param capacidadPeso
	 * @param capacidadVolumen
	 */
	public Bodega(Long id, Double capacidadPeso, Double capacidadVolumen, Long idCategoria, Long idSucursal) {
		this.id = id;
		this.capacidadPeso = capacidadPeso;
		this.capacidadVolumen = capacidadVolumen;
		this.idCategoria = idCategoria;
		this.idSucursal = idSucursal;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Bodega(){
		this.id = (long) 0;
		this.capacidadPeso = 0.0;
		this.capacidadVolumen = 0.0;
		this.idCategoria = (long) 0;
		this.idSucursal = (long) 0;
	}

	/**
	 * 
	 * @return id de la bodega
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id - nuevo id de la bodega
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return capacidad de la bodega
	 */
	public Double getCapacidadPeso() {
		return capacidadPeso;
	}

	/**
	 * 
	 * @param capacidadPeso - nueva capacidad de peso de la bodega
	 */
	public void setCapacidadPeso(Double capacidadPeso) {
		this.capacidadPeso = capacidadPeso;
	}
	
	/**
	 * 
	 * @return capacidad de volumen de la bodega
	 */
	public Double getCapacidadVolumen() {
		return capacidadVolumen;
	}

	/**
	 * 
	 * @param capacidadVolumen - nueva capacidad de volumen de la bodega
	 */
	public void setCapacidadVolumen(Double capacidadVolumen) {
		this.capacidadVolumen = capacidadVolumen;
	}
	
	/**
	 * 
	 * @return el id de la sucursal
	 */
	public Long getIdSucursal() {
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
	 * de la bodega
	 */
	@Override
	public String toString() {
		return "Bodega [id=" + id + ", capacidadPeso=" + capacidadPeso + ", capacidadVolumen=" + capacidadVolumen + "]";
	}
	
	

}
