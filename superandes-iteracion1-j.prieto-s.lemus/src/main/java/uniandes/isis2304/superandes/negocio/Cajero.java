package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de CAJERO en Superandes.
 * @author s.lemus
 *
 */
public class Cajero implements VOCajero{

	private Long id;
	
	private String nombre;
	
	private Long idSucursal;

	public Cajero(Long id, String nombre, Long idSucursal) {
		this.id = id;
		this.nombre = nombre;
		this.idSucursal = idSucursal;
	}
	
	public Cajero() {
		this.id = (long) 0;
		this.idSucursal = (long) 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Cajero [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
