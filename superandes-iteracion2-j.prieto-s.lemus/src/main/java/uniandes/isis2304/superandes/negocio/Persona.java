package uniandes.isis2304.superandes.negocio;

public class Persona implements VOPersona{

	/**
	 * Identificación de la persona que realiza la compra
	 */
	private String identificacion;
	
	/**
	 * Nombre de la persona
	 */
	private String nombre;
	
	/**
	 * Correo electrónico de la persona
	 */
	private String correo;

	public Persona(long id, String nombre, String correo, String identificacion) {
		this.identificacion = identificacion;
		this.nombre=nombre;
		this.correo=correo;
	}
	
	public Persona(){
		identificacion = "";
		nombre="";
		correo="";
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	@Override
	public String toString() {
		return "PersonaNatural [identificacion=" + identificacion + "]";
	}

	/**
	 * Retorna el nombre de la persona
	 * @return - nombre de la persona
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la persona
	 * @param nombre - nuevo nombre de la persona
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna el correo electrónico de la persona
	 * @return - correo electrónico de la persona
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Modifica el correo electrónico de la persona
	 * @param correo - correo electrónico de la persona
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}
