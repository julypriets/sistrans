package uniandes.isis2304.superandes.interfaz;

import java.io.FileReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Estante;
import uniandes.isis2304.superandes.negocio.FacturaCliente;
import uniandes.isis2304.superandes.negocio.Superandes;

public class Prueba {
	
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_Superandes.json"; 
	
	private static Logger log = Logger.getLogger(Prueba.class.getName());
	
	private static JsonObject tableConfig;
	
    private static JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }

	public static void main(String[] args) {
		tableConfig = openConfig("Tablas BD", CONFIG_TABLAS);
		Superandes superandes = new Superandes (tableConfig);
//		
//		superandes.adicionarEmpresa("e4", "e4c", "1236", "cra4");
//		
//		List<Cliente> cs = superandes.darClientes();
//		System.out.println(cs.size());
//		
//		for(Cliente c : cs) {
//			System.out.println(c.toString());
//		}
		
//		System.out.println(superandes.darCarroPorCliente((long)835));
//		System.out.println(superandes.getPersistenciaSuperandes().darCantidadDeProducto(1584, "RO094VSGI981U"));
//		//superandes.getPersistenciaSuperandes().desocuparCarro(834);
//		List<Estante> estantes = superandes.getPersistenciaSuperandes().darEstantesPorCategoria(6);
//		for(Estante e : estantes){
//			System.out.println(e.toString());
//		}
		
//		Timestamp fechaInicioFormateada = null;
//		Timestamp fechaFinFormateada = null;
//		
//		try{
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Date parsedDateIni = dateFormat.parse("2000-01-01");
//			fechaInicioFormateada = new Timestamp(parsedDateIni.getTime());
//
//			Date parsedDateFin = dateFormat.parse("2000-11-01");
//			fechaFinFormateada = new Timestamp(parsedDateFin.getTime());
//			System.out.println(fechaInicioFormateada + ", " + fechaFinFormateada);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		List<FacturaCliente> fc = new LinkedList<>();
		List<FacturaCliente> fc1 = new LinkedList<>();
		try {
			fc = superandes.clientesQueCompraronElProductoPorRangoFecha("2001-01-01", "2001-11-01", "nombre", "Bread - Olive", 0);
			//fc1 = superandes.clientesQueNoCompraronElProductoPorRangoFecha("2001-01-01", "2001-11-01", "nombre", "Bread - Olive", 0);
		}catch (Exception e) {
			e.getStackTrace();
		}
		for(FacturaCliente f : fc) {
			System.out.println(f);
		}
		
		for(FacturaCliente f : fc1) {
			System.out.println(f);
		}
	}
}
