import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.negocio.Carrito;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.Superandes;

public class ReqIteracion2Test {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(ReqIteracion2Test.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_SUPERANDES = "./src/main/resources/config/TablasBD_Superandes.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    private Superandes superandes;
    
    /* ****************************************************************
	 * 			Métodos de prueba Iteración 2
	 *****************************************************************/
    
    /**
     * Prueba RF12
     */
    @Test
    public void solicitarCarroTest(){
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre solicitarCarroTest");
			superandes = new Superandes(openConfig(CONFIG_TABLAS_SUPERANDES));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba solicitarCarroTest. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba solicitarCarroTest incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		// Cliente número 834 con identificador 7970484561
    		Carrito cSolicitado = superandes.solicitarCarro(834);
    		long cEntregado = superandes.darCarroPorCliente(834);
    		assertEquals("El identificador del carro solicitado no es correcto", cSolicitado.getId(), cEntregado);
    		
    		// Desocupamos carro sin productos
    		Carrito cDesocupado = superandes.getPersistenciaSuperandes().desocuparCarro(834);
    		assertEquals("El carro debería estar en estado DESOCUPADO", "DESOCUPADO", cDesocupado.getEstado());
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones en solicitarCarroTest.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas en solicitarCarroTest");
		}
		finally
		{
			//superandes.limpiarSuperandes ();
    		superandes.cerrarUnidadPersistencia ();    		
		}
    }
    
