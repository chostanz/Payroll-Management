package view;

import controller.PegawaiController;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import model.PegawaiTetap;

public class DashboardView extends JFrame {

    //Color Palette
    static final Color BG         = new Color(240, 242, 248);  // abu terang
    static final Color NAVY       = new Color(25, 35, 60);     // navy gelap
    static final Color BLUE       = new Color(66, 133, 244);   // biru utama
    static final Color LIME       = new Color(180, 220, 60);   // lime/hijau
    static final Color DANGER     = new Color(220, 60, 60);    // merah delete
    static final Color DARK       = new Color(45, 50, 70);     // teks gelap
    static final Color LABEL_CLR  = new Color(100, 110, 140);  // label abu
    static final Color FIELD_BG   = Color.WHITE;
    static final Color FIELD_BOR  = new Color(210, 215, 230);

    // LABEL
    JLabel lNik      = new JLabel("NIK");
    JLabel lNama     = new JLabel("Nama");
    JLabel lJabatan  = new JLabel("Jabatan");
    JLabel lJenis    = new JLabel("Jenis Pegawai");

    // TEXTFIELD
    JTextField tfNik     = new JTextField();
    JTextField tfNama    = new JTextField();
    JTextField tfJabatan = new JTextField();

    // COMBOBOX
    JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Tetap", "Kontrak", "PartTime"});

    // BUTTON
    JButton btnTambah    = buatTombol("+ Tambah",    BLUE,   Color.WHITE);
    JButton btnUpdate    = buatTombol("✎ Update",    LIME,   DARK);
    JButton btnDelete    = buatTombol("✕ Delete",    DANGER, Color.WHITE);
    JButton btnClear     = buatTombol("Clear",       FIELD_BOR, DARK);
    JButton btnPenggajian= buatTombol("Penggajian",  NAVY,   Color.WHITE);
    JButton btnRefresh   = buatTombol("↻ Refresh",   FIELD_BOR, DARK);
    JButton btnLogout    = buatTombol("Logout",      DANGER, Color.WHITE);

    // TABLE
    JTable      table  = new JTable();
    JScrollPane scroll = new JScrollPane(table);

    PegawaiController controller;
    int selectedId = -1;

    public DashboardView() {
        controller = new PegawaiController();
        setTitle("Dashboard Payroll");
        setSize(980, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Root panel 
        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // Header bar 
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 980, 56);
        root.add(header);

        JLabel title = new JLabel("Dashboard Payroll");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setBounds(24, 14, 300, 28);
        header.add(title);

        JLabel sub = new JLabel("Manajemen Data Pegawai");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(160, 175, 210));
        sub.setBounds(220, 18, 300, 18);
        header.add(sub);

        // Logout di header pojok kanan
        btnLogout.setBounds(860, 12, 100, 32);
        header.add(btnLogout);

        // garis aksen lime di bawah header
        JPanel accent = new JPanel();
        accent.setBackground(LIME);
        accent.setBounds(0, 52, 980, 4);
        root.add(accent);

        // Panel form kiri
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(20, 72, 360, 280);
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
        lJenis.setBounds(16, 192, 100, 20);
        formPanel.add(lJenis);

        styleCombo(cbJenis);
        cbJenis.setBounds(120, 190, 220, 30);
        formPanel.add(cbJenis);

        // Penggajian di bawah form
        btnPenggajian.setBounds(16, 234, 328, 36);
        formPanel.add(btnPenggajian);

        // Panel Aksi kanan
        JPanel aksiPanel = new JPanel(null);
        aksiPanel.setBackground(Color.WHITE);
        aksiPanel.setBounds(400, 72, 556, 280);
        aksiPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(aksiPanel);

        JLabel aksiTitle = new JLabel("Aksi");
        aksiTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        aksiTitle.setForeground(DARK);
        aksiTitle.setBounds(16, 12, 200, 20);
        aksiPanel.add(aksiTitle);

        // Kolom 1: Tambah, Update, Delete (x=16)
        btnTambah.setBounds(16,  44, 240, 38);
        btnUpdate.setBounds(16,  96, 240, 38);
        btnDelete.setBounds(16, 148, 240, 38);
        aksiPanel.add(btnTambah);
        aksiPanel.add(btnUpdate);
        aksiPanel.add(btnDelete);

