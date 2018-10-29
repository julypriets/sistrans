package uniandes.isis2304.superandes.negocio;

public class Persona extends Cliente implements VOPersona{
	
	/**
	 * Identificador del cliente
	 */
	private long id;
	
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

	@Override
	public String toString() {
		return "PersonaNatural [identificacion=" + identificacion + "]";
	}
	
	
}
