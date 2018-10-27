package uniandes.isis2304.superandes.negocio;

public class ProductoPromocion implements VOProductoPromocion{

	/**
	 * El id del producto con la promoción
	 */
	private long idProducto;
	
	/**
	 * El id de la promoción del producto
	 */
	private long idPromocion;

	/**
	 * Creación de una nueva relación entre una promoción y un producto
	 * @param idProducto
	 * @param idPromocion
	 */
	public ProductoPromocion(long idProducto, long idPromocion) {
		this.idProducto = idProducto;
		this.idPromocion = idPromocion;
	}

	/**
	 * Obtiene el id del producto relacionado con la promoción
	 */
	public long getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el id del producto relacionado con la promoción
	 * @param idProducto
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Obtiene el id de la promoción
	 */
	public long getIdPromocion() {
		return idPromocion;
	}

	/**
	 * Modifica el id de la promoción
	 * @param idPromocion - nuevo identificador de la promoción
	 */
	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
	}

	@Override
	public String toString() {
		return "ProductoPromocion [idProducto=" + idProducto + ", idPromocion=" + idPromocion + "]";
	}
}
