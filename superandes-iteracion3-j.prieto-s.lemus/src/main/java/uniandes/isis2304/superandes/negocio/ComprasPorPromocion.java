package uniandes.isis2304.superandes.negocio;

public class ComprasPorPromocion {

	/**
	 * Identificador de la promoción
	 */
	private long idPromocion;
	
	/**
	 * Cantidad de compras por promoción
	 */
	private long numCompras;

	/**
	 * Crea un objeto que contiene las compras por promoción
	 * @param idPromocion - identificador de la promoción
	 * @param numCompras - número de compras por promoción
	 */
	public ComprasPorPromocion(long idPromocion, long numCompras) {
		super();
		this.idPromocion = idPromocion;
		this.numCompras = numCompras;
	}
	
	/**
	 * Constructor por defecto
	 */
	public ComprasPorPromocion() {
		this.idPromocion = 0;
		this.numCompras = 0;
	}

	/**
	 * Retorna el identificador de la promoción
	 * @return - identificador de la promoción
	 */
	public long getIdPromocion() {
		return idPromocion;
	}

	/**
	 * Modifica el identificador de la promoción
	 * @param idPromocion - identificador de la promoción
	 */
	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
	}

	/**
	 * Retorna el número de compras por promoción
	 * @return - número de compras por promoción
	 */
	public long getNumCompras() {
		return numCompras;
	}

	/**
	 * Modifica el número de compras por promoción
	 * @param numCompras - número de compras por promoción
	 */
	public void setNumCompras(long numCompras) {
		this.numCompras = numCompras;
	}
}
