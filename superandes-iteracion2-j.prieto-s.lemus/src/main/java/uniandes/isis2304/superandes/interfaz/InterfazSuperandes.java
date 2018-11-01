/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.superandes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.interfaz.PanelDatos;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.ComprasPorPromocion;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.Superandes;
import uniandes.isis2304.superandes.negocio.VOBebedor;
import uniandes.isis2304.superandes.negocio.VOBodega;
import uniandes.isis2304.superandes.negocio.VOCliente;
import uniandes.isis2304.superandes.negocio.VOEmpresa;
import uniandes.isis2304.superandes.negocio.VOEstante;
import uniandes.isis2304.superandes.negocio.VOOrden;
import uniandes.isis2304.superandes.negocio.VOProducto;
import uniandes.isis2304.superandes.negocio.VOPromocion;
import uniandes.isis2304.superandes.negocio.VOProveedor;
import uniandes.isis2304.superandes.negocio.VOSucursal;
import uniandes.isis2304.superandes.tareasTemporales.VerificacionPromocion;


/**
 * Clase principal de la interfaz
 * 
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazSuperandes extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazSuperandes.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigDemo.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_Superandes.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private Superandes superandes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;
    
    /**
     * El cliente que inició sesión en la aplicación
     */
    private VOCliente loggedClient;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazSuperandes( )
    {
    	loggedClient = null;
    	
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        superandes = new Superandes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );
        //VerificacionPromocion.intervaloLimpiezaPromocion(this, 10000);
    }
    
	/* ****************************************************************
	 * 			Métodos para la configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
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
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			Operaciones PRODUCTO
	 *****************************************************************/

    /**
     * (RF2) Registra un producto de acuerdo a los valores dados por el usuario
     */
    public void registrarProducto( )
    {
    	try 
    	{
    		//String nombreTipo = JOptionPane.showInputDialog (this, "Nombre del tipo de bedida?", "Adicionar tipo de bebida", JOptionPane.QUESTION_MESSAGE);
    		JTextField nombre = new JTextField();
    		JTextField marca = new JTextField();
    		JTextField precioUnitario = new JTextField();
    		JTextField presentacion = new JTextField();
    		JTextField precioUnidadMedida = new JTextField();
    		JTextField unidadMedida = new JTextField();
    		JTextField empacado = new JTextField();
    		JTextField codigoBarras = new JTextField();
    		JTextField nivelReorden = new JTextField();
    		JTextField existencias = new JTextField();
    		JTextField idCategoria = new JTextField();
    		JTextField idSucursal = new JTextField();
    		JTextField fechaVencimiento = new JTextField();
    		Object[] message = {
    		    "nombre:", nombre,
    		    "marca:", marca,
    		    "precio unitario:", precioUnitario,
    		    "presentacion:", presentacion,
    		    "precioUnidadMedida:", precioUnidadMedida,
    		    "unidadMedida:", unidadMedida,
    		    "empacado:", empacado,
    		    "codigoBarras:", codigoBarras,
    		    "nivelReorden:", nivelReorden,
    		    "existencias:", existencias,
    		    "idCategoria:", idCategoria,
    		    "idSucursal:", idSucursal,
    		    "fechaVencimiento (debe estar en formato año/mes/día: yyyy/MM/dd ):", fechaVencimiento,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Crear el producto ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		String nombreResp = nombre.getText();
        		String marcaResp = marca.getText();
        		Double precioUnitarioResp = Double.parseDouble(precioUnitario.getText());
        		String presentacionResp = presentacion.getText();
        		Double precioUnidadMedidaResp = Double.parseDouble(precioUnidadMedida.getText());
        		String unidadMedidaResp = unidadMedida.getText();
        		String empacadoResp = empacado.getText();
        		String codigoBarrasResp = codigoBarras.getText();
        		Integer nivelReordenResp = Integer.parseInt(nivelReorden.getText());
        		Integer existenciasResp = Integer.parseInt(existencias.getText());
        		Long idCategoriaResp = Long.parseLong(idCategoria.getText());
        		Long idSucursalResp = Long.parseLong(idSucursal.getText());
        		String fechaVencimientoResp = fechaVencimiento.getText();
        		
        		VOProducto p = superandes.registrarProducto(nombreResp, marcaResp, precioUnitarioResp, presentacionResp, precioUnidadMedidaResp, unidadMedidaResp, empacadoResp, 
        				codigoBarrasResp, nivelReordenResp, existenciasResp, idCategoriaResp, idSucursalResp, fechaVencimientoResp);
        		String resultado = "En adicionarProducto\n\n";
        		resultado += "Producto adicionado exitosamente: " + p;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Muestra todos los productos existentes
     */
    public void darProductos() {
    	String resultado = "En darProductos\n\n";
    	List<Producto> ps = superandes.darProductos();
    	
    	if(ps.size() == 0){
    		resultado += "No hay productos disponibles\n\n";
    	}else{
        	for(Producto p : ps) {
        		resultado += p.toString() + "\n";
        	}
    	}
    	resultado += "Terminó proceso";
    	panelDatos.actualizarInterfaz(resultado);
    	
    }
    
    /**
     * Muestra todos los productos asociados al nombre entregado por el usuario
     */
    public void darProductosPorNombre() {
    	
    	try 
    	{
    		JTextField nombre = new JTextField();

    		Object[] message = {
    		    "Inserte el nombre del producto:", nombre,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Buscar productos por nombre", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		String nombreResp = nombre.getText();
        		
        		List<Producto> ps = superandes.darProductosPorNombre(nombreResp);
            	String resultado = "En darProductosPorNombre\n\n";
            	
            	if(ps.size() == 0){
            		resultado += "No hay productos disponibles\n\n";
            	}else{
                	for(Producto p : ps) {
                		resultado += "Producto: " + p.toString() + "\n";
                	}
            	}
            	resultado += "Terminó proceso";
            	panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    	

    	
    }
    
    /**
     * Muestra el producto asociado al id entregado por el usuario
     */
    public void darProductoPorId(){
    	try 
    	{
    		JTextField codigoBarras = new JTextField();

    		Object[] message = {
    		    "Ingrese el código de barras:", codigoBarras,

    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Encuentre el producto por su código de barras", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		String codigoBarrasResp = codigoBarras.getText();
        		
        		VOProducto p = superandes.darProductoPorId(codigoBarrasResp);
        		String resultado = "En darProductoPorId\n\n";
        		resultado += "Producto encontrado exitosamente: " + p;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
	/**
	 * (RFC4)Retorna los productos dado un rango de precios unitarios
	 * @param p1 - límite inferior
	 * @param p2 - límite superior
	 * @return
	 */
	public void darProductosPorRangoDePrecioUnitario() {
//		
//		JTextField p1 = new JTextField();
//		JTextField p2 = new JTextField();
//		Object[] message = {
//		    "Límite inferior:", p1,
//		    "Límite superior:", p2,
//		};
//		int option = JOptionPane.showConfirmDialog(this, message, "Obtener los productos por rango de precio: ", JOptionPane.OK_CANCEL_OPTION);
//		if (option == JOptionPane.OK_OPTION)
//		{
//
//    		double p1Resp = Double.parseDouble(p1.getText());
//    		double p2Resp = Double.parseDouble(p2.getText());
//
//    		
//        	String resultado = "En darProductosPorRangoDePrecioUnitario\n\n";
//        	List<Producto> ps = superandes.darProductosPorRangoDePrecioUnitario(p1Resp, p2Resp);
//        	
//        	for(Producto p : ps) {
//        		resultado += "Producto: " + p.toString() + "\n";
//        	}
//        	resultado += "Terminó proceso";
//        	panelDatos.actualizarInterfaz(resultado);
//		}else {
//			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//		}
//		
	}
	
	/**
	 * (RFC4)Retorna los productos dado un rango de precios por unidad de medida
	 * @param p1 - límite superior
	 * @param p2 - límite inferior
	 * @return
	 */
	public void darProductosPorRangoDePrecioUM() {
		
//		JTextField p1 = new JTextField();
//		JTextField p2 = new JTextField();
//		Object[] message = {
//		    "Límite inferior:", p1,
//		    "Límite superior:", p2,
//		};
//		int option = JOptionPane.showConfirmDialog(this, message, "Obtener los productos por rango de precio: ", JOptionPane.OK_CANCEL_OPTION);
//		if (option == JOptionPane.OK_OPTION)
//		{
//
//    		double p1Resp = Double.parseDouble(p1.getText());
//    		double p2Resp = Double.parseDouble(p2.getText());
//
//    		
//        	String resultado = "En darProductosPorRangoDePrecioUM\n\n";
//        	List<Producto> ps = superandes.darProductosPorRangoDePrecioUM(p1Resp, p2Resp);
//        	
//        	for(Producto p : ps) {
//        		resultado += "Producto: " + p.toString() + "\n";
//        	}
//        	resultado += "Terminó proceso";
//        	panelDatos.actualizarInterfaz(resultado);
//		}else {
//			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//		}
//		
	}
	
	/**
	 * (RFC4)Retorna los productos de una sucursal dada
	 * @param idSucursal
	 * @return
	 */
	public void darProductosPorSucursal(){
		
//		JTextField idSucursal = new JTextField();
//		Object[] message = {
//		    "idSucursal", idSucursal,
//		};
//		int option = JOptionPane.showConfirmDialog(this, message, "Obtener los productos por sucursal: ", JOptionPane.OK_CANCEL_OPTION);
//		if (option == JOptionPane.OK_OPTION)
//		{
//
//    		long p1Resp = Long.parseLong(idSucursal.getText());
//    		
//        	String resultado = "En darProductosPorSucursal\n\n";
//        	List<Producto> ps = superandes.darProductosPorSucursal(p1Resp);
//        	
//        	for(Producto p : ps) {
//        		resultado += "Producto: " + p.toString() + "\n";
//        	}
//        	resultado += "Terminó proceso";
//        	panelDatos.actualizarInterfaz(resultado);
//		}else {
//			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//		}

	}
	
	/* ****************************************************************
	 * 			Operaciones Proveedor
	 *****************************************************************/
    
	/**
	 * Registra un nuevo proveedor de acuerdo a los valores dados por el usuario
	 */
	public void registrarProveedor(){
    	try 
    	{
    		//String nombreTipo = JOptionPane.showInputDialog (this, "Nombre del tipo de bedida?", "Adicionar tipo de bebida", JOptionPane.QUESTION_MESSAGE);
    		JTextField nit = new JTextField();
    		JTextField nombre = new JTextField();
    		JTextField calificación = new JTextField();

    		Object[] message = {
    		    "nit:", nit,
    		    "nombre:", nombre,
    		    "calificación (entre 0 y 5):", calificación,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Registrar el proveedor ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		String nombreResp = nombre.getText();
        		String nitResp = nit.getText();
        		Double calificacionResp = Double.parseDouble(calificación.getText());
        		
        		VOProveedor p = superandes.registrarProveedor(nitResp, nombreResp, calificacionResp);
        		String resultado = "En registrarProveedor\n\n";
        		resultado += "Proveedor adicionado exitosamente: " + p;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
    
	/* ****************************************************************
	 * 			Operaciones Cliente
	 *****************************************************************/
    
	/**
	 * (RF3) Registra una empresa (Cliente) de acuerdo a los valores dados por el usuario
	 */
    public void registrarEmpresa( )
    {
    	try 
    	{

    		JTextField nombre = new JTextField();
    		JTextField correo = new JTextField();
    		JTextField nit = new JTextField();
    		JTextField direccion = new JTextField();
    		Object[] message = {
    		    "nombre:", nombre,
    		    "correo:", correo,
    		    "nit:", nit,
    		    "direccion:", direccion,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar una empresa ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{

        		String nombreResp = nombre.getText();
        		String correoResp = correo.getText();
        		String nitResp = nit.getText();
        		String direccionResp = direccion.getText();

        		
        		VOCliente e = superandes.registrarEmpresa(nombreResp, correoResp, nitResp, direccionResp);
        		String resultado = "En adicionarEmpresa\n\n";
        		resultado += "Empresa adicionada exitosamente: " + e;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
	/**
	 * (RF3) Registra una persona natural (Cliente) de acuerdo a los valores dados por el usuario
	 */
    public void registrarPersona( )
    {
    	try 
    	{
    		JTextField nombre = new JTextField();
    		JTextField correo = new JTextField();
    		JTextField identificacion = new JTextField();
    		Object[] message = {
    		    "nombre:", nombre,
    		    "correo:", correo,
    		    "identificacion:", identificacion,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar una empresa ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		String nombreResp = nombre.getText();
        		String correoResp = correo.getText();
        		String identificacionResp = identificacion.getText();
        		
        		VOCliente e = superandes.registrarPersona(nombreResp, correoResp, identificacionResp);
        		String resultado = "En adicionarPersonaNatural\n\n";
        		resultado += "Persona adicionada exitosamente: " + e;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Muestra todos los clientes disponibles
     */
    public void darClientes() {
    	String resultado = " en darClientes\n\n";
		List<Cliente> cs = superandes.darClientes();
		
		if(cs.size() == 0){
			resultado += "no hay clientes registrados\n\n";
		}else{
			for(Cliente c : cs) {
				resultado += c.toString() + "\n";
			}
		}
		resultado += "Proceso terminado";
		panelDatos.actualizarInterfaz(resultado);
    }
    
    /**
     * Verifica si la identificación del cliente está registrada e inicia sesión
     */
    public void iniciarSesionClientePersona(){
    	if(loggedClient == null){
        	try 
        	{
        		JTextField identificacion = new JTextField();
        		Object[] message = {
        		    "identificacion:", identificacion,
        		};
        		int option = JOptionPane.showConfirmDialog(this, message, "Ingresar con documento de identificación", JOptionPane.OK_CANCEL_OPTION);
        		if (option == JOptionPane.OK_OPTION)
        		{
            		long identificacionResp = Long.parseLong(identificacion.getText());
            		
            		VOCliente e = superandes.darClientePersonaPorId(identificacionResp);
            		if(e != null){
            			loggedClient = e;
            			JOptionPane.showMessageDialog(this, "Bienvenido(a) " + e.getNombre(), "Sesión iniciada exitosamente", JOptionPane.OK_OPTION);
            			
                		String resultado = "En iniciarSesionClientePersona\n\n";
                		resultado += "Se inició sesión exitosamente: " + e.getNombre() + " Correo: " + e.getCorreo();
            			resultado += "\n Operación terminada";
                		panelDatos.actualizarInterfaz(resultado);
            		}else{
            			JOptionPane.showMessageDialog(this, "El documento ingresado no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
            		}

        		}else {
        			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        		}

    		} 
        	catch (Exception e) 
        	{
//    			e.printStackTrace();
    			String resultado = generarMensajeError(e);
    			panelDatos.actualizarInterfaz(resultado);
    		}
    	}else{
    		JOptionPane.showMessageDialog(this, "Ya existe una sesión iniciada, por favor cerrar sesión", "Error", JOptionPane.ERROR_MESSAGE);
    	}

    }
    
    /**
     * Verifica si el nit de la empresa está registrada e inicia sesión
     */
    public void iniciarSesionClienteEmpresa(){
    	if(loggedClient == null){
        	try 
        	{
        		JTextField nit = new JTextField();
        		Object[] message = {
        		    "nit:", nit,
        		};
        		int option = JOptionPane.showConfirmDialog(this, message, "Ingresar con NIT", JOptionPane.OK_CANCEL_OPTION);
        		if (option == JOptionPane.OK_OPTION)
        		{
            		long nitResp = Long.parseLong(nit.getText());
            		
            		VOCliente e = superandes.darClienteEmpresaPorId(nitResp);
            		if(e != null){
            			loggedClient = e;
            			JOptionPane.showMessageDialog(this, "Bienvenido(a) " + e.getNombre(), "Sesión iniciada exitosamente", JOptionPane.OK_OPTION);
                		String resultado = "En iniciarSesionClienteEmpresa\n\n";
                		resultado += "Se inició sesión exitosamente: " + e.getNombre() + " Correo: " + e.getCorreo();
            			resultado += "\n Operación terminada";
                		panelDatos.actualizarInterfaz(resultado);
            		}else{
            			JOptionPane.showMessageDialog(this, "El nit ingresado no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
            		}

        		}else {
        			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        		}

    		} 
        	catch (Exception e) 
        	{
//    			e.printStackTrace();
    			String resultado = generarMensajeError(e);
    			panelDatos.actualizarInterfaz(resultado);
    		}
    	}else{
    		JOptionPane.showMessageDialog(this, "Ya existe una sesión iniciada, por favor cerrar sesión", "Error", JOptionPane.ERROR_MESSAGE);
    	}

    }
    
    /**
     * Cerrar sesión 
     */
    public void cerrarSesion(){
    	int option = JOptionPane.showConfirmDialog(this, "Desea cerrar sesión");
    	if (option == JOptionPane.OK_OPTION) {
			loggedClient = null;
    		String resultado = "En cerrarSesion\n\n";
    		resultado += "Se cerró sesión exitosamente ";
			resultado += "\n Operación terminada";
    		panelDatos.actualizarInterfaz(resultado);
		}else{
			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		}
    }
    
	/* ****************************************************************
	 * 			Operaciones Promoción
	 *****************************************************************/
    
    /**
     * (RF7) Registra una promoción
     */
    public void registrarPromocion(){
    
    	
    	try 
    	{
    		JTextField tipo = new JTextField();
    		JTextField precio = new JTextField();
    		JTextField fechaInicio = new JTextField();
    		JTextField fechaFin = new JTextField();
    		JTextField idSucursal = new JTextField();
    		JTextField idProducto = new JTextField();
    		JTextField cantidad1 = new JTextField();
    		JTextField cantidad2 = new JTextField();
    		JTextField descuento = new JTextField();
    		Object[] message = {
    		    "tipo:", tipo,
    		    "precio:", precio,
    		    "fecha de Inicio:", fechaInicio,
    		    "fecha de Finalización:", fechaFin,
    		    "id de la Sucursal:", idSucursal,
    		    "id del Producto:", idProducto,
    		    "Cantidad del producto 1 (llenar solo si la promoción es tipo 'n<m' o 'x<y):", cantidad1,
    		    "Cantidad del producto 2 (llenar solo si la promoción es tipo 'n<m' o 'x<y):", cantidad2,
    		    "descuento:", descuento,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar una promoción ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		int tipoResp = Integer.parseInt(tipo.getText());
        		double precioResp = Double.parseDouble(precio.getText());
        		String fechaInicioResp = fechaInicio.getText();
        		String fechaFinResp = fechaFin.getText();
        		Long idSucursalResp = Long.parseLong(idSucursal.getText());
        		String idProductoResp = idProducto.getText();
        		int cantidad1Resp = Integer.parseInt(cantidad1.getText());
        		int cantidad2Resp = Integer.parseInt(cantidad2.getText());
        		double descuentoResp = Double.parseDouble(descuento.getText());
        		
        		VOPromocion e = superandes.registrarPromocion(tipoResp, precioResp, fechaInicioResp, fechaFinResp, idSucursalResp, idProductoResp, cantidad1Resp, cantidad2Resp, descuentoResp);
        		String resultado = "En adicionarPersonaNatural\n\n";
        		resultado += "Persona adicionada exitosamente: " + e;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
	/**
	 * (RF8) Método para finalizar promociones cuando los productos se acaben o cuando ya llegó la fecha de vencimiento
	 */
	public void limpiarPromociones() {
		Date fechaActual = new Date();
		superandes.finalizarPromocion(fechaActual);		
	}
	
	/**
	 * (RFC2) Muestra las promociones (no finalizadas) con más ventas
	 */
	public void dar20PromocionesMasPopulares() {
		List<ComprasPorPromocion> cs = superandes.dar20PromocionesMasPopulares();
		String resultado = " en dar20PromocionesMasPopulares\n\n";
		
		if(cs.size() == 0){
			resultado += "no hay promociones disponibles\n\n";
		}else{
			for (int i = 0; i < cs.size(); i++) {
				resultado += (i + 1) + ". id_promoción: " + cs.get(i).getIdPromocion() + "; número_compras: " + cs.get(i).getNumCompras() + "\n";
			}
		}
		resultado += "Terminó proceso";
		panelDatos.actualizarInterfaz(resultado);
		
	}
	
	/* ****************************************************************
	 * 			Operaciones Sucursal
	 *****************************************************************/
	/**
	 * (RF4) Registra una sucursal
	 */
	public void registrarSucursal(){
    	try 
    	{
    		JTextField nombre = new JTextField();
    		JTextField ciudad = new JTextField();
    		JTextField direccion = new JTextField();
    		Object[] message = {
    		    "nombre:", nombre,
    		    "ciudad:", ciudad,
    		    "direccion:", direccion,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar una sucursal ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		String nombreResp = nombre.getText();
        		String ciudadResp = ciudad.getText();
        		String direccionResp = direccion.getText();
        		
        		VOSucursal e = superandes.registrarSucursal(nombreResp, ciudadResp, direccionResp);
        		String resultado = "En RegistrarSucursal\n\n";
        		resultado += "Sucursal adicionada exitosamente: " + e;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	/* ****************************************************************
	 * 			Operaciones Bodega
	 *****************************************************************/
	
	/**
	 * (RF5) Registra una bodega dada una sucursal
	 */
	public void registrarBodega(){
    	try 
    	{
    		JTextField capacidadPeso = new JTextField();
    		JTextField capacidadVolumen = new JTextField();
    		JTextField idCategoria = new JTextField();
    		JTextField idSucursal = new JTextField();
    		Object[] message = {
    		    "capacidad de peso:", capacidadPeso,
    		    "capacidad de volumen:", capacidadVolumen,
    		    "id de categoría:", idCategoria,
    		    "id de sucursal:", idSucursal,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar una bodega a una sucursal ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		double capacidadPesoResp = Double.parseDouble(capacidadPeso.getText());
        		double capacidadVolumenResp = Double.parseDouble(capacidadVolumen.getText());
        		long idCategoriaResp = Long.parseLong(idCategoria.getText());
        		long idSucursalResp = Long.parseLong(idSucursal.getText());
        		
        		VOBodega e = superandes.registrarBodega(idCategoriaResp, capacidadPesoResp, capacidadVolumenResp, idSucursalResp);
        		String resultado = "En registrarBodega\n\n";
        		resultado += "Bodega adicionada exitosamente: " + e;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/* ****************************************************************
	 * 			Operaciones Estante
	 *****************************************************************/
	
	/**
	 * (RF6) Registra un estante dada una sucursal
	 */
	public void registrarEstante(){
    	try 
    	{
    		JTextField capacidadPeso = new JTextField();
    		JTextField capacidadVolumen = new JTextField();
    		JTextField idCategoria = new JTextField();
    		JTextField idSucursal = new JTextField();
    		Object[] message = {
    		    "capacidad de peso:", capacidadPeso,
    		    "capacidad de volumen:", capacidadVolumen,
    		    "id de categoría:", idCategoria,
    		    "id de sucursal:", idSucursal,
    		};
    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar un estatne a una sucursal ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
    		if (option == JOptionPane.OK_OPTION)
    		{
        		double capacidadPesoResp = Double.parseDouble(capacidadPeso.getText());
        		double capacidadVolumenResp = Double.parseDouble(capacidadVolumen.getText());
        		long idCategoriaResp = Long.parseLong(idCategoria.getText());
        		long idSucursalResp = Long.parseLong(idSucursal.getText());
        		
        		VOEstante e = superandes.registrarEstante(idCategoriaResp, capacidadPesoResp, capacidadVolumenResp, idSucursalResp);
        		String resultado = "En registrarEstante\n\n";
        		resultado += "Estante adicionada exitosamente: " + e;
    			resultado += "\n Operación terminada";
        		panelDatos.actualizarInterfaz(resultado);
    		}else {
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	/* ****************************************************************
	 * 			Operaciones ORDENES
	 *****************************************************************/

	/**
	 * (RF10) Registra una orden en la base de datos
	 */
    public void adicionarOrden( )
    {
//    	try 
//    	{
//    		JTextField id = new JTextField();
//    		JTextField precio = new JTextField();
//    		JTextField fechaEsperada = new JTextField();
//    		JTextField fechaLlegada= new JTextField();
//    		JTextField estado = new JTextField();
//    		JTextField calificacion = new JTextField();
//    		JTextField idSucursal = new JTextField();
//    		JTextField idProveedor = new JTextField();
//    		Object[] message = {
//    		    "id:", id,
//    		    "precio:", precio,
//    		    "fechaEsperada:", fechaEsperada,
//    		    "fechaLlegada:", fechaLlegada,
//    		    "estado:", estado,
//    		    "calificacion:", calificacion,
//    		    "idSucursal:", idSucursal,
//    		    "idProveedor:", idProveedor,
//    		};
//    		int option = JOptionPane.showConfirmDialog(this, message, "Adicionar una orden ingresando todos sus valores", JOptionPane.OK_CANCEL_OPTION);
//    		if (option == JOptionPane.OK_OPTION)
//    		{
//        		long idResp = Long.parseLong(id.getText());
//        		double precioResp = Double.parseDouble(precio.getText());
//        		Timestamp fechaEsperadaResp = Timestamp.valueOf(fechaEsperada.getText());
//        		String estadoResp = estado.getText();
//        		long idSucursalResp = Long.parseLong(idSucursal.getText());
//        		long idProveedorResp = Long.parseLong(idProveedor.getText());
//        		Timestamp fechaLlegadaResp = Timestamp.valueOf(fechaLlegada.getText());
//        		double calificacionResp = Double.parseDouble(calificacion.getText());
//        		
//        		VOOrden e = superandes.adicionarOrden(precioResp, fechaEsperadaResp, fechaLlegadaResp, estadoResp, calificacionResp, idSucursalResp, idProveedorResp);
//        		String resultado = "En adicionarOrden\n\n";
//        		resultado += "Orden adicionada exitosamente: " + e;
//    			resultado += "\n Operación terminada";
//        		panelDatos.actualizarInterfaz(resultado);
//    		}else {
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
    }
	
	
	

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogSuperandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
//		try 
//		{
//    		// Ejecución de la demo y recolección de los resultados
//			long eliminados [] = parranderos.limpiarParranderos();
//			
//			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
//			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
//			resultado += eliminados [0] + " Gustan eliminados\n";
//			resultado += eliminados [1] + " Sirven eliminados\n";
//			resultado += eliminados [2] + " Visitan eliminados\n";
//			resultado += eliminados [3] + " Bebidas eliminadas\n";
//			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
//			resultado += eliminados [5] + " Bebedores eliminados\n";
//			resultado += eliminados [6] + " Bares eliminados\n";
//			resultado += "\nLimpieza terminada";
//   
//			panelDatos.actualizarInterfaz(resultado);
//		} 
//		catch (Exception e) 
//		{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.txt");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
    /**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Superandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Sebastián Lemus Cadena\n";
		resultado += " * 2018\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);
    }
    



    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazSuperandes.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    
    
    public static void main( String[] args )
    {
        try
        {
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazSuperandes interfaz = new InterfazSuperandes( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }


}
