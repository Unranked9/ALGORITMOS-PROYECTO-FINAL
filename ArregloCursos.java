package arreglo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import clase.Curso; 


public class ArregloCursos {

    //Atributo
    private ArrayList<Curso> cursos;

    // Constructor
    public ArregloCursos() {
        cursos = new ArrayList<>();
        cargarDatos(); 
    }

    
//METODOS PRINCIPALES
    
    public void adicionar(Curso cur) {
        
        // 1. Añade el nuevo curso al final de la lista
        cursos.add(cur);
        
        // 2.  Ordenar la lista por código de curso
        Collections.sort(cursos, new Comparator<Curso>() {
            
            public int compare(Curso c1, Curso c2) {
                // Compara los códigos de curso
                return Integer.compare(c1.getCodCurso(), c2.getCodCurso());
            }
        });
        
        // 3. Graba la lista (ya ordenada) en el archivo
        grabarDatos();
    }

    
    public void modificar(Curso curModificado) {
        
        // 1. Busca el curso en la lista usando su código
        Curso curExistente = buscarPorCodigo(curModificado.getCodCurso());
        
        // 2. Si se encontró, actualiza los campos permitidos
        if (curExistente != null) {
            curExistente.setAsignatura(curModificado.getAsignatura());
            curExistente.setCiclo(curModificado.getCiclo());
            curExistente.setCreditos(curModificado.getCreditos());
            curExistente.setHoras(curModificado.getHoras());
            
            // 3. Graba los cambios en el archivo de texto
            grabarDatos();
        }
    }

   
     
    public boolean eliminar(Curso curEliminar, ArregloMatriculas arrMatriculas) {
        
        // 1. Valida si hay alumnos matriculados en este curso.
        //    (Depende de ArregloMatriculas.buscarPorCodCurso)
        if (arrMatriculas.buscarPorCodCurso(curEliminar.getCodCurso()).isEmpty()) {
            
            // 2. Si la lista de matrículas está vacía, es seguro eliminar
            cursos.remove(curEliminar);
            
            // 3. Graba los cambios
            grabarDatos();
            return true; // Eliminación exitosa
        }
        
        // 4. Si la lista de matrículas NO estaba vacía, no se elimina
        return false; // No se pudo eliminar
    }

    /** Retorna la lista completa de cursos.
      @return Un ArrayList<Curso> con todos los cursos.*/
    public ArrayList<Curso> listar() {
        return cursos;
    }
    
    // Métodos de Búsqueda y Consulta 

    
public Curso buscarPorCodigo(int codCurso) {
        
        // Recorre la lista de cursos.
        for (Curso cur : cursos) { 
            
            // Compara el código del curso actual con el buscado.
            if (cur.getCodCurso() == codCurso) { 
                
                // Si coinciden, devuelve el curso encontrado.
                return cur; 
            }
        }
        
        // Si termina el bucle sin encontrarlo, devuelve null.
        return null; // No encontrado.
    }



   //Metodos Cargar-Grabar
    public void grabarDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("cursos.txt"))) {
            for (Curso cur : cursos) {
                // Escribe una línea por curso en formato CSV
                String linea = cur.getCodCurso() + "," + 
                               cur.getAsignatura() + "," +
                               cur.getCiclo() + "," + 
                               cur.getCreditos() + "," +
                               cur.getHoras();
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("cursos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                
                // Parsea los datos de String a su tipo correcto
                int cod = Integer.parseInt(datos[0]);
                String asig = datos[1];
                int ciclo = Integer.parseInt(datos[2]);
                int cred = Integer.parseInt(datos[3]);
                int horas = Integer.parseInt(datos[4]);
                
                // Crea el objeto Curso y lo añade a la lista
                Curso cur = new Curso(cod, asig, ciclo, cred, horas);
                cursos.add(cur);
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, no es un error, solo informa.
            System.out.println("Archivo cursos.txt no encontrado. Empezando vacío.");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
     