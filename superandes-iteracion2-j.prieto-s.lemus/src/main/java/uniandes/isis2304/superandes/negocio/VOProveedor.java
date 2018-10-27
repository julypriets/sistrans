package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de PROVEEDOR 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOProveedor {

	public Integer getNIT();

	public String getNombre();

	public Double getCalificacion();

	@Override
	public String toString();
}
