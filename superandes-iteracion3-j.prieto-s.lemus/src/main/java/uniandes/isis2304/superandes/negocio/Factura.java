package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	 * Una colección de los productos comprados por el cliente
	 */
	private List<Producto> productos;
	
	/**
	 * El dinero que hay que devolver al cliente luego de su compra (si hay exedente)
	 */
	private double sobrante;
	
	
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
		this.productos = new LinkedList<>();
		this.sobrante = 0.0;
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
		this.productos = new LinkedList<>();
		this.sobrante = 0.0;
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public double getSobrante() {
		return sobrante;
	}

	public void setSobrante(double sobrante) {
		this.sobrante = sobrante;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", precioTotal=" + precioTotal + ", fecha=" + fecha + ", idCajero=" + idCajero
				+ ", idCliente=" + idCliente + ", productos=" + productos + ", sobrante=" + sobrante + "]";
	}
	
	
	
}
