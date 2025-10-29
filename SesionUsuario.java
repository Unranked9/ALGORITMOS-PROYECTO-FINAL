package clase;

public class SesionUsuario {

	private static String nombre;
	
	public static void setNombre(String nombreUsuario) {
		nombre = nombreUsuario;
	}
	
	public static String getNombre() {
		return nombre;
	}
}
