package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de VISITAN 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOSucursal {

	/**
	 * 
	 * @return El id de la sucursal
	 */
	public long getId();

	/**
	 * 
	 * @return nombre de la sucursal
	 */
	public String getNombre();

	/**
	 * 
	 * @return ciudad de la sucursal
	 */
	public String getCiudad();

	/**
	 * 
	 * @return dirección de la sucursal
	 */
	public String getDireccion();
	
	/**
	 * Retorna una cadena de caractéres con la información básica
	 * de la sucursal
	 */
	@Override
	public String toString();

}
