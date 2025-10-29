package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
// import javax.swing.JComboBox; // CAMBIO: Eliminado ComboBox
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel; // CAMBIO: Para manejar selección de tabla

// CAMBIO: Importar Arreglo y Modelo necesarios
import arreglo.ArregloAlumno;
import clase.Alumno;


public class AlumnoWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- CAMPOS GLOBALES (GUI) ---
    private JPanel pnlPrincipal;
    private JPanel pnlDatos;
    private JPanel pnlBotones;
    private JTable tblAlumnos;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtEdad;
    private JTextField txtCelular;
    private JButton btnAdicionar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar; 
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblDni;
    private JLabel lblEdad;
    private JLabel lblCelular;
    private JScrollPane scroll;
    private DefaultTableModel modeloTabla; 

    
    private ArregloAlumno arrAlumnos;

 

    /* El constructor recibe el ArregloAlumno  */
    public AlumnoWindow(ArregloAlumno arrAlumnos) { 
        //  Guarda el Arreglo en la variable de esta clase 
        this.arrAlumnos = arrAlumnos; 

        setTitle("Mantenimiento de Alumnos - EDUCARE360");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(950, 650); 
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        configurarEventos(); 

        // --- CAMBIO: Cargar datos iniciales en la tabla ---
        actualizarTabla();
    }

    // --- COMPONENTES (Modificado para reflejar el Modelo Alumno) ---
    private void inicializarComponentes() {
        pnlPrincipal = new JPanel();
        pnlPrincipal.setBackground(Color.WHITE);
        pnlPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlPrincipal.setLayout(null);
        setContentPane(pnlPrincipal);

        lblTitulo = new JLabel("Mantenimiento de Alumnos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(0, 159, 191));
        lblTitulo.setBounds(300, 10, 400, 40);
        pnlPrincipal.add(lblTitulo);

        pnlDatos = new JPanel();
        pnlDatos.setBackground(new Color(245, 250, 250));
        pnlDatos.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        pnlDatos.setBounds(70, 70, 800, 190); // Aumentado alto para nuevos campos
        pnlDatos.setLayout(null);
        pnlPrincipal.add(pnlDatos);

        // Fila 1
        lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCodigo.setBounds(40, 30, 100, 25);
        pnlDatos.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCodigo.setBounds(130, 30, 120, 30);
        // CAMBIO: Código no editable, se genera automáticamente
        txtCodigo.setEditable(false);
        txtCodigo.setFocusable(false); // Evita que se pueda seleccionar
        txtCodigo.setBackground(Color.LIGHT_GRAY); // Indica visualmente que no es editable
        pnlDatos.add(txtCodigo);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setBounds(280, 30, 100, 25);
        pnlDatos.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNombre.setBounds(370, 30, 160, 30);
        pnlDatos.add(txtNombre);

        lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblApellido.setBounds(560, 30, 100, 25); // Movido a la derecha
        pnlDatos.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtApellido.setBounds(650, 30, 120, 30); // Ajustado ancho
        pnlDatos.add(txtApellido);

        // Fila 2
        lblDni = new JLabel("DNI/CE:"); // CAMBIO: Nuevo campo
        lblDni.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDni.setBounds(40, 80, 100, 25);
        pnlDatos.add(lblDni);

        txtDni = new JTextField(); // CAMBIO: Nuevo campo
        txtDni.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtDni.setBounds(130, 80, 120, 30);
        pnlDatos.add(txtDni);

        lblEdad = new JLabel("Edad:"); // CAMBIO: Nuevo campo
        lblEdad.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEdad.setBounds(280, 80, 100, 25);
        pnlDatos.add(lblEdad);

        txtEdad = new JTextField(); // CAMBIO: Nuevo campo
        txtEdad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEdad.setBounds(370, 80, 80, 30); // Más corto
        pnlDatos.add(txtEdad);

        lblCelular = new JLabel("Celular:"); // CAMBIO: Nuevo campo
        lblCelular.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCelular.setBounds(480, 80, 100, 25);
        pnlDatos.add(lblCelular);

        txtCelular = new JTextField(); // CAMBIO: Nuevo campo
        txtCelular.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCelular.setBounds(570, 80, 120, 30);
        pnlDatos.add(txtCelular);

 

        // Columnas de la tabla ajustadas al modelo Alumno
        String[] columnas = {"Código", "Nombres", "Apellidos", "DNI", "Edad", "Celular", "Estado"};
        // Modelo de tabla como variable global para fácil acceso
        modeloTabla = new DefaultTableModel(columnas, 0) {
             // Para evitar que se editen las celdas directamente en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tblAlumnos = new JTable(modeloTabla);
        tblAlumnos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblAlumnos.setRowHeight(25);
        tblAlumnos.setGridColor(new Color(220, 220, 220));
        tblAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 

        scroll = new JScrollPane(tblAlumnos);
        scroll.setBounds(70, 280, 800, 200); 
        pnlPrincipal.add(scroll);

        pnlBotones = new JPanel();
        pnlBotones.setBackground(new Color(245, 250, 250));
        pnlBotones.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        pnlBotones.setBounds(70, 500, 800, 70); 
        pnlBotones.setLayout(null);
        pnlPrincipal.add(pnlBotones);

        //  Posiciones de botones ajustadas, añadido botón Limpiar
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(40, 15, 120, 40);
        estiloBoton(btnAdicionar);
        pnlBotones.add(btnAdicionar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(180, 15, 120, 40);
        estiloBoton(btnModificar);
        pnlBotones.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(320, 15, 120, 40);
        estiloBoton(btnEliminar);
        pnlBotones.add(btnEliminar);

   

        btnLimpiar = new JButton("Limpiar"); 
        btnLimpiar.setBounds(460, 15, 120, 40);
        estiloBoton(btnLimpiar);
        pnlBotones.add(btnLimpiar);
    }

    // --- EVENTOS (Conectados a métodos privados de lógica) ---
    private void configurarEventos() {
        // Listeners para botones
        btnAdicionar.addActionListener(e -> adicionarAlumno());
        btnModificar.addActionListener(e -> modificarAlumno());
        btnEliminar.addActionListener(e -> eliminarAlumno());
        btnLimpiar.addActionListener(e -> limpiarCampos());

       
        tblAlumnos.getSelectionModel().addListSelectionListener(e -> {
            
            if (!e.getValueIsAdjusting()) {
                cargarDatosFormularioDesdeTabla();
            }
        });
    }

    // --- LÓGICA FUNCIONAL  ---

    /* Añade un nuevo alumno usando los datos de los JTextFields */
    private void adicionarAlumno() {
        // 1. Leer datos de la GUI
        String nombres = txtNombre.getText().trim();
        String apellidos = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String sEdad = txtEdad.getText().trim();
        String sCelular = txtCelular.getText().trim();
        
     // Validacion(no numeros en campo nombre)
        if (nombres.matches(".*\\d+.*")) {
            mensajeError("El nombre no puede contener números.");
            txtNombre.requestFocus(); // Pone el foco en el campo incorrecto
            return;
        }
     // Validacion(no numeros en campo apellidos)
        if (apellidos.matches(".*\\d+.*")) {
            mensajeError("El apellido no puede contener números.");
            txtApellido.requestFocus();
            return;
        }

        // 2. Validar Entradas básicas
        if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || sEdad.isEmpty() || sCelular.isEmpty()) {
            mensajeError("Debe completar todos los campos (excepto Código).");
            return;
        }
      
        
     // Verifica que sean solo números y longitud entre 8 y 12 
        if (!dni.matches("\\d{8,12}")) {
            mensajeError("El DNI o Carnet de Extranjería debe contener solo números (entre 8 y 12 dígitos).");
            txtDni.requestFocus(); 
            return;
        }

        // 3. Validar que DNI sea unico
        if (arrAlumnos.buscarPorDni(dni) != null) {
            mensajeError("Ya existe un alumno registrado con ese DNI.");
            return;
        }

        try {
            // 4. Parsear  números
            int edad = Integer.parseInt(sEdad);
            int celular = Integer.parseInt(sCelular);

            // Validar rangos (ejemplo)
            if (edad <= 0 || celular <= 0) {
                 mensajeError("La Edad y el Celular deben ser números positivos.");
                 return;
            }

            // 5. Crear el nuevo objeto Alumno (Código y Estado se asignan en ArregloAlumno)
            Alumno nuevoAlumno = new Alumno(0, nombres, apellidos, dni, edad, celular, 0);

            // 6. Añadir usando la lógica de ArregloAlumno 
            arrAlumnos.adicionar(nuevoAlumno);

            // 7. Informar y actualizar GUI
            mensajeExito("Alumno registrado con éxito. Código asignado: " + nuevoAlumno.getCodAlumno());
            limpiarCampos();
            actualizarTabla(); // Refresca la tabla

        } catch (NumberFormatException ex) {
            mensajeError("La Edad y el Celular deben ser números válidos.");
        }
    }

    /* Modifica el alumno seleccionado en la tabla con los datos de los JTextFields */
    private void modificarAlumno() {
        int filaSeleccionada = tblAlumnos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mensajeAdvertencia("Seleccione un alumno de la tabla para modificar.");
            return;
        }

        // Obtener el código del alumno seleccionado (no se modifica)
        int codigo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Leer datos modificados de la GUI
        String nombres = txtNombre.getText().trim();
        String apellidos = txtApellido.getText().trim();
        String sEdad = txtEdad.getText().trim();
        String sCelular = txtCelular.getText().trim();
        String dni = txtDni.getText().trim(); // DNI tampoco se modifica, pero lo leemos para el objeto
        
     //  Validación de DNI/Documento Flexible 
        // (Aunque no se modifique, validamos el dato que está en el campo)
        if (!dni.matches("\\d{8,12}")) {
            mensajeError("El DNI o Carnet de Extranjería debe contener solo números (entre 8 y 12 dígitos).");
            txtDni.requestFocus();
            return;
        }

     // Validación de Nombre y Apellido (Sin Dígitos) 
        if (nombres.matches(".*\\d+.*")) {
            mensajeError("El nombre no puede contener números.");
            txtNombre.requestFocus();
            return;
        }
        if (apellidos.matches(".*\\d+.*")) {
            mensajeError("El apellido no puede contener números.");
            txtApellido.requestFocus();
            return;
        }
        
        
        // Validar entradas básicas
        if (nombres.isEmpty() || apellidos.isEmpty() || sEdad.isEmpty() || sCelular.isEmpty()) {
            mensajeError("Los campos Nombre, Apellido, Edad y Celular no pueden estar vacíos.");
            return;
        }

        try {
            // Parsear números
            int edad = Integer.parseInt(sEdad);
            int celular = Integer.parseInt(sCelular);
             if (edad <= 0 || celular <= 0) {
                 mensajeError("La Edad y el Celular deben ser números positivos.");
                 return;
            }

            // Crear un objeto Alumno TEMPORAL con los datos modificados
            // (El DNI y Estado no se usan en modificar, pero el constructor los pide)
            Alumno alumnoModificado = new Alumno(codigo, nombres, apellidos, dni, edad, celular, 0);

            // LLAMAR A LA RAMA 2: Modificar en el Arreglo
            arrAlumnos.modificar(alumnoModificado);

            // Informar y actualizar GUI
            mensajeExito("Alumno modificado con éxito.");
            limpiarCampos();
            actualizarTabla();

        } catch (NumberFormatException ex) {
            mensajeError("La Edad y el Celular deben ser números válidos.");
        }
    }

    /* Elimina el alumno seleccionado en la tabla */
    private void eliminarAlumno() {
        int filaSeleccionada = tblAlumnos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mensajeAdvertencia("Seleccione un alumno de la tabla para eliminar.");
            return;
        }

        // Obtener el código del alumno seleccionado
        int codigo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Confirmación
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar al alumno con código " + codigo + "?\n(Solo se eliminará si su estado es 'Registrado')",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            // LLAMAR A LA RAMA 2: Intentar eliminar
            boolean eliminado = arrAlumnos.eliminar(codigo); // Pasamos solo el código

            // Informar resultado y actualizar GUI
            if (eliminado) {
                mensajeExito("Alumno eliminado con éxito.");
                limpiarCampos();
                actualizarTabla();
            } else {
                // El método eliminar retornó false (probablemente por el estado)
                Alumno alu = arrAlumnos.buscarPorCodigo(codigo); // Buscamos para ver el estado
                String estadoActual = obtenerTextoEstado(alu != null ? alu.getEstado() : -1);
                mensajeError("No se pudo eliminar el alumno.\nMotivo: Su estado actual es '" + estadoActual + "' (solo se puede eliminar si es 'Registrado').");
            }
        }
    }

    /* Carga los datos de la fila seleccionada en la tabla a los JTextFields */
    private void cargarDatosFormularioDesdeTabla() {
        int filaSeleccionada = tblAlumnos.getSelectedRow();
        if (filaSeleccionada != -1) { // Si hay una fila seleccionada
            txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtApellido.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtDni.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtEdad.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtCelular.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            
        }
    }

    // MÉTODOS DE APOYO

    /* Limpia los campos del formulario y deselecciona la tabla */
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtEdad.setText("");
        txtCelular.setText("");
        tblAlumnos.clearSelection(); 
        txtNombre.requestFocus(); 
    }

    
    private void estiloBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(0, 159, 191));
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFocusPainted(false); 
        agregarEfectoHover(boton); 
    }

    
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

    /* Refresca la JTable con los datos actuales del ArregloAlumno */
    private void actualizarTabla() {
        // Limpia la tabla
        modeloTabla.setRowCount(0);

        // Recorre el Arreglo de Alumnos
        for (Alumno alu : arrAlumnos.listar()) {
            // Crea una fila de datos para la tabla
            Object[] fila = new Object[7]; // 7 columnas
            fila[0] = alu.getCodAlumno();
            fila[1] = alu.getNombres();
            fila[2] = alu.getApellidos();
            fila[3] = alu.getDni();
            fila[4] = alu.getEdad();
            fila[5] = alu.getCelular();
            fila[6] = obtenerTextoEstado(alu.getEstado()); 

          
            modeloTabla.addRow(fila);
        }
    }

    /* Convertir codigo de estado a texto*/
    private String obtenerTextoEstado(int estado) {
        switch (estado) {
            case 0: return "Registrado";
            case 1: return "Matriculado";
            case 2: return "Retirado";
            default: return "Desconocido";
        }
    }

    //  Métodos de Mensajes 
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