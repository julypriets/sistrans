package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperandes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class Superandes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Superandes.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperandes ps;

	/**
	 * Estructura de datos donde se guarda la información
	 * del estante del cuál el cliente recogió un producto para
	 * adicionarlo al carro de compras. La llave es el id del producto  
	 * y el valor es el id del estante
	 */
	private HashMap<String, Long> productosRecogidos;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Superandes ()
	{
		productosRecogidos = new HashMap<>();
		ps = PersistenciaSuperandes.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Superandes (JsonObject tableConfig)
	{
		productosRecogidos = new HashMap<>();
		ps = PersistenciaSuperandes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia ();
	}


	/*
	 * REQUERIMIENTOS FUNCIONALES
	 * 
	 *  Registrar proveedores
	 *  
	 *  Registrar productos
	 *  
	 *  Registrar clientes
	 *  
	 *  Registar una sucursal
	 *  
	 *  Registrar una bodega a una sucursal
	 *  
	 *  Registrar un estante en una sucursal
	 *  
	 *  Registrar una promoción
	 *  
	 *  Finalizar una promoción
	 *  
	 *  Registrar un pedido de un producto a un proveedor para una sucursal
	 *  
	 *  Registrar la llegada de un pedido de un producto a una sucursal
	 *  
	 *  Regsistrar una venta de un producto en una sucursal
	 *  
	 */

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Superandes
	 * @return Un arreglo con 24 números que indican el número de tuplas borradas en las tablas: 
	 * SUCURSAL, CATEGORIA, PRODUCTO, ORDEN, ESTANTE, BODEGA, ABASTECIMIENTO, PROVEEDOR, CLIENTE, PERSONA,
	 * EMPRESA, CAJERO, FACTURA, PROMOCION, USUARIO, CARRITO, ABASTECIMIENTO_BODEGA, SURTIDO, PRODUCTO_ABASTECIMIENTO, 
	 * INVENTARIO, PRODUCTO_ORDEN, CATALOGO, ORDEN_PROVEEDOR, COMPRA, PRODUCTO_PROMOCION, FACTURA_PROMOCION y CARRITO_PRODUCTO
	 * respectivamente.
	 */
	public long [] limpiarParranderos ()
	{
		log.info ("Limpiando la BD de Superandes");
		long [] borrrados = ps.limpiarSuperandes();	
		log.info ("Limpiando la BD de Superanes: Listo!");
		return borrrados;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las PRODUCTOS
	 *****************************************************************/

	/**
	 * (RF1)
	 * @param nombre
	 * @param marca
	 * @param precioUnitario
	 * @param presentacion
	 * @param precioUnidadMedida
	 * @param unidadMedida
	 * @param empacado
	 * @param codigoBarras
	 * @param nivelReorden
	 * @param existencias
	 * @param idCategoria
	 * @param idSucursal
	 * @param fechaVencimiento
	 * @return La instancia de producto de acuerdo a los parámetros asignados
	 * @throws Exception si alguno de los valores no son válidos
	 */
	public Producto registrarProducto(String nombre, String marca, Double precioUnitario, String presentacion,
			Double precioUnidadMedida, String unidadMedida, String empacado, String codigoBarras, Integer nivelReorden,
			Integer existencias, Long idCategoria, Long idSucursal, String fechaVencimiento) 
					throws Exception
	{
		if(precioUnitario < 0 || precioUnidadMedida < 0 || nivelReorden < 0 || existencias < 0){
			throw new Exception("Los valores del precio, existencias o nivel de reorden son inválidos");
		}

		if(ps.darSucursalPorId(idSucursal) == null){
			throw new Exception("El id de la sucursal ingresado no es válido");
		}

		if(ps.darCategoriaPorId(idCategoria) == null){
			throw new Exception("El id de la categoría ingresado no es válido");
		}

		Timestamp fechaVencimientoFormateada = (Timestamp) new Date();

		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date parsedDate = dateFormat.parse(fechaVencimiento);
			fechaVencimientoFormateada = new Timestamp(parsedDate.getTime());
		}catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Adicionando el producto: " + nombre + "con código de barras: " + codigoBarras);
		Producto p = ps.registrarProducto(nombre, marca, precioUnitario, presentacion, precioUnidadMedida, unidadMedida, empacado, codigoBarras, nivelReorden, existencias, idCategoria, idSucursal, fechaVencimientoFormateada);
		log.info("Adicionando el producto: " + p.toString());
		return p;
	}
	/**
	 * 
	 * @return Una lista de todos los productos disponibles
	 */
	public List<Producto> darProductos(){
		return ps.darProductos();
	}

	/**
	 * 
	 * @param idProducto
	 * @return El producto asociado con el id
	 */
	public Producto darProductoPorId(String idProducto){
		return ps.darProductoPorId(idProducto);
	}

	public List<Producto> darProductosPorNombre(String nombre){
		return ps.darProductosPorNombre(nombre);
	}

	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/

	/**
	 * (RF7) Se encarga de adicionar una promoción según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param tipo
	 * @param precio
	 * @param fechaInicio
	 * @param fechaFin
	 * @param idSucursal
	 * @param idProducto
	 * @param cantidad1
	 * @param cantidad2
	 * @param descuento
	 * @return
	 */
	public Promocion registrarPromocion(int tipo, double precio, String fechaInicio, 
			String fechaFin, long idSucursal, String idProducto, int cantidad1, int cantidad2, double descuento) throws Exception{
		if(precio < 0){
			throw new Exception("El valor ingresado para el precio es inválido");
		}
		if(descuento < 0 || descuento > 100){
			throw new Exception("El valor ingresado para el descuento es inválido");
		}
		if(cantidad1 < 0 || cantidad2 < 0){
			throw new Exception("Los valores ingresados para las cantidades son inválidas");
		}

		Timestamp fechaInicioFormateada = (Timestamp) new Date();
		Timestamp fechaFinFormateada = (Timestamp) new Date();

		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date parsedDateIni = dateFormat.parse(fechaInicio);
			fechaInicioFormateada = new Timestamp(parsedDateIni.getTime());

			Date parsedDateFin = dateFormat.parse(fechaFin);
			fechaFinFormateada = new Timestamp(parsedDateFin.getTime());

		}catch (Exception e) {
			e.printStackTrace();
		}

		// falta verificar los tipos

		log.info("Adicionando la promoción: " + tipo + "con descuento: " + descuento);
		Promocion p = ps.registrarPromocion(tipo, precio, fechaInicioFormateada, fechaFinFormateada, idSucursal, idProducto, cantidad1, cantidad2, descuento);
		log.info("Adicionando el producto: " + p.toString());
		return p;
	}

	/**
	 * (RF8) Finaliza las promociones que ya se vencieron o cuyos productos
	 * asociados hayan agotado sus existencias
	 * @param fechaActual
	 * @return número de tuplas eliminadas
	 */
	public long finalizarPromocion(Date fechaActual) {
		return ps.finalizarPromocion(fechaActual);
	}

	/**
	 * (RFC2)
	 * @return las 20 promociones con más compras
	 */
	public List<ComprasPorPromocion> dar20PromocionesMasPopulares(){
		return ps.dar20PromocionesMasPopulares();
	}

	/* ****************************************************************
	 * 			Métodos para manejar los PROVEEDORES
	 *****************************************************************/

	public Proveedor registrarProveedor(String nit, String nombre, double calificacion) throws Exception{
		if (calificacion < 0 || calificacion > 5) {
			throw new Exception("La calificación ingresada no es válida");
		}

		log.info("Adicionando el proveedor: " + nombre + "con nit: " + nit);
		Proveedor p = ps.registrarProveedor(nit, nombre, calificacion);
		log.info("Adicionando el proveedor: " + p.toString());
		return p;
	}


	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/

	/**
	 * (RF3) Se encarga de adicionar una empresa según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param id
	 * @param nombre
	 * @param correo
	 * @param nit
	 * @param direccion
	 * @return
	 */
	public Cliente registrarEmpresa(String nombre, String correo, String nit, String direccion) {
		log.info("Adicionando la empresa: " + nombre + "con nit: " + nit);
		Cliente e = ps.registrarEmpresa(nombre, correo, nit, direccion);
		log.info("Adicionando la empresa: " + e.toString());
		return e;
	} 

	/**
	 * (RF3) Se encarga de adicionar una persona natural según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param id
	 * @param nombre
	 * @param correo
	 * @param identificacion
	 * @return
	 */
	public Cliente registrarPersona(String nombre, String correo, String identificacion) {
		log.info("Adicionando la persona: " + nombre + "con documento de identificación: " + identificacion);
		Cliente p = ps.registrarPersona(nombre, correo, identificacion);
		log.info("Adicionando la persona: " + p.toString());
		return p;
	} 

	/**
	 * Retorna todos los clientes existentes
	 * @return
	 */
	public List<Cliente> darClientes(){
		return ps.darClientes();
	}

	/**
	 * 
	 * @param identificacion
	 * @return La persona natural correspondiente al documento de identificación
	 */
	public Cliente darClientePersonaPorId(long identificacion){
		return ps.darClientePersonaPorId(identificacion);
	}

	/**
	 * 
	 * @param nit
	 * @return La empresa correspondiente al nit 
	 */
	public Cliente darClienteEmpresaPorId(long nit){
		return ps.darClienteEmpresaPorId(nit);
	}

	/* ****************************************************************
	 * 			Métodos para manejar las SUCURSALES
	 *****************************************************************/

	/**
	 * (RF4) Se encarga de adicionar una sucursal según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param nombre
	 * @param ciudad
	 * @param direccion
	 * @return la sucursal creada
	 */
	public Sucursal registrarSucursal(String nombre, String ciudad, String direccion){
		log.info("Adicionando la sucursal: " + nombre + "con dirección: " + direccion);
		Sucursal s = ps.registrarSucursal(nombre, ciudad, direccion);
		log.info("Adicionando la sucursal: " + s.toString());
		return s;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las BODEGAS
	 *****************************************************************/

	/**
	 * (RF5) Se encarga de adicionar una bodega a una sucursal según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param idCategoria
	 * @param capacidadPeso
	 * @param capacidadVolumen
	 * @param idSucursal
	 * @return
	 */
	public Bodega registrarBodega(long idCategoria, double capacidadPeso, double capacidadVolumen, long idSucursal) throws Exception{

		if(ps.darSucursalPorId(idSucursal) == null){
			throw new Exception("El id de la sucursal ingresado no es válido");
		}

		if(ps.darCategoriaPorId(idCategoria) == null){
			throw new Exception("El id de la categoría ingresado no es válido");
		}

		log.info("Adicionando una bodega de categoría: " + idCategoria + "en la sucursal: " + idSucursal);
		Bodega b = ps.registrarBodega(idCategoria, capacidadPeso, capacidadVolumen, idSucursal);
		log.info("Adicionando la bodega: " + b.toString());
		return b;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los ESTANTES
	 *****************************************************************/

	/**
	 * (RF6) Se encarga de adicionar una bodega a una sucursal según los valores dados
	 * y de retornar la representación en Objeto respectiva
	 * @param idCategoria
	 * @param capacidadPeso
	 * @param capacidadVolumen
	 * @param idSucursal
	 * @return
	 * @throws Exception
	 */
	public Estante registrarEstante(long idCategoria, double capacidadPeso, double capacidadVolumen, long idSucursal) throws Exception{

		if(ps.darSucursalPorId(idSucursal) == null){
			throw new Exception("El id de la sucursal ingresado no es válido");
		}

		if(ps.darCategoriaPorId(idCategoria) == null){
			throw new Exception("El id de la categoría ingresado no es válido");
		}

		log.info("Adicionando un estante de categoría: " + idCategoria + "en la sucursal: " + idSucursal);
		Estante e = ps.registrarEstante(idCategoria, capacidadPeso, capacidadVolumen, idSucursal);
		log.info("Adicionando la bodega: " + e.toString());
		return e;
	}
	

	/* ****************************************************************
	 * 			Métodos para manejar las Ordenes
	 *****************************************************************/
	
	public long[] registrarLlegadaOrden(long idOrden, Timestamp fechaLlegada, String estado, String idProveedor, double calificacion){
		log.info("Registrando la llegada de la orden: " + idOrden);
		long[] cambios = ps.registrarLlegadaOrden(idOrden, fechaLlegada, estado, idProveedor, calificacion);
		return cambios;
		
	}

	/* ****************************************************************
	 * 			Métodos para manejar los CARRITOS
	 *****************************************************************/

	/**
	 * (RF12) Método que se encarga de asignar un carro de compras desocupado a un cliente.
	 * Retorna null si no hay algún carro desocupado
	 * @param idCliente
	 * @return Carro solicitado
	 */
	public Carrito solicitarCarro(long idCliente){
		return ps.solicitarCarro(idCliente);
	}

	/**
	 * (RF16) Método que se encarga de actualizar el estado de un carro de compras
	 * si su dueño lo abandona. Retorna null si el cliente no tenía un carro previamente
	 * @param idCliente
	 * @return Carro abandonado
	 */
	public Carrito abandonarCarro(long idCliente){
		return ps.abandonarCarro(idCliente);
	}

	/**
	 * 
	 * @return Todos los carros registrados
	 */
	public List<Carrito> darCarros(){
		return ps.darCarros();
	}

	/**
	 * 
	 * @return Una colección de todos los carros desocupados
	 */
	public List<Carrito> darCarrosDesocupados(){
		return ps.darCarrosDesocupados();
	}

	/**
	 * 
	 * @return Una colección de todos los carros abandonados
	 */
	public List<Carrito> darCarrosAbandonados(){
		return ps.darCarrosAbandonados();
	}

	/**
	 * (RF13) Método que se encarga de insertar un producto al carro
	 * y actualizar el estado del estante respectivo
	 * @param idCarrito
	 * @param idProducto
	 * @param cantidad
	 * @param idEstante
	 * @return Número de tuplas actualizadas
	 */
	public long insertarProductoAlCarro(long idCarrito, String nombreProducto, long cantidad, long idEstante) throws Exception{
		Producto p = ps.darProductoPorNombreYEstante(nombreProducto, idEstante);
		if(p == null){
			throw new Exception("El producto ingresado o el estante no son correctos");
		}
		if(ps.darCantidadProductoPorEstante(p.getCodigoBarras(), idEstante) == 0){
			throw new Exception("No hay existencias del producto en el estante: " + idEstante);
		}

		productosRecogidos.put(p.getCodigoBarras(), idEstante);
		return ps.insertarProductoAlCarro(idCarrito, p.getCodigoBarras(), cantidad, idEstante);

	}

	/**
	 * (RF14) Método que se encarga de devolver un producto del carro
	 * y actualizar el estado del estante respectivo
	 * @param idCarrito
	 * @param nombreProducto
	 * @param cantidad
	 * @return Número de tuplas actualizadas
	 */
	public long devolverProductoDelCarro(long idCarrito, String nombreProducto, long cantidad) throws Exception{
		List<Producto> productos = ps.darProductosEnCarro(idCarrito);
		Producto producto = null;
		for(Producto p : productos){
			if(p.getNombre().equals(nombreProducto)){
				producto = p;
			}
		}
		if(producto == null || ps.darCantidadDeProducto(idCarrito, producto.getCodigoBarras()) == 0){
			throw new Exception("El producto que se está tratando de remover no se encuentra en el carro de compras");
		}
		String idProducto = producto.getCodigoBarras();
		long idEstante = productosRecogidos.get(idProducto);
		int cantidadEnCarro = ps.darCantidadDeProducto(idCarrito, idProducto);

		if(ps.darCantidadDeProducto(idCarrito, idProducto) - cantidad == 0){
			productosRecogidos.remove(idProducto);
		}else if (cantidadEnCarro - cantidad < 0){
			throw new Exception("Está tratando de remover una cantidad superior a la que posee del producto");
		}
		return ps.devolverProductoDelCarro(idCarrito, idProducto, cantidadEnCarro, idEstante);
	}

	/**
	 * (RF17) Método utilizado para identificar los carros de compra abandonados,
	 * desocupar sus productos en los estantes respectivos, y volverlos disponibles 
	 * de nuevo para el cliente
	 */
	public void  recolectarProductosAbandonados(){
		ps.recolectarProductosAbandonados();
	}

	/**
	 * 
	 * @param idCliente
	 * @return El id del carro correspondiente a su dueño
	 */
	public long darCarroPorCliente(long idCliente){
		return ps.darCarroPorCliente(idCliente);
	}

	/**
	 * 
	 * @param idCarrito
	 * @return Una colección con los productos del carro determinado
	 */
	public List<Producto> darProductosEnCarro(long idCarrito){
		return ps.darProductosEnCarro(idCarrito);
	}

	/* ****************************************************************
	 * 			Métodos para manejar las Facturas
	 *****************************************************************/

	/**
	 * (RF15) Método que se encarga de generar la factura cuando un cliente paga todos
	 * los productos en su carro de compras
	 * @param idCarrito
	 * @param idCajero
	 * @param idCliente
	 * @return
	 */
	public Factura pagarCompra(long idCarrito, long idCajero, long idCliente, double dineroIngresado) throws Exception{
		List<Producto> productos = ps.darProductosEnCarro(idCarrito);
		double sobrante = dineroIngresado - ps.calcularCostoTotalCarro(productos, idCarrito) ;
		if(sobrante < 0){
			throw new Exception("El dinero ingresado es insuficiente");
		}
		Factura f = ps.pagarCompra(idCarrito, idCajero, idCliente);
		f.setSobrante(sobrante);
		return f;
	}

	public PersistenciaSuperandes getPersistenciaSuperandes(){
		return ps;
	} 
	
	/* ****************************************************************
	 * 			Métodos para manejar las Ventas
	 *****************************************************************/
	

	
}
