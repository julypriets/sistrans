package uniandes.isis2304.superandes.negocio;

import java.math.BigDecimal;

/**
 * Clase que se encarga de modelar el concepto de CLIENTE en Superandes.
 * @author s.lemus
 *
 */
public class Cliente implements VOCliente{

	/**
	 * Identificador del cliente
	 */
	private long id;
	
	/**
	 * Nombre del cliente
	 */
	private String nombre;
	
	/**
	 * Correo electrónico del cliente
	 */
	private String correo;
	
	/**
	 * Tipo de cliente
	 */
	private String tipo;
	
	/**
	 * Constante que representa una empresa que es cliente de SuperAndes.
	 */
	public static final String EMPRESA = "EMPRESA";
	
	/**
	 * Constante que representa una persona natural que es cliente de SuperAndes.
	 */
	public static final String PERSONA_NATURAL = "PERSONA_NATURAL";

	/**
	 * Crea un nuevo cliente
	 * @param id - identificador del cliente
	 * @param nombre - nombre del cliente
	 * @param correo - correo electrónico del cliente
	 */
	public Cliente(long id, String nombre, String correo) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
	}
	
	/**
	 * Constructor por defecto.
	 */
	public Cliente(){
		this.id = 0;
		this.nombre = "";
		this.correo = "";
	}

	/**
	 * Retorna el nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del cliente
	 * @param nombre - nuevo nombre del cliente
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna el correo electrónico del cliente
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Modifica el correo electrónico del cliente
	 * @param correo - nuevo correo electrónico del cliente
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Retorna el identificador del cliente
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el identificador del cliente
	 * @param id - identificador del cliente
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna el tipo de cliente
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Modifica el tipo de cliente
	 * @param tipo - nuevo tipo de cliente
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", correo=" + correo + "]";
	}
	
	
}
