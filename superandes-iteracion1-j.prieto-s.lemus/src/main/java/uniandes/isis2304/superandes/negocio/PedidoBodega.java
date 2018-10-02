package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de PEDIDO_BODEGA en Superandes.
 * @author s.lemus
 *
 */
public class PedidoBodega implements VOPedidoBodega{

	private Long id;
	
	private Long idCategoria;
	
	private Long idEstante;

	public PedidoBodega(Long id, Long idCategoria, Long idEstante) {
		this.id = id;
		this.idCategoria = idCategoria;
		this.idEstante = idEstante;
	} 
	
	public PedidoBodega(){
		id = (long) 0;
		this.idCategoria = (long) 0;
		this.idEstante = (long) 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getIdEstante() {
		return idEstante;
	}

	public void setIdEstante(Long idEstante) {
		this.idEstante = idEstante;
	}

	@Override
	public String toString() {
		return "PedidoBodega [id=" + id + "]";
	}
	
	
}
