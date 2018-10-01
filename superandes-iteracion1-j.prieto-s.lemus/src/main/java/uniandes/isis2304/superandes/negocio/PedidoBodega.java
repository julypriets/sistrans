package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de PEDIDO_BODEGA en Superandes.
 * @author s.lemus
 *
 */
public class PedidoBodega implements VOPedidoBodega{

	private Long id;

	public PedidoBodega(Long id) {
		this.id = id;
	} 
	
	public PedidoBodega(){
		id = (long) 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PedidoBodega [id=" + id + "]";
	}
	
	
}
