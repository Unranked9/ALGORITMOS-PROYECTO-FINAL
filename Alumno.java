package clase;

/*Representa a un alumno dentro del sistema de matrícula.
 Incluye sus datos personales y su estado dentro del proceso académico.*/

public class Alumno {

    // ATRIBUTOS
    private int codAlumno;
    private String nombres;
    private String apellidos;
    private String dni;
    private int edad;
    private int celular;
    private int estado; 

    //CONSTRUCTOR 
    
    public Alumno(int codAlumno, String nombres, String apellidos, String dni, int edad, int celular, int estado) {
        this.codAlumno = codAlumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.celular = celular;
        this.estado = estado;
    }

    // MÉTODOS GET Y SET 
    public int getCodAlumno() { return codAlumno; }
    public void setCodAlumno(int codAlumno) { this.codAlumno = codAlumno; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getCelular() { return celular; }
    public void setCelular(int celular) { this.celular = celular; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    // MÉTODOS ADICIONALES 

    /* Para retornar una descripción textual del estado del alumno.*/
    public String obtenerDescripcionEstado() {
        switch (estado) {
            case 0: return "Registrado";
            case 1: return "Matriculado";
            case 2: return "Retirado";
            default: return "Desconocido";
        }
    }

    public String toString() {
        return codAlumno + " - " + nombres + " " + apellidos;
    }
}
