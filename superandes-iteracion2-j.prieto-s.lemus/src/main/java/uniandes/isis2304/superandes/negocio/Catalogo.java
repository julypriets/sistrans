package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación CATALOGO del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Un proveedor que ofrece un cierto producto
 *  - Un producto que es ofrecido por un proveedor
 *  Debe existir un proveedor con el identificador dado.
 *  Debe existir un producto con el identificador dado.
 * 
 * @author j.prieto
 */

public class Catalogo implements VOCatalogo {
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador del proveedor que ofrece cierto producto
	 */
	private String idProveedor;
	
	/**
	 * Identificador del producto ofrecido por cierto proveedor
	 */
	private String idProducto;
	
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public Catalogo() {
		this.idProveedor = "";
		this.idProducto = "";
	}
	
	/**
	 * Constructor con valores
	 * @param idProveedor - el identificador del proveedor. Debe existir un proveedor con dicho identificador.
	 * @param idProducto - el identificador del producto. Debe existir un producto con dicho identificador.
	 */
	public Catalogo ( String idProveedor, String idProducto) {
		this.idProveedor = idProveedor;
		this.idProducto = idProducto;
	}

	/**
	 * Retorna el identificador del proveedor
	 * @return - el identificador del proveedor
	 */
	public String getIdProveedor() {
		return idProveedor;
	}

	/**
	 * Modifica el identificador del proveedor
	 * @param idProveedor - el nuevo identificador del proveedor
	 */
	public void setIdProveedor(String idProveedor) {
		this.idProveedor = idProveedor;
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
	 * @param idProducto - el nuevo identificador del producto
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
		return "Catalogo [idProveedor=" + idProveedor + ", idProducto=" + idProducto + "]";
	}
	
}
