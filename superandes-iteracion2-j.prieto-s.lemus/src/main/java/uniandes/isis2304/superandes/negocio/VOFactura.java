package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de FACTURA 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOFactura {

	public long getId();

	public Double getPrecioTotal();

	public Timestamp getFecha();

	public long getIdCajero();

	public long getIdCliente();
	
	@Override
	public String toString();
}
