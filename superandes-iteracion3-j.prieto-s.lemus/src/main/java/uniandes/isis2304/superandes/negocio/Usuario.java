package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de USUARIO en Superandes.
 * @author s.lemus
 *
 */
public class Usuario implements VOUsuario{
	
	/**
	 * Nombre la persona usuario
	 */
	private String nombre;
	
	/**
	 * Nombre de usuario 
	 */
	private String username;
	
	/**
	 * Clave del usuario
	 */
	private String password;
	
	/**
	 * Tipo de usuario
	 */
	private String tipo;
	
	/**
	 * Id de la sucursal del usuario, si el usuario es administrador,
	 * el valor para este atributo es cero
	 */
	private long idSucursal;
	
	/**
	 * Tipo de usuario USUP; el administrador
	 */
	public static final String ADMINISTRADOR = "ADMINISTRADOR";
	
	/**
	 * Tipo de usuario USUC; el gerente de una sucursal
	 */
	public static final String GERENTE = "GERENTE";

	
	/**
	 * Constructor del Usuario con valores
	 * @param nombre
	 * @param edad
	 * @param username
	 * @param password
	 */
	public Usuario(String nombre, String username, String password, String tipo, long idSucursal) {
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.tipo = tipo;
		this.idSucursal = idSucursal;
	}

	/**
	 * Cosntructor por defecto
	 */
	public Usuario(){
		this.nombre = "";
		this.username = "";
		this.password = "";
		this.idSucursal = 0;
	}

	/**
	 * Obtiene el nombre de la persona usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la persona usuario
	 * @param nombre - nuevo nombre de la persona usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna el nombre de usuario
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Modifica el nombre de usuario
	 * @param username - nuevo nombre de usuario
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retorna la clave del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Modifica la clave del usuario
	 * @param password - nueva clave del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Retorna el tipo de usuario
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Modifica el tipo de usuario
	 * @param tipo - nuevo tipo de usuario
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el id de la sucursal del usuario
	 */
	public long getIdSucursal() {
		return idSucursal;
	}

	/**
	 * Modifica el id de la sucursal del usuario
	 * @param idSucursal - nuevo id de la sucursal del usuario
	 */
	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", username=" + username
				+ ", password=" + password + "]";
	}	

}
