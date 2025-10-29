package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter; // CAMBIO: Para hover
import java.awt.event.MouseEvent;  // CAMBIO: Para hover
import java.util.ArrayList; // CAMBIO: Necesario para el reporte por curso
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

// CAMBIO: Importar Arreglos y Modelos necesarios (nombres correctos)
import arreglo.ArregloAlumno;
import arreglo.ArregloCursos;
import arreglo.ArregloMatriculas;
import clase.Alumno;
import clase.Curso;
import clase.Matricula;


public class ReporteWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- CAMPOS GLOBALES (GUI) ---
    private JPanel contentPane;
    private JTable tablaReporte;
    private DefaultTableModel modeloTabla; 
    private JComboBox<String> cboTipoReporte;
    private JButton btnExportar; 
    private JButton btnSalir;
    private JButton btnGenerar;
    private JLabel lblTipo;
    private JPanel panelBotones;
    private JScrollPane scroll;
    private JPanel panelOpciones;
    private JLabel lblTitulo;

    // Variables para guardar los Arreglos 
    private ArregloAlumno arrAlumnos;
    private ArregloCursos arrCursos;
    private ArregloMatriculas arrMatriculas;

  

    /**  Constructor MODIFICADO para recibir los Arreglos  */
    public ReporteWindow(ArregloAlumno arrAlumnos, ArregloCursos arrCursos, ArregloMatriculas arrMatriculas) { 
        //  Guarda los Arreglos 
        this.arrAlumnos = arrAlumnos;         
        this.arrCursos = arrCursos;           
        this.arrMatriculas = arrMatriculas;   
        setTitle("Reportes del Sistema - EDUCARE360");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        configurarEventos();
    }

    
    private void inicializarComponentes() {
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblTitulo = new JLabel("Generación de Reportes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 159, 191));
        lblTitulo.setBounds(300, 20, 400, 40);
        contentPane.add(lblTitulo);

        panelOpciones = new JPanel();
        panelOpciones.setBackground(new Color(245, 250, 250));
        panelOpciones.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        panelOpciones.setBounds(80, 80, 780, 100);
        panelOpciones.setLayout(null);
        contentPane.add(panelOpciones);

        lblTipo = new JLabel("Tipo de reporte:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTipo.setBounds(40, 35, 150, 25);
        panelOpciones.add(lblTipo);

        
        cboTipoReporte = new JComboBox<>(new String[]{
                "Alumnos con matrícula pendiente", 
                "Alumnos con matrícula vigente",   
                "Alumnos matriculados por curso" 
        });
        cboTipoReporte.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cboTipoReporte.setBounds(230, 35, 300, 30);
        panelOpciones.add(cboTipoReporte);

        btnGenerar = new JButton("Generar");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGenerar.setBackground(new Color(0, 159, 191));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setBounds(560, 30, 130, 40);
        panelOpciones.add(btnGenerar);

        // Columnas iniciales genéricas
        String[] columnas = {"Columna 1", "Columna 2", "Columna 3", "Columna 4"};
        modeloTabla = new DefaultTableModel(columnas, 0){
             @Override
            public boolean isCellEditable(int row, int column) { return false; } // No editable
        };
        tablaReporte = new JTable(modeloTabla);
        tablaReporte.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaReporte.setRowHeight(25);
        tablaReporte.setGridColor(new Color(200, 200, 200));
        tablaReporte.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 

        scroll = new JScrollPane(tablaReporte);
        scroll.setBounds(80, 210, 780, 260);
        contentPane.add(scroll);

        panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 250, 250));
        panelBotones.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        panelBotones.setBounds(80, 490, 780, 70);
        panelBotones.setLayout(null);
        contentPane.add(panelBotones);

        btnExportar = new JButton("Exportar"); 
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnExportar.setBackground(new Color(0, 159, 191));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.setBounds(250, 15, 130, 40);
        btnExportar.setEnabled(false); 
        panelBotones.add(btnExportar);

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSalir.setBackground(new Color(0, 159, 191));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBounds(420, 15, 130, 40);
        panelBotones.add(btnSalir);
    }

    // --- EVENTOS ---
    private void configurarEventos() {
        agregarEfectoHover(btnGenerar);
        agregarEfectoHover(btnExportar);
        agregarEfectoHover(btnSalir);

        btnGenerar.addActionListener(e -> generarReporte());
        btnExportar.addActionListener(e -> exportarReporte()); 
        btnSalir.addActionListener(e -> dispose());
    }

    // --- LÓGICA DE NEGOCIO (Conexión con Arreglos ) ---

    
    private void generarReporte() {
        String tipoReporte = (String) cboTipoReporte.getSelectedItem();
        // Validación inicial
        if (tipoReporte == null) {
            mensajeAdvertencia("Por favor, seleccione un tipo de reporte."); 
            return;
        }

        modeloTabla.setRowCount(0); 

        try {
            switch (tipoReporte) {
                case "Alumnos con matrícula pendiente": 
                    // Configura columnas para este reporte
                    modeloTabla.setColumnIdentifiers(new String[]{"Código", "Nombres", "Apellidos", "DNI", "Edad", "Celular"});
                    ajustarColumnas(new int[]{80, 150, 150, 100, 50, 100}); 

                    // Recorre todos los alumnos
                    for (Alumno alu : arrAlumnos.listar()) {
                        // Filtra por estado 'Registrado'
                        if (alu.getEstado() == 0) {
                            modeloTabla.addRow(new Object[]{
                                alu.getCodAlumno(),
                                alu.getNombres(),
                                alu.getApellidos(),
                                alu.getDni(),
                                alu.getEdad(),
                                alu.getCelular()
                            }); 
                        } 
                    } 
                    break; 

                case "Alumnos con matrícula vigente": 
                    // Configura columnas
                    modeloTabla.setColumnIdentifiers(new String[]{"Código Alumno", "Nombres", "Apellidos", "Curso Matriculado", "N° Matrícula"});
                    ajustarColumnas(new int[]{100, 150, 150, 200, 100});

                    // Recorre todos los alumnos
                    for (Alumno alu : arrAlumnos.listar()) {
                        // Filtra por estado 'Matriculado'
                        if (alu.getEstado() == 1) {
                            // Busca la matrícula del alumno
                            Matricula mat = arrMatriculas.buscarPorCodAlumno(alu.getCodAlumno());
                            // Busca el curso de esa matrícula
                            Curso cur = (mat != null) ? arrCursos.buscarPorCodigo(mat.getCodCurso()) : null;
                            // Prepara los datos (manejando nulos)
                            String nombreCurso = (cur != null) ? cur.getAsignatura() : "Curso no encontrado";
                            int numMat = (mat != null) ? mat.getNumMatricula() : -1;

                            // Añade la fila
                            modeloTabla.addRow(new Object[]{
                                alu.getCodAlumno(),
                                alu.getNombres(),
                                alu.getApellidos(),
                                nombreCurso,
                                (numMat != -1) ? String.valueOf(numMat) : "N/A" // Muestra N/A si no hay matrícula
                            }); 
                        } 
                    } 
                    break; 

                case "Alumnos matriculados por curso": 
                    // Configura columnas
                    modeloTabla.setColumnIdentifiers(new String[]{"Código Curso", "Asignatura", "Código Alumno", "Nombre Alumno"});
                    ajustarColumnas(new int[]{100, 250, 100, 250});

                    // Recorre Cursos primero
                    for (Curso cur : arrCursos.listar()) {
                        // Busca matrículas para este curso específico
                        for (Matricula mat : arrMatriculas.buscarPorCodCurso(cur.getCodCurso())) {
                             // Busca el alumno de esa matrícula
                             Alumno alu = arrAlumnos.buscarPorCodigo(mat.getCodAlumno());
                             // Asegura que el alumno exista y esté Matriculado
                             if (alu != null && alu.getEstado() == 1) {
                                 // Añade la fila
                                 modeloTabla.addRow(new Object[]{
                                     cur.getCodCurso(),
                                     cur.getAsignatura(),
                                     alu.getCodAlumno(),
                                     alu.getNombres() + " " + alu.getApellidos()
                                 }); 
                             } 
                        } 
                    } 
                    break; 

                default:
                    // Si el texto del ComboBox no coincide con ningún case
                    mensajeAdvertencia("Tipo de reporte no reconocido.");
                    // No es necesario un break aquí si es el último caso
            } // Cierre del switch

            // Verifica si se generaron filas y habilita/deshabilita Exportar
            if (modeloTabla.getRowCount() == 0) {
                mensajeInfo("No se encontraron datos para el reporte seleccionado.");
                btnExportar.setEnabled(false);
            } else {
                 btnExportar.setEnabled(true);
            }

        } catch (Exception ex) {
            // Captura cualquier error inesperado durante la generación
            mensajeError("Ocurrió un error al generar el reporte.");
            ex.printStackTrace(); // Imprime el error completo en consola para depuración
            btnExportar.setEnabled(false); // Deshabilita exportar si hubo error
        } // Cierre del try-catch
    } // Cierre del método generarReporte

    /** Exporta los datos de la tabla (funcionalidad pendiente) */
    private void exportarReporte() {
         JOptionPane.showMessageDialog(this,
                "La funcionalidad de exportar el reporte (ej. a CSV o PDF)\n" +
                "se implementará en una fase posterior.",
                "Exportar Reporte", JOptionPane.INFORMATION_MESSAGE);
        
    }

    // --- MÉTODOS DE APOYO ---

    /** Ajusta los anchos preferidos de las columnas de la tabla */
    private void ajustarColumnas(int[] anchos) {
        for (int i = 0; i < anchos.length && i < tablaReporte.getColumnCount(); i++) {
            tablaReporte.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    /** Añade efecto hover a un botón */
    private void agregarEfectoHover(JButton boton) {
        Color colorOriginal = boton.getBackground();
        Color colorHover = new Color(0, 200, 230); // Azul claro
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if(boton.isEnabled()) boton.setBackground(colorHover);
            }
            public void mouseExited(MouseEvent e) {
                 if(boton.isEnabled()) boton.setBackground(colorOriginal);
            }
        });
    }

    // --- Métodos de Mensajes (Helpers) ---
    private void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void mensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
     private void mensajeInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

   

     // CAMBIO: Añadidos helpers que faltaban de ConsultaWindow (si los necesitas aquí)
     /** Convierte el código de estado (0, 1, 2) a texto legible */
    private String obtenerTextoEstado(int estado) {
        switch (estado) {
            case 0: return "Registrado";
            case 1: return "Matriculado";
            case 2: return "Retirado";
            default: return "Desconocido";
        }
    }

    /** Convierte el índice de ciclo (0-5) a texto ("Primero", "Segundo", ...) */
    private String obtenerTextoCiclo(int cicloIndex) {
        String[] ciclos = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};
        if (cicloIndex >= 0 && cicloIndex < ciclos.length) {
            return ciclos[cicloIndex];
        }
        return "Desconocido";
    }

}