package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que representa un producto con ventas máximas o mínimas durante una semana
 * Ayuda al requerimiento RFC12. La clase especifíca si el producto corresponde a uno con
 * ventas máximas o con ventas mínimas en la semana
 * @author sebastian
 *
 */
public class ProductoPorSemana {

	/**
	 * El nombre del producto
	 */
	public String nombre;
	
	/**
	 * El identificador del producto
	 */
	public String codigo_barras;
	
	/**
	 * La fecha de la semana correspondiente
	 */
	public Timestamp fechaSemana;
	
	/**
	 * Número de ventas máximas del producto en la semana,
	 * si el producto no es un producto de ventas máximas, el atributo es cero
	 */
	public double ventasMaximas;
	
	/**
	 * Número de ventas mínimas del producto en la semana,
	 * si el producto no es un producto de ventas mínimas, el atributo es cero
	 */
	public double ventasMinimas;
	
	/**
	 * Atributo que determina si el producto es de ventas máximas o mínimas
	 */
	public String tipo;
	
	// Constantes para determinar si el producto es de ventas máximas o mínimas

	public static String MAXIMAL = "MAXIMAL";
	
	public static String MINIMAL = "MINIMAL";
	
	/**
	 * Constructor vacío
	 */
	public ProductoPorSemana() {
		
	}

	/**
	 * Crea una instancia sin el número de ventas máximas o mínimas 
	 * @param nombre
	 * @param codigo_barras
	 * @param fechaSemana
	 * @param tipo
	 */
	public ProductoPorSemana(String nombre, String codigo_barras, Timestamp fechaSemana) {
		super();
		this.nombre = nombre;
		this.codigo_barras = codigo_barras;
		this.fechaSemana = fechaSemana;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo_barras() {
		return codigo_barras;
	}

	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras;
	}

	public Timestamp getFechaSemana() {
		return fechaSemana;
	}

	public void setFechaSemana(Timestamp fechaSemana) {
		this.fechaSemana = fechaSemana;
	}

	public double getVentasMaximas() {
		return ventasMaximas;
	}

	public void setVentasMaximas(double ventasMaximas) {
		this.ventasMaximas = ventasMaximas;
	}

	public double getVentasMinimas() {
		return ventasMinimas;
	}

	public void setVentasMinimas(double ventasMinimas) {
		this.ventasMinimas = ventasMinimas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		
		String msg = "ProductoPorSemana [nombre=" + nombre + ", codigo_barras=" + codigo_barras + ", fechaSemana="
				+ fechaSemana;
		
		if(tipo.equals(MAXIMAL)) {
			msg += ", ventasMaximas=" + ventasMaximas + " ]";
		}else {
			msg += ", ventasMinimas=" + ventasMinimas + " ]";
		}
		
		return msg;
	}

	
}
