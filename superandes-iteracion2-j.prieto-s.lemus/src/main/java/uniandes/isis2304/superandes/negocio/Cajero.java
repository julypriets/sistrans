package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de CAJERO en Superandes.
 * @author s.lemus
 *
 */
public class Cajero implements VOCajero{

	/**
	 * Identificador del cajero
	 */
	private long id;
	
	/**
	 * Nombre del cajero
	 */
	private String nombre;
	
	/**
	 * Identificador de la sucursal a la que pertenece el cajero
	 */
	private long idSucursal;

	/**
	 * Crea un nuevo cajero
	 * @param id - identificador del cajero
	 * @param nombre - nombre de la persona que atiende el cajero
	 * @param idSucursal - identificador de la sucursal a la que pertenece el cajero
	 */
	public Cajero(long id, String nombre, long idSucursal) {
		this.id = id;
		this.nombre = nombre;
		this.idSucursal = idSucursal;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Cajero() {
		this.id = 0;
		this.idSucursal = 0;
	}

	/**
	 * Retorna el identificador del cajero
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el identificador del cajero
	 * @param id - nuevo identificador del cajero
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna el nombre del cajero
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del cajero
	 * @param nombre - nuevo nombre del cajero
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Cajero [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
