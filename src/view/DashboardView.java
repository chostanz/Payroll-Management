package view;

import controller.PegawaiController;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import model.PegawaiTetap;

public class DashboardView extends JFrame {
    //Color Palette
    static final Color BG         = new Color(240, 242, 248);
    static final Color NAVY       = new Color(25, 35, 60);
    static final Color BLUE       = new Color(66, 133, 244);
    static final Color LIME       = new Color(180, 220, 60);
    static final Color DANGER     = new Color(220, 60, 60);
    static final Color DARK       = new Color(45, 50, 70);
    static final Color LABEL_CLR  = new Color(100, 110, 140);
    static final Color FIELD_BG   = Color.WHITE;
    static final Color FIELD_BOR  = new Color(210, 215, 230);
    static final Color TAB_BG     = new Color(35, 47, 78);   // tab aktif bg
    static final Color TAB_INACTIVE = new Color(160, 175, 210); // teks tab non-aktif

    // LABEL
    JLabel lNik      = new JLabel("NIK");
    JLabel lNama     = new JLabel("Nama");
    JLabel lJabatan  = new JLabel("Jabatan");
    JLabel lJenis    = new JLabel("Jenis Pegawai");
    // Tambah field ini
    JLabel lGajiPokok    = new JLabel("Gaji Pokok");
    JTextField tfGajiPokok = new JTextField();
    JLabel lTunjangan    = new JLabel("Tunjangan");
    JTextField tfTunjangan = new JTextField();

    // TEXTFIELD
    JTextField tfNik     = new JTextField();
    JTextField tfNama    = new JTextField();
    JTextField tfJabatan = new JTextField();

    // COMBOBOX
    JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Tetap", "Kontrak", "PartTime"});

    // BUTTON
    JButton btnTambah    = buatTombol("+ Tambah",       BLUE,      Color.WHITE);
    JButton btnUpdate    = buatTombol("✎ Update",        LIME,      DARK);
    JButton btnDelete    = buatTombol("✕ Hapus pegawai ini", DANGER, Color.WHITE);
    JButton btnClear     = buatTombol("◇ Clear",         FIELD_BOR, DARK);
    JButton btnPenggajian= buatTombol("Penggajian",      NAVY,      Color.WHITE);
    JButton btnRefresh   = buatTombol("↻ Refresh",       FIELD_BOR, DARK);
    JButton btnLogout    = buatTombol("Logout",          DANGER,    Color.WHITE);

    // Label info editing
    JLabel lEditInfo = new JLabel("");
    // TABLE
    JTable      table  = new JTable();
    JScrollPane scroll = new JScrollPane(table);

    PegawaiController controller;
    int selectedId = -1;

