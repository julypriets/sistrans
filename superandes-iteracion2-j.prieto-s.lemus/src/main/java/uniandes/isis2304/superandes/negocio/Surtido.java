package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación SURTIDO del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Un estante que tiene cierto producto
 *  - Un producto que pertenece a un estante
 *  Debe existir un estante con el identificador dado.
 *  Debe existir un producto con el identificador dado.
 * 
 * @author j.prieto
 */
public class Surtido implements VOSurtido {

	/* ----------------- Atributos ----------------------------------- */

	/**
	 * Identificador del estante
	 */
	private long idEstante;
	
	/**
	 * Identificador del producto
	 */
	private String idProducto;
	
	
	/* ----------------- Métodos ----------------------------------- */

	/**
	 * Constructor por defecto
	 */
	public Surtido() {
		this.idEstante = 0;
		this.idProducto = "";
	}
	
	/**
	 * Constructor con valores
	 * @param idEstante - identificador del estante. Debe existir un estante con dicho identificador.
	 * @param idProducto - identificador del producto. Debe existir un producto con dicho identificador.
	 */
	public Surtido( long idEstante, String idProducto) {
		this.idEstante = idEstante;
		this.idProducto = idProducto;
	}
	
	/**
	 * Retorna el identificador del estante 
	 * @return - el identificador del estante 
	 */
	public long getIdEstante() {
		return idEstante;
	}

	/**
	 * Modifica el identificador del estante 
	 * @param idEstante - nuevo identificador del estante 
	 */
	public void setIdEstante(long idEstante) {
		this.idEstante = idEstante;
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
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Surtido [idEstante=" + idEstante + ", idProducto=" + idProducto + "]";
	}
}