        // Kolom 2: Clear, Refresh (x=276)
        btnClear.setBounds(276,  44, 240, 38);
        btnRefresh.setBounds(276, 96, 240, 38);
        aksiPanel.add(btnClear);
        aksiPanel.add(btnRefresh);

        // Tabel 
        styleTable();
        JLabel tblTitle = new JLabel("Daftar Pegawai");
        tblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        tblTitle.setForeground(DARK);
        tblTitle.setBounds(20, 366, 200, 20);
        root.add(tblTitle);

        scroll.setBounds(20, 390, 936, 210);
        scroll.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scroll);

        // Load data & semua listener
        controller.tampilData(table);

        btnTambah.addActionListener(e -> {
            try {
                PegawaiTetap p = new PegawaiTetap();
                p.setNik(tfNik.getText());
                p.setNama(tfNama.getText());
                p.setJabatan(tfJabatan.getText());
                p.setJenisPegawai(cbJenis.getSelectedItem().toString());
                controller.insertPegawai(p);
                controller.tampilData(table);
                clearForm();
                JOptionPane.showMessageDialog(null, "Data berhasil ditambah");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                if (selectedId == -1) { JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu"); return; }
                PegawaiTetap p = new PegawaiTetap();
                p.setIdPegawai(selectedId);
                p.setNik(tfNik.getText());
                p.setNama(tfNama.getText());
                p.setJabatan(tfJabatan.getText());
                p.setJenisPegawai(cbJenis.getSelectedItem().toString());
                controller.updatePegawai(p);
                controller.tampilData(table);
                clearForm();
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                if (selectedId == -1) { JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu"); return; }
                controller.deletePegawai(selectedId);
                controller.tampilData(table);
                clearForm();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> clearForm());

        btnRefresh.addActionListener(e -> controller.tampilData(table));

        btnLogout.addActionListener(e -> { new LoginView().setVisible(true); dispose(); });

        btnPenggajian.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(null, "Pilih pegawai terlebih dahulu");
            } else {
                new PenggajianView(selectedId, tfNama.getText(), cbJenis.getSelectedItem().toString()).setVisible(true);
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                selectedId   = Integer.parseInt(table.getValueAt(row, 0).toString());
                tfNik.setText(table.getValueAt(row, 1).toString());
                tfNama.setText(table.getValueAt(row, 2).toString());
                tfJabatan.setText(table.getValueAt(row, 3).toString());
                cbJenis.setSelectedItem(table.getValueAt(row, 4).toString());
            }
        });
    }

    // Helper: isi satu baris label + field
    private void isiForm(JPanel panel, JLabel label, JTextField field, int x, int y) {
        styleLabel(label);
        label.setBounds(x + 6, y, 100, 20);
        panel.add(label);

        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(DARK);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BOR, 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        field.setBounds(120, y - 4, 220, 32);
        panel.add(field);
    }

    private void styleLabel(JLabel l) {
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(LABEL_CLR);
    }

    private void styleCombo(JComboBox<String> cb) {
        cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cb.setBackground(FIELD_BG);
        cb.setForeground(DARK);
    }

    // Helper: buat tombol berwarna
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

    // Style tabel 
    private void styleTable() {
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(66, 133, 244, 40));
        table.setSelectionForeground(DARK);
        table.setBackground(Color.WHITE);
        table.setForeground(DARK);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("SansSerif", Font.BOLD, 12));
        th.setBackground(NAVY);
        th.setForeground(Color.WHITE);
        th.setPreferredSize(new Dimension(0, 36));
        th.setBorder(BorderFactory.createEmptyBorder());

        // Warna baris selang-seling
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (sel) {
                    setBackground(new Color(66, 133, 244, 50));
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    // CLEAR FORM 
    private void clearForm() {
        tfNik.setText("");
        tfNama.setText("");
        tfJabatan.setText("");
        cbJenis.setSelectedIndex(0);
        selectedId = -1;
    }
}