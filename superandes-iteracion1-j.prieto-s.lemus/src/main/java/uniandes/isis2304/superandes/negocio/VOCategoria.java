package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de CATEGORIA 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOCategoria {

	public Long getId();

	public String getNombre();

	@Override
	public String toString();
}
