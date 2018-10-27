package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación ABASTECIMIENTO_BODEGA del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Un abastecimiento o pedido para la bodega tiene asignada una bodega
 *  - Una bodega tiene un abastecimiento asignado o pedido
 *  Debe existir una bodega con el identificador dado.
 *  Debe existir un abastecimiento con el identificador dado.
 * 
 * @author j.prieto
 */

public class AbastecimientoBodega implements VOAbastecimientoBodega {
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador del abastecimiento
	 */
	private long idAbastecimiento;
	
	/**
	 * Identificador de la bodega a abastecer
	 */
	private long idBodega;


	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public AbastecimientoBodega() {
		this.idAbastecimiento = 0;
		this.idBodega = 0;
	}

	/**
	 * Constructor con valores
	 * @param idAbastecimiento - El identificador del abastecimiento. Debe existir un abastecimiento con dicho identificador.
	 * @param idBebida - El identificador de la bodega. Debe existir una bodega con dicho identificador
	 */
	public AbastecimientoBodega(long idAbastecimiento, long idBodega) {
		this.idAbastecimiento = idAbastecimiento;
		this.idBodega = idBodega;
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
	 * Retorna el identificador de la bodega a abastecer
	 * @return - el identificador de la bodega a abastecer
	 */
	public long getIdBodega() {
		return idBodega;
	}

	/**
	 * Modifica el identificador de la bodega a abastecer
	 * @param idBodega - nuevo identificador de la bodega a abastecer
	 */
	public void setIdBodega(long idBodega) {
		this.idBodega = idBodega;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "AbastecimientoBodega [idAbastecimiento=" + idAbastecimiento + ", idBodega=" + idBodega + "]";
	}

}
