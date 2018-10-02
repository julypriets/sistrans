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
	
	private Long idCajero;
	
	private Long idCliente;

	public Factura(Long id, Double precioTotal, Timestamp fecha, Long idCajero, Long idCliente) {
		this.id = id;
		this.precioTotal = precioTotal;
		this.fecha = fecha;
		this.idCajero = idCajero;
		this.idCliente = idCliente;
	}
	
	public Factura(){
		this.id = (long) 0;
		this.precioTotal = 0.0;
		this.fecha = new Timestamp(0);
		this.idCajero = (long) 0;
		this.idCliente = (long) 0;
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
	
	public Long getIdCajero() {
		return idCajero;
	}

	public void setIdCajero(Long idCajero) {
		this.idCajero = idCajero;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", precioTotal=" + precioTotal + ", fecha=" + fecha + "]";
	}
	
	
}
