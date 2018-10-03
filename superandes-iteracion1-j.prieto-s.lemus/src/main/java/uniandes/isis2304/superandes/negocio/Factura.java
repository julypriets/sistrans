package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que se encarga de modelar el concepto de FACTURA en Superandes.
 * @author s.lemus
 *
 */
public class Factura implements VOFactura{

	private long id;
	
	private Double precioTotal;
	
	private Timestamp fecha;
	
	private long idCajero;
	
	private long idCliente;

	public Factura(long id, Double precioTotal, Timestamp fecha, long idCajero, long idCliente) {
		this.id = id;
		this.precioTotal = precioTotal;
		this.fecha = fecha;
		this.idCajero = idCajero;
		this.idCliente = idCliente;
	}
	
	public Factura(){
		this.id = 0;
		this.precioTotal = 0.0;
		this.fecha = new Timestamp(0);
		this.idCajero = 0;
		this.idCliente = 0;
	}

	public long getId() {
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
	
	public long getIdCajero() {
		return idCajero;
	}

	public void setIdCajero(Long idCajero) {
		this.idCajero = idCajero;
	}

	public long getIdCliente() {
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
