package uniandes.isis2304.superandes.negocio;

public class Carrito implements VOCarrito {
	
	/**
	 * Identificador del carrito
	 */
	private long id;
	
	/**
	 * Identificador del cliente que tiene el carrito
	 */
	private long idCliente;
	
	/**
	 * Estado actual del carrito
	 */
	private String estado;
	
	//Constantes
	
	/**
	 * Constante que indica el estado ocupado del carro de compras
	 */
	private static final String OCUPADO = "OCUPADO";
	
	/**
	 * Constante que indica el estado abandonado del carro de compras
	 */
	private static final String ABANDONADO = "ABANDONADO";
	
	/**
	 * Constante que indica el estado desocupado del carro de compras
	 */
	private static final String DESOCUPADO = "DESOCUPADO";		

	/**
	 * Crea un nuevo carrito
	 * @param id - id del carrito 
	 * @param idCliente - id del cliente que tiene el carrito
	 * @param estado - estado del carrito
	 */
	public Carrito(long id, long idCliente, String estado) {
		this.id = id;
		this.idCliente = idCliente;
		this.estado = estado;
	}
	
	/**
	 * Constuctor por defecto
	 */
	public Carrito() {
		this.id = 0;
		this.idCliente = (long)0;
		this.estado = "";
	}
	
	/**
	 * Obtiene el id del carrito
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el id del carrito
	 * @param id - nuevo id del carrito
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el id del cliente que tiene el carrito
	 */
	public long getIdCliente() {
		return idCliente;
	}

	/**
	 * Modifica el id del cliente que tiene el carrito
	 * @param idCliente - nuevo id del cliente que tiene el carrito
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente == null ? 0 : (long) idCliente;
	}

	/**
	 * Obtiene el estado del carrito
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Modifica el estado del carrito
	 * @param estado - nuevo estado del carrito
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Carrito [id=" + id + ", idCliente=" + idCliente + ", estado=" + estado + "]";
	}
	
	
		
}
