package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de PRODUCTO 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOProducto {

	/**
	 * 
	 * @return el código de barras del producto
	 */
	public String getCodigoBarras();

	/**
	 * 
	 * @return el nombre del producto
	 */
	public String getNombre();

	/**
	 * 
	 * @return la marca del producto
	 */
	public String getMarca();
	
	/**
	 * 
	 * @return el precio unitario del producto
	 */
	public Double getPrecioUnitario();

	/**
	 * 
	 * @return presentación del producto
	 */
	public String getPresentacion();

	/**
	 * 
	 * @return el precio por unidad de medida del producto
	 */
	public Double getPrecioUnidadMedida();

	/**
	 * 
	 * @return la unidad de medida del producto
	 */
	public String getUnidadMedida();

	/**
	 * 
	 * @return el empaquetado del producto
	 */
	public String getEmpacado();

	/**
	 * 
	 * @return el nivel de reorden del producto
	 */
	public Integer getNivelReorden();

	/**
	 * 
	 * @return el número de productos disponibles
	 */
	public Integer getExistencias();
	
	/**
	 * 
	 * @return el id de la categoría del producto
	 */
	public long getIdCategoria();
	
	public long getIdSucursal();

	/**
	 * Retorna una cadena de caractéres con la información básica
	 * de la producto
	 */
	@Override
	public String toString();
	
}
