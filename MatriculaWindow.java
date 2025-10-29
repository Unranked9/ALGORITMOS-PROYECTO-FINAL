package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter; // CAMBIO: Para efecto hover
import java.awt.event.MouseEvent;  // CAMBIO: Para efecto hover
import javax.swing.JButton;
// import javax.swing.JComboBox; // CAMBIO: Eliminado ComboBox Turno
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder; // CAMBIO: Usaremos TitledBorder para agrupar

// CAMBIO: Importar Arreglos y Modelos necesarios (usando nombres correctos)
import arreglo.ArregloAlumno;
import arreglo.ArregloCursos;
import arreglo.ArregloMatriculas;
import clase.Alumno;
import clase.Curso;
import clase.Matricula;


public class MatriculaWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- CAMPOS GLOBALES (GUI) ---
    private JPanel contentPane;
    private JTextField txtNumMatricula; 
    private JTextField txtCodAlumno;    
    private JTextField txtCodCurso;     
    private JTextField txtFecha;        
    private JTextField txtHora;         
    private JButton btnRegistrar;
    private JButton btnModificar;     
    private JButton btnEliminar;      
    private JButton btnLimpiar;       
    private JButton btnBuscarMatricula; 
    private JPanel panelDatos;
    private JLabel lblNumMatricula;
    private JLabel lblFecha;
    private JLabel lblHora;
    private JLabel lblCodAlumno;
    private JLabel lblCodCurso;
    private JLabel lblTitulo;
    private JPanel panelBotones;

    //  Variables para guardar los Arreglos  
    private ArregloAlumno arrAlumnos;
    private ArregloCursos arrCursos;
    private ArregloMatriculas arrMatriculas;


    /** Constructor MODIFICADO para recibir los Arreglos  */
    public MatriculaWindow(ArregloAlumno arrAlumnos, ArregloCursos arrCursos, ArregloMatriculas arrMatriculas) { 
        //  Guardar los Arreglos 
        this.arrAlumnos = arrAlumnos;         
        this.arrCursos = arrCursos;           
        this.arrMatriculas = arrMatriculas;  

        setTitle("Registro y Gestión de Matrículas - EDUCARE360"); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 450); 
        setResizable(false);
        setLocationRelativeTo(null);

        inicializarComponentes();
        configurarEventos();
        limpiarCampos(); 
    }

    //  INTERFAZ 
    private void inicializarComponentes() {
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblTitulo = new JLabel("Registro y Gestión de Matrículas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 159, 191));
        lblTitulo.setBounds(150, 15, 400, 35); 
        contentPane.add(lblTitulo);

        panelDatos = new JPanel();
        panelDatos.setLayout(null);
        panelDatos.setBackground(new Color(245, 250, 250));
        panelDatos.setBorder(new TitledBorder(
                new LineBorder(new Color(0, 159, 191), 2, true),
                "Datos de Matrícula", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), new Color(0, 100, 130)));
        panelDatos.setBounds(30, 70, 625, 190); 
        contentPane.add(panelDatos);

        // Fila 1: N° Matrícula (solo lectura) y Botón Buscar
        lblNumMatricula = new JLabel("N° Matrícula:");
        lblNumMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNumMatricula.setBounds(30, 30, 110, 25);
        panelDatos.add(lblNumMatricula);

        txtNumMatricula = new JTextField();
        txtNumMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtNumMatricula.setBounds(150, 30, 150, 30);
      
        panelDatos.add(txtNumMatricula);

        btnBuscarMatricula = new JButton("Buscar"); 
        btnBuscarMatricula.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscarMatricula.setBounds(310, 30, 90, 30);
        estiloBoton(btnBuscarMatricula, new Color(220, 220, 255), new Color(180, 180, 240)); 
        panelDatos.add(btnBuscarMatricula);

        // Fila 2: Código Alumno y Código Curso
        lblCodAlumno = new JLabel("Cod. Alumno:");
        lblCodAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCodAlumno.setBounds(30, 75, 110, 25);
        panelDatos.add(lblCodAlumno);

        txtCodAlumno = new JTextField();
        txtCodAlumno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCodAlumno.setBounds(150, 75, 150, 30);
        panelDatos.add(txtCodAlumno);

        lblCodCurso = new JLabel("Cod. Curso:"); 
        lblCodCurso.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCodCurso.setBounds(320, 75, 100, 25);
        panelDatos.add(lblCodCurso);

        txtCodCurso = new JTextField(); 
        txtCodCurso.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCodCurso.setBounds(430, 75, 150, 30);
        panelDatos.add(txtCodCurso);

        // Fila 3: Fecha y Hora (solo lectura)
        lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblFecha.setBounds(30, 120, 110, 25);
        panelDatos.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtFecha.setBounds(150, 120, 150, 30);
        txtFecha.setEditable(false); 
        txtFecha.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtFecha);

        lblHora = new JLabel("Hora:"); 
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblHora.setBounds(320, 120, 100, 25);
        panelDatos.add(lblHora);

        txtHora = new JTextField(); 
        txtHora.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtHora.setBounds(430, 120, 150, 30);
        txtHora.setEditable(false); // No editable
        txtHora.setBackground(Color.LIGHT_GRAY);
        panelDatos.add(txtHora);

       

        panelBotones = new JPanel();
        panelBotones.setLayout(null);
        panelBotones.setBackground(new Color(245, 250, 250));
        panelBotones.setBorder(new LineBorder(new Color(0, 159, 191), 2, true));
        panelBotones.setBounds(30, 280, 625, 80); // Ajustado tamaño y posición
        contentPane.add(panelBotones);

       
        btnRegistrar = new JButton("Registrar Nueva");
        btnRegistrar.setBounds(20, 20, 150, 40);
        estiloBoton(btnRegistrar, new Color(0, 159, 191), new Color(0, 200, 230));
        panelBotones.add(btnRegistrar);

        btnModificar = new JButton("Modificar Curso"); 
        btnModificar.setBounds(185, 20, 150, 40);
        estiloBoton(btnModificar, new Color(255, 165, 0), new Color(255, 190, 50)); 
        panelBotones.add(btnModificar);

        btnEliminar = new JButton("Cancelar Matrícula"); 
        btnEliminar.setBounds(350, 20, 150, 40);
        estiloBoton(btnEliminar, new Color(220, 50, 50), new Color(250, 80, 80)); 
        panelBotones.add(btnEliminar);
        
        btnLimpiar = new JButton("Limpiar"); 
        btnLimpiar.setBounds(515, 20, 90, 40);
        estiloBoton(btnLimpiar, new Color(150,150,150), new Color(180,180,180)); 
        panelBotones.add(btnLimpiar);

       
    }

    // EVENTOS (Conectados a métodos privados)
    private void configurarEventos() {
        btnRegistrar.addActionListener(e -> registrarMatricula());
        btnModificar.addActionListener(e -> modificarMatricula());
        btnEliminar.addActionListener(e -> eliminarMatricula());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnBuscarMatricula.addActionListener(e -> buscarMatricula());
    }

    // LÓGICA

    /** Registra una nueva matrícula */
    private void registrarMatricula() {
        // 1. Leer datos de la GUI (solo los necesarios para crear)
        String sCodAlumno = txtCodAlumno.getText().trim();
        String sCodCurso = txtCodCurso.getText().trim();

        // 2. Validar Entradas
        if (sCodAlumno.isEmpty() || sCodCurso.isEmpty()) {
            mensajeError("Debe ingresar el Código del Alumno y el Código del Curso.");
            return;
        }

        try {
            // 3. Parsear códigos
            int codAlumno = Integer.parseInt(sCodAlumno);
            int codCurso = Integer.parseInt(sCodCurso);

            // 4. Validar Lógica de Negocio (Usando los Arreglos)
            Alumno alu = arrAlumnos.buscarPorCodigo(codAlumno);
            if (alu == null) {
                mensajeError("No existe un alumno con el código " + codAlumno + ".");
                return;
            }
            Curso cur = arrCursos.buscarPorCodigo(codCurso);
            if (cur == null) {
                mensajeError("No existe un curso con el código " + codCurso + ".");
                return;
            }
            if (arrMatriculas.buscarPorCodAlumno(codAlumno) != null) {
                mensajeError("El alumno con código " + codAlumno + " ya se encuentra matriculado.");
                return;
            }
            if (alu.getEstado() == 2) { // Estado Retirado
                 mensajeError("El alumno con código " + codAlumno + " se encuentra retirado y no puede matricularse.");
                 return;
            }
             if (alu.getEstado() == 1) { // Doble chequeo por si acaso
                 mensajeError("El alumno con código " + codAlumno + " ya figura como matriculado.");
                 return;
            }


            // 5. Crear objeto Matricula (solo con códigos, el resto lo genera el Arreglo)
            Matricula nuevaMatricula = new Matricula(0, codAlumno, codCurso, "", "");

            // 6. LLAMAR A LA RAMA 2: Añadir usando ArregloMatriculas
            arrMatriculas.adicionar(nuevaMatricula, arrAlumnos);

            // 7. Informar y actualizar GUI
            mensajeExito("Matrícula registrada con éxito.\nN°: " + nuevaMatricula.getNumMatricula() +
                         "\nFecha: " + nuevaMatricula.getFecha() + " Hora: " + nuevaMatricula.getHora());
            limpiarCampos(); // Limpia y actualiza fecha/hora

        } catch (NumberFormatException ex) {
            mensajeError("Los Códigos de Alumno y Curso deben ser números.");
        }
    }

    /** Busca una matrícula existente por su número y carga sus datos */
    private void buscarMatricula() {
        String sNumMatricula = txtNumMatricula.getText().trim();
        if (sNumMatricula.isEmpty()) {
            mensajeAdvertencia("Ingrese el N° de Matrícula que desea buscar.");
            return;
        }

        try {
            int numMatricula = Integer.parseInt(sNumMatricula);

            // LLAMAR A LA RAMA 2: Buscar
            Matricula mat = arrMatriculas.buscarPorNumero(numMatricula);

            if (mat != null) {
                // Cargar datos en la GUI
                txtNumMatricula.setText(String.valueOf(mat.getNumMatricula()));
                txtCodAlumno.setText(String.valueOf(mat.getCodAlumno()));
                txtCodCurso.setText(String.valueOf(mat.getCodCurso()));
                txtFecha.setText(mat.getFecha());
                txtHora.setText(mat.getHora());
                // Bloquear campos clave para evitar modificaciones accidentales
                txtNumMatricula.setEditable(false);
                txtCodAlumno.setEditable(false);
            } else {
                mensajeAdvertencia("No se encontró una matrícula con el número " + numMatricula + ".");
                // Opcional: limpiar campos si no se encontró
                // limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            mensajeError("El N° de Matrícula debe ser un número.");
        }
    }

    /** Modifica el curso de la matrícula cargada en el formulario */
    private void modificarMatricula() {
        String sNumMatricula = txtNumMatricula.getText().trim();
        String sNuevoCodCurso = txtCodCurso.getText().trim(); 

        if (sNumMatricula.isEmpty() || txtCodAlumno.isEditable()) {
            mensajeAdvertencia("Primero busque la matrícula que desea modificar usando el N° de Matrícula.");
            return;
        }
        if (sNuevoCodCurso.isEmpty()) {
            mensajeError("Ingrese el nuevo Código de Curso.");
            return;
        }

        try {
            int numMatricula = Integer.parseInt(sNumMatricula);
            int nuevoCodCurso = Integer.parseInt(sNuevoCodCurso);

            // Validar que el nuevo curso exista
            if (arrCursos.buscarPorCodigo(nuevoCodCurso) == null) {
                mensajeError("No existe un curso con el código " + nuevoCodCurso + ".");
                return;
            }

            //  Modificar curso
            arrMatriculas.modificarCurso(numMatricula, nuevoCodCurso);

            mensajeExito("Curso de la matrícula N° " + numMatricula + " modificado con éxito.");
            // Opcional: limpiar campos después de modificar
            // limpiarCampos();

        } catch (NumberFormatException ex) {
            mensajeError("El N° de Matrícula y el Código de Curso deben ser números.");
        }
    }

    /** Elimina (cancela) la matrícula cargada en el formulario */
    private void eliminarMatricula() {
         String sNumMatricula = txtNumMatricula.getText().trim();

        if (sNumMatricula.isEmpty() || txtCodAlumno.isEditable()) { // Si no se ha buscado una matrícula
            mensajeAdvertencia("Primero busque la matrícula que desea cancelar usando el N° de Matrícula.");
            return;
        }

        try {
            int numMatricula = Integer.parseInt(sNumMatricula);

            // Buscar el objeto Matricula completo para pasarlo a eliminar
            Matricula matAEliminar = arrMatriculas.buscarPorNumero(numMatricula);
            if (matAEliminar == null) {
                // Esto no debería pasar si se buscó primero, pero por seguridad...
                mensajeError("Error: No se encontró la matrícula para eliminar.");
                return;
            }

            // Confirmación
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea cancelar la matrícula N° " + numMatricula + "?\n" +
                    "(Esto revertirá el estado del alumno a 'Registrado', si no está 'Retirado').",
                    "Confirmar Cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirmar == JOptionPane.YES_OPTION) {
                 //  Eliminar
                arrMatriculas.eliminar(matAEliminar, arrAlumnos);

                // Verificar si el alumno sigue existiendo para mostrar el estado correcto
                Alumno alu = arrAlumnos.buscarPorCodigo(matAEliminar.getCodAlumno());
                if(alu != null && alu.getEstado() == 0) { // Si se revirtió a Registrado
                     mensajeExito("Matrícula N° " + numMatricula + " cancelada con éxito.\n" +
                                 "El estado del alumno se ha revertido a 'Registrado'.");
                } else if (alu != null && alu.getEstado() == 2) { // Si el alumno estaba Retirado, no se revierte
                     mensajeExito("Matrícula N° " + numMatricula + " cancelada con éxito.\n" +
                                 "El estado del alumno sigue siendo 'Retirado'.");
                } else {
                     mensajeExito("Matrícula N° " + numMatricula + " cancelada con éxito."); // Mensaje genérico
                }
                limpiarCampos();
            }

        } catch (NumberFormatException ex) {
            mensajeError("El N° de Matrícula debe ser un número.");
        }
    }

    // --- MÉTODOS DE APOYO ---

    /** Limpia los campos y actualiza fecha/hora */
    private void limpiarCampos() {
        txtNumMatricula.setText("");
        txtCodAlumno.setText("");
        txtCodCurso.setText("");
        // Actualiza fecha y hora actuales (generadas por ArregloMatriculas)
        txtFecha.setText(arrMatriculas.obtenerFechaActual());
        txtHora.setText(arrMatriculas.obtenerHoraActual());
        // Desbloquea campos para nueva matrícula o búsqueda
        txtNumMatricula.setEditable(true);
        txtCodAlumno.setEditable(true);
        txtCodCurso.requestFocus(); // Pone cursor en Cod Curso
    }

    /** Aplica estilo a un botón con colores específicos */
    private void estiloBoton(JButton boton, Color colorBase, Color colorHover) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Tamaño reducido
        boton.setBackground(colorBase);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.setBorder(new LineBorder(colorBase.darker(), 1, true)); // Borde sutil
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