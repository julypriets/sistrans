package uniandes.isis2304.superandes.tareasTemporales;

import java.util.Timer;
import java.util.TimerTask;

import uniandes.isis2304.superandes.interfaz.InterfazSuperandes;

public class RecoleccionProductosAbandonados extends TimerTask{

	private InterfazSuperandes interfaz;
	private int millisRepeticion;
	
	public static void intervaloRecoleccionProductosAbandonados(InterfazSuperandes interfaz, int millis) {
    	new Timer().schedule(new RecoleccionProductosAbandonados(interfaz, millis), millis);
    }

	public RecoleccionProductosAbandonados(InterfazSuperandes interfaz, int millisRepeticion) {
		this.interfaz = interfaz;
		this.millisRepeticion = millisRepeticion;
	}
	
	public void run() {
		this.interfaz.recolectarProductosAbandonados();
		intervaloRecoleccionProductosAbandonados(interfaz, this.millisRepeticion);
	}
	
	
}
