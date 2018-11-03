package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que se encarga de modelar el concepto de FACTURA en Superandes.
 * @author s.lemus
 *
 */
public class Factura implements VOFactura{

	/**
	 * Identificador de la factura
	 */
	private long id;
	
	/**
	 * Precio total de la compra 
	 */
	private Double precioTotal;
	
	/**
	 * Fecha de la compra
	 */
	private Timestamp fecha;
	
	/**
	 * Identificador del cajero que atendió la compra
	 */
	private long idCajero;
	
	/**
	 * El identificador del cliente que realizó la compra
	 */
	private long idCliente;
	
	
	/**
	 * Crea una nueva factura
	 * @param id - identificador de la factura
	 * @param precioTotal - precio total de la compra
	 * @param fecha - fecha de la compra
	 * @param idCajero - identificador del cajero que atendió la compra
	 * @param idPersona - identificador de la persona que hizo la compra
	 * @param idEmpresa - identificador de la empresa que hizo la compra
	 * @param cliente - tipo de cliente que hizo la compra
	 * @param comprada - indica si la compra se realizó o no
	 */
	public Factura(long id, Double precioTotal, Timestamp fecha, long idCajero, long idCliente) {
		this.id = id;
		this.precioTotal = precioTotal;
		this.fecha = fecha;
		this.idCajero = idCajero;
		this.idCliente = idCliente;

	}
	
	/**
	 * Constructor por defecto
	 */
	public Factura(){
		this.id = 0;
		this.precioTotal = 0.0;
		this.fecha = new Timestamp(0);
		this.idCajero = 0;
		this.idCliente = 0;
	}

	/**
	 * Retorna el identificador de la factura
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el identificador de la factura
	 * @param id - nuevo identificador de la factura
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna el precio total de la compra
	 */
	public Double getPrecioTotal() {
		return precioTotal;
	}

	/**
	 * Modifica el precio total de la compra
	 * @param precioTotal - nuevo precio total de la compra
	 */
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	/**
	 * Retorna la fecha en que se realizó la compra
	 */
	public Timestamp getFecha() {
		return fecha;
	}

	/**
	 * Modifica la fecha en la que se realizó la compra
	 * @param fecha - fecha en que se realizó la compra
	 */
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * Obtiene el identificador del cajero
	 */
	public long getIdCajero() {
		return idCajero;
	}

	/**
	 * Modifica el identificador del cajero
	 * @param idCajero - nuevo identificador del cajero
	 */
	public void setIdCajero(Long idCajero) {
		this.idCajero = idCajero;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", precioTotal=" + precioTotal + ", fecha=" + fecha + "]";
	}


	/**
	 * Modifica el identificador del cajero que atendió la compra
	 * @param idCajero - identificador del cajero que atendió la compra
	 */
	public void setIdCajero(long idCajero) {
		this.idCajero = idCajero;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
