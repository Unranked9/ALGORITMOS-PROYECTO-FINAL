package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import clase.SesionUsuario;

// CAMBIO: Importar las 4 clases de Arreglos (Lógica - Rama 2)
import arreglo.ArregloAlumno;
import arreglo.ArregloCursos;
import arreglo.ArregloMatriculas;
import arreglo.ArregloRetiro;


public class MenuPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    // COMPONENTES GLOBALES GUI
    private JPanel contentPane;
    private JPanel panelSuperior;
    private JPanel panelLateral;
    private JPanel panelCentral;

    private JLabel lblBienvenida;
    private JLabel lblMantenimiento;
    private JLabel lblRegistro;
    private JLabel lblConsulta;
    private JLabel lblReporte;
    private JLabel lblSalir;
    private JLabel lblLogo;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;

    private JPanel btnAlumno;
    private JPanel btnCurso;
    private JPanel btnMatricula;
    private JPanel btnRetiro;
    private JLabel iconAlumno;
    private JLabel txtAlumno;
    private JLabel iconCurso;
    private JLabel txtCurso;
    private JLabel iconMatricula;
    private JLabel txtMatricula;
    private JLabel iconRetiro;
    private JLabel txtRetiro;

    // Declarar las 4 variables para los Arreglos 
    private ArregloAlumno arrAlumnos;
    private ArregloCursos arrCursos;
    private ArregloMatriculas arrMatriculas;
    private ArregloRetiro arrRetiros;

    // Variables para guardar instancias únicas de las ventanas hijas
    private AlumnoWindow ventanaAlumno = null;
    private CursoWindow ventanaCurso = null;
    private MatriculaWindow ventanaMatricula = null;
    private RetiroWindow ventanaRetiro = null;
    private ConsultaWindow ventanaConsulta = null;
    private ReporteWindow ventanaReporte = null;


    // MÉTODO MAIN
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }

    // CONSTRUCTOR
    public MenuPrincipal() {

        //  Inicializar los arreglos al principio 
        // Esto crea las listas y llama a cargarDatos() de cada uno
        arrAlumnos = new ArregloAlumno();
        arrCursos = new ArregloCursos();
        arrMatriculas = new ArregloMatriculas();
        arrRetiros = new ArregloRetiro();

        setTitle("Portal Educativo EDUCARE360");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);

        contentPane = new BackgroundPanel("/img/fondomenu.png");
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        crearPanelSuperior();
        crearPanelLateral();
        crearPanelCentral();
        configurarEventos(); 
    }

    private void crearPanelSuperior() {
        panelSuperior = new JPanel(null);
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setPreferredSize(new Dimension(0, 70));
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        String nombre = SesionUsuario.getNombre();
        lblBienvenida = new JLabel("Bienvenido, " + (nombre != null ? nombre : "Usuario"));
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBienvenida.setForeground(new Color(0, 120, 140));
        lblBienvenida.setBounds(40, 20, 300, 30);
        panelSuperior.add(lblBienvenida);


        lblMantenimiento = new JLabel("Mantenimiento");
        lblMantenimiento.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblMantenimiento.setForeground(new Color(0, 120, 140));
        lblMantenimiento.setBounds(340, 20, 180, 30);
        lblMantenimiento.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelSuperior.add(lblMantenimiento);

        lblRegistro = new JLabel("Registro");
        lblRegistro.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblRegistro.setForeground(new Color(0, 120, 140));
        lblRegistro.setBounds(540, 20, 180, 30);
        lblRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelSuperior.add(lblRegistro);

        lblConsulta = new JLabel("Consulta");
        lblConsulta.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblConsulta.setForeground(new Color(0, 120, 140));
        lblConsulta.setBounds(700, 20, 180, 30);
        lblConsulta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelSuperior.add(lblConsulta);

        lblReporte = new JLabel("Reporte");
        lblReporte.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblReporte.setForeground(new Color(0, 120, 140));
        lblReporte.setBounds(850, 20, 180, 30);
        lblReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelSuperior.add(lblReporte);

        lblSalir = new JLabel("Salir", JLabel.CENTER);
        lblSalir.setOpaque(true);
        lblSalir.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSalir.setForeground(Color.WHITE);
        lblSalir.setBackground(new Color(0, 180, 200));
        lblSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
     
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(panelSuperior != null && lblSalir != null) {
                    lblSalir.setBounds(panelSuperior.getWidth() - 160, 15, 120, 40);
                }
            }
        });
        
        lblSalir.setBounds(1100, 15, 120, 40); 
        panelSuperior.add(lblSalir);
    }

    // PANEL LATERAL 
    private void crearPanelLateral() {
        panelLateral = new JPanel(null);
        panelLateral.setBackground(new Color(0, 159, 191));
        panelLateral.setPreferredSize(new Dimension(270, 0));
        contentPane.add(panelLateral, BorderLayout.WEST);

        lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setOpaque(true);
        lblLogo.setBackground(new Color(255, 255, 255, 80));
        lblLogo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        lblLogo.setBounds(35, 70, 200, 110);

        URL urlLogo = getClass().getResource("/img/logo.png");
        if (urlLogo != null) {
            ImageIcon icon = new ImageIcon(urlLogo);
            Image scaled = icon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaled));
        }
        panelLateral.add(lblLogo);

        btnAlumno = new JPanel(null);
        btnAlumno.setBounds(0, 230, 270, 110);
        btnAlumno.setBackground(new Color(0, 159, 191));
        btnAlumno.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon iconA = new ImageIcon(getClass().getResource("/img/icomenu1.png"));
        Image imgA = iconA.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        iconAlumno = new JLabel(new ImageIcon(imgA));
        iconAlumno.setBounds(45, 30, 45, 45);

        txtAlumno = new JLabel("Alumno");
        txtAlumno.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtAlumno.setBounds(120, 40, 150, 30);
        btnAlumno.add(iconAlumno);
        btnAlumno.add(txtAlumno);
        panelLateral.add(btnAlumno);

        btnCurso = new JPanel(null);
        btnCurso.setBounds(0, 360, 270, 110);
        btnCurso.setBackground(new Color(0, 159, 191));
        btnCurso.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon iconC = new ImageIcon(getClass().getResource("/img/icomenu2.png"));
        Image imgC = iconC.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        iconCurso = new JLabel(new ImageIcon(imgC));
        iconCurso.setBounds(45, 30, 45, 45);

        txtCurso = new JLabel("Curso");
        txtCurso.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtCurso.setBounds(120, 40, 150, 30);
        btnCurso.add(iconCurso);
        btnCurso.add(txtCurso);
        panelLateral.add(btnCurso);

        btnMatricula = new JPanel(null);
        btnMatricula.setBounds(0, 490, 270, 110);
        btnMatricula.setBackground(new Color(0, 159, 191));
        btnMatricula.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon iconM = new ImageIcon(getClass().getResource("/img/icomenu3.png"));
        Image imgM = iconM.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        iconMatricula = new JLabel(new ImageIcon(imgM));
        iconMatricula.setBounds(45, 30, 45, 45);

        txtMatricula = new JLabel("Matrícula");
        txtMatricula.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtMatricula.setBounds(120, 40, 150, 30);
        btnMatricula.add(iconMatricula);
        btnMatricula.add(txtMatricula);
        panelLateral.add(btnMatricula);

        btnRetiro = new JPanel(null);
        btnRetiro.setBounds(0, 620, 270, 110);
        btnRetiro.setBackground(new Color(0, 159, 191));
        btnRetiro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon iconR = new ImageIcon(getClass().getResource("/img/icomenu4.png"));
        Image imgR = iconR.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        iconRetiro = new JLabel(new ImageIcon(imgR));
        iconRetiro.setBounds(45, 30, 45, 45);

        txtRetiro = new JLabel("Retiro");
        txtRetiro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtRetiro.setBounds(120, 40, 150, 30);
        btnRetiro.add(iconRetiro);
        btnRetiro.add(txtRetiro);
        panelLateral.add(btnRetiro);
    }

    // PANEL CENTRAL 
    private void crearPanelCentral() {
        panelCentral = new JPanel(null);
        panelCentral.setOpaque(false);
        contentPane.add(panelCentral, BorderLayout.CENTER);

        lblTitulo = new JLabel("Edúcate en línea del lugar que quieras");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(360, 250, 1000, 80);
        panelCentral.add(lblTitulo);

        lblSubtitulo = new JLabel("Porque el conocimiento abre caminos, y tú decides hacia dónde avanzar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(360, 320, 1000, 50);
        panelCentral.add(lblSubtitulo);
    }

    // EVENTOS
    private void configurarEventos() {
       
        agregarHover(btnAlumno, "Alumno");
        agregarHover(btnCurso, "Curso");
        agregarHover(btnMatricula, "Matrícula");
        agregarHover(btnRetiro, "Retiro");

       
        agregarHoverSuperior(lblMantenimiento, () -> {
            try {
               
                if (ventanaCurso == null || !ventanaCurso.isVisible()) {
                    ventanaCurso = new CursoWindow(arrCursos, arrMatriculas);
                    ventanaCurso.setVisible(true);
                } else {
                    ventanaCurso.toFront();
                }
            } catch (Exception ex) { handleWindowError("Mantenimiento (Curso)", ex); }
        });
        agregarHoverSuperior(lblRegistro, () -> {
             try {
                if (ventanaMatricula == null || !ventanaMatricula.isVisible()) {
                    ventanaMatricula = new MatriculaWindow(arrAlumnos, arrCursos, arrMatriculas);
                    ventanaMatricula.setVisible(true);
                } else {
                    ventanaMatricula.toFront();
                }
            } catch (Exception ex) { handleWindowError("Registro (Matrícula)", ex); }
        });
        agregarHoverSuperior(lblConsulta, () -> {
             try {
                if (ventanaConsulta == null || !ventanaConsulta.isVisible()) {
                    ventanaConsulta = new ConsultaWindow(arrAlumnos, arrCursos, arrMatriculas, arrRetiros);
                    ventanaConsulta.setVisible(true);
                } else {
                    ventanaConsulta.toFront();
                }
            } catch (Exception ex) { handleWindowError("Consulta", ex); }
        });
        agregarHoverSuperior(lblReporte, () -> {
             try {
                if (ventanaReporte == null || !ventanaReporte.isVisible()) {
                    ventanaReporte = new ReporteWindow(arrAlumnos, arrCursos, arrMatriculas);
                    ventanaReporte.setVisible(true);
                } else {
                    ventanaReporte.toFront();
                }
            } catch (Exception ex) { handleWindowError("Reporte", ex); }
        });

        // Listener del botón Salir (sin cambios)
        lblSalir.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { lblSalir.setBackground(new Color(0, 210, 230)); }
            public void mouseExited(MouseEvent e) { lblSalir.setBackground(new Color(0, 180, 200)); }
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Desea salir del sistema?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
            }
        });
    }

    //  Método 'agregarHover' modificado para pasar arreglos E implementar instancia única 
    private void agregarHover(JPanel boton, String tipo) {
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { boton.setBackground(new Color(0, 200, 230)); }
            public void mouseExited(MouseEvent e) { boton.setBackground(new Color(0, 159, 191)); }

           
            public void mouseClicked(MouseEvent e) {
                 try {
                    switch (tipo) {
                        case "Alumno":
                            // Verifica si ya existe y está visible
                            if (ventanaAlumno == null || !ventanaAlumno.isVisible()) {
                                // Si no, crea nueva
                                ventanaAlumno = new AlumnoWindow(arrAlumnos);
                                ventanaAlumno.setVisible(true);
                            } else {
                                // Si sí, trae al frente
                                ventanaAlumno.toFront();
                            }
                            break;
                        case "Curso":
                            if (ventanaCurso == null || !ventanaCurso.isVisible()) {
                                ventanaCurso = new CursoWindow(arrCursos, arrMatriculas);
                                ventanaCurso.setVisible(true);
                            } else {
                                ventanaCurso.toFront();
                            }
                            break;
                        case "Matrícula":
                             if (ventanaMatricula == null || !ventanaMatricula.isVisible()) {
                                ventanaMatricula = new MatriculaWindow(arrAlumnos, arrCursos, arrMatriculas);
                                ventanaMatricula.setVisible(true);
                            } else {
                                ventanaMatricula.toFront();
                            }
                            break;
                        case "Retiro":
                             if (ventanaRetiro == null || !ventanaRetiro.isVisible()) {
                                ventanaRetiro = new RetiroWindow(arrAlumnos, arrMatriculas, arrRetiros);
                                ventanaRetiro.setVisible(true);
                            } else {
                                ventanaRetiro.toFront();
                            }
                            break;
                        default:
                             JOptionPane.showMessageDialog(null, "Opción no reconocida: " + tipo);
                    }
                 } catch (Exception ex) { 
                    handleWindowError(tipo, ex); 
                 }
            }
        });
    }

    
    private void agregarHoverSuperior(JLabel lbl, Runnable accion) {
        lbl.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { lbl.setForeground(new Color(0, 200, 230)); }
            public void mouseExited(MouseEvent e) { lbl.setForeground(new Color(0, 120, 140)); }
            public void mouseClicked(MouseEvent e) {
                
                if(accion != null) accion.run();
            }
        });
    }

    //  Método helper para manejar errores al abrir ventanas
    private void handleWindowError(String windowType, Exception ex) {
        JOptionPane.showMessageDialog(this, 
                "Error al abrir la ventana de " + windowType,
                "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace(); 
    }


    //PANEL DE FONDO 
    private static class BackgroundPanel extends JPanel {
       
         private static final long serialVersionUID = 1L;
        private final String resourcePath;
        private Image bg;
        BackgroundPanel(String resourcePath) { this.resourcePath = resourcePath; }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg == null) {
                URL url = getClass().getResource(resourcePath);
                if (url != null) bg = new ImageIcon(url).getImage();
            }
            if (bg != null) {
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0, 0, 0, 90),
                        0, getHeight(), new Color(0, 0, 0, 190));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        }
    }
}