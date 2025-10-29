package clase; 

/**
 * Representa la entidad Matricula (la inscripción de un alumno a un curso).
 * Esta es una clase POJO (Modelo) que solo contiene datos,
 * su constructor, y sus getters/setters.
 */
public class Matricula {

    // --- Atributos (Según PDF) ---
    private int numMatricula; // Correlativo (base 100001)
    private int codAlumno;     // Código del alumno que se matricula
    private int codCurso;     // Código del curso al que se matricula
    private String fecha;      // Fecha en formato "dd/MM/yyyy"
    private String hora;       // Hora en formato "HH:mm:ss"

    /**
     * Constructor para crear un objeto Matricula.
     * En la lógica de ArregloMatriculas, los valores de numMatricula, fecha y hora
     * serán asignados por el sistema (generados), no pedidos por la GUI.
     */
    public Matricula(int numMatricula, int codAlumno, int codCurso, String fecha, String hora) {
        this.numMatricula = numMatricula;
        this.codAlumno = codAlumno;
        this.codCurso = codCurso;
        this.fecha = fecha;
        this.hora = hora;
    }

    // --- Getters y Setters ---
    // (Métodos para obtener y establecer los valores de los atributos)

    public int getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }

    public int getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}