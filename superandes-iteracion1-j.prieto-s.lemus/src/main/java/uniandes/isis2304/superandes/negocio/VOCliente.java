package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de CLIENTE 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOCliente {

	public String getNombre();

	public String getCorreo();
	
	public long getId();

	public String getTipo();

	@Override
	public String toString();
	
}
