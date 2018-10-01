package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de CATEGORIA en Superandes.
 * @author s.lemus
 *
 */
public class Categoria implements VOCategoria{
	
	private Long id;
	
	private String nombre;

	/**
	 * Constructor de Categoría por valores
	 * @param id
	 * @param nombre
	 */
	public Categoria(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Categoria(){
		
	}

	public Long getId() {
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
