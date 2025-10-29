package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// import java.awt.event.ActionEvent; // No necesario
// import java.awt.event.ActionListener; // No necesario

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
import javax.swing.ListSelectionModel; // Importado si se usa selección

// CAMBIO: Importar TODOS los Arreglos y Modelos necesarios (CON NOMBRES CORRECTOS)
import arreglo.ArregloAlumno;
import arreglo.ArregloCursos; // Plural
import arreglo.ArregloMatriculas; // Plural
import arreglo.ArregloRetiro;
import clase.Alumno;
import clase.Curso;
import clase.Matricula;
import clase.Retiro;


public class ConsultaWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- CAMPOS GLOBALES (GUI) ---
    private JPanel contentPane;
    private JPanel panelBotones;
    private JPanel panelOpciones;
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cboTipoConsulta;
    private JButton btnBuscar;
    private JButton btnSalir;
    private JLabel lblTitulo;
    private JLabel lblTipo;
    private JScrollPane scroll;
    private ArregloAlumno arrAlumnos;
    private ArregloCursos arrCursos; 
    private ArregloMatriculas arrMatriculas; 
    private ArregloRetiro arrRetiros;

    

    
    public ConsultaWindow(ArregloAlumno arrAlumnos, ArregloCursos arrCursos, ArregloMatriculas arrMatriculas, ArregloRetiro arrRetiros) { // <--- RECIBE
        
        this.arrAlumnos = arrAlumnos;         
        this.arrCursos = arrCursos;           
        this.arrMatriculas = arrMatriculas;   
        this.arrRetiros = arrRetiros;         

        setTitle("Consulta de Datos - EDUCARE360");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes(); // Usa el código de inicialización de ConsultaWindow
        configurarEventos();
    }

    // INTERFAZ 
    private void inicializarComponentes() {
        
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblTitulo = new JLabel("Consultas del Sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 159, 191));
        lblTitulo.setBounds(320, 20, 400, 40);
        contentPane.add(lblTitulo);

        panelOpciones = new JPanel();
        panelOpciones.setBackground(new Color(245, 250, 250));
        panelOpciones.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        panelOpciones.setBounds(80, 80, 780, 100);
        panelOpciones.setLayout(null);
        contentPane.add(panelOpciones);

        lblTipo = new JLabel("Tipo de consulta:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTipo.setBounds(40, 35, 150, 25);
        panelOpciones.add(lblTipo);

        cboTipoConsulta = new JComboBox<>();
        cboTipoConsulta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cboTipoConsulta.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{
                    "Alumnos por Estado...",
                    "Todos los Alumnos",
                    "Alumnos Registrados (Pendientes)",
                    "Alumnos Matriculados (Vigentes)",
                    "Alumnos Retirados",
                    "Todos los Cursos",
                    "Todas las Matrículas",
                    "Todos los Retiros"
                }));
        cboTipoConsulta.setBounds(200, 35, 300, 30);
        panelOpciones.add(cboTipoConsulta);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBuscar.setBackground(new Color(0, 159, 191));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setBounds(540, 30, 130, 40);
        panelOpciones.add(btnBuscar);

        String[] columnas = {"ID/Código", "Nombre/Asignatura", "Detalle 1", "Detalle 2 / Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
             @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaDatos = new JTable(modeloTabla);
        tablaDatos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaDatos.setRowHeight(25);
        tablaDatos.setGridColor(new Color(200, 200, 200));
        tablaDatos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaDatos.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(150);

        scroll = new JScrollPane(tablaDatos);
        scroll.setBounds(80, 210, 780, 260);
        contentPane.add(scroll);

        panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 250, 250));
        panelBotones.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        panelBotones.setBounds(80, 490, 780, 70);
        panelBotones.setLayout(null);
        contentPane.add(panelBotones);

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSalir.setBackground(new Color(0, 159, 191));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBounds(320, 15, 130, 40);
        panelBotones.add(btnSalir);
    }

    // --- EVENTOS ---
    private void configurarEventos() {
        // Hover visual
        agregarEfectoHover(btnBuscar);
        agregarEfectoHover(btnSalir);

        // Acciones de botones
        btnBuscar.addActionListener(e -> mostrarDatos());
        btnSalir.addActionListener(e -> dispose());
    }

    // LOGICA

    /** Muestra los datos en la tabla según la consulta seleccionada */
    private void mostrarDatos() {
        String seleccion = (String) cboTipoConsulta.getSelectedItem();
        if (seleccion == null || seleccion.equals("Alumnos por Estado...")) {
            mensajeAdvertencia("Seleccione un tipo de consulta válido.");
            return;
        }

        modeloTabla.setRowCount(0); // Limpia tabla

        try {
            switch (seleccion) {
                case "Todos los Alumnos":
                   
                    for (Alumno alu : this.arrAlumnos.listar()) {
                        modeloTabla.addRow(new Object[]{ alu.getCodAlumno(), alu.getNombres() + " " + alu.getApellidos(), "DNI: " + alu.getDni(), obtenerTextoEstado(alu.getEstado()) });
                    }
                    break;
                case "Alumnos Registrados (Pendientes)":
                    
                    for (Alumno alu : this.arrAlumnos.listar()) {
                        if (alu.getEstado() == 0) {
                            modeloTabla.addRow(new Object[]{ alu.getCodAlumno(), alu.getNombres() + " " + alu.getApellidos(), "DNI: " + alu.getDni(), obtenerTextoEstado(alu.getEstado()) });
                        }
                    }
                    break;
                case "Alumnos Matriculados (Vigentes)":
                     
                     for (Alumno alu : this.arrAlumnos.listar()) {
                        if (alu.getEstado() == 1) {
                            Matricula mat = this.arrMatriculas.buscarPorCodAlumno(alu.getCodAlumno());
                            Curso cur = (mat != null) ? this.arrCursos.buscarPorCodigo(mat.getCodCurso()) : null;
                            String detalleCurso = (cur != null) ? "Curso: " + cur.getAsignatura() : "Curso no encontrado";
                            modeloTabla.addRow(new Object[]{ alu.getCodAlumno(), alu.getNombres() + " " + alu.getApellidos(), detalleCurso, obtenerTextoEstado(alu.getEstado()) });
                        }
                    }
                    break;
                 case "Alumnos Retirados":
                    
                     for (Alumno alu : this.arrAlumnos.listar()) {
                        if (alu.getEstado() == 2) {
                             modeloTabla.addRow(new Object[]{ alu.getCodAlumno(), alu.getNombres() + " " + alu.getApellidos(), "DNI: " + alu.getDni(), obtenerTextoEstado(alu.getEstado()) });
                        }
                    }
                    break;
                case "Todos los Cursos":
                   
                    for (Curso cur : this.arrCursos.listar()) {
                        modeloTabla.addRow(new Object[]{ cur.getCodCurso(), cur.getAsignatura(), "Ciclo: " + obtenerTextoCiclo(cur.getCiclo()), "Créditos: " + cur.getCreditos() + ", Horas: " + cur.getHoras() });
                    }
                    break;
                 case "Todas las Matrículas":
                     
                     for (Matricula mat : this.arrMatriculas.listar()) {
                         Alumno alu = this.arrAlumnos.buscarPorCodigo(mat.getCodAlumno());
                         Curso cur = this.arrCursos.buscarPorCodigo(mat.getCodCurso());
                         String nomAlu = (alu != null) ? alu.getNombres() + " " + alu.getApellidos() : "Alumno N/E";
                         String nomCur = (cur != null) ? cur.getAsignatura() : "Curso N/E";
                         modeloTabla.addRow(new Object[]{ mat.getNumMatricula(), nomAlu, "Curso: " + nomCur, "Fecha: " + mat.getFecha() });
                     }
                    break;
                 case "Todos los Retiros":
                      
                      for (Retiro ret : this.arrRetiros.listar()) {
                         Matricula mat = this.arrMatriculas.buscarPorNumero(ret.getNumMatricula());
                         Alumno alu = (mat != null) ? this.arrAlumnos.buscarPorCodigo(mat.getCodAlumno()) : null;
                         String nomAlu = (alu != null) ? alu.getNombres() + " " + alu.getApellidos() : "Alumno N/E";
                         modeloTabla.addRow(new Object[]{ ret.getNumRetiro(), "Matrícula: " + ret.getNumMatricula(), "Alumno: " + nomAlu, "Fecha: " + ret.getFecha() });
                      }
                    break;
                default:
                    mensajeAdvertencia("Consulta no implementada todavía.");
            }

            if (modeloTabla.getRowCount() == 0) {
                mensajeInfo("No se encontraron datos para la consulta seleccionada.");
            }

        } catch (Exception ex) {
            mensajeError("Ocurrió un error al realizar la consulta.");
            ex.printStackTrace();
        }
    }

 // --- MÉTODOS DE APOYO ---

    /** Añade efecto hover a un botón */
    private void agregarEfectoHover(JButton boton) {
        Color colorOriginal = boton.getBackground();
        Color colorHover = new Color(0, 200, 230); 
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorOriginal);
            }
        });
    }

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

    // --- Métodos de Mensajes (Helpers) ---
    private void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void mensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
     private void mensajeInfo(String mensaje) { // Añadido para mensajes informativos
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

} 