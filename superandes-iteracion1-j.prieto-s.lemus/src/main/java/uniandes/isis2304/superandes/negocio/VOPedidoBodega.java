package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de PEDIDO_BODEGA 
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * @author s.lemus
 *
 */
public interface VOPedidoBodega {

	public long getId();

	public long getIdCategoria();

	public long getIdEstante();
	
	@Override
	public String toString();
}
