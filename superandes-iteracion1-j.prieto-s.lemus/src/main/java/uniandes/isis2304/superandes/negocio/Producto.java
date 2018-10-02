package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de PRODUCTO en Superandes.
 * @author s.lemus
 *
 */
public class Producto implements VOProducto{

	/**
	 * Identificador único para el producto
	 */
	private String codigoBarras;
	
	/**
	 * Nombre del producto
	 */
	private String nombre;
	
	/**
	 * Nombre de la marca
	 */
	private String marca;
	
	/**
	 * Precio por unidad de producto
	 */
	private Double precioUnitario;
	
	/**
	 * Presentación del producto
	 */
	private String presentacion;
	
	/**
	 * Precio por cantidad (unidad de medida) del producto
	 */
	private Double precioUnidadMedida;
	
	/**
	 * Unidad de medida del producto 
	 */
	private String unidadMedida;
	
	/**
	 * Empacado del producto
	 */
	private String empacado;
	
	/**
	 * Nivel de reorden (límite de existencias permitidas como mínimo) del producto
	 */
	private Integer nivelReorden;
	
	/**
	 * Número de productos disponibles
	 */
	private Integer existencias;
	
	/**
	 * El id correspondiente a la Categoría del producto
	 */
	private Long idCategoria;
	
	private Long idSucursal;

	/**
	 * Constructor del Producto con valores
	 * @param id
	 * @param nombre
	 * @param marca
	 * @param precioUnitario
	 * @param presentacion
	 * @param precioUnidadMedida
	 * @param unidadMedida
	 * @param empacado
	 * @param codigoBarras
	 * @param nivelReorden
	 * @param existencias
	 */
	public Producto(String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal) {
		this.nombre = nombre;
		this.marca = marca;
		this.precioUnitario = precioUnitario;
		this.presentacion = presentacion;
		this.precioUnidadMedida = precioUnidadMedida;
		this.unidadMedida = unidadMedida;
		this.empacado = empacado;
		this.codigoBarras = codigoBarras;
		this.nivelReorden = nivelReorden;
		this.existencias = existencias;
		this.idCategoria = idCategoria;
		this.idSucursal = idSucursal;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Producto(){
		this.nombre = "";
		this.marca = "";
		this.precioUnitario = 0.0;
		this.presentacion = "";
		this.precioUnidadMedida = 0.0;
		this.unidadMedida = "";
		this.empacado = "";
		this.codigoBarras = "";
		this.nivelReorden = 0;
		this.existencias = 0;
		this.idCategoria = (long) 0;
		this.idSucursal = (long) 0;
	}

	/**
	 * 
	 * @return el código de barras del producto
	 */
	public String getCodigoBarras() {
		return codigoBarras;
	}

	/**
	 * 
	 * @param codigoBarras - el nuevo código de barras del producto
	 */
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	/**
	 * 
	 * @return el nombre del producto
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre - nuevo nombre del producto
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return la marca del producto
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * 
	 * @param marca - la nueva marca del producto
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * 
	 * @return el precio unitario del producto
	 */
	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * 
	 * @param precioUnitario - el nuevo precio unitario del producto
	 */
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * 
	 * @return presentación del producto
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * 
	 * @param presentacion - la nueva presentación del producto
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * 
	 * @return el precio por unidad de medida del producto
	 */
	public Double getPrecioUnidadMedida() {
		return precioUnidadMedida;
	}

	/**
	 * 
	 * @param precioUnidadMedida - el nuevo precio por unidad de medida del producto
	 */
	public void setPrecioUnidadMedida(Double precioUnidadMedida) {
		this.precioUnidadMedida = precioUnidadMedida;
	}

	/**
	 * 
	 * @return la unidad de medida del producto
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * 
	 * @param unidadMedida - la nueva unidad de medida del producto
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * 
	 * @return el empaquetado del producto
	 */
	public String getEmpacado() {
		return empacado;
	}

	/**
	 * 
	 * @param empacado - el nuevo empaquetado del producto
	 */
	public void setEmpacado(String empacado) {
		this.empacado = empacado;
	}

	/**
	 * 
	 * @return el nivel de reorden del producto
	 */
	public Integer getNivelReorden() {
		return nivelReorden;
	}

	/**
	 * 
	 * @param nivelReorden - el nuevo nivel de reorden del producto
	 */
	public void setNivelReorden(Integer nivelReorden) {
		this.nivelReorden = nivelReorden;
	}

	/**
	 * 
	 * @return el número de productos disponibles
	 */
	public Integer getExistencias() {
		return existencias;
	}

	/**
	 * 
	 * @param existencias - el nuevo número de existencias del producto
	 */
	public void setExistencias(Integer existencias) {
		this.existencias = existencias;
	}
	
	
	/**
	 * 
	 * @return el id de la categoría del producto
	 */
	public Long getIdCategoria() {
		return idCategoria;
	}

	/**
	 * 
	 * @param idCategoria - el nuevo id de una nueva categoría para el producto
	 */
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Retorna una cadena de caractéres con la información básica
	 * de la producto
	 */
	@Override
	public String toString() {
		return "Producto [codigoBarras=" + codigoBarras + ", nombre=" + nombre + ", marca=" + marca
				+ ", precioUnitario=" + precioUnitario + ", presentacion=" + presentacion + ", precioUnidadMedida="
				+ precioUnidadMedida + ", unidadMedida=" + unidadMedida + ", empacado=" + empacado + ", nivelReorden="
				+ nivelReorden + ", existencias=" + existencias + "]";
	}
	
	
}
