package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que se encarga de modelar el concepto de FACTURA en Superandes.
 * @author s.lemus
 *
 */
public class Factura implements VOFactura{

	private Long id;
	
	private Double precioTotal;
	
	private Timestamp fecha;

	public Factura(Long id, Double precioTotal, Timestamp fecha) {
		this.id = id;
		this.precioTotal = precioTotal;
		this.fecha = fecha;
	}
	
	public Factura(){
		this.id = (long) 0;
		this.precioTotal = 0.0;
		this.fecha = new Timestamp(0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", precioTotal=" + precioTotal + ", fecha=" + fecha + "]";
	}
	
	
}
