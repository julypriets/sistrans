package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;


import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Factura;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random; 

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto FACTURA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */

public class SQLFactura {

	/* ----------------- Constantes ----------------------------------- */

	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;


	/* ----------------- Atributos ----------------------------------- */

	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes ps;

	private ComparatorFechas cf;

	/* ----------------- Métodos ----------------------------------- */

	/**
	 * Constructor
	 * @param ps - El Manejador de persistencia de la aplicación
	 */
	public SQLFactura (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar una FACTURA 
	 * @param pm - El manejador de persistencia
	 * @param id - el identificador de la factura
	 * @param fecha - fecha de creación de la factura
	 * @param precioTotal - precio total de la factura
	 * @param idCajero - id del cajero que atendió la compra
	 * @param idPersona - id de la persona que realizó la compra
	 * @param idEmpresa - id de la empresa que realizó la compra
	 * @param cliente - tipp de cliente
	 * @param comprada - si la compra se realizó o no
	 * @return El número de tuplas insertadas
	 */
	public long registarFactura(PersistenceManager pm, long id, Timestamp fecha, double precioTotal, long idCajero, 
			long idCliente) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaFactura() + " VALUES (?, ?, ?, ?, ?)");
		q.setParameters(id, fecha, precioTotal, idCajero, idCliente);
		return (long) q.executeUnique();
	}
	
	/**
	 * Registra una venta de un producto en una sucursal
	 * @param pm - El manejador de persistencia
	 * @param idFactura - id de la factura de la venta
	 * @param idCajero - cajero que atendió la compra
	 * @return - El número de tuplas modificadas
	 */
	public long[] registrarVenta(PersistenceManager pm, long idFactura, long idCajero) {
		
		long[] resp = new long[100];
		int indice = 0;
		Query q1 = pm.newQuery(SQL, "SELECT id_producto, cantidad FROM " + ps.darTablaCompra() + " WHERE id_factura = ?");
		q1.setParameters(idFactura);
		//Lista de de id's de productos y cantidad involucrados en la compra
		List<Object> list1 = q1.executeList();
		
		Query q2 = pm.newQuery(SQL, "SELECT id_sucursal FROM + " + ps.darTablaCajero() + " WHERE id = ?");
		q2.setParameters(idCajero);
		//Obtengo la sucursal en la que se hace la venta
		long idSucursal = (long)q2.execute();
		
		Query q3 = pm.newQuery(SQL, "SELECT id FROM " + ps.darTablaBodega() + " WHERE id_sucursal = ?");
		q3.setParameters(idSucursal);
		List<Object> list2 = q3.executeList();
		
		//Actualizo los productos en el inventario de esa sucursal
		for(Object ventas : list1) {
			Object[] venta = (Object[])ventas;
			String idProducto = String.valueOf(venta[0]);
			int cantidad = ((BigDecimal) venta[1]).intValue();
			for(Object bodegas : list2) {
				Object[] bodega = (Object[])bodegas;
				long idBodega = ((BigDecimal) bodega[0]).longValue();
				Query q4 = pm.newQuery(SQL, "UPDATE " + ps.darTablaInventario() + " SET cantidad = cantidad - ? WHERE id_bodega = ? AND id_producto = ?");
				q4.setParameters(cantidad, idBodega, idProducto);
				resp[indice] = (long)q4.executeUnique();
				indice ++;
			} verificarInventario(pm, idSucursal);
		}
		
		//Verificar si hay promociones, y si las hay, actualizar inventarios
		Query q5 = pm.newQuery(SQL, "SELECT id_promocion FROM " + ps.darTablaFacturaPromocion() + " WHERE id_factura = ?");
		List<Object> list3 = q5.executeList();
		//Busco las promociones y según su tipo actualizo
		for(Object promociones : list3) {
			Object[] promocion = (Object[]) promociones;
			long idPromocion = ((BigDecimal)promocion[0]).longValue();
			
			Query q6 = pm.newQuery(SQL, "SELECT tipo FROM " + ps.darTablaPromocion() + " WHERE id = ?");
			q6.setParameters(idPromocion);
			int tipo = (int)q6.execute();
			List<Object> list4;
			Query q7;
			Query q8;
			switch(tipo) {
			case 1: { //Pague n y lleve m
				q7 = pm.newQuery(SQL, "SELECT id_producto, cantidad2 FROM " + ps.darTablaPromocion() + " WHERE id = ?");
				q7.setParameters(idPromocion);
				//Lista con id producto y cantidad
				list4 = q7.executeList();
				Object[] productos = (Object[])list4.get(0);
				String idProducto1 = String.valueOf(productos[0]);
				int cantidad2 = ((BigDecimal)productos[1]).intValue();
				
				for(Object bodegas : list2) {
					Object[] bodega = (Object[])bodegas;
					long idBodega = ((BigDecimal) bodega[0]).longValue();
					q8 = pm.newQuery(SQL, "UPDATE " + ps.darTablaInventario() + " SET cantidad = cantidad - ? WHERE id_bodega = ? AND id_producto = ?");
					q8.setParameters(cantidad2, idBodega, idProducto1);
					resp[indice] = (long)q8.executeUnique();
					indice ++;
				} verificarInventario(pm, idSucursal);
				break; }
			case 2: //Descuento
			case 3: { //Pague x cantidad de n producto y lleve y
				q7 = pm.newQuery(SQL, "SELECT id_producto FROM " + ps.darTablaPromocion() + " WHERE id = ?");
				q7.setParameters(idPromocion);
				String idProducto2 = String.valueOf(q7.execute());
				
				for(Object bodegas : list2) {
					Object[] bodega = (Object[])bodegas;
					long idBodega = ((BigDecimal) bodega[0]).longValue();
					q8 = pm.newQuery(SQL, "UPDATE " + ps.darTablaInventario() + " SET cantidad = cantidad - ? WHERE id_bodega = ? AND id_producto = ?");
					q8.setParameters(1, idBodega, idProducto2);
					resp[indice] = (long)q8.executeUnique();
					indice ++;
				} verificarInventario(pm, idSucursal);
				break; }
			case 4: //Pague 1 y lleve 2
				q7 = pm.newQuery(SQL, "SELECT id_producto FROM " + ps.darTablaPromocion() + " WHERE id = ?");
				q7.setParameters(idPromocion);
				String idProducto3 = String.valueOf(q7.execute());
				
				for(Object bodegas : list2) {
					Object[] bodega = (Object[])bodegas;
					long idBodega = ((BigDecimal) bodega[0]).longValue();
					q8 = pm.newQuery(SQL, "UPDATE " + ps.darTablaInventario() + " SET cantidad = cantidad - ? WHERE id_bodega = ? AND id_producto = ?");
					q8.setParameters(2,idBodega, idProducto3);
					resp[indice] = (long)q8.executeUnique();
					indice ++;
				} verificarInventario(pm, idSucursal);
				break;
			case 5: { //Paquete de productos
				q7 = pm.newQuery(SQL, "SELECT id_producto, cantidad FROM " + ps.darTablaProductoPromocion() + " WHERE id_promocion = ?");
				q7.setParameters(idPromocion);
				list4 = q7.executeList();
				//Recorro la lista con los id's de los productos
				for(Object productos : list4) {
					Object[] producto = (Object[])productos;
					String idProducto4 = String.valueOf(producto[0]);
					int cantidad3 = ((BigDecimal)producto[1]).intValue();
					//Actualizo las bodegas restando los productos y cuántos de ellos están en la promoción
					for(Object bodegas : list2) {
						Object[] bodega = (Object[])bodegas;
						long idBodega = ((BigDecimal) bodega[0]).longValue();
						q8 = pm.newQuery(SQL, "UPDATE " + ps.darTablaInventario() + " SET cantidad = cantidad - ? WHERE id_bodega = ? AND id_producto = ?");
						q8.setParameters(cantidad3,idBodega, idProducto4);
						resp[indice] = (long)q8.executeUnique();
						indice ++;
					}
				} verificarInventario(pm, idSucursal);
				break; }
			}
		}
		return resp;
	}
	
	/**
	 * Verifica el inventario
	 * @param pm - El manejador de la persistencia
	 * @param idSucursal - identificador de la sucursal que verifica su inventario
	 * @return - lista con los identificadores de las ordenes generadas
	 */
	public List<Object> verificarInventario(PersistenceManager pm, long idSucursal) {
		List<Object> resp = new LinkedList<>();
		//Busco en mi inventario si hay algún producto que esté escaseando
		Query q1 = pm.newQuery(SQL, "SELECT id_producto, precio FROM "+ ps.darTablaProducto() + " WHERE producto.existencias < producto.nivel_reorden");
		//Manejador sql de orden
		SQLOrden sqlOrden = new SQLOrden(this.ps);
		SQLProductoOrden sqlProductoOrden = new SQLProductoOrden(this.ps);
		SQLOrdenProveedor sqlOrdenProveedor = new SQLOrdenProveedor(this.ps);
		//Generador aleatorio para el proveedor
		Random rand = new Random(); 
		
		List<Object> list = q1.executeList();
		int cantidad = 50; //Por defecto se piden 50 productos
		
		//Por cada producto voy a hacer un pedido
		for(Object productos : list) {
			Object[] producto = (Object[]) productos;
			String idProducto = String.valueOf(producto[0]);
			double precio = ((BigDecimal)producto[1]).doubleValue();
			//Se espera que el pedido llegue en 5 días
			Timestamp ts = new Timestamp(System.currentTimeMillis());
					Calendar cal = Calendar.getInstance();
					cal.setTime(ts);
					cal.add(Calendar.DAY_OF_WEEK, 5);
					ts.setTime(cal.getTime().getTime()); // or
					ts = new Timestamp(cal.getTime().getTime());
					
			//El precio de los productos se dará con un 30% de descuento
			double precioFinal = (precio * cantidad) - (0.3*(precio*cantidad));
			long idOrden = ps.nextval();
			//Orden general
			sqlOrden.registrarOrden(pm, idOrden, precioFinal, ts, null, "PENDIENTE", idSucursal);
			
			//Producto_Orden
			sqlProductoOrden.registrarProductoOrden(pm, idProducto, idOrden, cantidad);
			
			
			//Busco algún proveedor que me venda el producto
			Query q2 = pm.newQuery(SQL, "SELECT id_proveedor FROM " + ps.darTablaCatalogo() + " WHERE id_producto = ?");
			q2.setParameters(idProducto);
			List<Object> list2 = q2.executeList();
			
			//Selecciono aleatoriamente al proveedor
			int elegido = rand.nextInt(list2.size()); 
			Object[] proveedor = (Object[]) list2.get(elegido);
			
			String idProveedor = String.valueOf(proveedor[0]);
			
			//Orden_Proveedor
			sqlOrdenProveedor.registrarOrdenProveedor(pm, idOrden, idProveedor, 0.0);
			
			resp.add(idOrden);
		}
		return resp;
	}
	
	//Atributos extra
	private List<Timestamp> fechasMayorD = new ArrayList<>();
	private List<Timestamp> fechasMenorD = new ArrayList<>();
	private List<Integer> cantidadMayorD = new ArrayList<>();
	private List<Integer> cantidadMenorD = new ArrayList<>();
	private Timestamp fechaMayor;
	private Timestamp fechaMenor;
	
	
	public Timestamp fechaMayorDemanda(PersistenceManager pm, int tipo, long idSucursal, String option){
		
		//Cajeros con el id de la sucursal
		Query q = pm.newQuery(SQL, "SELECT id FROM " + ps.darTablaCajero() + " WHERE id_sucursal = ?");
		q.setParameters(idSucursal);
		List<Object> list = q.executeList();
		
		//Los productos de esa categoría
		Query q2= pm.newQuery(SQL, "SELECT producto.codigo_barras FROM " + ps.darTablaProducto() + " WHERE producto.id_categoria = ?");
		q2.setParameters(tipo);
		List<Object> list2 = q2.executeList();
		
		for(Object idsCajero : list) {
			Object[] idCajero = (Object[]) idsCajero;
			long id = ((BigDecimal)idCajero[0]).longValue();
			//Por cajero seleccionamos las facturas
			Query q1 = pm.newQuery(SQL, "SELECT id_factura FROM " + ps.darTablaFactura() + " WHERE factura.id_cajero = ?");
			q1.setParameters(id);
			List<Object> list1 = q1.executeList();
			
			for(Object facturas : list1) {
				Object[] factura = (Object[]) facturas;
				long idFactura = ((BigDecimal)factura[0]).longValue();
				
				//Por cada producto encontrado
				for(Object productos : list2) {
					Object[] producto = (Object[]) productos;
					String idProducto = String.valueOf(producto[0]);
					
					Query q3 = pm.newQuery(SQL, "SELECT factura.fecha, SUM(compra.cantidad) FROM (( producto INNER JOIN compra "
							+ "ON producto.codigo_barras = compra.id_producto) INNER JOIN factura ON factura.id = compra.id_factura) "
							+ "WHERE producto.codigo_barras = ? AND id_factura = ? GROUP BY factura.fecha");
					q3.setParameters(idProducto, idFactura);
					List<Object> list3 = q3.executeList();
					
					int maxVenta = 0;
					for(Object ventas : list3) {
						Object[] venta = (Object[]) ventas;
						Timestamp fech = (Timestamp)venta[0];
						int cant = ((BigDecimal)venta[1]).intValue();
						
						if(cant > maxVenta) {
							//Agrego las fechas
							fechaMayor = fech;
							//cantidadMayorD.add(cant);							
						}
					}
				}
			}
		}
		return fechaMayor;
	}
	
	public Timestamp fechaMenorDemanda(PersistenceManager pm, int tipo, long idSucursal, String option){
		
		//Cajeros con el id de la sucursal
		Query q = pm.newQuery(SQL, "SELECT id FROM " + ps.darTablaCajero() + " WHERE id_sucursal = ?");
		q.setParameters(idSucursal);
		List<Object> list = q.executeList();
		
		//Los productos de esa categoría
		Query q2= pm.newQuery(SQL, "SELECT producto.codigo_barras FROM " + ps.darTablaProducto() + " WHERE producto.id_categoria = ?");
		q2.setParameters(tipo);
		List<Object> list2 = q2.executeList();
		
		for(Object idsCajero : list) {
			Object[] idCajero = (Object[]) idsCajero;
			long id = ((BigDecimal)idCajero[0]).longValue();
			//Por cajero seleccionamos las facturas
			Query q1 = pm.newQuery(SQL, "SELECT id_factura FROM " + ps.darTablaFactura() + " WHERE factura.id_cajero = ?");
			q1.setParameters(id);
			List<Object> list1 = q1.executeList();
			
			for(Object facturas : list1) {
				Object[] factura = (Object[]) facturas;
				long idFactura = ((BigDecimal)factura[0]).longValue();
				
				//Por cada producto encontrado
				for(Object productos : list2) {
					Object[] producto = (Object[]) productos;
					String idProducto = String.valueOf(producto[0]);
					
					Query q3 = pm.newQuery(SQL, "SELECT factura.fecha, SUM(compra.cantidad) FROM (( producto INNER JOIN compra "
							+ "ON producto.codigo_barras = compra.id_producto) INNER JOIN factura ON factura.id = compra.id_factura) "
							+ "WHERE producto.codigo_barras = ? AND id_factura = ? GROUP BY factura.fecha");
					q3.setParameters(idProducto, idFactura);
					List<Object> list3 = q3.executeList();
					
					int maxVenta = 0;
					for(Object ventas : list3) {
						Object[] venta = (Object[]) ventas;
						Timestamp fech = (Timestamp)venta[0];
						int cant = ((BigDecimal)venta[1]).intValue();
						
						if(cant < maxVenta) {
							//Agrego las fechas
							fechaMenor = fech;
							//cantidadMayorD.add(cant);							
						}
					}
				}
			}
		}
		return fechaMenor;
	}
	public List<Timestamp> getFechasMayorD() {
		return fechasMayorD;
	}

	public void setFechasMayorD(List<Timestamp> fechasMayorD) {
		this.fechasMayorD = fechasMayorD;
	}


	public List<Timestamp> getFechasMenorD() {
		return fechasMenorD;
	}

	public void setFechasMenorD(List<Timestamp> fechasMenorD) {
		this.fechasMenorD = fechasMenorD;
	}

	public List<Integer> getCantidadMayorD() {
		return cantidadMayorD;
	}

	public void setCantidadMayorD(List<Integer> cantidadMayorD) {
		this.cantidadMayorD = cantidadMayorD;
	}

	public List<Integer> getCantidadMenorD() {
		return cantidadMenorD;
	}

	public void setCantidadMenorD(List<Integer> cantidadMenorD) {
		this.cantidadMenorD = cantidadMenorD;
	}
	
	public boolean tieneProductosCostosos( PersistenceManager pm, long idFactura){
		Query q = pm.newQuery(SQL, "SELECT id FROM factura, compra, producto where factura.id "
				+ "= compra.id_factura and producto.codigo_barras = compra.id_producto and id_factura "
				+ "= ? and producto.precio_unitario > 40000;");
		q.setParameters(idFactura);
		if(q.executeList().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean tieneCompras(PersistenceManager pm, long idCliente ) {
		Query q = pm.newQuery(SQL, "SELECT * FROM FACTURA WHERE ID_CLIENTE = ?");
		q.setResultClass(Factura.class);
		q.setParameters(idCliente);
		List<Factura> facturas = (List<Factura>)q.executeList();
		if(facturas.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<Factura> darFacturasCliente(PersistenceManager pm, long idCliente){
		Query q = pm.newQuery(SQL, "SELECT * FROM FACTURA WHERE ID_CLIENTE = ?");
		q.setResultClass(Factura.class);
		q.setParameters(idCliente);
		return (List<Factura>)q.executeList();
	}

}
