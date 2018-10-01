package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de PROVEEDOR en Superandes.
 * @author s.lemus
 *
 */
public class Proveedor implements VOProveedor{

	private Integer NIT;
	
	private String nombre;
	
	private Double calificacion;

	/**
	 * Constructor del Proveedor con valores
	 * @param nIT
	 * @param nombre
	 * @param calificacion
	 */
	public Proveedor(Integer nIT, String nombre, Double calificacion) {
		NIT = nIT;
		this.nombre = nombre;
		this.calificacion = calificacion;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Proveedor(){
		NIT = 0;
		this.nombre = "";
		this.calificacion = 0.0;
	}

	public Integer getNIT() {
		return NIT;
	}

	public void setNIT(Integer nIT) {
		NIT = nIT;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	@Override
	public String toString() {
		return "Proveedor [NIT=" + NIT + ", nombre=" + nombre + ", calificacion=" + calificacion + "]";
	}
	
	
}
