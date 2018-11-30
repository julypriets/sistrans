package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que representa un proveedor con solicitudes máximas o mínimas durante una semana
 * Ayuda al requerimiento RFC12. La clase especifíca si el proveedor corresponde a uno con
 * solicitudes máximas o con solicitudes mínimas en la semana
 * @author sebastian
 *
 */
public class ProveedorPorSemana {

	/**
	 * El nombre del proveedor
	 */
	public String nombre;
	
	/**
	 * El identificador del proveedor
	 */
	public String nit;
	
	/**
	 * La fecha de la semana correspondiente
	 */
	public Timestamp fechaSemana;
	
	/**
	 * Número de órdenes máximas del proveedor en la semana,
	 * si el proveedor no es uno de solicitudes máximas, el atributo es cero
	 */
	public double solicitudesMaximas;
	
	/**
	 * Número de órdenes mínimas del proveedor en la semana,
	 * si el proveedor no es uno de solicitudes mínimas, el atributo es cero
	 */
	public double solicitudesMinimas;
	
	/**
	 * Atributo que determina si el proveedor es de solicitudes máximas o mínimas
	 */
	public String tipo;
	
	// Constantes para determinar si el proveedor es de solicitudes máximas o mínimas

	public static String MAXIMAL = "MAXIMAL";
	
	public static String MINIMAL = "MINIMAL";
	
	/**
	 * Constructor vacío
	 */
	public ProveedorPorSemana() {
		
	}

	/**
	 * Crea una instancia de ProveedorPorSemana sin las solicitudes máximas o mínimas
	 * @param nombre
	 * @param nit
	 * @param fechaSemana
	 */
	public ProveedorPorSemana(String nombre, String nit, Timestamp fechaSemana) {
		super();
		this.nombre = nombre;
		this.nit = nit;
		this.fechaSemana = fechaSemana;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public Timestamp getFechaSemana() {
		return fechaSemana;
	}

	public void setFechaSemana(Timestamp fechaSemana) {
		this.fechaSemana = fechaSemana;
	}

	public double getSolicitudesMaximas() {
		return solicitudesMaximas;
	}

	public void setSolicitudesMaximas(double solicitudesMaximas) {
		this.solicitudesMaximas = solicitudesMaximas;
	}

	public double getSolicitudesMinimas() {
		return solicitudesMinimas;
	}

	public void setSolicitudesMinimas(double solicitudesMinimas) {
		this.solicitudesMinimas = solicitudesMinimas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		String msg = "ProveedorPorSemana [nombre=" + nombre + ", nit=" + nit + ", fechaSemana=" + fechaSemana;
		
		if(tipo.equals(MAXIMAL)) {
			msg += ", solicitudesMaximas=" + solicitudesMaximas + "]";
		}else {
			msg += ", solicitudesMinimas=" + solicitudesMinimas + "]";
		}
		
		return msg;
	}
	
	
	
	
}