    public DashboardView() {
        controller = new PegawaiController();
        setTitle("Dashboard Payroll");
        setSize(980, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // ── Header bar ──────────────────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 980, 56);
        root.add(header);

        JLabel title = new JLabel("Dashboard Payroll");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setBounds(24, 14, 240, 28);
        header.add(title);

        JLabel sub = new JLabel("Manajemen Data Pegawai");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(160, 175, 210));
        sub.setBounds(236, 18, 240, 18);
        header.add(sub);

        btnLogout.setBounds(860, 12, 100, 32);
        header.add(btnLogout);

        // ── Tab bar ──────────────────────────────────────────────────────────
        JPanel tabBar = new JPanel(null);
        tabBar.setBackground(NAVY);
        tabBar.setBounds(0, 56, 980, 44);
        root.add(tabBar);

        // Tab: Data Pegawai (aktif)
        JLabel tabDataPegawai = buatTab("  \uD83D\uDC64  Data Pegawai", true);
        tabDataPegawai.setBounds(0, 0, 180, 44);
        tabBar.add(tabDataPegawai);

        // Tab: Penggajian
        JLabel tabRekap = buatTab("  \uD83D\uDCB0  Rekap Penggajian", false);
        tabRekap.setBounds(180, 0, 160, 44);
        tabBar.add(tabRekap);

        // Garis aksen lime di bawah tab bar
        JPanel accent = new JPanel();
        accent.setBackground(LIME);
        accent.setBounds(0, 98, 980, 4);
        root.add(accent);

        // Navigasi tab
        tabDataPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                // sudah di halaman ini, tidak perlu aksi
            }
        });
       tabRekap.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override public void mouseClicked(java.awt.event.MouseEvent e) {
        new RekapPenggajianView().setVisible(true);
        dispose();
        }
        });

        // ── Panel form kiri ──────────────────────────────────────────────────
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(20, 114, 360, 250);
        formPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(formPanel);

        JLabel formTitle = new JLabel("DATA PEGAWAI");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        formTitle.setForeground(LABEL_CLR);
        formTitle.setBounds(16, 12, 200, 20);
        formPanel.add(formTitle);

        isiForm(formPanel, lNik,     tfNik,     10, 42);
        isiForm(formPanel, lNama,    tfNama,    10, 92);
        isiForm(formPanel, lJabatan, tfJabatan, 10, 142);

        styleLabel(lJenis);
        lJenis.setBounds(16, 192, 100, 20);
        formPanel.add(lJenis);

        styleCombo(cbJenis);
        cbJenis.setBounds(120, 190, 220, 30);
        formPanel.add(cbJenis);

        // ── Panel Aksi kanan ─────────────────────────────────────────────────
        JPanel aksiPanel = new JPanel(null);
        aksiPanel.setBackground(Color.WHITE);
        aksiPanel.setBounds(400, 114, 556, 250);
        aksiPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(aksiPanel);

        JLabel aksiTitle = new JLabel("AKSI");
        aksiTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        aksiTitle.setForeground(LABEL_CLR);
        aksiTitle.setBounds(16, 12, 200, 20);
        aksiPanel.add(aksiTitle);

        // Label info pegawai yang sedang diedit
        lEditInfo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lEditInfo.setForeground(new Color(160, 175, 210));
        lEditInfo.setOpaque(true);
        lEditInfo.setBackground(new Color(45, 55, 80));
        lEditInfo.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        lEditInfo.setBounds(16, 38, 524, 24);
        lEditInfo.setVisible(false);
        aksiPanel.add(lEditInfo);

        // Baris 1: Tambah | Clear
        btnTambah.setBounds(16,  72, 244, 38);
        btnClear .setBounds(276, 72, 244, 38);
        aksiPanel.add(btnTambah);
        aksiPanel.add(btnClear);

        // Baris 2: Update | Refresh
        btnUpdate .setBounds(16,  124, 244, 38);
        btnRefresh.setBounds(276, 124, 244, 38);
        aksiPanel.add(btnUpdate);
        aksiPanel.add(btnRefresh);

        // Baris 3: Hapus (full width)
        btnDelete.setBounds(16, 176, 244, 38);
        btnPenggajian.setBounds(276, 176, 244, 38);
        aksiPanel.add(btnDelete);
        aksiPanel.add(btnPenggajian);
        //agar disabel kalau ga select row
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnPenggajian.setEnabled(false);

        // ── Tabel ─────────────────────────────────────────────────────────────
        styleTable();
        JLabel tblTitle = new JLabel("Daftar Pegawai");
        tblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        tblTitle.setForeground(DARK);
        tblTitle.setBounds(20, 380, 200, 20);
        root.add(tblTitle);

        scroll.setBounds(20, 404, 936, 224);
        scroll.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scroll);

        // ── Load data & listener ──────────────────────────────────────────────
        controller.tampilData(table);

        btnTambah.addActionListener(e -> {
            try {
                 // VALIDASI KOSONG
                if (tfNik.getText().trim().isEmpty()
                        || tfNama.getText().trim().isEmpty()
                        || tfJabatan.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Semua field wajib diisi!");
                    return;
                }
                // VALIDASI NIK
                if (!tfNik.getText().trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null,"NIK harus angka!");
                    return;
                }
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
                // VALIDASI KOSONG
               if (tfNik.getText().trim().isEmpty()
                       || tfNama.getText().trim().isEmpty()
                       || tfJabatan.getText().trim().isEmpty()) {
                   JOptionPane.showMessageDialog(
                           null,
                           "Semua field wajib diisi!");
                   return;
               }
               // VALIDASI NIK
               if (!tfNik.getText().trim().matches("\\d+")) {
                   JOptionPane.showMessageDialog(null,"NIK harus angka!");
                   return;
               }
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
                new PenggajianView(selectedId, tfNama.getText(),
                        cbJenis.getSelectedItem().toString()).setVisible(true);
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                selectedId = Integer.parseInt(table.getValueAt(row, 0).toString());
                tfNik.setText(table.getValueAt(row, 1).toString());
                tfNama.setText(table.getValueAt(row, 2).toString());
                tfJabatan.setText(table.getValueAt(row, 3).toString());
                cbJenis.setSelectedItem(table.getValueAt(row, 4).toString());

                // Tampilkan label info editing
                lEditInfo.setText("  \uD83D\uDC64  Mengedit: " + tfNama.getText() + " (ID " + selectedId + ")");
                lEditInfo.setVisible(true);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnPenggajian.setEnabled(true);
            }
        });
    }

    // ── Helper: buat tab label ────────────────────────────────────────────────
    private JLabel buatTab(String teks, boolean aktif) {
        JLabel tab = new JLabel(teks) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (aktif) {
                    // Garis bawah lime sebagai indikator aktif
                    g2.setColor(LIME);
                    g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tab.setFont(new Font("SansSerif", aktif ? Font.BOLD : Font.PLAIN, 13));
        tab.setForeground(aktif ? Color.WHITE : TAB_INACTIVE);
        tab.setOpaque(false);
        tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tab.setHorizontalAlignment(SwingConstants.CENTER);
        return tab;
    }

    // ── Helper: isi satu baris label + field ─────────────────────────────────
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

    // ── Helper: buat tombol berwarna ─────────────────────────────────────────
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

    // ── Clear form ───────────────────────────────────────────────────────────
    private void clearForm() {
        tfNik.setText("");
        tfNama.setText("");
        tfJabatan.setText("");
        cbJenis.setSelectedIndex(0);
        selectedId = -1;
        lEditInfo.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnPenggajian.setEnabled(false);
    }
}