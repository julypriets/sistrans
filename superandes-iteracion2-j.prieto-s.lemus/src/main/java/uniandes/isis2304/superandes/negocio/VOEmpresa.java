package uniandes.isis2304.superandes.negocio;

public interface VOEmpresa {
	
	public String getNit();

	public String getDireccion();
	
	public String getNombre();
	
	public String getCorreo();

	@Override
	public String toString();
}
