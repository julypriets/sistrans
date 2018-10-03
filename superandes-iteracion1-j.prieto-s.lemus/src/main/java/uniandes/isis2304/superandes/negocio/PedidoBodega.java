package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de PEDIDO_BODEGA en Superandes.
 * @author s.lemus
 *
 */
public class PedidoBodega implements VOPedidoBodega{

	private long id;
	
	private long idCategoria;
	
	private long idEstante;

	public PedidoBodega(long id, long idCategoria, long idEstante) {
		this.id = id;
		this.idCategoria = idCategoria;
		this.idEstante = idEstante;
	} 
	
	public PedidoBodega(){
		id = (long) 0;
		this.idCategoria = (long) 0;
		this.idEstante = (long) 0;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public long getIdEstante() {
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
