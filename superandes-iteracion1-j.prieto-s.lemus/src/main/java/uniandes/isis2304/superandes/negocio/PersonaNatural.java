package uniandes.isis2304.superandes.negocio;

public class PersonaNatural extends Cliente implements VOPersonaNatural{
	
	private String identificacion;

	public PersonaNatural(String nombre, String correo, String identificacion) {
		super(nombre, correo);
		this.identificacion = identificacion;
	}
	
	public PersonaNatural(){
		super();
		identificacion = "";
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	@Override
	public String toString() {
		return "PersonaNatural [identificacion=" + identificacion + "]";
	}
	
	
}
