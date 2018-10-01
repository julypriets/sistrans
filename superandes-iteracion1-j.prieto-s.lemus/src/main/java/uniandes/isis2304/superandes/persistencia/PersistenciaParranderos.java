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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.superandes.negocio.Bebedor;


/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaParranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaParranderos.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;
	

	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaParranderos ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add ("TIPOBEBIDA");
		tablas.add ("BEBIDA");
		tablas.add ("BAR");
		tablas.add ("BEBEDOR");
		tablas.add ("GUSTAN");
		tablas.add ("SIRVEN");
		tablas.add ("VISITAN");
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaParranderos (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{		
		sqlUtil = new SQLUtil(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaTipoBebida ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaBebida ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaBar ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaBebedor ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaGustan ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaSirven ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaVisitan ()
	{
		return tablas.get (7);
	}
	
	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
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


	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
	 *****************************************************************/
	
//	/**
//	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
//	 * Adiciona entradas al log de la aplicación
//	 * @param nombre - El nombre del bebedor
//	 * @param ciudad - La ciudad del bebedor
//	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
//	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
//	 */
//	public Bebedor adicionarBebedor(String nombre, String ciudad, String presupuesto) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long idBebedor = nextval ();
//            long tuplasInsertadas = sqlBebedor.adicionarBebedor(pmf.getPersistenceManager(), idBebedor, nombre, ciudad, presupuesto);
//            tx.commit();
//
//            log.trace ("Inserción de bebedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
//            
//            return new Bebedor (idBebedor, nombre, ciudad, presupuesto);
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//        	return null;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el nombre del bebedor
//	 * Adiciona entradas al log de la aplicación
//	 * @param nombre - El nombre del bebedor
//	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
//	 */
//	public long eliminarBebedorPorNombre(String nombre) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long resp = sqlBebedor.eliminarBebedorPorNombre (pm, nombre);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//            return -1;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
//	 * Adiciona entradas al log de la aplicación
//	 * @param idBebedor - El identificador del bebedor
//	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
//	 */
//	public long eliminarBebedorPorId (long idBebedor) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long resp = sqlBebedor.eliminarBebedorPorId (pm, idBebedor);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//            return -1;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el nombre dado
//	 * @param nombreBebedor - El nombre del bebedor
//	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
//	 */
//	public List<Bebedor> darBebedoresPorNombre (String nombreBebedor) 
//	{
//		return sqlBebedor.darBebedoresPorNombre (pmf.getPersistenceManager(), nombreBebedor);
//	}
//
//	/**
//	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
//	 * @param idBebedor - El identificador del bebedor
//	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
//	 */
//	public Bebedor darBebedorPorId (long idBebedor) 
//	{
//		return (Bebedor) sqlBebedor.darBebedorPorId (pmf.getPersistenceManager(), idBebedor);
//	}
//
//	/**
//	 * Método que consulta TODA LA INFORMACIÓN DE UN BEBEDOR con el identificador dado. Incluye la información básica del bebedor,
//	 * las visitas realizadas y las bebidas que le gustan.
//	 * @param idBebedor - El identificador del bebedor
//	 * @return El objeto BEBEDOR, construido con base en las tuplas de la tablas BEBEDOR, VISITAN, BARES, GUSTAN, BEBIDAS y TIPOBEBIDA,
//	 * relacionadas con el identificador de bebedor dado
//	 */
//	public Bebedor darBebedorCompleto (long idBebedor) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Bebedor bebedor = (Bebedor) sqlBebedor.darBebedorPorId (pm, idBebedor);
//		bebedor.setVisitasRealizadas(armarVisitasBebedor (sqlBebedor.darVisitasRealizadas (pm, idBebedor)));
//		bebedor.setBebidasQueLeGustan(armarGustanBebedor (sqlBebedor.darBebidasQueLeGustan (pm, idBebedor)));
//		return bebedor;
//	}
//
//	/**
//	 * Método que consulta todas las tuplas en la tabla BEBEDOR
//	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
//	 */
//	public List<Bebedor> darBebedores ()
//	{
//		return sqlBebedor.darBebedores (pmf.getPersistenceManager());
//	}
// 
//	/**
//	 * Método que consulta los bebedores y el número de visitas que ha realizado
//	 * @return La lista de parejas de objetos, construidos con base en las tuplas de la tabla BEBEDOR y VISITAN. 
//	 * El primer elemento de la pareja es un bebedor; 
//	 * el segundo elemento es el número de visitas de ese bebedor (0 en el caso que no haya realizado visitas)
//	 */
//	public List<Object []> darBebedoresYNumVisitasRealizadas ()
//	{
//		List<Object []> respuesta = new LinkedList <Object []> ();
//		List<Object> tuplas = sqlBebedor.darBebedoresYNumVisitasRealizadas (pmf.getPersistenceManager());
//        for ( Object tupla : tuplas)
//        {
//			Object [] datos = (Object []) tupla;
//			long idBebedor = ((BigDecimal) datos [0]).longValue ();
//			String nombreBebedor = (String) datos [1];
//			String ciudadBebedor = (String) datos [2];
//			String presupuesto = (String) datos [3];
//			int numBares = ((BigDecimal) datos [4]).intValue ();
//
//			Object [] resp = new Object [2];
//			resp [0] = new Bebedor (idBebedor, nombreBebedor, ciudadBebedor, presupuesto);
//			resp [1] = numBares;	
//			
//			respuesta.add(resp);
//        }
//
//		return respuesta;
//	}
// 
//	/**
//	 * Método que consulta CUÁNTOS BEBEDORES DE UNA CIUDAD VISITAN BARES
//	 * @param ciudad - La ciudad que se quiere consultar
//	 * @return El número de bebedores de la ciudad dada que son referenciados en VISITAN
//	 */
//	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
//	{
//		return sqlBebedor.darCantidadBebedoresCiudadVisitanBares (pmf.getPersistenceManager(), ciudad);
//	}
//	
//	/**
//	 * Método que actualiza, de manera transaccional, la ciudad de un  BEBEDOR
//	 * @param idBebedor - El identificador del bebedor
//	 * @param ciudad - La nueva ciudad del bebedor
//	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
//	 */
//	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long resp = sqlBebedor.cambiarCiudadBebedor (pm, idBebedor, ciudad);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//            return -1;
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//
//	/**
//	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
//	 * Si el bebedor está referenciado en alguna otra relación, no se borra ni el bebedor NI las visitas
//	 * @param idBebedor - El identificador del bebedor
//	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
//	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
//	 */
//	public long []  eliminarBebedorYVisitas_v1 (long idBebedor)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long [] resp = sqlBebedor.eliminarBebedorYVisitas_v1 (pm, idBebedor);
//            tx.commit();
//            return resp;
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//        	return new long[] {-1, -1};
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}
//	
//	/**
//	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
//	 * Si el bebedor está referenciado en alguna otra relación, no se puede borrar, SIN EMBARGO SÍ SE BORRAN TODAS SUS VISITAS
//	 * @param idBebedor - El identificador del bebedor
//	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
//	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
//	 */
//	public long [] eliminarBebedorYVisitas_v2 (long idBebedor)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx=pm.currentTransaction();
//        try
//        {
//            tx.begin();
//            long visitasEliminadas = eliminarVisitanPorIdBebedor(idBebedor);
//            long bebedorEliminado = eliminarBebedorPorId (idBebedor);
//            tx.commit();
//            return new long [] {bebedorEliminado, visitasEliminadas};
//        }
//        catch (Exception e)
//        {
////        	e.printStackTrace();
//        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//        	return new long [] {-1, -1};
//        }
//        finally
//        {
//            if (tx.isActive())
//            {
//                tx.rollback();
//            }
//            pm.close();
//        }
//	}	
//
//	/**
//	 * Método privado para generar las información completa de las visitas realizadas por un bebedor: 
//	 * La información básica del bar visitado, la fecha y el horario, en el formato esperado por los objetos BEBEDOR
//	 * @param tuplas - Una lista de arreglos de 7 objetos, con la información del bar y de la visita realizada, en el siguiente orden:
//	 *   bar.id, bar.nombre, bar.ciudad, bar.presupuesto, bar.cantsedes, vis.fechavisita, vis.horario
//	 * @return Una lista de arreglos de 3 objetos. El primero es un objeto BAR, el segundo corresponde a la fecha de la visita y
//	 * el tercero corresponde al horaario de la visita
//	 */
//	private List<Object []> armarVisitasBebedor (List<Object []> tuplas)
//	{
//		List<Object []> visitas = new LinkedList <Object []> ();
//		for (Object [] tupla : tuplas)
//		{
//			long idBar = ((BigDecimal) tupla [0]).longValue ();
//			String nombreBar = (String) tupla [1];
//			String ciudadBar = (String) tupla [2];
//			String presupuestoBar = (String) tupla [3];
//			int sedesBar = ((BigDecimal) tupla [4]).intValue ();
//			Timestamp fechaVisita = (Timestamp) tupla [5];
//			String horarioVisita = (String) tupla [6];
//			
//			Object [] visita = new Object [3];
//			visita [0] = new Bar (idBar, nombreBar, ciudadBar, presupuestoBar, sedesBar);
//			visita [1] = fechaVisita;
//			visita [2] = horarioVisita;
//
//			visitas.add (visita);
//		}
//		return visitas;
//	}
//	
//	/**
//	 * Método privado para generar las información completa de las bebidas que le gustan a un bebedor: 
//	 * La información básica de la bebida, especificando también el nombre de la bebida, en el formato esperado por los objetos BEBEDOR
//	 * @param tuplas - Una lista de arreglos de 5 objetos, con la información de la bebida y del tipo de bebida, en el siguiente orden:
//	 * 	 beb.id, beb.nombre, beb.idtipobebida, beb.gradoalcohol, tipobebida.nombre
//	 * @return Una lista de arreglos de 2 objetos. El primero es un objeto BEBIDA, el segundo corresponde al nombre del tipo de bebida
//	 */
//	private List<Object []> armarGustanBebedor (List<Object []> tuplas)
//	{
//		List<Object []> gustan = new LinkedList <Object []> ();
//		for (Object [] tupla : tuplas)
//		{			
//			long idBebida = ((BigDecimal) tupla [0]).longValue ();
//			String nombreBebida = (String) tupla [1];
//			long idTipoBebida = ((BigDecimal) tupla [2]).longValue ();
//			int gradoAlcohol = ((BigDecimal) tupla [3]).intValue ();
//			String nombreTipo = (String) tupla [4];
//
//			Object [] gusta = new Object [2];
//			gusta [0] = new Bebida (idBebida, nombreBebida, idTipoBebida, gradoAlcohol);
//			gusta [1] = nombreTipo;	
//			
//			gustan.add(gusta);
//		}
//		return gustan;
//	}

 }
