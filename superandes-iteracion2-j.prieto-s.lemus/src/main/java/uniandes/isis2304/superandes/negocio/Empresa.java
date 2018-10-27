package uniandes.isis2304.superandes.negocio;

public class Empresa implements VOEmpresa {
	
	/**
	 * NIT único de la empresa
	 */
	private String nit;
	
	/**
	 * Nombre de la empresa
	 */
	private String nombre;
	
	/**
	 * Dirección de la empresa
	 */
	private String direccion;
	
	/**
	 * Correo electrónico de la empresa
	 */
	private String correo;
	
	/**
	 * Crea una nueva empresa
	 * @param id - identificador de la empresa
	 * @param nombre - nombre de la empresa
	 * @param correo - correo electrónico de la empresa
	 * @param nit - nit de la empresa
	 * @param direccion - dirección de la empresa
	 */
	public Empresa(long id, String nombre, String correo, String nit, String direccion) {
		this.nit = nit;
		this.direccion = direccion;
		this.correo = correo;
		this.nombre = nombre;
	}

	/**
	 * Constructor por defecto
	 */
	public Empresa(){
		super();
		nit = "";
		nombre="";
		direccion = "";
		correo="";
	}

	/**
	 * Retorna el NIT de la empresa
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * Modifica el NIT de la empresa
	 * @param nit
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}

	/**
	 * Retorna la dirección de la empresa
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Modifica la dirección de la emrpesa
	 * @param direccion - dirección de la empresa
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Empresa [nit=" + nit + ", direccion=" + direccion + "]";
	}

	/**
	 * Retorna el nombre de la empresa
	 * @return - nombre de la empresa
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la empresa
	 * @param nombre - nuevo nombre de la empresa
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna el correo electrónico de la empresa
	 * @return - correo electrónico de la empresa
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Modifica el correo electrónico de la empresa
	 * @param correo - nuevo correo electrónico de la empresa
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	

}
