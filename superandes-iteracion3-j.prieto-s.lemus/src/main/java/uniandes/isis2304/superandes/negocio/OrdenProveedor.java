package uniandes.isis2304.superandes.negocio;

/** 
 * Clase para modelar la relación ORDEN_PROVEEDOR del negocio de SuperAndes:
 * Cada objeto de esta clase representa:
 * 	- Una orden que fue asignada a cierto proveedor
 *  - Un proveedor que tiene asignada cierta orden
 *  Debe existir una orden con el identificador dado.
 *  Debe existir un proveedor con el identificador dado.
 * 
 * @author j.prieto
 */

public class OrdenProveedor implements VOOrdenProveedor {
	
	/* ----------------- Atributos ----------------------------------- */
	
	/**
	 * Identificador de la orden asignada a un proveedor
	 */
	private long idOrden;
	
	/**
	 * Identificador de un proveedor a quien se le asignó una orden
	 */
	private String idProveedor;
	
	/**
	 * Calificación del servicio prestado por el proveedor en la orden
	 */
	private double calificacion;
	
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor por defecto
	 */
	public OrdenProveedor() {
		this.idOrden = 0;
		this.idProveedor = "";
		this.calificacion = 0;
	}
	
	/**
	 * Constructor con valores
	 * @param idOrden - identificador de la orden. Debe existir una orden con dicho identificador.
	 * @param idProveedor - identificador del proveedor. Debe existir un proveedor con dicho identificador.
	 * @param calificacion - calificacion dada por el servicio prestado.
	 */
	public OrdenProveedor( long idOrden, String idProveedor, double calificacion) {
		this.idOrden = idOrden;
		this.idProveedor = idProveedor;
		this.calificacion = calificacion;
	}

	/**
	 * Retorna el identificador de la orden.
	 * @return - el identificador de la orden.
	 */
	public long getIdOrden() {
		return idOrden;
	}

	/**
	 * Modifica el identificador de la orden.
	 * @param idOrden - nuevo identificador de la orden.
	 */
	public void setIdOrden(long idOrden) {
		this.idOrden = idOrden;
	}

	/**
	 * Retorna el identificador del proveedor.
	 * @return - el identificador del proveedor.
	 */
	public String getIdProveedor() {
		return idProveedor;
	}

	/**
	 * Modifica el identificador del proveedor.
	 * @param idProveedor - nuevo identificador del proveedor.
	 */
	public void setIdProveedor(String idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * Retorna la calificación dada al servicio prestado. 
	 * @return - la calificación dada al servicio prestado. 
	 */
	public double getCalificacion() {
		return calificacion;
	}

	/**
	 * Modifica la calificación dada al servicio prestado. 
	 * @param calificacion - la nueva calificación dada al servicio prestado. 
	 */
	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "OrdenProveedor [idOrden=" + idOrden + ", idProveedor=" + idProveedor + ", calificacion=" + calificacion + "]";
	}
	

}
