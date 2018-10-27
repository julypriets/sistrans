package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación PRODUCTO_ABASTECIMIENTO del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Un abastecimiento o pedido para una bodega que tiene un producto
 *  - Una producto que hace parte de un abastecimiento o pedido para una bodega
 *  Debe existir un abastecimiento con el identificador dado.
 *  Debe existir un producto con el identificador dado.
 * 
 * @author j.prieto
 */

public class ProductoAbastecimiento implements VOProductoAbastecimiento {
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador del abastecimiento
	 */
	private long idAbastecimiento;
	
	/**
	 * Identificador del producto
	 */
	private String idProducto;
	
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public ProductoAbastecimiento() {
		this.idAbastecimiento = 0;
		this.idProducto = "";
	}
	
	/**
	 * Constructor con valores.
	 * @param idAbastecimiento - identificador del abastecimiento. Debe existir un abastecimiento con dicho identificador. 
	 * @param idProducto identificador del producto. Debe existir un abastecimiento con dicho identificador.
	 */
	public ProductoAbastecimiento ( long idAbastecimiento, String idProducto) {
		this.idAbastecimiento = idAbastecimiento;
		this.idProducto = idProducto;
	}

	
	/**
	 * Retorna el identificador del abastecimiento
	 * @return - identificador del abastecimiento
	 */
	public long getIdAbastecimiento() {
		return idAbastecimiento;
	}

	/**
	 * Modifica el identificador del abastecimiento
	 * @param idAbastecimiento - nuevo identificador del abastecimiento
	 */
	public void setIdAbastecimiento(long idAbastecimiento) {
		this.idAbastecimiento = idAbastecimiento;
	}

	/**
	 * Retorna el identificador del producto
	 * @return - identificador del producto
	 */
	public String getIdProducto() {
		return idProducto;
	}

	/**
	 * Modifica el identificador del producto
	 * @param idAbastecimiento - nuevo identificador del producto
	 */
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "ProductoAbastecimiento [idAbastecimiento=" + idAbastecimiento + ", idProducto=" + idProducto + "]";
	}
	
}
