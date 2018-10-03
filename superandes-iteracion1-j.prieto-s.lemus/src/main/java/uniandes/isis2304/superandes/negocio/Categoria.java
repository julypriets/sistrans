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

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}

}
