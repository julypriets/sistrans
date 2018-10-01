package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de FACTURA 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOFactura {

	public Long getId();

	public Double getPrecioTotal();

	public Timestamp getFecha();

	@Override
	public String toString();
}
