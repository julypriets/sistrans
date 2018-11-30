package uniandes.isis2304.superandes.negocio;

public class ProductoPromocion implements VOProductoPromocion{

	/**
	 * El id del producto con la promoción
	 */
	private String idProducto;

	/**
	 * El id de la promoción del producto
	 */
	private long idPromocion;

	/**
	 * Cantidad del producto
	 */
	private int cantidad;

	/**
	 * Creación de una nueva relación entre una promoción y un producto
	 * @param idProducto - identificador del producto contenido en la promoción
	 * @param idPromocion - identificador de la promoción que contiene el producto
	 * @param cantidad - cantidad del producto
	 */
	public ProductoPromocion(String idProducto, long idPromocion, int cantidad) {
		this.idProducto = idProducto;
		this.idPromocion = idPromocion;
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el id del producto relacionado con la promoción
	 */
	public String getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el id del producto relacionado con la promoción
	 * @param idProducto
	 */
	public void setIdProducto(String idProducto) {
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

	/**
	 * Retorna la cantidad del producto
	 * @return
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Modifica la cantidad del producto
	 * @param cantidad - nueva cantidad del producto
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ProductoPromocion [idProducto=" + idProducto + ", idPromocion=" + idPromocion + ", cantidad=" + cantidad + "]";
	}
}
