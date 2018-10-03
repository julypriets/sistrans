package uniandes.isis2304.superandes.negocio;

public class ComprasPorPromocion {

	private long idPromocion;
	
	private long numCompras;

	public ComprasPorPromocion(long idPromocion, long numCompras) {
		super();
		this.idPromocion = idPromocion;
		this.numCompras = numCompras;
	}
	
	public ComprasPorPromocion() {
		this.idPromocion = 0;
		this.numCompras = 0;
	}

	public long getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
	}

	public long getNumCompras() {
		return numCompras;
	}

	public void setNumCompras(long numCompras) {
		this.numCompras = numCompras;
	}
	
	
	
}
