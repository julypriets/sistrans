package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de CAJERO 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOCajero {

	/**
	 * Obtiene el id del cajero
	 * @return
	 */
	public long getId();

	/**
	 * Obtiene el nombre del cajero
	 * @return
	 */
	public String getNombre();

	@Override
	public String toString();
}
