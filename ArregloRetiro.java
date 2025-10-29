package arreglo; 


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

// Importaciones para la lógica de datos
import java.util.ArrayList;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Importaciones de las clases Modelo (Rama 1)
import clase.Retiro;
import clase.Matricula;
import clase.Alumno;

/** Gestiona el ArrayList de Retiros y su persistencia en "retiros.txt".
  Es responsable de cambiar el estado de los Alumnos a "Retirado" (2).
  Depende de ArregloAlumno y ArregloMatriculas.*/
public class ArregloRetiro {

    // Atributo
    private ArrayList<Retiro> retiros;

    //Constructor
    public ArregloRetiro() {
        retiros = new ArrayList<>();
        // Carga los retiros guardados desde retiros.txt
        cargarDatos();
    }

  //METODOS PRINCIPALES
    

    
     //Añadir un nuevo retiro
    public void adicionar(Retiro ret, ArregloMatriculas arrMatriculas, ArregloAlumno arrAlumnos) {

        // 1. Asigna los datos generados por el sistema
        ret.setNumRetiro(generarCodigoCorrelativo());
        ret.setFecha(obtenerFechaActual());
        ret.setHora(obtenerHoraActual());

        // 2. Busca la matrícula que se está retirando
        Matricula mat = arrMatriculas.buscarPorNumero(ret.getNumMatricula());

        // 3. Si la matrícula existe..
        if (mat != null) {
            // 4. ...busca al alumno de esa matrícula
            Alumno alu = arrAlumnos.buscarPorCodigo(mat.getCodAlumno());

            // 5. Si el alumno existe, actualiza su estado a 'Retirado'
            if (alu != null) {
                alu.setEstado(2); // 2 = Retirado
                arrAlumnos.grabarDatos(); // Graba el cambio en alumnos.txt
            }
        }

        // 6. Añade el retiro a la lista y graba
        retiros.add(ret);
        grabarDatos(); // Graba el cambio en retiros.txt
    }

  
    
    //"Cancelar retiro"
    public void eliminar(Retiro retEliminar, ArregloMatriculas arrMatriculas, ArregloAlumno arrAlumnos) {

        // 1. Busca la matrícula asociada al retiro
        Matricula mat = arrMatriculas.buscarPorNumero(retEliminar.getNumMatricula());

        if (mat != null) {
            // 2. Busca al alumno de esa matrícula
            Alumno alu = arrAlumnos.buscarPorCodigo(mat.getCodAlumno());

            // 3. Valida que el estado actual sea 'Retirado' (2)
            if (alu != null && alu.getEstado() == 2) {
                // 4. Revierte el estado a 'Matriculado'
                alu.setEstado(1); // 1 = Matriculado
                arrAlumnos.grabarDatos(); // Graba el cambio en alumnos.txt
            }
        }

        // 5. Elimina el retiro de la lista y graba
        retiros.remove(retEliminar);
        grabarDatos(); // Graba el cambio en retiros.txt
    }

    /** Retorna la lista completa de retiros.
      @return Un ArrayList<Retiro> con todos los retiros.*/
    public ArrayList<Retiro> listar() {
        return retiros;
    }

    // --- Métodos de Búsqueda y Consulta ---

    /**
     * Busca un retiro específico por su número correlativo.
     * @param numRetiro El N° de retiro (ej. 200001).
     * @return El objeto Retiro encontrado, o null si no existe.
     */
    public Retiro buscarPorNumero(int numRetiro) {
        for (Retiro ret : retiros) {
            if (ret.getNumRetiro() == numRetiro) {
                return ret;
            }
        }
        return null; // No se encontró
    }

    //METODOS GENERADORES

    /* Genera un N° de retiro correlativo (base 200001).*/
    public int generarCodigoCorrelativo() {
        if (retiros.isEmpty()) {
            return 200001; // Código base
        } else {
            // Obtiene el N° del último y le suma 1
            return retiros.get(retiros.size() - 1).getNumRetiro() + 1;
        }
    }

    /* Retorna la fecha actual en formato  */
    public String obtenerFechaActual() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /* Retorna la hora actual en formato .*/
    public String obtenerHoraActual() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    // METODOS GRABAR -CARGAR

    
    public void grabarDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("retiros.txt"))) {
            for (Retiro ret : retiros) {
                // Escribe una línea por retiro en formato CSV
                String linea = ret.getNumRetiro() + "," +
                               ret.getNumMatricula() + "," +
                               ret.getFecha() + "," +
                               ret.getHora();
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("retiros.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separa la línea por las comas
                String[] datos = linea.split(",");

                // Parsea los datos
                int numRet = Integer.parseInt(datos[0]);
                int numMat = Integer.parseInt(datos[1]);
                String fecha = datos[2];
                String hora = datos[3];

                Retiro ret = new Retiro(numRet, numMat, fecha, hora);
                retiros.add(ret);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo retiros.txt no encontrado. Empezando vacío.");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}