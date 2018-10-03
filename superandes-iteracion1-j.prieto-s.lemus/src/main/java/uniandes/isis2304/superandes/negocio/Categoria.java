package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de CATEGORIA en Superandes.
 * @author s.lemus
 *
 */
public class Categoria implements VOCategoria{
	
	private long id;
	
	private String nombre;

	/**
	 * Constructor de Categoría por valores
	 * @param id
	 * @param nombre
	 */
	public Categoria(long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Categoria(){
		this.id = 0;
		this.nombre = "";
	}

	/**
	 * Obtiene el identificador de la categoría
	 */
	public long getId() {
		return id;
	}

	/**
	 * Modifica el identificador de la categoría
	 * @param id - nuevo identificador de la categoría
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de la categoría
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la categoría
	 * @param nombre - nuevo nombre de la categoría
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}

}