    /**
     * Prueba insertar productos
     */
    @Test
    public void testInsertarProductos(){
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre solicitarCarroTest");
			superandes = new Superandes(openConfig(CONFIG_TABLAS_SUPERANDES));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba solicitarCarroTest. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba solicitarCarroTest incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		// insertar en el carro
    		Carrito cSolicitado = superandes.solicitarCarro(834);
    		
    		int cantidadInicialMustard = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("D8548PO84W64M", 802);
    		int cantidadInicialClams = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("6XT34L0Q9X3EX", 800);
    		
    		superandes.insertarProductoAlCarro(cSolicitado.getId(), "Mustard - Pommery", 1, 802, 834);
    		superandes.insertarProductoAlCarro(cSolicitado.getId(), "Clams - Littleneck, Whole", 3, 800, 834);
    		
    		int cantidadEnCarroMustard = superandes.darCantidadDeProducto(cSolicitado.getId(), "D8548PO84W64M");
    		int cantidadEnCarroClams = superandes.darCantidadDeProducto(cSolicitado.getId(), "6XT34L0Q9X3EX");
    		
    		int cantidadFinalMustard = cantidadInicialMustard - cantidadEnCarroMustard;
    		int cantidadFinalClams = cantidadInicialClams - cantidadEnCarroClams;
    		
    		int cantidadFinalEsperadaMustard = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("D8548PO84W64M", 802);
    		int cantidadFinalEsperadaClams = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("6XT34L0Q9X3EX", 800);
    		
    		List<Producto> productos = superandes.darProductosEnCarro(cSolicitado.getId());
    		//assertEquals("El número de productos insertados no es correcto", 4, productos.size());
    		
    		assertEquals("No se actualizó correctamente el inventario del estante", cantidadFinalEsperadaClams, cantidadFinalClams);
    		assertEquals("No se actualizó correctamente el inventario del estante", cantidadFinalEsperadaMustard, cantidadFinalMustard);
    		
    		superandes.abandonarCarro(834);
    		superandes.recolectarProductosAbandonados();
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones en solicitarCarroTest.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas en solicitarCarroTest");
		}
		finally
		{
			//superandes.limpiarSuperandes ();
    		superandes.cerrarUnidadPersistencia ();    		
		}
    }
    
    @Test
    public void abandonarCarroComprasTest(){
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre solicitarCarroTest");
			superandes = new Superandes(openConfig(CONFIG_TABLAS_SUPERANDES));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba solicitarCarroTest. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba solicitarCarroTest incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		Carrito cSolicitado1 = superandes.solicitarCarro(834);
    		Carrito cSolicitado2 = superandes.solicitarCarro(831);
    		Carrito cSolicitado3 = superandes.solicitarCarro(832);
    		
    		Carrito cAbandonado1 = superandes.abandonarCarro(834);
    		Carrito cAbandonado2 = superandes.abandonarCarro(831);
    		
    		assertEquals("El carro no fue abandonado correctamente", "ABANDONADO", cAbandonado1.getEstado());
    		assertEquals("El carro no fue abandonado correctamente", "ABANDONADO", cAbandonado2.getEstado());
    		
    		// insertamos productos en carro no abandonado
    		
    		superandes.insertarProductoAlCarro(cSolicitado3.getId(), "Mustard - Pommery", 1, 802, 834);
    		superandes.insertarProductoAlCarro(cSolicitado3.getId(), "Clams - Littleneck, Whole", 3, 800, 834);
    		
    		// Abandonamos carro con productos
    		Carrito cAbandonado3 = superandes.abandonarCarro(832);
    		assertEquals("El carro no fue abandonado correctamente", "ABANDONADO", cAbandonado3.getEstado());
    		
    		superandes.recolectarProductosAbandonados();
    		
    		long id = superandes.darCarroPorCliente(831);
    		assertEquals("No debería existir ninguna tupla con el carro en la tabla CARRITO", -1, id);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones en solicitarCarroTest.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas en solicitarCarroTest");
		}
		finally
		{
			//superandes.limpiarSuperandes ();
    		superandes.cerrarUnidadPersistencia ();    		
		}
    }
    
    //@Test
    public void devolverProductosTest(){
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre solicitarCarroTest");
			superandes = new Superandes(openConfig(CONFIG_TABLAS_SUPERANDES));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba solicitarCarroTest. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba solicitarCarroTest incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		// insertar en el carro
    		Carrito cSolicitado = superandes.solicitarCarro(834);
    		
    		int cantidadInicialMustard = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("D8548PO84W64M", 802);
    		int cantidadInicialClams = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("6XT34L0Q9X3EX", 800);
    		
    		superandes.insertarProductoAlCarro(cSolicitado.getId(), "Mustard - Pommery", 1, 802, 834);
    		superandes.insertarProductoAlCarro(cSolicitado.getId(), "Clams - Littleneck, Whole", 3, 800, 834);
    		
    		int cantidadEnCarroMustard = superandes.darCantidadDeProducto(cSolicitado.getId(), "D8548PO84W64M");
    		int cantidadEnCarroClams = superandes.darCantidadDeProducto(cSolicitado.getId(), "6XT34L0Q9X3EX");
    		
    		int cantidadFinalMustard = cantidadInicialMustard - cantidadEnCarroMustard;
    		int cantidadFinalClams = cantidadInicialClams - cantidadEnCarroClams;
    		
    		int cantidadFinalEsperadaMustard = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("D8548PO84W64M", 802);
    		int cantidadFinalEsperadaClams = superandes.getPersistenciaSuperandes().darCantidadProductoPorEstante("6XT34L0Q9X3EX", 800);
    		
    		
    		assertEquals("No se actualizó correctamente el inventario del estante", cantidadFinalEsperadaClams, cantidadFinalClams);
    		assertEquals("No se actualizó correctamente el inventario del estante", cantidadFinalEsperadaMustard, cantidadFinalMustard);
    		
    		superandes.devolverProductoDelCarro(cSolicitado.getId(), "Mustard - Pommery", 1, 834);
    		
    		int cantidadMustard = superandes.getPersistenciaSuperandes().darCantidadDeProducto(cSolicitado.getId(), "D8548PO84W64M");
    		
    		assertEquals("No se devolvió el producto en cuestión", 0, cantidadMustard);
    		
    		superandes.devolverProductoDelCarro(cSolicitado.getId(), "Clams - Littleneck, Whole", 2, 834);
    		
    		int cantidadClams = superandes.getPersistenciaSuperandes().darCantidadDeProducto(cSolicitado.getId(), "D8548PO84W64M");
    		
    		assertEquals("No se devolvió el producto en cuestión", 1, cantidadClams);
    		
    		superandes.devolverProductoDelCarro(cSolicitado.getId(), "Clams - Littleneck, Whole", 1, 834);
    		
    		superandes.abandonarCarro(834);
    		superandes.recolectarProductosAbandonados();
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones en solicitarCarroTest.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas en solicitarCarroTest");
		}
		finally
		{
			//superandes.limpiarSuperandes ();
    		superandes.cerrarUnidadPersistencia ();    		
		}
    }
    
    
	/* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
