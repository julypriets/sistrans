package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de USUARIO 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOUsuario {

	public String getNombre();

	public String getUsername();

	public String getPassword();
	
	public String getTipo();

	public long getIdSucursal();

	@Override
	public String toString();
}
