package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter; // CAMBIO: Para efecto hover
import java.awt.event.MouseEvent;  // CAMBIO: Para efecto hover
import javax.swing.JButton;
// import javax.swing.JComboBox; // CAMBIO: Eliminado ComboBox TipoRetiro
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder; // CAMBIO: Usaremos TitledBorder
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel; // CAMBIO: Para manejar selección de tabla

// CAMBIO: Importar Arreglos y Modelos necesarios
import arreglo.ArregloAlumno;
import arreglo.ArregloMatriculas;
import arreglo.ArregloRetiro;
import clase.Alumno;
import clase.Matricula;
import clase.Retiro;

// CAMBIO: Ya no implementa ActionListener directamente
public class RetiroWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- CAMPOS GLOBALES---
    private JPanel contentPane;
    private JTable tablaRetiros;
    private JTextField txtNumMatricula;  
    private JTextField txtCodAlumno;     
    private JTextField txtNombreAlumno;  
    private JTextField txtNumRetiro;     
    private JTextField txtFechaRetiro;   
    private JTextField txtHoraRetiro;    
    private JButton btnRegistrarRetiro; 
    private JButton btnCancelarRetiro;  
    private JButton btnLimpiar;         
    private JButton btnBuscarMatricula; 
    private JLabel lblTitulo;
    private JPanel panelDatos;
    private JLabel lblNumMatricula;
    private JLabel lblCodAlumno;
    private JLabel lblNombreAlumno;
    private JLabel lblNumRetiro;
    private JLabel lblFechaRetiro;
    private JLabel lblHoraRetiro;
    private JScrollPane scroll;
    private JPanel panelBotones;
    private DefaultTableModel modeloTabla; 
    private ArregloAlumno arrAlumnos;
    private ArregloMatriculas arrMatriculas;
    private ArregloRetiro arrRetiros;

    

    /* Constructor MODIFICADO para recibir los Arreglos  */
    public RetiroWindow(ArregloAlumno arrAlumnos, ArregloMatriculas arrMatriculas, ArregloRetiro arrRetiros) { 
        //  Guarda los Arreglos 
        this.arrAlumnos = arrAlumnos;        
        this.arrMatriculas = arrMatriculas;  
        this.arrRetiros = arrRetiros;         

        setTitle("Gestión de Retiros - EDUCARE360"); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 680); 
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        configurarEventos();

        //  Cargar datos iniciales en la tabla  
        actualizarTabla();
        limpiarCampos(); // Inicia limpio
    }

    
    private void inicializarComponentes() {
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblTitulo = new JLabel("Registro y Gestión de Retiros de Alumnos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(0, 159, 191));
        lblTitulo.setBounds(220, 10, 550, 40); 

        // PANEL DATOS
        panelDatos = new JPanel();
        panelDatos.setBackground(new Color(250, 245, 245)); 
        panelDatos.setBorder(new TitledBorder(
                new LineBorder(new Color(191, 0, 0), 2, true), 
                "Datos del Retiro", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(130, 0, 0))); 
        panelDatos.setBounds(70, 70, 800, 200); 
        panelDatos.setLayout(null);
        contentPane.add(panelDatos);

        // Fila 1: N° Matrícula a retirar y Botón Buscar
        lblNumMatricula = new JLabel("N° Matrícula a Retirar:");
        lblNumMatricula.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNumMatricula.setBounds(40, 30, 200, 25);
        panelDatos.add(lblNumMatricula);

        txtNumMatricula = new JTextField(); 
        txtNumMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNumMatricula.setBounds(240, 30, 150, 30);
        panelDatos.add(txtNumMatricula);

        btnBuscarMatricula = new JButton("Buscar Datos"); 
        btnBuscarMatricula.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscarMatricula.setBounds(410, 30, 150, 30);
        estiloBoton(btnBuscarMatricula, new Color(220, 220, 255), new Color(180, 180, 240));
        panelDatos.add(btnBuscarMatricula);

        // Fila 2: Datos informativos del alumno (solo lectura)
        lblCodAlumno = new JLabel("Código Alumno:");
        lblCodAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCodAlumno.setBounds(40, 75, 140, 25);
        panelDatos.add(lblCodAlumno);

        txtCodAlumno = new JTextField();
        txtCodAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCodAlumno.setBounds(180, 75, 140, 30);
        txtCodAlumno.setEditable(false);
        txtCodAlumno.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtCodAlumno);

        lblNombreAlumno = new JLabel("Nombre Alumno:");
        lblNombreAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNombreAlumno.setBounds(360, 75, 130, 25);
        panelDatos.add(lblNombreAlumno);

        txtNombreAlumno = new JTextField();
        txtNombreAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNombreAlumno.setBounds(500, 75, 250, 30); 
        txtNombreAlumno.setEditable(false);
        txtNombreAlumno.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtNombreAlumno);

        // Fila 3: Datos del Retiro (solo lectura, se llenan al registrar o seleccionar tabla)
        lblNumRetiro = new JLabel("N° Retiro:");
        lblNumRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNumRetiro.setBounds(40, 120, 140, 25);
        panelDatos.add(lblNumRetiro);

        txtNumRetiro = new JTextField();
        txtNumRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNumRetiro.setBounds(180, 120, 140, 30);
        txtNumRetiro.setEditable(false);
        txtNumRetiro.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtNumRetiro);

        lblFechaRetiro = new JLabel("Fecha Retiro:");
        lblFechaRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblFechaRetiro.setBounds(360, 120, 130, 25);
        panelDatos.add(lblFechaRetiro);

        txtFechaRetiro = new JTextField();
        txtFechaRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtFechaRetiro.setBounds(500, 120, 120, 30);
        txtFechaRetiro.setEditable(false);
        txtFechaRetiro.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtFechaRetiro);

        lblHoraRetiro = new JLabel("Hora:");
        lblHoraRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblHoraRetiro.setBounds(640, 120, 50, 25);
        panelDatos.add(lblHoraRetiro);

        txtHoraRetiro = new JTextField();
        txtHoraRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtHoraRetiro.setBounds(690, 120, 90, 30);
        txtHoraRetiro.setEditable(false);
        txtHoraRetiro.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtHoraRetiro);

        

        // TABLA DE RETIROS REGISTRADOS
        
        String[] columnas = { "N° Retiro", "N° Matrícula", "Fecha", "Hora", "Cod. Alumno", "Nombre Alumno" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
             @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaRetiros = new JTable(modeloTabla);
        tablaRetiros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaRetiros.setRowHeight(25);
        tablaRetiros.setGridColor(new Color(220, 220, 220));
        tablaRetiros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scroll = new JScrollPane(tablaRetiros);
        scroll.setBounds(70, 290, 800, 200);
        contentPane.add(scroll);

        // PANEL BOTONES
        panelBotones = new JPanel();
        panelBotones.setBackground(new Color(250, 245, 245)); 
        panelBotones.setBorder(new LineBorder(new Color(191, 0, 0), 2, true));
        panelBotones.setBounds(70, 510, 800, 70); 
        panelBotones.setLayout(null);
        contentPane.add(panelBotones);

        
        btnRegistrarRetiro = new JButton("Registrar Retiro");
        btnRegistrarRetiro.setBounds(50, 15, 180, 40);
        estiloBoton(btnRegistrarRetiro, new Color(0, 159, 191), new Color(0, 200, 230));
        panelBotones.add(btnRegistrarRetiro);

        btnCancelarRetiro = new JButton("Cancelar Retiro");
        btnCancelarRetiro.setBounds(260, 15, 180, 40);
        estiloBoton(btnCancelarRetiro, new Color(220, 50, 50), new Color(250, 80, 80)); 
        panelBotones.add(btnCancelarRetiro);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(470, 15, 120, 40);
        estiloBoton(btnLimpiar, new Color(150,150,150), new Color(180,180,180)); 
        panelBotones.add(btnLimpiar);

        
    }

    // --- EVENTOS (Conectados a métodos privados) ---
    private void configurarEventos() {
        btnRegistrarRetiro.addActionListener(e -> registrarRetiro());
        btnCancelarRetiro.addActionListener(e -> cancelarRetiro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnBuscarMatricula.addActionListener(e -> buscarMatriculaParaRetirar());

        // Listener para la tabla (cargar datos de retiro al seleccionar)
        tablaRetiros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosFormularioDesdeTabla();
            }
        });
    }

    // --- LÓGICA FUNCIONAL (Ahora usa los 3 Arreglos ) ---

    /** Busca una matrícula por su número y carga los datos del alumno asociado */
    private void buscarMatriculaParaRetirar() {
        String sNumMatricula = txtNumMatricula.getText().trim();
        if (sNumMatricula.isEmpty()) {
            mensajeAdvertencia("Ingrese el N° de Matrícula del alumno que desea retirar.");
            limpiarCamposAlumno(); // Limpia campos de alumno si no hay número
            return;
        }

        try {
            int numMatricula = Integer.parseInt(sNumMatricula);

            // Buscar la matrícula
            Matricula mat = arrMatriculas.buscarPorNumero(numMatricula);

            if (mat != null) {
                // Si existe, buscar al alumno asociado
                Alumno alu = arrAlumnos.buscarPorCodigo(mat.getCodAlumno());
                if (alu != null) {
                    // Cargar datos del alumno en la GUI (solo lectura)
                    txtCodAlumno.setText(String.valueOf(alu.getCodAlumno()));
                    txtNombreAlumno.setText(alu.getNombres() + " " + alu.getApellidos());
                    // Habilitar botón de registrar retiro si el alumno está matriculado
                    btnRegistrarRetiro.setEnabled(alu.getEstado() == 1);
                     if(alu.getEstado() != 1){
                         mensajeAdvertencia("El alumno asociado a esta matrícula no está actualmente 'Matriculado'.\nEstado actual: " + obtenerTextoEstado(alu.getEstado()));
                     }
                } else {
                    mensajeError("Error: Se encontró la matrícula pero no el alumno asociado.");
                    limpiarCamposAlumno();
                }
            } else {
                mensajeAdvertencia("No se encontró una matrícula con el número " + numMatricula + ".");
                limpiarCamposAlumno();
            }
        } catch (NumberFormatException ex) {
            mensajeError("El N° de Matrícula debe ser un número.");
            limpiarCamposAlumno();
        }
    }

    /** Registra un retiro para la matrícula ingresada */
    private void registrarRetiro() {
        String sNumMatricula = txtNumMatricula.getText().trim();

        // Validar que se haya buscado una matrícula válida
        if (sNumMatricula.isEmpty() || txtCodAlumno.getText().isEmpty()) {
            mensajeAdvertencia("Primero busque la matrícula del alumno que desea retirar.");
            return;
        }

        try {
            int numMatricula = Integer.parseInt(sNumMatricula);

            // Doble chequeo (la matrícula y el alumno deberían existir si btn está habilitado)
             Matricula mat = arrMatriculas.buscarPorNumero(numMatricula);
             if (mat == null) {
                 mensajeError("Error: No se encontró la matrícula " + numMatricula + ".");
                 return;
             }
             Alumno alu = arrAlumnos.buscarPorCodigo(mat.getCodAlumno());
             if (alu == null || alu.getEstado() != 1) { // Asegurarse que sigue matriculado
                 mensajeError("El alumno ya no está en estado 'Matriculado'. No se puede registrar el retiro.");
                 btnRegistrarRetiro.setEnabled(false); // Deshabilitar por si acaso
                 return;
             }

            // Confirmación
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de registrar el retiro para la matrícula N° " + numMatricula +
                    " del alumno " + txtNombreAlumno.getText() + "?\n" +
                    "(Esto cambiará el estado del alumno a 'Retirado')",
                    "Confirmar Retiro", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                // Crear objeto Retiro (solo con numMatricula, el resto lo genera el Arreglo)
                Retiro nuevoRetiro = new Retiro(0, numMatricula, "", "");

                // LLAMAR A LA RAMA 2: Adicionar usando ArregloRetiros
                arrRetiros.adicionar(nuevoRetiro, arrMatriculas, arrAlumnos);

                // Informar y actualizar GUI
                mensajeExito("Retiro registrado con éxito.\nN° Retiro: " + nuevoRetiro.getNumRetiro() +
                             "\nEl estado del alumno ahora es 'Retirado'.");
                limpiarCampos();
                actualizarTabla(); // Refresca la tabla de retiros
            }

        } catch (NumberFormatException ex) {
            mensajeError("El N° de Matrícula debe ser un número.");
        }
    }

    /** Cancela el retiro seleccionado en la tabla */
    private void cancelarRetiro() {
        int filaSeleccionada = tablaRetiros.getSelectedRow();
        if (filaSeleccionada == -1) {
            mensajeAdvertencia("Seleccione un retiro de la tabla para cancelar.");
            return;
        }

        // Obtener el N° de Retiro de la fila seleccionada
        int numRetiro = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Buscar el objeto Retiro completo
        Retiro retiroACancelar = arrRetiros.buscarPorNumero(numRetiro);
        if (retiroACancelar == null) {
            mensajeError("Error: No se encontró el retiro seleccionado en la lista interna.");
            return;
        }

        // Confirmación
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de cancelar el retiro N° " + numRetiro + "?\n" +
                "(Esto intentará revertir el estado del alumno a 'Matriculado')",
                "Confirmar Cancelación de Retiro", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            //  Eliminar (Cancelar) el retiro
            arrRetiros.eliminar(retiroACancelar, arrMatriculas, arrAlumnos);

            // Informar y actualizar GUI
            mensajeExito("Retiro N° " + numRetiro + " cancelado con éxito.\n" +
                         "Se intentó revertir el estado del alumno a 'Matriculado'.");
            limpiarCampos();
            actualizarTabla();
        }
    }

    /** Carga los datos del retiro seleccionado en la tabla a los campos informativos */
    private void cargarDatosFormularioDesdeTabla() {
        int filaSeleccionada = tablaRetiros.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Datos del retiro
            txtNumRetiro.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNumMatricula.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString()); // N° Matrícula retirada
            txtFechaRetiro.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtHoraRetiro.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            // Datos del alumno asociado (ya estaban en la tabla)
            txtCodAlumno.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtNombreAlumno.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());

            // Habilitar/Deshabilitar botones según contexto
            btnRegistrarRetiro.setEnabled(false); // No se puede registrar si se seleccionó uno existente
            txtNumMatricula.setEditable(false); // Bloquear N° Matrícula al cargar
        }
    }

    // MÉTODOS DE APOYO

    /** Limpia todos los campos del formulario */
    private void limpiarCampos() {
        txtNumMatricula.setText("");
        txtCodAlumno.setText("");
        txtNombreAlumno.setText("");
        txtNumRetiro.setText("");
        txtFechaRetiro.setText("");
        txtHoraRetiro.setText("");
        tablaRetiros.clearSelection();
        // Habilitar/Deshabilitar botones para estado inicial
        btnRegistrarRetiro.setEnabled(false); // Se habilita solo después de buscar matrícula válida
        txtNumMatricula.setEditable(true); // Permitir ingresar N° Matrícula
        txtNumMatricula.requestFocus();
    }
    
    /** Limpia solo los campos relacionados al alumno */
    private void limpiarCamposAlumno(){
        txtCodAlumno.setText("");
        txtNombreAlumno.setText("");
        btnRegistrarRetiro.setEnabled(false); // Deshabilitar si no se encontró alumno válido
    }

    
    private void estiloBoton(JButton boton, Color colorBase, Color colorHover) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(colorBase);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.setBorder(new LineBorder(colorBase.darker(), 1, true));
        agregarEfectoHover(boton, colorBase, colorHover);
    }

    
    private void agregarEfectoHover(JButton boton, Color colorOriginal, Color colorHover) {
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorOriginal);
            }
        });
    }

    /** Refresca la JTable con los datos actuales del ArregloRetiro */
    private void actualizarTabla() {
        // Limpia la tabla
        modeloTabla.setRowCount(0);

        // Recorre el Arreglo de Retiros
        for (Retiro ret : arrRetiros.listar()) {
            // Busca la matrícula asociada para obtener datos del alumno
            Matricula mat = arrMatriculas.buscarPorNumero(ret.getNumMatricula());
            Alumno alu = (mat != null) ? arrAlumnos.buscarPorCodigo(mat.getCodAlumno()) : null;

            // Crea una fila de datos para la tabla
            Object[] fila = new Object[6]; // 6 columnas
            fila[0] = ret.getNumRetiro();
            fila[1] = ret.getNumMatricula();
            fila[2] = ret.getFecha();
            fila[3] = ret.getHora();
            fila[4] = (alu != null) ? alu.getCodAlumno() : "N/A"; // Código Alumno
            fila[5] = (alu != null) ? (alu.getNombres() + " " + alu.getApellidos()) : "Alumno no encontrado"; // Nombre

            // Añade la fila al modelo de la tabla
            modeloTabla.addRow(fila);
        }
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

    // --- Métodos de Mensajes (Helpers) ---
    private void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void mensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    private void mensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

   
}