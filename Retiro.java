package clase; 


public class Retiro {

    //  Atributos 
    private int numRetiro;     // Correlativo (base 200001)
    private int numMatricula; // N° de la matrícula que se está retirando
    private String fecha;      // Fecha en formato "dd/MM/yyyy"
    private String hora;       // Hora en formato "HH:mm:ss"

    /**
     * Constructor para crear un objeto Retiro.
     * En la lógica de ArregloRetiros, los valores de numRetiro, fecha y hora
     * serán asignados por el sistema (generados).
     */
    public Retiro(int numRetiro, int numMatricula, String fecha, String hora) {
        this.numRetiro = numRetiro;
        this.numMatricula = numMatricula;
        this.fecha = fecha;
        this.hora = hora;
    }

    // --- Getters y Setters ---
 

    public int getNumRetiro() {
        return numRetiro;
    }

    public void setNumRetiro(int numRetiro) {
        this.numRetiro = numRetiro;
    }

    public int getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
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