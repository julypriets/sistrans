package uniandes.isis2304.superandes.negocio;

public class Empresa extends Cliente implements VOEmpresa {
	
	/**
	 * NIT único de la empresa
	 */
	private String nit;
	
	/**
	 * Dirección de la empresa
	 */
	private String direccion;
	
	/**
	 * Crea una nueva empresa
	 * @param id - identificador de la empresa
	 * @param nombre - nombre de la empresa
	 * @param correo - correo electrónico de la empresa
	 * @param nit - nit de la empresa
	 * @param direccion - dirección de la empresa
	 */
	public Empresa(long id, String nombre, String correo, String nit, String direccion) {
		super(id, nombre, correo);
		this.nit = nit;
		this.direccion = direccion;
	}

	/**
	 * Constructor por defecto
	 */
	public Empresa(){
		super();
		nit = "";
		direccion = "";
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
	

	/**
	 * Modifica la dirección de la empresa
	 * @param direccion - dirección de la empresa
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Empresa [nit=" + nit + ", direccion=" + direccion + "]";
	}
	

}
