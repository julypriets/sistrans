package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de SUCURSAL en Superandes.
 * @author s.lemus
 *
 */
public class Sucursal implements VOSucursal{
	
	/**
	 * Identificador único de la sucursal
	 */
	private long id;
	
	/**
	 * Nombre de la sucursal 
	 */
	private String nombre;
	
	/**
	 * Ciudad de la sucursal
	 */
	private String ciudad;
	
	/**
	 * Dirección de la sucursal
	 */
	private String direccion;

	/**
	 * Constructor de la Sucursal con valores
	 * @param id
	 * @param nombre
	 * @param ciudad
	 * @param direccion
	 */
	public Sucursal(long id, String nombre, String ciudad, String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Sucursal(){
		this.id = 0;
		this.nombre = "";
		this.ciudad = "";
		this.direccion = "";

	}

	/**
	 * 
	 * @return El id de la sucursal
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id - nuevo id de la sucursal
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return nombre de la sucursal
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre - nuevo nombre de la sucursal
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return ciudad de la sucursal
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * 
	 * @param ciudad - nueva ciudad de la sucursal
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * 
	 * @return dirección de la sucursal
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * 
	 * @param direccion - nueva dirección de la sucursal
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	/**
	 * Retorna una cadena de caractéres con la información básica
	 * de la sucursal
	 */
	@Override
	public String toString() {
		return "Sucursal [id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad + ", direccion=" + direccion + "]";
	}

	
}
