package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de CLIENTE en Superandes.
 * @author s.lemus
 *
 */
public class Cliente implements VOCliente{

	private String nombre;
	
	private String correo;

	public Cliente(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	} 
	
	public Cliente(){
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

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", correo=" + correo + "]";
	}
	
	
}
