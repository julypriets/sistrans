package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public class Promocion implements VOPromocion{
	
	/**
	 * Id de la promoción
	 */
	private long id;
	
	/**
	 * Tipo de la promoción
	 */
	private int tipo;
	
	/**
	 * Precio total al aplicar el descuento
	 */
	private double precio;
	
	/**
	 * Inicio de la promoción
	 */
	private Timestamp fechaInicio;
	
	/**
	 * Final de la promoción
	 */
	private Timestamp fechaFin;
	
	/**
	 * Cantidad del producto 1 (usado para descuentos "pague n unidades lleve m" o "Pague x cantidad lleve y" )
	 */
	private int cantidad1;
	
	/**
	 * Cantidad del producto 2 (usado para descuentos "pague n unidades lleve m" o "Pague x cantidad lleve y" )
	 */
	private int cantidad2;
	
	/**
	 * Cantidad de descuento resultante al aplicar algún tipo de promoción
	 */
	private int descuento;
	
	/**
	 * Sucursal a la que pertenece
	 */
	private long idSucural;
	
	/**
	 * El producto al cual pertenece el descuento
	 */
	private String idProducto;
	
	// Las siguientes constantes representan el tipo de descuento:
	
	/**
	 * Representa la promoción "pague n unidades lleve m"
	 */
	public static final int N_UNI_M = 0;
	
	/**
	 * Representa la promoción de aplicar un porcentaje de descuento al 
	 * precio total
	 */
	public static final int DESC_PORCENTAJE = 1;
	
	/**
	 * Representa la promoción "pague x cantidad y lleve y"
	 */
	public static final int X_CANT_Y = 2;

	/**
	 * Representa la promoción "pague 1 y lleve el segundo por algún porcentaje del precio"
	 */
	public static final int DESC_PORCENTAJE_COMPRA = 3;
	
	/**
	 * Representa la promoción de un paquete de productos a menor precio
	 * que la suma total de la compra individual de dichos productos
	 */
	public static final int PAQUETE_PRODUCTOS = 4;
	
	
	/**
	 * Creación de una promoción dados los valores
	 * @param id
	 * @param tipo
	 * @param precio
	 * @param fechaInicio
	 * @param fechaFin
	 * @param cantidad1
	 * @param cantidad2
	 * @param descuento
	 * @param idSucural
	 * @param idProducto
	 */
	public Promocion(long id, int tipo, double precio, Timestamp fechaInicio, Timestamp fechaFin, int cantidad1,
			int cantidad2, int descuento, long idSucural, String idProducto) {
		this.id = id;
		this.tipo = tipo;
		this.precio = precio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.descuento = descuento;
		this.idSucural = idSucural;
		this.idProducto = idProducto;
	}
	
	/**
	 * Constructor por defecto
	 */
	public Promocion() {
		this.id = 0;
		this.tipo = 0;
		this.precio = 0.0;
		this.fechaInicio = new Timestamp(0);
		this.fechaFin = new Timestamp(0);
		this.cantidad1 = 0;
		this.cantidad2 = 0;
		this.descuento = 0;
		this.idSucural = 0;
		this.idProducto = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getCantidad1() {
		return cantidad1;
	}

	public void setCantidad1(int cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public int getCantidad2() {
		return cantidad2;
	}

	public void setCantidad2(int cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public long getIdSucural() {
		return idSucural;
	}

	public void setIdSucural(long idSucural) {
		this.idSucural = idSucural;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public String toString() {
		return "Promocion [id=" + id + ", tipo=" + tipo + ", precio=" + precio + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", cantidad1=" + cantidad1 + ", cantidad2=" + cantidad2 + ", descuento="
				+ descuento + ", idSucural=" + idSucural + ", idProducto=" + idProducto + "]";
	}
	

	
	
}
