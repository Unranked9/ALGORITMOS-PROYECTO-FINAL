package arreglo; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import clase.Alumno;



public class ArregloAlumno {
    
    
    private ArrayList<Alumno> alumnos; 

    public ArregloAlumno() {
        alumnos = new ArrayList<>();
        // Cargar  datos guardados al iniciar
        cargarDatos(); 
    }

    // MÉTODOS 
    
   //Adicionar 
    public void adicionar(Alumno alu) {
        // 1. Asigna el código correlativo
        alu.setCodAlumno(generarCodigoCorrelativo());
        // 2. Asigna el estado por defecto
        alu.setEstado(0); // 0 = Registrado
        // 3. Agrega a la lista
        alumnos.add(alu);
        // 4. Graba la lista actualizada en el archivo  A
        grabarDatos(); 
    }

    /*Retorna la lista completa de alumnos */
    public ArrayList<Alumno> listar() {
        return alumnos;
    }

   //Buscar por codigo   
    public Alumno buscarPorCodigo(int codigo) {
        for (Alumno a : alumnos) {
            if (a.getCodAlumno() == codigo)
                return a;
        }
        return null;
    }

   //Buscar por DNI ("Validador")   
    public Alumno buscarPorDni(String dni) {
        for (Alumno a : alumnos) {
            if (a.getDni().equals(dni)) {
                return a;
            }
        }
        return null;
    }

    // Metodo modificar 
    public void modificar(Alumno aluModificado) {
        // Busca al alumno por su código
        Alumno aluExistente = buscarPorCodigo(aluModificado.getCodAlumno());
        
        if (aluExistente != null) {
            // Actualiza solo los campos permitidos
            aluExistente.setNombres(aluModificado.getNombres());
            aluExistente.setApellidos(aluModificado.getApellidos());
            aluExistente.setEdad(aluModificado.getEdad());
            aluExistente.setCelular(aluModificado.getCelular());
            
            // Graba los cambios
            grabarDatos();
        }
    }

    // Eliminar un alumno cuando su estado es registrado (0)  
     
    public boolean eliminar(int codigo) {
        Alumno a = buscarPorCodigo(codigo); 
        if (a != null && a.getEstado() == 0) {
            alumnos.remove(a);
            // Grabar el cambio  
            grabarDatos(); 
            return true;
        }
        return false;
    }

    //Nombre más especifico     
    public int generarCodigoCorrelativo() {
        if (alumnos.isEmpty()) return 202010001;
        return alumnos.get(alumnos.size() - 1).getCodAlumno() + 1;
    }

    /*Retorna el tamaño de la lista*/
    public int tamanio() {
        return alumnos.size();
    }
    
    // METODOS  CARGAR-GRABAR     

    public void grabarDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("alumnos.txt"))) {
            for (Alumno alu : alumnos) {
                String linea = alu.getCodAlumno() + "," + alu.getNombres() + "," +
                               alu.getApellidos() + "," + alu.getDni() + "," +
                               alu.getEdad() + "," + alu.getCelular() + "," +
                               alu.getEstado();
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();  //"Si ocurre un error(e), imprime lo que sucedió en la consola"
        }
    }

    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int cod = Integer.parseInt(datos[0]);
                String nom = datos[1];
                String ape = datos[2];
                String dni = datos[3];
                int edad = Integer.parseInt(datos[4]);
                int cel = Integer.parseInt(datos[5]);
                int est = Integer.parseInt(datos[6]);
                
                Alumno alu = new Alumno(cod, nom, ape, dni, edad, cel, est);
                alumnos.add(alu);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo alumnos.txt no encontrado. Empezando vacío.");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); //"Si ocurre un error(e), imprime lo que sucedió en la consola"
        }
    }
} 