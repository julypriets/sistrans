package uniandes.isis2304.superandes.negocio;

/**
 * Clase que se encarga de modelar el concepto de PROVEEDOR en Superandes.
 * @author s.lemus
 *
 */
public class Proveedor implements VOProveedor{

	private String nit;
	
	private String nombre;
	
	private Double calificacion;

	/**
	 * Constructor del Proveedor con valores
	 * @param nit
	 * @param nombre
	 * @param calificacion
	 */
	public Proveedor(String nit, String nombre, Double calificacion) {
		this.nit = nit;
		this.nombre = nombre;
		this.calificacion = calificacion;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Proveedor(){
		nit = "";
		this.nombre = "";
		this.calificacion = 0.0;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
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
		return "Proveedor [NIT=" + nit + ", nombre=" + nombre + ", calificacion=" + calificacion + "]";
	}
	
	
}
