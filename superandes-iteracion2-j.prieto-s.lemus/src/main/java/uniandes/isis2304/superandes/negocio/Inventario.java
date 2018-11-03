package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación INVENTARIO del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Un producto que está almacenado en una cierta bodega.
 *  - Una bodega que almacena un producto.
 *  Debe existir un producto con el identificador dado.
 *  Debe existir una bodega con el identificador dado.
 * 
 * @author j.prieto
 */

public class Inventario implements VOInventario{
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador del producto almacenado en la bodega.
	 */
	private String idProducto;
	
	/**
	 * Identificador de la bodega que contiene el producto.
	 */
	private long idBodega;
	
	/**
	 * Cantidad del producto
	 */
	private int cantidad;
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public Inventario() {
		this.idProducto = "";
		this.idBodega = 0;
		this.cantidad = 0; 
	}
	
	/**
	 * Constructor con valores.
	 * @param idProducto - El identificador del producto. Debe existir un producto con dicho identificador
	 * @param idBodega - El identificador de la bodega. Debe existir una bodega con dicho identificador
	 */
	public Inventario ( String idProducto, long idBodega, int cantidad ) {
		this.idProducto = idProducto;
		this.idBodega = idBodega;
		this.cantidad = cantidad;
	}

	/**
	 * Retorna el identificador del producto
	 * @return - el identificador del producto
	 */
	public String getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el identificador del producto
	 * @param idProducto - nuevo identificador del producto
	 */
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Retorna el identificador de la bodega
	 * @return - el identificador de la bodega
	 */
	public long getIdBodega() {
		return idBodega;
	}

	/**
	 * Modifica el identificador de la bodega
	 * @param idBodega - nuevo identificador de la bodega
	 */
	public void setIdBodega(long idBodega) {
		this.idBodega = idBodega;
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

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Inventario [idProducto=" + idProducto + ", idBodega=" + idBodega + ", cantidad=" + cantidad + "]";
	}
	
}
