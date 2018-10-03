package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de CAJERO 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOCajero {

	public long getId();

	public String getNombre();

	@Override
	public String toString();
}
