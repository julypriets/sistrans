package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de CLIENTE en Superandes.
 * @author s.lemus
 *
 */
public abstract class Cliente implements VOCliente{

	private Long id;
	
	private String nombre;
	
	private String correo;
	
	private String tipo;
	
	public static final String EMPRESA = "EMPRESA";
	
	public static final String PERSONA_NATURAL = "PERSONA_NATURAL";

	public Cliente(Long id, String nombre, String correo) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
	} 
	
	public Cliente(){
		this.id = (long) 0;
		this.nombre = "";
		this.correo = "";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", correo=" + correo + "]";
	}
	
	
}
