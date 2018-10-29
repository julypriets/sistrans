package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de ABASTECIMIENTO en Superandes.
 * @author s.lemus
 *
 */
public class Abastecimiento implements VOAbastecimiento{

	/**
	 * Identificador del abastecimiento
	 */
	private long id;

	/**
	 * Identificador de la categoría del abastecimiento
	 */
	private long idCategoria;

	/**
	 * Identificador del estante que se abastecerá
	 */
	private long idEstante;

	/**
	 * Crea un nuevo abastecimiento
	 * @param id - identificador del abastecimiento
	 * @param idCategoria - identificador de la categoría del abastecimiento
	 * @param idEstante - identificador del estante que se abastecerá
	 */
	public Abastecimiento(long id, long idCategoria, long idEstante) {
		this.id = id;
		this.idCategoria = idCategoria;
		this.idEstante = idEstante;
	} 

	/**
	 * Constructor por defecto
	 */
	public Abastecimiento(){
		id = (long) 0;
		this.idCategoria = (long) 0;
		this.idEstante = (long) 0;
	}

	/**
	 * Obtiene el id del abastecimiento
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el id del abastecimiento
	 * @param id - nuevo id del abastecimiento
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador de la categoría del abastecimiento
	 */
	public long getIdCategoria() {
		return idCategoria;
	}

	/**
	 * Modifica el identificador de la categoría del abastecimiento
	 * @param idCategoria - nuevo identificador de la categoría del abastecimiento
	 */
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Obtiene el identificador del estante que se abastecerá
	 */
	public long getIdEstante() {
		return idEstante;
	}

	/**
	 * Modifica el identificador del estante que se abastecerá
	 * @param idEstante - el nuevo identificador del estante que se abastecerá
	 */
	public void setIdEstante(Long idEstante) {
		this.idEstante = idEstante;
	}

	@Override
	public String toString() {
		return "Abastecimiento [id=" + id + "]";
	}

}
