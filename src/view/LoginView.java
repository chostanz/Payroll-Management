package view;

import controller.LoginController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class LoginView extends JFrame {
    // ── Color Palette (dari dashboard)
    private static final Color BG_LEFT      = new Color(25, 35, 60);      // navy gelap
    private static final Color BG_RIGHT     = new Color(240, 242, 248);   // abu-abu terang
    private static final Color ACCENT_BLUE  = new Color(66, 133, 244);    // biru
    private static final Color ACCENT_LIME  = new Color(180, 220, 60);    // lime/hijau
    private static final Color DARK_GRAY    = new Color(45, 50, 70);      // dark untuk teks
    private static final Color FIELD_BG     = new Color(255, 255, 255);
    private static final Color FIELD_BORDER = new Color(210, 215, 230);
    private static final Color LABEL_COLOR  = new Color(100, 110, 140);

    JLabel lUser    = new JLabel("Username");
    JLabel lPass    = new JLabel("Password");
    JTextField    tfUser = new JTextField();
    JPasswordField pfPass = new JPasswordField();
    JButton btnLogin = new JButton("LOGIN");
    LoginController controller;

    public LoginView() {
        controller = new LoginController();
        setTitle("LOGIN");
        setSize(720, 440);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ── Root panel dengan dua sisi ───────────────────────────────────────
        JPanel root = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Panel kiri (navy)
                g2.setColor(BG_LEFT);
                g2.fillRect(0, 0, 280, getHeight());

                // Dekorasi lingkaran di panel kiri
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(-60, -60, 240, 240);
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillOval(100, 280, 200, 200);

                // Garis aksen lime
                g2.setColor(ACCENT_LIME);
                g2.setStroke(new BasicStroke(4f));
                g2.drawLine(0, getHeight() - 6, 280, getHeight() - 6);

                // Panel kanan (light)
                g2.setColor(BG_RIGHT);
                g2.fillRect(280, 0, getWidth() - 280, getHeight());
            }
        };
        root.setBounds(0, 0, 720, 440);
        setContentPane(root);

        // ── Sisi kiri
        JLabel logo = new JLabel("HR");
        logo.setFont(new Font("SansSerif", Font.BOLD, 48));
        logo.setForeground(ACCENT_BLUE);
        logo.setBounds(30, 80, 100, 60);
        root.add(logo);

        JLabel logoSub = new JLabel("<html><span style='letter-spacing:6px'>PORTAL</span></html>");
        logoSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        logoSub.setForeground(new Color(180, 190, 210));
        logoSub.setBounds(30, 130, 200, 20);
        root.add(logoSub);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 40));
        sep.setBounds(30, 160, 200, 2);
        root.add(sep);

        JLabel tagline = new JLabel("<html><div style='color:#b4bcd0;font-size:11px;line-height:1.6'>"
                + "Manage your team<br>with confidence.</div></html>");
        tagline.setBounds(30, 175, 220, 60);
        root.add(tagline);

        // Sisi kanan
        JLabel welcomeLabel = new JLabel("Login");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcomeLabel.setForeground(DARK_GRAY);
        welcomeLabel.setBounds(320, 70, 360, 30);
        root.add(welcomeLabel);

        JLabel subLabel = new JLabel("Log in to your account");
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subLabel.setForeground(LABEL_COLOR);
        subLabel.setBounds(320, 100, 300, 20);
        root.add(subLabel);

        // Username label
        styleLabel(lUser, 320, 148, root);

        // Username field
        styleField(tfUser, 320, 170, root);

        // Password label
        styleLabel(lPass, 320, 222, root);

        // Password field
        styleField(pfPass, 320, 244, root);

        // Login button
        styleButton(btnLogin);
        btnLogin.setBounds(320, 320, 340, 44);
        root.add(btnLogin);

        // ── Logika
        btnLogin.addActionListener(e -> {
            String username = tfUser.getText();
            String password = pfPass.getText();
            boolean login   = controller.login(username, password);
            if (login) {
                JOptionPane.showMessageDialog(null, "Login berhasil");
                new DashboardView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Username / Password salah");
            }
        });
    }
    private void styleLabel(JLabel label, int x, int y, JPanel parent) {
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setForeground(LABEL_COLOR);
        label.setBounds(x, y, 340, 18);
        parent.add(label);
    }

    private void styleField(JTextField field, int x, int y, JPanel parent) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(DARK_GRAY);
        field.setBackground(FIELD_BG);
        field.setCaretColor(ACCENT_BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(FIELD_BORDER, 8, 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        field.setBounds(x, y, 340, 40);
        parent.add(field);

        // Focus effect
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(ACCENT_BLUE, 8, 2),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(FIELD_BORDER, 8, 1),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
        });
    }
    private void styleButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT_BLUE);
        btn.setBorder(new RoundedBorder(ACCENT_BLUE, 10, 0));
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                Color bg = model.isPressed() ? ACCENT_BLUE.darker()
                         : model.isRollover() ? ACCENT_BLUE.brighter()
                         : ACCENT_BLUE;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                super.paint(g, c);
                g2.dispose();
            }
        });
    }

    private void addStat(JPanel parent, String value, String desc, int x, int y) {
        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.BOLD, 22));
        val.setForeground(Color.WHITE);
        val.setBounds(x, y, 90, 28);
        parent.add(val);

        JLabel d = new JLabel(desc);
        d.setFont(new Font("SansSerif", Font.PLAIN, 10));
        d.setForeground(new Color(160, 175, 200));
        d.setBounds(x, y + 26, 90, 16);
        parent.add(d);
    }

    // ── Custom rounded border ────────────────────────────────────────────────
    static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        private final int thickness;

        RoundedBorder(Color color, int radius, int thickness) {
            this.color     = color;
            this.radius    = radius;
            this.thickness = thickness;
        }

        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                             w - thickness, h - thickness, radius, radius);
            g2.dispose();
        }

        @Override public Insets getBorderInsets(Component c) {
            return new Insets(thickness + 4, thickness + 10, thickness + 4, thickness + 10);
        }
    }
}