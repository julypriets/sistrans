package uniandes.isis2304.superandes.negocio;

public class Empresa extends Cliente implements VOEmpresa {
	
	private long id;
	
	private String nit;
	
	private String direccion;
	
	public Empresa(long id, String nombre, String correo, String nit, String direccion) {
		super(id, nombre, correo);
		this.nit = nit;
		this.direccion = direccion;
	}

	public Empresa(){
		super();
		nit = "";
		direccion = "";
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Empresa [nit=" + nit + ", direccion=" + direccion + "]";
	}
	

}
