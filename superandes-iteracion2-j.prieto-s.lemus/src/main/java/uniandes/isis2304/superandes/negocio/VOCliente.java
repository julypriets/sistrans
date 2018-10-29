package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de CLIENTE 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOCliente {

	/**
	 * Obtiene el nombre del cliente
	 * @return - nombre del cliente
	 */
	public String getNombre();

	/**
	 * Obtiene el correo del cliente
	 * @return - correo del cliente
	 */
	public String getCorreo();
	
	/**
	 * Obtiene el identificador del cliente
	 * @return - identificador del cliente
	 */
	public long getId();

	/**
	 * Obtiene el tipo de cliente
	 * @return
	 */
	public String getTipo();

	@Override
	public String toString();
	
}
