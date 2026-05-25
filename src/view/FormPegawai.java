package view;

import controller.PegawaiController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import model.PegawaiTetap;

public class FormPegawai extends JFrame {
    // Color Palette
    static final Color BG        = new Color(240, 242, 248);
    static final Color NAVY      = new Color(25, 35, 60);
    static final Color BLUE      = new Color(66, 133, 244);
    static final Color LIME      = new Color(180, 220, 60);
    static final Color DARK      = new Color(45, 50, 70);
    static final Color LABEL_CLR = new Color(100, 110, 140);
    static final Color FIELD_BG  = Color.WHITE;
    static final Color FIELD_BOR = new Color(210, 215, 230);

    // LABEL
    JLabel lNik     = new JLabel("NIK");
    JLabel lNama    = new JLabel("Nama");
    JLabel lJabatan = new JLabel("Jabatan");
    JLabel lJenis   = new JLabel("Jenis Pegawai");

    // TEXTFIELD
    JTextField tfNik     = new JTextField();
    JTextField tfNama    = new JTextField();
    JTextField tfJabatan = new JTextField();

    // COMBOBOX
    JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Tetap", "Kontrak", "PartTime"});

    // BUTTON
    JButton btnSimpan = buatTombol("Simpan", BLUE, Color.WHITE);

    PegawaiController controller;

    public FormPegawai() {
        controller = new PegawaiController();
        setTitle("Form Pegawai");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Root panel 
        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // Header bar 
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 420, 56);
        root.add(header);

        JLabel title = new JLabel("Form Pegawai");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 14, 200, 28);
        header.add(title);

        JLabel sub = new JLabel("Tambah data pegawai baru");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(160, 175, 210));
        sub.setBounds(200, 18, 200, 18);
        header.add(sub);

        // Garis aksen lime
        JPanel accent = new JPanel();
        accent.setBackground(LIME);
        accent.setBounds(0, 52, 420, 4);
        root.add(accent);

        // Panel Form 
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(20, 72, 380, 280);
        formPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(formPanel);

        JLabel formTitle = new JLabel("Data Pegawai");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        formTitle.setForeground(DARK);
        formTitle.setBounds(16, 12, 200, 20);
        formPanel.add(formTitle);

        // Label & Field
        isiForm(formPanel, lNik,     tfNik,     10, 42);
        isiForm(formPanel, lNama,    tfNama,    10, 92);
        isiForm(formPanel, lJabatan, tfJabatan, 10, 142);

        styleLabel(lJenis);
        lJenis.setBounds(16, 192, 110, 20);
        formPanel.add(lJenis);

        cbJenis.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbJenis.setBackground(FIELD_BG);
        cbJenis.setForeground(DARK);
        cbJenis.setBounds(130, 190, 230, 30);
        formPanel.add(cbJenis);

        // Tombol Simpan — lebar penuh di bawah form
        btnSimpan.setBounds(16, 234, 348, 36);
        formPanel.add(btnSimpan);

        // ── Logika (tidak diubah) ────────────────────────────────────────────
        btnSimpan.addActionListener(e -> {
            try {
                PegawaiTetap p = new PegawaiTetap();
                p.setNik(tfNik.getText());
                p.setNama(tfNama.getText());
                p.setJabatan(tfJabatan.getText());
                p.setJenisPegawai(cbJenis.getSelectedItem().toString());
                controller.insertPegawai(p);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    // ── Helper: satu baris label + field ────────────────────────────────────
    private void isiForm(JPanel panel, JLabel label, JTextField field, int x, int y) {
        styleLabel(label);
        label.setBounds(x + 6, y, 110, 20);
        panel.add(label);

        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(DARK);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BOR, 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        field.setBounds(130, y - 4, 230, 32);
        panel.add(field);

        // Focus highlight
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BLUE, 2),
                    BorderFactory.createEmptyBorder(3, 9, 3, 9)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(FIELD_BOR, 1),
                    BorderFactory.createEmptyBorder(4, 10, 4, 10)
                ));
            }
        });
    }

    private void styleLabel(JLabel l) {
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(LABEL_CLR);
    }

    // Helper: tombol berwarna 
    static JButton buatTombol(String teks, Color bg, Color fg) {
        JButton btn = new JButton(teks) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? bg.darker()
                           : getModel().isRollover() ? bg.brighter() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}