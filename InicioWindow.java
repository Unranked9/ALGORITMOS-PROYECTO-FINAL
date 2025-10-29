package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class InicioWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // CAMPOS GLOBALES 
    private JPanel contentPane;
    private JLabel lblTitulo;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private JButton btnSalir;
    private JLabel icoIngresar;
    private JLabel icoRegistrar;
    private JLabel icoSalir;
    private JButton botplano;
    private String nombreUsuario;
    private JLabel crearIcono;

    // MAIN
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InicioWindow frame = new InicioWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // CONSTRUCTOR 
    public InicioWindow() {
        setTitle("Portal Educativo EDUCARE360");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);

        contentPane = new BackgroundPanel("/img/Ciberteclogo01.png");
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblTitulo = new JLabel("BIENVENIDO AL PORTAL EDUCATIVO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 46));
        lblTitulo.setForeground(Color.WHITE);
        contentPane.add(lblTitulo);

        btnIngresar = crearBotonPlano("INGRESAR");
        btnRegistrar = crearBotonPlano("REGISTRAR");
        btnSalir = crearBotonPlano("SALIR");

        contentPane.add(btnIngresar);
        contentPane.add(btnRegistrar);
        contentPane.add(btnSalir);

        icoIngresar = crearIcono("/img/i.png");
        icoRegistrar = crearIcono("/img/icono02.png");
        icoSalir = crearIcono("/img/icono03.png");

        contentPane.add(icoIngresar);
        contentPane.add(icoRegistrar);
        contentPane.add(icoSalir);

        btnIngresar.addActionListener(this);
        btnRegistrar.addActionListener(this);
        btnSalir.addActionListener(this);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) { layoutComponents(); }
            public void componentResized(ComponentEvent e) { layoutComponents(); }
        });
        layoutComponents();
    }

    // CREACIÓN DE COMPONENTES 
    private JButton crearBotonPlano(String texto) {
        botplano = new JButton(texto);
        botplano.setFont(new Font("Segoe UI", Font.BOLD, 22));
        botplano.setForeground(Color.WHITE);
        botplano.setBackground(new Color(0, 160, 190, 200));
        botplano.setFocusPainted(false);
        botplano.setOpaque(true);
        botplano.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        botplano.setHorizontalAlignment(SwingConstants.CENTER);
        return botplano;
    }

    private JLabel crearIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        crearIcono = new JLabel();
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH);
            crearIcono.setIcon(new ImageIcon(scaled));
        } else {
            crearIcono.setText("⚠");
            crearIcono.setForeground(Color.WHITE);
            crearIcono.setFont(new Font("Segoe UI", Font.BOLD, 18));
        }
        return crearIcono;
    }

    // LAYOUT RESPONSIVO
    private void layoutComponents() {
        int W = contentPane.getWidth();
        int H = contentPane.getHeight();
        if (W <= 0 || H <= 0) return;

        int titleWidth = (int) (W * 0.70);
        int titleX = (W - titleWidth) / 2;
        lblTitulo.setBounds(titleX, (int) (H * 0.14), titleWidth, 60);

        int btnW = (int) (W * 0.25);
        int btnH = 54;
        int baseX = (int) (W * 0.57);
        int baseY = (int) (H * 0.36);
        int stepY = 80;
        int stepX = 60;
        int iconSize = 42;

        btnIngresar.setBounds(baseX, baseY, btnW, btnH);
        icoIngresar.setBounds(baseX - (iconSize + 20), baseY + (btnH - iconSize) / 2, iconSize, iconSize);

        btnRegistrar.setBounds(baseX + stepX, baseY + stepY, btnW, btnH);
        icoRegistrar.setBounds(baseX + stepX - (iconSize + 20),
                baseY + stepY + (btnH - iconSize) / 2, iconSize, iconSize);

        btnSalir.setBounds(baseX + 2 * stepX, baseY + 2 * stepY, btnW, btnH);
        icoSalir.setBounds(baseX + 2 * stepX - (iconSize + 20),
                baseY + 2 * stepY + (btnH - iconSize) / 2, iconSize, iconSize);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnIngresar) {
            actionPerformedBtnIngresar(e);
        }
        if (e.getSource() == btnRegistrar) {
            actionPerformedBtnRegistrar(e);
        }
        if (e.getSource() == btnSalir) {
            actionPerformedBtnSalir(e);
        }
    }

    protected void actionPerformedBtnIngresar(ActionEvent e) {
        if (nombreUsuario != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido de nuevo, " + nombreUsuario + "!",
                    "Saludos",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        new MenuPrincipal().setVisible(true);
        dispose();
    }

    protected void actionPerformedBtnRegistrar(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog(
                this,
                "Introduce tu nombre:",
                "Registro de usuario",
                JOptionPane.QUESTION_MESSAGE
        );
        if (nombre != null && !nombre.trim().isEmpty()) {
            clase.SesionUsuario.setNombre(nombre);
            nombreUsuario = nombre;
            JOptionPane.showMessageDialog(
                    this,
                    "¡Hola, " + nombre + "! Gracias por registrarte.",
                    "Bienvenida",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se ingresó ningún nombre.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    protected void actionPerformedBtnSalir(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    }

    private static class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final String resourcePath;
        private Image bg;

        BackgroundPanel(String resourcePath) {
            this.resourcePath = resourcePath;
            setOpaque(true);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg == null) {
                URL url = getClass().getResource(resourcePath);
                if (url != null) bg = new ImageIcon(url).getImage();
            }
            if (bg != null) {
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}
