package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public interface VOPromocion {

	public long getId();

	public int getTipo();

	public double getPrecio();

	public Timestamp getFechaInicio();

	public Timestamp getFechaFin();

	public int getCantidad1();

	public int getCantidad2();

	public double getDescuento();

	public long getIdSucural();

	public long getIdProducto();
	
	@Override
	public String toString();
	
}
