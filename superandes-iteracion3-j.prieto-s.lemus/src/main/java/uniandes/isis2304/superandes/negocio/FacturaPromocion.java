package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación FACTURA_PROMOCION del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Una factura que contiene una promoción
 *  - Una promoción que figura en una factura
 *  Debe existir una factura con el identificador dado.
 *  Debe existir una promoción con el identificador dado.
 * 
 * @author j.prieto
 */

public class FacturaPromocion implements VOFacturaPromocion {
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador de la factura que contiene la promoción
	 */
	private long idFactura;
	
	/**
	 * Identificador de la promoción que está contenida en una factura
	 */
	private long idPromocion;
	
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public FacturaPromocion() {
		this.idFactura = 0;
		this.idPromocion = 0;
	}
	
	/**
	 * Constructor con valores.
	 * @param idFactura - El identificador de la factura. Debe existir una factura con dicho identificador.
	 * @param idPromocion - El identificador de la promoción. Debe existir una promoción con dicho identificador.
	 */
	public FacturaPromocion( long idFactura, long idPromocion) {
		this.idFactura = idFactura;
		this.idPromocion = idPromocion;
	}

	/**
	 * Retorna el identificador de la factura
	 * @return - identificador de la factura
	 */
	public long getIdFactura() {
		return idFactura;
	}

	/**
	 * Modifica el identificador de la factura
	 * @param idFactura - nuevo identificador de la factura
	 */
	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	/**
	 * Retorna el identificador de la promoción
	 * @return - el identificador de la promoción 
	 */
	public long getIdPromocion() {
		return idPromocion;
	}

	/**
	 * Modifica el identificador de la promoción
	 * @param idPromocion - nuevo identificador de la promoción
	 */
	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "FacturaPromocion [idFactura=" + idFactura + ", idPromocion=" + idPromocion + "]";
	}
	
}
