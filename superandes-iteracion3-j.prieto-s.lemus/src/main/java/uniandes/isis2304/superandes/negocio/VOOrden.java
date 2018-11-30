package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de ORDEN 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOOrden {
	
	public long getId();

	public Double getPrecio();

	public Timestamp getFechaEsperada();

	public Timestamp getFechaLlegada();
	
	public String getEstado();

	public Double getCalificacion();
	
	public long getIdSucursal();

	@Override
	public String toString();
	
	
}
