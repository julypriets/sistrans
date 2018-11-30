package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Estante;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ESTANTE de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author j.prieto
 */
public class SQLEstante {
	
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
	
	
	/* ----------------- Métodos ----------------------------------- */
	
	/**
	 * Constructor
	 * @param ps - El Manejador de persistencia de la aplicación
	 */
	public SQLEstante (PersistenciaSuperandes ps) {
		this.ps = ps;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para registrar un ESTANTE 
	 * @param pm - El manejador de persistencia
	 * @param id - identificador del estante
	 * @param idCategoria - identificador de la categoría del estante
	 * @param capacidadPeso - peso que puede soportar el estante
	 * @param capacidadVolumen - volumen que puede contener el estante
	 * @param idSucursal - identificador de la sucursal a la que pertenece el estante
	 * @return El número de tuplas insertadas
	 */
	public long registrarEstante(PersistenceManager pm, long id, long idCategoria, double capacidadPeso, double capacidadVolumen, long idSucursal ) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + ps.darTablaEstante() + " VALUES (?, ?, ?, ?, ?)");
		q.setParameters(id, idCategoria, capacidadPeso, capacidadVolumen, idSucursal);
		return (long) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idEstante
	 * @return La capacidad de peso del estante respectivo
	 */
	public double darCapacidadPesoEstante(PersistenceManager pm, long idEstante){
		Query q = pm.newQuery(SQL, "SELECT capacidad_peso FROM ESTANTE WHERE id = " + idEstante);
		q.setResultClass(Double.class);
		return (double) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idEstante
	 * @return La capacidad de volumen del estante respectivo
	 */
	public double darCapacidadVolumenEstante(PersistenceManager pm, long idEstante){
		Query q = pm.newQuery(SQL, "SELECT capacidad_volumen FROM ESTANTE WHERE id = " + idEstante);
		q.setResultClass(Double.class);
		return (double) q.executeUnique();
	}
	
	/**
	 * 
	 * @param pm - Persistence Manager
	 * @param idCategoria
	 * @return Una colección con todos los estantes de determinada categoría
	 */
	public List<Estante> darEstantesPorCategoria(PersistenceManager pm, long idCategoria){
		Query q = pm.newQuery(SQL, "SELECT id, id_categoria idCategoria, capacidad_peso capacidadPeso, capacidad_volumen capacidadVolumen, id_sucursal idSucursal  FROM ESTANTE WHERE id_categoria = " + idCategoria);
		q.setResultClass(Estante.class);
		return (List<Estante>) q.executeList();
	}
}