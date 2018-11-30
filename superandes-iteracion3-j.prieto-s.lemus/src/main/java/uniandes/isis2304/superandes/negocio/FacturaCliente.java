package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;

/**
 * Clase que agrupa los datos de una factura y su respectivo cliente y un producto que se haya comprado
 * Ayuda a cumplir requerimientos RFC10 y RFC11
 * @author sebastian
 *
 */
public class FacturaCliente {

	/**
	 * Id de la factura
	 */
	public long id;
	
	/**
	 * Fecha en la que fue generada la factura
	 */
	public Timestamp fecha;
	
	/**
	 * Precio total cancelado por el cliente
	 */
	public double precio_total;
	
	/**
	 * El identificador del cliente que realizó la compra
	 */
	public long id_cliente;
	
	/**
	 * El nombre del cliente
	 */
	public String nombre;
	
	/**
	 * El correo del cliente
	 */
	public String correo;
	
	/**
	 * El nombre del producto comprado
	 */
	public String nombreProducto;
	
	/**
	 * Cantidad del producto comprado
	 */
	public int cantidad;

	/**
	 * Criterios de ordenamiento para las consultas
	 */
	public static final String ID_FACTURA = "id";
	public static final String FECHA = "fecha";
	public static final String CANTIDAD = "cantidad";
	public static final String NOMBRE = "nombre";
	public static final String CORREO = "correo";
	

	/**
	 * Constructor vacío
	 */
	public FacturaCliente() {
		
	}
	

	/**
	 * Realiza una instancia de FacturaCliente
	 * @param id
	 * @param fecha
	 * @param precio_total
	 * @param id_cliente
	 * @param nombre
	 * @param correo
	 * @param nombreProducto
	 * @param cantidad
	 */
	public FacturaCliente(long id, Timestamp fecha, double precio_total, long id_cliente, String nombre, String correo,
			String nombreProducto, int cantidad) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.precio_total = precio_total;
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.correo = correo;
		this.nombreProducto = nombreProducto;
		this.cantidad = cantidad;
	}



	/**
	 * 
	 * @return el id de la factura
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el id de la factura
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return la fecha de la factura
	 */
	public Timestamp getFecha() {
		return fecha;
	}

	/**
	 * Modifica la fecha de la factura
	 * @param fecha
	 */
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	/**
	 * 
	 * @return el precio total cancelado
	 */
	public double getPrecio_total() {
		return precio_total;
	}

	/**
	 * Modifica el precio total
	 * @param precio_total
	 */
	public void setPrecio_total(double precio_total) {
		this.precio_total = precio_total;
	}

	/**
	 * 
	 * @return el id del cliente
	 */
	public long getId_cliente() {
		return id_cliente;
	}

	/**
	 * Modifica el id del cliente
	 * @param id_cliente
	 */
	public void setId_cliente(long id_cliente) {
		this.id_cliente = id_cliente;
	}

	/**
	 * 
	 * @return el nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del cliente
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return el correo del cliente
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Modifica el correo del cliente
	 * @param correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getNombreProducto() {
		return nombreProducto;
	}


	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	@Override
	public String toString() {
		return "FacturaCliente [id=" + id + ", fecha=" + fecha + ", precio_total=" + precio_total + ", id_cliente="
				+ id_cliente + ", nombre=" + nombre + ", correo=" + correo + ", nombreProducto=" + nombreProducto
				+ ", cantidad=" + cantidad + "]";
	}
	
	
	
}
