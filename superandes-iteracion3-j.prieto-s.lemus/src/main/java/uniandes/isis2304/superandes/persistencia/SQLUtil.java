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

package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperandes ps;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUtil (PersistenciaSuperandes ps)
	{
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ ps.darSeqSuperandes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 24 números que indican el número de tuplas borradas en las tablas: 
	 * SUCURSAL, CATEGORIA, PRODUCTO, ORDEN, ESTANTE, BODEGA, ABASTECIMIENTO, PROVEEDOR, CLIENTE, PERSONA,
	 * EMPRESA, CAJERO, FACTURA, PROMOCION, USUARIO, CARRITO, ABASTECIMIENTO_BODEGA, SURTIDO, PRODUCTO_ABASTECIMIENTO, 
	 * INVENTARIO, PRODUCTO_ORDEN, CATALOGO, ORDEN_PROVEEDOR, COMPRA, PRODUCTO_PROMOCION, FACTURA_PROMOCION y CARRITO_PRODUCTO.
	 * respectivamente.
	 */
	public long [] limpiarSuperandes (PersistenceManager pm)
	{
		
		Query qCarritoProducto = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCarritoProducto());
		Query qFacturaPromocion = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaFacturaPromocion());
		Query qProductoPromocion = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProductoPromocion());
		Query qCompra = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCompra());
		Query qOrdenProveedor = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaOrdenProveedor());
		Query qCatalogo = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCatalogo());
		Query qProductoOrden = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProductoOrden());
		Query qInventario = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaInventario());
		Query qProductoAbastecimiento = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProductoAbastecimiento());
		Query qSurtido = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaSurtido());
		Query qAbastecimientoBodega = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaAbastecimientoBodega());
		Query qUsuario = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaUsuario());
		Query qCarrito = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCarrito());
		Query qPromocion = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPromocion());
		Query qFactura = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaFactura());
		Query qCajero = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCajero());
		Query qAbastecimiento = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaAbastecimiento());
		Query qBodega = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaBodega());
		Query qEstante = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaEstante());
		Query qOrden = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaOrden());
		Query qProducto = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProducto());
		Query qEmpresa = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaEmpresa());
		Query qPersona = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaPersona());
		Query qCliente = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCliente());
		Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaProveedor());
		Query qCategoria = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaCategoria());
		Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + ps.darTablaSucursal());
		
		
		
		long sucursalDeleted = (long) qSucursal.executeUnique();
		long categoriaDeleted = (long) qCategoria.executeUnique();
		long productoDeleted = (long) qProducto.executeUnique();
		long ordenDeleted = (long) qOrden.executeUnique();
		long estanteDeleted = (long) qEstante.executeUnique();
		long bodegaDeleted = (long) qBodega.executeUnique();
		long abastecimientoDeleted = (long) qAbastecimiento.executeUnique();
		long proveedorDeleted = (long) qProveedor.executeUnique();
		long clienteDeleted = (long) qCliente.executeUnique();
		long personaDeleted = (long) qPersona.executeUnique();
		long empresaDeleted = (long) qEmpresa.executeUnique();
		long cajeroDeleted = (long) qCajero.executeUnique();
		long facturaDeleted = (long) qFactura.executeUnique();
		long promocionDeleted = (long) qPromocion.executeUnique();
		long usuarioDeleted = (long) qUsuario.executeUnique();
		long carritoDeleted = (long) qCarrito.executeUnique();
		long abastecimientoBodegaDeleted = (long) qAbastecimientoBodega.executeUnique();
		long surtidoDeleted = (long) qSurtido.executeUnique();
		long productoAbastecimientoDeleted = (long) qProductoAbastecimiento.executeUnique();
		long productoOrdenDeleted = (long) qProductoOrden.executeUnique();
		long inventarioDeleted = (long) qInventario.executeUnique();
		long catalogoDeleted = (long) qCatalogo.executeUnique();
		long ordenProveedorDeleted = (long) qOrdenProveedor.executeUnique();
		long compraDeleted = (long) qCompra.executeUnique();
		long productoPromocionDeleted = (long) qProductoPromocion.executeUnique();
		long facturaPromocionDeleted = (long) qFacturaPromocion.executeUnique();
		long carritoProductoDeleted = (long) qCarritoProducto.executeUnique();
		

		
        return new long[] {carritoProductoDeleted, facturaPromocionDeleted, productoPromocionDeleted, compraDeleted,
        		ordenProveedorDeleted, catalogoDeleted, productoOrdenDeleted, inventarioDeleted, productoAbastecimientoDeleted,
        		surtidoDeleted, abastecimientoBodegaDeleted, usuarioDeleted, carritoDeleted, promocionDeleted, facturaDeleted,
        		cajeroDeleted, abastecimientoDeleted, bodegaDeleted, estanteDeleted, ordenDeleted, productoDeleted, 
        		empresaDeleted, personaDeleted, clienteDeleted, proveedorDeleted, categoriaDeleted, sucursalDeleted};
	}

}
