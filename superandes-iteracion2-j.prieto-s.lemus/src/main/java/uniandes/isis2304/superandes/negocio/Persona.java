package uniandes.isis2304.superandes.negocio;

public class Persona extends Cliente implements VOPersona{
	
	/**
	 * Identificacion de la persona
	 */
	private String identificacion;

	/**
	 * Crea una nueva persona
	 * @param id - identificador del cliente
	 * @param nombre - nombre del cliente
	 * @param correo - correo del cliente
	 * @param identificacion - identificacion del cliente
	 */
	public Persona(long id, String nombre, String correo, String identificacion) {
		super(id, nombre, correo);
		this.identificacion = identificacion;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Persona(){
		super();
		identificacion = "";
	}

	/**
	 * Obtiene la identificación de la persona
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * Modifica la identificación de la persona
	 * @param identificacion
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	// DataNucleus no detecta los métodos heredados, por eso se repiten los setters en esta clase
	
	public void setId(long id){
		super.setId(id);	
	}
	
	public void setNombre(String nombre){
		super.setNombre(nombre);
	}
	
	public void setCorreo(String correo){
		super.setCorreo(correo);
	}

	@Override
	public String toString() {
		return "PersonaNatural [identificacion=" + identificacion + "]";
	}
	
	
}
