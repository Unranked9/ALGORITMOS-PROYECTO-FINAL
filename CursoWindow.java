package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel; // CAMBIO: Para manejar selección de tabla
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

// CAMBIO: Importar Arreglos y Modelo necesarios
import arreglo.ArregloCursos;
import arreglo.ArregloMatriculas;
import clase.Curso;


public class CursoWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- CAMPOS GLOBALES (GUI) ---
    private JPanel contentPane;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCreditos;
    private JTextField txtHoras;
    private JComboBox<String> cboCiclo;
    private JTable tablaCursos;
    private DefaultTableModel modeloTabla;
    private JButton btnAdicionar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar; 
    private JLabel lblTitulo;
    private JPanel panelEncabezado;
    private JPanel panelDatos;
    private JPanel panelAcciones;
    private JScrollPane scrollPane;
    private JLabel lblCodigo, lblNombre, lblCiclo, lblCreditos, lblHoras;
   

    // Variables para guardar los Arreglos 
    private ArregloCursos arrCursos;
    private ArregloMatriculas arrMatriculas;

    /**  Constructor recibe los arreglos  */
    public CursoWindow(ArregloCursos arrCursos, ArregloMatriculas arrMatriculas) { // <--- RECIBE
        //  Guarda los Arreglos ---
        this.arrCursos = arrCursos;         // <--- GUARDA
        this.arrMatriculas = arrMatriculas; // <--- GUARDA

        setTitle("Mantenimiento de Cursos - EDUCARE360");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        configurarEventos();

        // --- CAMBIO: Cargar datos iniciales en la tabla (Paso 3.4) ---
        actualizarTabla();
    }

    // --- INTERFAZ (Modificaciones menores) ---
    private void inicializarComponentes() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        panelEncabezado = new JPanel();
        panelEncabezado.setBackground(new Color(0, 160, 200));
        panelEncabezado.setBounds(0, 0, 1000, 70);
        panelEncabezado.setLayout(null);
        contentPane.add(panelEncabezado);

        lblTitulo = new JLabel("Mantenimiento de Cursos", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setBounds(0, 15, 1000, 40);
        panelEncabezado.add(lblTitulo);

        panelDatos = new JPanel();
        panelDatos.setBackground(new Color(255, 255, 240)); // Color crema suave
        panelDatos.setBorder(new TitledBorder(
                new LineBorder(new Color(0, 150, 180), 2, true),
                "Datos del Curso",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(0, 100, 130)));
        panelDatos.setBounds(30, 90, 600, 200);
        panelDatos.setLayout(null);
        contentPane.add(panelDatos);

        lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCodigo.setBounds(30, 35, 80, 25);
        panelDatos.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(120, 35, 150, 25);
        panelDatos.add(txtCodigo);

        lblNombre = new JLabel("Nombre Asignatura:");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNombre.setBounds(30, 70, 160, 25);
        panelDatos.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 70, 390, 25); 
        panelDatos.add(txtNombre);

        lblCiclo = new JLabel("Ciclo:");
        lblCiclo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCiclo.setBounds(30, 105, 80, 25);
        panelDatos.add(lblCiclo);

      
        String[] ciclos = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};
        cboCiclo = new JComboBox<>(ciclos);
        cboCiclo.setBounds(120, 105, 150, 25);
        panelDatos.add(cboCiclo);

        lblCreditos = new JLabel("Nro. Créditos:");
        lblCreditos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCreditos.setBounds(300, 105, 120, 25); 
        panelDatos.add(lblCreditos);

        txtCreditos = new JTextField();
        txtCreditos.setBounds(410, 105, 80, 25); 
        panelDatos.add(txtCreditos);

        lblHoras = new JLabel("Cant. Horas:");
        lblHoras.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblHoras.setBounds(30, 140, 100, 25); 
        panelDatos.add(lblHoras);

        txtHoras = new JTextField();
        txtHoras.setBounds(130, 140, 80, 25); 
        panelDatos.add(txtHoras);

        // --- Panel de Acciones ---
        panelAcciones = new JPanel();
        panelAcciones.setBackground(new Color(250, 250, 230)); 
        panelAcciones.setBorder(new TitledBorder(
                new LineBorder(new Color(0, 150, 180), 2, true),
                "Acciones", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(0, 100, 130)));
        panelAcciones.setBounds(650, 90, 300, 200);
        panelAcciones.setLayout(new GridLayout(3, 1, 15, 15)); 
       
        panelAcciones.setBorder(BorderFactory.createCompoundBorder(
                panelAcciones.getBorder(),
                BorderFactory.createEmptyBorder(10, 15, 10, 15))); 
        contentPane.add(panelAcciones);

        btnAdicionar = crearBoton("Adicionar", "/img/add.png");
        btnModificar = crearBoton("Modificar", "/img/edit.png");
        btnEliminar = crearBoton("Eliminar", "/img/delete.png");
        btnLimpiar = crearBoton("Limpiar", "/img/clear.png"); 

        

        panelAcciones.add(btnAdicionar);
        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnLimpiar); 
        

       
        String[] columnas = {"Código", "Asignatura", "Ciclo", "Créditos", "Horas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // No editable
        };
        tablaCursos = new JTable(modeloTabla);
        tablaCursos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaCursos.setRowHeight(25);
        tablaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selección única

        scrollPane = new JScrollPane(tablaCursos);
        scrollPane.setBounds(30, 310, 920, 200);
        contentPane.add(scrollPane);
    }

    // --- EVENTOS (Conectados a métodos privados) ---
    private void configurarEventos() {
        btnAdicionar.addActionListener(e -> adicionarCurso());
        btnModificar.addActionListener(e -> modificarCurso());
        btnEliminar.addActionListener(e -> eliminarCurso());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Listener para la tabla (cargar datos al seleccionar)
        tablaCursos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosFormularioDesdeTabla();
            }
        });
    }

    // --- LÓGICA FUNCIONAL ( usa arrCursos y arrMatriculas ) 

    /** Añade un nuevo curso usando los datos de la GUI */
    private void adicionarCurso() {
        // 1. Leer datos de la GUI
        String sCodigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        int cicloIndex = cboCiclo.getSelectedIndex(); // 0 para "Primero", 1 para "Segundo", ...
        String sCreditos = txtCreditos.getText().trim();
        String sHoras = txtHoras.getText().trim();

        // 2. Validar Entradas
        if (sCodigo.isEmpty() || nombre.isEmpty() || sCreditos.isEmpty() || sHoras.isEmpty()) {
            mensajeError("Debe completar todos los campos.");
            return;
        }
        if (!sCodigo.matches("\\d{4}")) { // Valida exactamente 4 dígitos numéricos
            mensajeError("El Código debe tener 4 dígitos numéricos.");
            return;
        }

        try {
            // 3. Parsear (convertir) datos
            int codigo = Integer.parseInt(sCodigo);
            int creditos = Integer.parseInt(sCreditos);
            int horas = Integer.parseInt(sHoras);

            // Validar rangos
            if (creditos <= 0 || horas <= 0) {
                 mensajeError("Créditos y Horas deben ser números positivos.");
                 return;
            }
             if (cicloIndex < 0 || cicloIndex > 5) { // Validación extra del índice del ciclo
                 mensajeError("Seleccione un ciclo válido.");
                 return;
            }

            // 4. Validar Lógica de Negocio (Código único)
            if (arrCursos.buscarPorCodigo(codigo) != null) {
                mensajeError("Ya existe un curso registrado con ese código.");
                return;
            }

            // 5. Crear el nuevo objeto Curso (Rama 1)
            Curso nuevoCurso = new Curso(codigo, nombre, cicloIndex, creditos, horas);

            // 6. Añadir usando la lógica de ArregloCurso (Rama 2)
            arrCursos.adicionar(nuevoCurso); // Este método ya ordena y graba

            // 7. Informar y actualizar GUI
            mensajeExito("Curso adicionado y lista ordenada con éxito.");
            limpiarCampos();
            actualizarTabla(); // Refresca la tabla

        } catch (NumberFormatException ex) {
            mensajeError("Código, Créditos y Horas deben ser números válidos.");
        }
    }

    /** Modifica el curso seleccionado en la tabla */
    private void modificarCurso() {
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mensajeAdvertencia("Seleccione un curso de la tabla para modificar.");
            return;
        }

        // Obtener el código original (no se puede modificar)
        int codigo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Leer datos modificados de la GUI
        String nombre = txtNombre.getText().trim();
        int cicloIndex = cboCiclo.getSelectedIndex();
        String sCreditos = txtCreditos.getText().trim();
        String sHoras = txtHoras.getText().trim();

        // Validar entradas
        if (nombre.isEmpty() || sCreditos.isEmpty() || sHoras.isEmpty()) {
            mensajeError("Los campos Nombre, Créditos y Horas no pueden estar vacíos.");
            return;
        }

        try {
            // Parsear números
            int creditos = Integer.parseInt(sCreditos);
            int horas = Integer.parseInt(sHoras);
            if (creditos <= 0 || horas <= 0) {
                 mensajeError("Créditos y Horas deben ser números positivos.");
                 return;
            }
            if (cicloIndex < 0 || cicloIndex > 5) {
                 mensajeError("Seleccione un ciclo válido.");
                 return;
            }

            // Crear un objeto Curso TEMPORAL con los datos modificados
            Curso cursoModificado = new Curso(codigo, nombre, cicloIndex, creditos, horas);

            // LLAMAR A LA RAMA 2: Modificar en el Arreglo
            arrCursos.modificar(cursoModificado);

            // Informar y actualizar GUI
            mensajeExito("Curso modificado con éxito.");
            limpiarCampos();
            actualizarTabla();

        } catch (NumberFormatException ex) {
            mensajeError("Créditos y Horas deben ser números válidos.");
        }
    }

    /** Elimina el curso seleccionado en la tabla */
    private void eliminarCurso() {
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mensajeAdvertencia("Seleccione un curso de la tabla para eliminar.");
            return;
        }

        // Obtener el código del curso seleccionado
        int codigo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreCurso = modeloTabla.getValueAt(filaSeleccionada, 1).toString();

        // Buscar el objeto Curso completo
        Curso cursoAEliminar = arrCursos.buscarPorCodigo(codigo);
        if (cursoAEliminar == null) {
            mensajeError("Error: No se encontró el curso en la lista interna.");
            return;
        }

        // Confirmación
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar el curso '" + nombreCurso + "' (Código: " + codigo + ")?\n" +
                "(Solo se eliminará si NO tiene alumnos matriculados)",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            // LLAMAR A LA RAMA 2: Intentar eliminar (pasando arrMatriculas para validar)
            boolean eliminado = arrCursos.eliminar(cursoAEliminar, arrMatriculas);

            // Informar resultado y actualizar GUI
            if (eliminado) {
                mensajeExito("Curso eliminado con éxito.");
                limpiarCampos();
                actualizarTabla();
            } else {
                // El método eliminar retornó false
                mensajeError("No se pudo eliminar el curso '" + nombreCurso + "'.\n" +
                             "Motivo: Aún hay alumnos matriculados en él.");
            }
        }
    }

    /** Carga los datos de la fila seleccionada en la tabla a los JTextFields */
    private void cargarDatosFormularioDesdeTabla() {
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            // CAMBIO: Mapear el texto del ciclo al índice del ComboBox
            cboCiclo.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtCreditos.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtHoras.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
        }
    }

    // --- MÉTODOS DE APOYO ---

    /** Limpia los campos del formulario y deselecciona la tabla */
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCreditos.setText("");
        txtHoras.setText("");
        cboCiclo.setSelectedIndex(0); // Selecciona "Primero"
        tablaCursos.clearSelection();
        txtCodigo.requestFocus(); // Pone el cursor en Código
    }

    /** Crea y estiliza un botón con icono */
    private JButton crearBoton(String texto, String rutaIcono) {
        JButton btn = new JButton(texto); // Usa variable local
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(255, 255, 200)); // Color base
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new LineBorder(new Color(0, 150, 180), 2, true));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);   // Texto debajo del icono
        btn.setHorizontalTextPosition(SwingConstants.CENTER); // Texto centrado

        URL url = getClass().getResource(rutaIcono);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        }
        // btn.addActionListener(this); // CAMBIO: Listener se añade en configurarEventos
        agregarHover(btn); // Añade efecto hover
        return btn;
    }

    /** Añade efecto hover (cambio de color) a un botón */
    private void agregarHover(JButton btn) {
        Color colorOriginal = new Color(255, 255, 200);
        Color colorHover = new Color(255, 255, 150); // Más amarillo
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(colorHover);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(colorOriginal);
            }
        });
    }

    /** Refresca la JTable con los datos actuales del ArregloCurso */
    private void actualizarTabla() {
        // Limpia la tabla
        modeloTabla.setRowCount(0);

        // Recorre el Arreglo de Cursos (ordenado por código)
        for (Curso cur : arrCursos.listar()) {
            // Crea una fila de datos para la tabla
            Object[] fila = new Object[5]; // 5 columnas
            fila[0] = String.format("%04d", cur.getCodCurso());
            fila[1] = cur.getAsignatura();
            fila[2] = obtenerTextoCiclo(cur.getCiclo()); // Convierte índice a texto
            fila[3] = cur.getCreditos();
            fila[4] = cur.getHoras();

            // Añade la fila al modelo de la tabla
            modeloTabla.addRow(fila);
        }
    }

    /** Convierte el índice de ciclo (0-5) a texto ("Primero", "Segundo", ...) */
    private String obtenerTextoCiclo(int cicloIndex) {
        String[] ciclos = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto"};
        if (cicloIndex >= 0 && cicloIndex < ciclos.length) {
            return ciclos[cicloIndex];
        }
        return "Desconocido"; // En caso de dato inválido
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