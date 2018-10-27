package uniandes.isis2304.superandes.negocio;

public class Compra implements VOCompra{
	
	/**
	 * El producto comprado
	 */
	private long idProducto;
	
	/**
	 * La factura respectiva de la compra
	 */
	private long idFactura;

	/**
	 * Crea una compra con la información del producto y la factura
	 * @param idProducto
	 * @param idFactura
	 */
	public Compra(long idProducto, long idFactura) {
		this.idProducto = idProducto;
		this.idFactura = idFactura;
	}

	/**
	 * Constructor por defecto
	 */
	public Compra() {
		this.idProducto = 0;
		this.idFactura = 0;
	}

	/**
	 * Retorna el identificador del producto adquirido
	 */
	public long getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el identificador del producto adquirido
	 * @param idProducto - nuevo identificador del producto adquirido
	 */
	public void setIdProducto(long idProducto) {
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

	@Override
	public String toString() {
		return "Compra [idProducto=" + idProducto + ", idFactura=" + idFactura + "]";
	}
	
}
