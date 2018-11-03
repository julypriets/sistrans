package uniandes.isis2304.superandes.negocio;

public class Compra implements VOCompra{

	/**
	 * El producto comprado
	 */
	private String idProducto;

	/**
	 * La factura respectiva de la compra
	 */
	private long idFactura;

	/**
	 * Cantidad del producto
	 */
	private int cantidad;

	/**
	 * Crea una compra con la información del producto y la factura
	 * @param idProducto - producto comprado
	 * @param idFactura - factura que constata la compra
	 * @param cantidad - Cantidad del producto
	 */
	public Compra(String idProducto, long idFactura, int cantidad) {
		this.idProducto = idProducto;
		this.idFactura = idFactura;
		this.cantidad = cantidad;
	}

	/**
	 * Constructor por defecto
	 */
	public Compra() {
		this.idProducto = "";
		this.idFactura = 0;
	}

	/**
	 * Retorna el identificador del producto adquirido
	 */
	public String getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el identificador del producto adquirido
	 * @param idProducto - nuevo identificador del producto adquirido
	 */
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Obtiene el identificador de la factura
	 */
	public long getIdFactura() {
		return idFactura;
	}

	/**
	 * Modifica el identificador de la factura
	 * @param idFactura
	 */
	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	/**
	 * Retorna la cantidad del producto
	 * @return - cantidad del producto
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
		return "Compra [idProducto=" + idProducto + ", idFactura=" + idFactura + ", cantidad=" + cantidad + "]";
	}

}
