package uniandes.isis2304.superandes.negocio;

public class CarritoProducto implements VOCarritoProducto {

	/**
	 * Identificador del carrito 
	 */
	private long idCarrito;
	
	/**
	 * Identificador del producto
	 */
	private String idProducto;
	
	private int cantidad;

	public CarritoProducto(){
		
	}
	
	public CarritoProducto(long idCarrito, String idProducto, int cantidad) {
		super();
		this.idCarrito = idCarrito;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el id del carrito
	 */
	public long getIdCarrito() {
		return idCarrito;
	}

	/**
	 * Modifica el id del carrito
	 * @param idCarrito - identificador del carrito
	 */
	public void setIdCarrito(long idCarrito) {
		this.idCarrito = idCarrito;
	}

	/**
	 * Obtiene el id del producto
	 */
	public String getIdProducto() {
		return idProducto;
	}
	
	

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Modifica el id del producto
	 * @param estado - nuevo id del producto
	 */
	public void setIdProducto(String estado) {
		this.idProducto = estado;
	}
}
