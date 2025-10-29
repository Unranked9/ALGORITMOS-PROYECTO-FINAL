package arreglo; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

// Importaciones para la lógica de datos
import java.util.ArrayList;

// Importaciones para Fecha y Hora
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Importaciones de las clases Modelo (Rama 1)
import clase.Matricula;
import clase.Alumno;

/* Gestiona el ArrayList de Matrículas y su persistencia en "matriculas.txt".
  Es responsable de cambiar el estado de los Alumnos a "Matriculado" (1).*/
public class ArregloMatriculas {

    // Atributo
    private ArrayList<Matricula> matriculas;

    // Constructor
    public ArregloMatriculas() {
        matriculas = new ArrayList<>();
        //Carga de Datos al iniciar
        cargarDatos(); 
    }

    
//METODOS PRINCIPALES
    
    public void adicionar(Matricula mat, ArregloAlumno arrAlumnos) {
        
        // 1. Asigna los datos generados por el sistema
        mat.setNumMatricula(generarCodigoCorrelativo());
        mat.setFecha(obtenerFechaActual());
        mat.setHora(obtenerHoraActual());
        
        // 2. Busca al alumno en el ArregloAlumnos**
        Alumno alu = arrAlumnos.buscarPorCodigo(mat.getCodAlumno());
        
        // 3. Si el alumno existe, actualiza su estado
        if (alu != null) {
            alu.setEstado(1); // 1 = matriculado
            
            // ¡Importante! Guarda el cambio en el archivo de alumnos
            arrAlumnos.grabarDatos(); 
        }
        
        // 4. Añade la nueva matrícula a la lista y graba
        matriculas.add(mat);
        grabarDatos(); // Graba el cambio en matriculas.txt
    }

   
     
    public void modificarCurso(int numMatricula, int nuevoCodCurso) {
        // 1. Busca la matrícula
        Matricula mat = buscarPorNumero(numMatricula);
        
        // 2. Si existe, cambia el código del curso y graba
        if (mat != null) {
            mat.setCodCurso(nuevoCodCurso);
            grabarDatos();
        }
    }
    
    
     
    public void eliminar(Matricula matEliminar, ArregloAlumno arrAlumnos) {
        
        // 1. Busca al alumno asociado a esta matrícula
        Alumno alu = arrAlumnos.buscarPorCodigo(matEliminar.getCodAlumno());
        
        // 2. Valida que el alumno NO esté retirado (estado != 2)
        if (alu != null && alu.getEstado() != 2) {
            
            // 3. Revierte el estado del alumno a "Registrado"
            alu.setEstado(0); 
            arrAlumnos.grabarDatos(); // Graba el cambio en alumnos.txt
            
            // 4. Elimina la matrícula de la lista y graba
            matriculas.remove(matEliminar);
            grabarDatos(); // Graba el cambio en matriculas.txt
        }
       
    }

    /** Retorna la lista completa de matrículas.
      @return Un ArrayList<Matricula> con todas las matrículas.*/
    public ArrayList<Matricula> listar() {
        return matriculas;
    }

    // Métodos de Búsqueda y Consulta

    
    
    public Matricula buscarPorNumero(int numMatricula) {
        // Recorre la lista de matrículas.
        for (Matricula mat : matriculas) {
            // Compara el número de la matrícula actual con el buscado.
            if (mat.getNumMatricula() == numMatricula) {
                // Si coinciden, devuelve la matrícula encontrada.
                return mat;
            }
        }
        // Si no se encontró tras recorrer toda la lista, devuelve null.
        return null; // No se encontró
    }


   
    public Matricula buscarPorCodAlumno(int codAlumno) {
        // Recorre la lista de matrículas.
        for (Matricula mat : matriculas) {
            // Compara el código de alumno de la matrícula actual con el buscado.
            if (mat.getCodAlumno() == codAlumno) {
                // Si coinciden, devuelve esa matrícula.
                return mat;
            }
        }
        // Si no se encontró matrícula para ese alumno, devuelve null.
        return null;
    }


    /* Busca TODAS las matrículas asociadas a un curso específico.
      (Necesario para ArregloCursos.eliminar()).*/
    public ArrayList<Matricula> buscarPorCodCurso(int codCurso) {
        // Crea una lista vacía para guardar las matrículas encontradas.
        ArrayList<Matricula> matsEncontradas = new ArrayList<>();
        // Recorre la lista completa de matrículas.
        for (Matricula mat : matriculas) {
            // Compara el código de curso de la matrícula actual con el buscado.
            if (mat.getCodCurso() == codCurso) {
                // Si coinciden, añade esta matrícula a la lista de encontradas.
                matsEncontradas.add(mat);
            }
        }
        // Devuelve la lista de matrículas encontradas (puede estar vacía).
        return matsEncontradas;
    }
    
    //Metodos Generadores
    
    public int generarCodigoCorrelativo() {
        if (matriculas.isEmpty()) {
            return 100001; // Código base 
        } else {
            // Obtiene el N° del último y le suma 1
            return matriculas.get(matriculas.size() - 1).getNumMatricula() + 1;
        }
    }
    
    /* Retorna la fecha actual */
    public String obtenerFechaActual() {
        // "dd/MM/yyyy" 
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    public String obtenerHoraActual() {
        // "HH:mm:ss" 
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    // Metodos Grabar-Cargar

    
    public void grabarDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("matriculas.txt"))) {
            for (Matricula mat : matriculas) {
                // Escribe una línea por matrícula en formato CSV
                String linea = mat.getNumMatricula() + "," + 
                               mat.getCodAlumno() + "," +
                               mat.getCodCurso() + "," + 
                               mat.getFecha() + "," +
                               mat.getHora();
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Informa del error en consola
        }
    }

    /* Carga los datos desde "matriculas.txt" al ArrayList.
      Se ejecuta automáticamente al crear el ArregloMatriculas.*/
    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("matriculas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separa la línea por las comas
                String[] datos = linea.split(",");
                
                // Parsea los datos y crea el objeto Matricula
                int numMat = Integer.parseInt(datos[0]);
                int codAlu = Integer.parseInt(datos[1]);
                int codCur = Integer.parseInt(datos[2]);
                String fecha = datos[3];
                String hora = datos[4];
                
                Matricula mat = new Matricula(numMat, codAlu, codCur, fecha, hora);
                matriculas.add(mat);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo matriculas.txt no encontrado. Empezando vacío.");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}