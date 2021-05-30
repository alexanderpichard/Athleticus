package ventanas;

public class Socio {
	
	private String nombre;
	private String email;
	private String usuario;
	private String pass;
	private int edad;
	
	
	public Socio() {
		this.nombre="";
		this.email="";
		this.usuario="";
		this.pass="";
		this.edad=0;
	}
	
	public Socio(String nombre, String email,String usuario,String pass,int edad) {
		this.nombre=nombre;
		this.email=email;
		this.usuario=usuario;
		this.pass=pass;
		this.edad=edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getEdad() {
		return edad;
	}
	
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String toString() {
		return "Socio: \n"
		+ "\n nombre: " + getNombre() 
		+ "\n email: " + getEmail() 
		+ "\n usuario: " + getUsuario() 
		+ "\n pass: " + getPass() 
		+"\n edad: " + getEdad()
		+ "\n";
	}

	
}
