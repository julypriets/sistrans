package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase que se encarga de modelar el concepto de PERECEDERO en Superandes.
 * @author s.lemus
 *
 */
public class Perecedero extends Categoria{
	
	private Timestamp fechaVencimiento;

	public Perecedero(Long id, String nombre, Timestamp fechaVencimiento) {
		super(id, nombre);
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public Perecedero(){
		super();
		fechaVencimiento = new Timestamp(0);
	}

	public Timestamp getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	@Override
	public String toString() {
		return "Perecedero [fechaVencimiento=" + fechaVencimiento + "]";
	}
	
	

}
