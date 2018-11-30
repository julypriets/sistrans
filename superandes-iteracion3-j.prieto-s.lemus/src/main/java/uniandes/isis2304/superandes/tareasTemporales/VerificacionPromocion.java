package uniandes.isis2304.superandes.tareasTemporales;

import java.util.Timer;
import java.util.TimerTask;

import uniandes.isis2304.superandes.interfaz.InterfazSuperandes;

public class VerificacionPromocion extends TimerTask{
	
	private InterfazSuperandes interfaz;
	private int millisRepeticion;
	
	public static void intervaloLimpiezaPromocion(InterfazSuperandes interfaz, int millis) {
    	new Timer().schedule(new VerificacionPromocion(interfaz, millis), millis);
    }
	
	public VerificacionPromocion (InterfazSuperandes interfaz, int millisRepeticion) {
		this.interfaz = interfaz;
		this.millisRepeticion = millisRepeticion;
	}

	@Override
	public void run() {
		this.interfaz.limpiarPromociones();
		intervaloLimpiezaPromocion(interfaz, this.millisRepeticion);
	}
	
}
