package view;

import controller.RekapPenggajianController;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RekapPenggajianView extends JFrame {

    // ── Color Palette ────────────────────────────────────────────────────────
    static final Color BG        = new Color(240, 242, 248);
    static final Color NAVY      = new Color(25, 35, 60);
    static final Color BLUE      = new Color(66, 133, 244);
    static final Color LIME      = new Color(180, 220, 60);
    static final Color DARK      = new Color(45, 50, 70);
    static final Color LABEL_CLR = new Color(100, 110, 140);
    static final Color FIELD_BOR = new Color(210, 215, 230);

    // Tabel rekap bulanan (atas)
    JTable      tableRekap  = new JTable();
    JScrollPane scrollRekap = new JScrollPane(tableRekap);

    // Tabel detail pegawai (bawah)
    JTable      tableDetail  = new JTable();
    JScrollPane scrollDetail = new JScrollPane(tableDetail);

    // Filter bulan & tahun
    JComboBox<String> cbBulan = new JComboBox<>(new String[]{
        "1","2","3","4","5","6","7","8","9","10","11","12"
    });
    JTextField tfTahun  = new JTextField("2026");
    JButton    btnCari  = buatTombol("Lihat Detail", BLUE,  Color.WHITE);
    JButton    btnRefresh = buatTombol("↻ Refresh",  NAVY,  Color.WHITE);

    RekapPenggajianController controller;

    public RekapPenggajianView() {
        controller = new RekapPenggajianController();

        setTitle("Rekap Penggajian");
        setSize(900, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ── Root ─────────────────────────────────────────────────────────────
        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // ── Header ───────────────────────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 900, 56);
        root.add(header);

        JLabel title = new JLabel("Rekap Penggajian");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 14, 250, 28);
        header.add(title);

        JLabel sub = new JLabel("Ringkasan penggajian per bulan");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(160, 175, 210));
        sub.setBounds(230, 18, 280, 18);
        header.add(sub);

        JPanel accent = new JPanel();
        accent.setBackground(LIME);
        accent.setBounds(0, 52, 900, 4);
        root.add(accent);

        // ── Panel Filter ─────────────────────────────────────────────────────
        JPanel filterPanel = new JPanel(null);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBounds(20, 72, 860, 60);
        filterPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(filterPanel);

        JLabel lBulan = new JLabel("Bulan");
        lBulan.setFont(new Font("SansSerif", Font.BOLD, 11));
        lBulan.setForeground(LABEL_CLR);
        lBulan.setBounds(16, 10, 50, 18);
        filterPanel.add(lBulan);

        cbBulan.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbBulan.setBackground(Color.WHITE);
        cbBulan.setForeground(DARK);
        cbBulan.setBounds(16, 28, 80, 24);
        filterPanel.add(cbBulan);

        JLabel lTahun = new JLabel("Tahun");
        lTahun.setFont(new Font("SansSerif", Font.BOLD, 11));
        lTahun.setForeground(LABEL_CLR);
        lTahun.setBounds(112, 10, 50, 18);
        filterPanel.add(lTahun);

        tfTahun.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tfTahun.setForeground(DARK);
        tfTahun.setBackground(Color.WHITE);
        tfTahun.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BOR, 1),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        tfTahun.setBounds(112, 28, 80, 24);
        filterPanel.add(tfTahun);

        // Focus effect tfTahun
        tfTahun.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                tfTahun.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BLUE, 2),
                    BorderFactory.createEmptyBorder(1, 7, 1, 7)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                tfTahun.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(FIELD_BOR, 1),
                    BorderFactory.createEmptyBorder(2, 8, 2, 8)
                ));
            }
        });

        btnCari.setBounds(210, 16, 150, 30);
        filterPanel.add(btnCari);

        btnRefresh.setBounds(374, 16, 130, 30);
        filterPanel.add(btnRefresh);

        // ── Tabel Rekap (atas) ───────────────────────────────────────────────
        JLabel lRekap = new JLabel("Rekap Bulanan");
        lRekap.setFont(new Font("SansSerif", Font.BOLD, 12));
        lRekap.setForeground(DARK);
        lRekap.setBounds(20, 144, 200, 18);
        root.add(lRekap);

        styleTable(tableRekap);
        scrollRekap.setBounds(20, 164, 860, 160);
        scrollRekap.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scrollRekap);

        // ── Tabel Detail (bawah) ─────────────────────────────────────────────
        JLabel lDetail = new JLabel("Detail Pegawai");
        lDetail.setFont(new Font("SansSerif", Font.BOLD, 12));
        lDetail.setForeground(DARK);
        lDetail.setBounds(20, 338, 200, 18);
        root.add(lDetail);

        styleTable(tableDetail);
        scrollDetail.setBounds(20, 358, 860, 250);
        scrollDetail.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scrollDetail);

        // ── Load data awal ───────────────────────────────────────────────────
        controller.tampilRekap(tableRekap);

        // ── Listener ─────────────────────────────────────────────────────────
        btnCari.addActionListener(e -> {
            try {
                int bulan = Integer.parseInt(cbBulan.getSelectedItem().toString());
                int tahun = Integer.parseInt(tfTahun.getText());
                controller.tampilDetailBulan(tableDetail, bulan, tahun);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Tahun tidak valid");
            }
        });

        btnRefresh.addActionListener(e -> {
            controller.tampilRekap(tableRekap);
        });

        // Klik baris rekap → otomatis isi detail
        tableRekap.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableRekap.getSelectedRow();
                if (row >= 0) {
                    int bulan = Integer.parseInt(tableRekap.getValueAt(row, 0).toString());
                    int tahun = Integer.parseInt(tableRekap.getValueAt(row, 1).toString());
                    cbBulan.setSelectedItem(String.valueOf(bulan));
                    tfTahun.setText(String.valueOf(tahun));
                    controller.tampilDetailBulan(tableDetail, bulan, tahun);
                }
            }
        });
    }

    // ── Style tabel ──────────────────────────────────────────────────────────
    private void styleTable(JTable table) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(30);
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
        th.setPreferredSize(new Dimension(0, 34));
        th.setBorder(BorderFactory.createEmptyBorder());

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(sel ? new Color(66, 133, 244, 50)
                             : row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252));
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    // ── Helper tombol ─────────────────────────────────────────────────────────
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