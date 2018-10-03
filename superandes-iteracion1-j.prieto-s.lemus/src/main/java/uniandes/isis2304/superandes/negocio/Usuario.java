package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de USUARIO en Superandes.
 * @author s.lemus
 *
 */
public class Usuario implements VOUsuario{
	
	private String nombre;
	
	private String apellido;
		
	private String username;
	
	private String password;
	
	private String tipo;
	
	private long idSucursal;
	
	private static final String USUARIO_SUPERMERCADO = "USUP";
	
	private static final String USUARIO_SUCURSAL = "USUC";
	
	/**
	 * Constructor del Usuario con valores
	 * @param nombre
	 * @param apellido
	 * @param edad
	 * @param username
	 * @param password
	 */
	public Usuario(String nombre, String apellido, String username, String password, String tipo, long idSucursal) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.tipo = tipo;
		this.idSucursal = idSucursal;
	}

	public Usuario(){
		this.nombre = "";
		this.apellido = "";
		this.username = "";
		this.password = "";
		this.idSucursal = 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellido=" + apellido + ", username=" + username
				+ ", password=" + password + "]";
	}
	
	

}
