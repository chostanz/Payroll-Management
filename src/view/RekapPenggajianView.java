package view;

import controller.RekapPenggajianController;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

import static view.DashboardView.*;
public class RekapPenggajianView extends JFrame {

    private final RekapPenggajianController controller = new RekapPenggajianController();

    // Filter 
    private final JComboBox<String> cbBulan = new JComboBox<>(new String[]{
        "Januari","Februari","Maret","April","Mei","Juni",
        "Juli","Agustus","September","Oktober","November","Desember"
    });
    private final JComboBox<String> cbTahun = new JComboBox<>(new String[]{
        "2023","2024","2025","2026"
    });

    // Tabel detail 
    private final JTable      tableDetail = new JTable();
    private final JScrollPane scrollDetail = new JScrollPane(tableDetail);

    // Kartu 
    private JLabel lblTotalPegawai, lblTotalGaji, lblSudah, lblBelum;

    // Tombol 
    private final JButton btnFilter  = buatTombol("Filter",         BLUE,      Color.WHITE);
    private final JButton btnKembali = buatTombol("← Data Pegawai", FIELD_BOR, DARK);
    private final JButton btnRefresh = buatTombol("↻ Refresh",      FIELD_BOR, DARK);

    private static final int W = 980;
    private static final int H = 680;

    public RekapPenggajianView() {
        setTitle("Dashboard Payroll – Rekap Gaji");
        setSize(W, H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        buatHeader(root);
        buatTabNav(root);
        buatKartuSummary(root);
        buatFilterBar(root);
        buatTabelDetail(root);
        buatTombolBar(root);

        // Default: bulan & tahun sekarang
        bulanTahunSekarang();
        muatData();

        // Listeners
        btnFilter.addActionListener(e -> muatData());
        btnRefresh.addActionListener(e -> muatData());
        btnKembali.addActionListener(e -> {
            new DashboardView().setVisible(true);
            dispose();
        });
        tableDetail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            int row = tableDetail.rowAtPoint(e.getPoint());
            int col = tableDetail.columnAtPoint(e.getPoint());

            if (col != 13) return; // hanya kolom Aksi

            int idPenggajian = Integer.parseInt(tableDetail.getValueAt(row, 0).toString());
            if (idPenggajian == 0) {
                JOptionPane.showMessageDialog(null,
                    "Pegawai ini belum diproses penggajiannya bulan ini.");
                return;
            }

            String statusSaatIni = tableDetail.getValueAt(row, 12).toString();

            JPopupMenu menu = new JPopupMenu();
            JMenuItem editItem  = new JMenuItem("✏ Edit Penggajian");
            JMenuItem lunasItem = new JMenuItem("✔ Tandai Lunas");
            JMenuItem batalItem = new JMenuItem("↩ Batalkan Pembayaran");

            // Kalau sudah Lunas, edit tidak bisa
            editItem.setEnabled(!"Lunas".equals(statusSaatIni));

            menu.add(editItem);
            menu.add(lunasItem);
            menu.add(batalItem);
            menu.show(tableDetail, e.getX(), e.getY());

            // EDIT
            editItem.addActionListener(ev -> {
                int    idPegawai    = Integer.parseInt(tableDetail.getValueAt(row, 1).toString());
                String nama         = tableDetail.getValueAt(row, 3).toString();
                String jenis        = tableDetail.getValueAt(row, 5).toString();
                double bonus        = Double.parseDouble(tableDetail.getValueAt(row, 8).toString());
                int    absen    = Integer.parseInt(tableDetail.getValueAt(row, 9).toString());

                PenggajianView penggajianView = new PenggajianView(idPenggajian, idPegawai, nama, jenis, bonus, absen);

                // Panggil muatData() otomatis saat PenggajianView ditutup
                penggajianView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        muatData();
                    }
                });

                penggajianView.setVisible(true);
            });

            // TANDAI LUNAS
            lunasItem.addActionListener(ev -> {
                if (JOptionPane.showConfirmDialog(null,
                        "Tandai penggajian sebagai lunas?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    controller.updateStatusBayar(idPenggajian, "Lunas");
                    muatData();
                    tableDetail.setValueAt("Lunas", row, 12);
                    JOptionPane.showMessageDialog(null, "Status diubah menjadi Lunas");
                }
            });

            // BATALKAN
            batalItem.addActionListener(ev -> {
                if (JOptionPane.showConfirmDialog(null,
                        "Batalkan pembayaran?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    controller.updateStatusBayar(idPenggajian, "Belum");
                    muatData();
                    tableDetail.setValueAt("Belum", row, 12);
                    JOptionPane.showMessageDialog(null, "Pembayaran dibatalkan");
                }
            });
        }
    });}


        // ── Muat data (summary + tabel) ──────────────────────────────────────────
        private void muatData() {
            int bulan = cbBulan.getSelectedIndex() + 1;
            int tahun = Integer.parseInt(cbTahun.getSelectedItem().toString());

            // Update kartu
            int[] s = controller.getSummary(bulan, tahun);
            lblTotalPegawai.setText(String.valueOf(s[0]));
            lblTotalGaji.setText("Rp " + String.format("%,.0f", (double) s[3]));
            lblSudah.setText(s[1] + " orang");
            lblBelum.setText(s[2] + " orang");

            // Update tabel detail
            controller.tampilDetailBulan(tableDetail, bulan, tahun);
            styleTable(tableDetail);
    }
    // HEADER
    private void buatHeader(JPanel root) {
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, W, 56);
        root.add(header);

        JLabel titleLbl = new JLabel("Dashboard Payroll");
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setBounds(24, 14, 260, 28);
        header.add(titleLbl);

        JLabel subLbl = new JLabel("Rekap Gaji Pegawai");
        subLbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subLbl.setForeground(new Color(160, 175, 210));
        subLbl.setBounds(220, 18, 260, 18);
        header.add(subLbl);

        JButton btnLogout = buatTombol("Logout", DANGER, Color.WHITE);
        btnLogout.setBounds(860, 12, 100, 32);
        btnLogout.addActionListener(e -> { new LoginView().setVisible(true); dispose(); });
        header.add(btnLogout);

        JPanel accent = new JPanel();
        accent.setBackground(LIME);
        accent.setBounds(0, 52, W, 4);
        root.add(accent);
    }

    // NAVIGASI
    private void buatTabNav(JPanel root) {
        JPanel navBar = new JPanel(null);
        navBar.setBackground(NAVY);
        navBar.setBounds(0, 56, W, 44);
        root.add(navBar);

        // Tab Data Pegawai (tidak aktif)
        JPanel tabDataPegawai = buatTab("Data Pegawai", false);
        tabDataPegawai.setBounds(0, 0, 180, 44);
        tabDataPegawai.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new DashboardView().setVisible(true);
                dispose();
            }
        });
        navBar.add(tabDataPegawai);

        // Tab Rekap Gaji (aktif)
        JPanel tabRekap = buatTab("Rekap Gaji", true);
        tabRekap.setBounds(180, 0, 160, 44);
        navBar.add(tabRekap);
    }

    private JPanel buatTab(String teks, boolean aktif) {
        JPanel tab = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (aktif) {
                    g.setColor(LIME);
                    g.fillRect(0, getHeight() - 3, getWidth(), 3);
                }
            }
        };
        tab.setBackground(NAVY);
        tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lbl = new JLabel(teks, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", aktif ? Font.BOLD : Font.PLAIN, 13));
        lbl.setForeground(aktif ? Color.WHITE : new Color(143, 163, 200));
        lbl.setBounds(0, 0, tab.getPreferredSize().width, 44);
        // setBounds diset dari luar, jadi gunakan fill
        lbl.setBounds(0, 0, 200, 44);
        tab.add(lbl);

        return tab;
    }
    //kartu atas
    private void buatKartuSummary(JPanel root) {
        int kartuW = 220;
        int kartuH = 90;
        int startX = 20;
        int startY = 112;
        int gap    = 16;

        // Total Pegawai
        JPanel k1 = buatKartu(root, startX, startY, kartuW, kartuH);
        buatIsiKartu(k1, "TOTAL PEGAWAI", "0", BLUE);
        lblTotalPegawai = cariValueLabel(k1);

        // Total Gaji Bulan Ini
        JPanel k2 = buatKartu(root, startX + (kartuW + gap), startY, kartuW, kartuH);
        buatIsiKartu(k2, "TOTAL GAJI BULAN INI", "Rp 0", LIME);
        lblTotalGaji = cariValueLabel(k2);

        // Sudah Dibayar
        JPanel k3 = buatKartu(root, startX + (kartuW + gap) * 2, startY, kartuW, kartuH);
        buatIsiKartu(k3, "SUDAH DIBAYAR", "0 orang", new Color(50, 180, 100));
        lblSudah = cariValueLabel(k3);

        // Belum Dibayar
        JPanel k4 = buatKartu(root, startX + (kartuW + gap) * 3, startY, kartuW, kartuH);
        buatIsiKartu(k4, "BELUM DIBAYAR", "0 orang", DANGER);
        lblBelum = cariValueLabel(k4);
    }

    private JPanel buatKartu(JPanel root, int x, int y, int w, int h) {
        JPanel kartu = new JPanel(null);
        kartu.setBackground(Color.WHITE);
        kartu.setBounds(x, y, w, h);
        kartu.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(kartu);
        return kartu;
    }

    private void buatIsiKartu(JPanel kartu, String judul, String nilai, Color warnaNilai) {
        JLabel lJudul = new JLabel(judul);
        lJudul.setFont(new Font("SansSerif", Font.BOLD, 10));
        lJudul.setForeground(LABEL_CLR);
        lJudul.setBounds(14, 12, 200, 16);
        kartu.add(lJudul);

        JLabel lNilai = new JLabel(nilai);
        lNilai.setFont(new Font("SansSerif", Font.BOLD, 22));
        lNilai.setForeground(warnaNilai);
        lNilai.setName("valueLabel"); // penanda untuk dicari
        lNilai.setBounds(14, 34, 200, 30);
        kartu.add(lNilai);

        // Garis bawah berwarna
        JPanel garis = new JPanel();
        garis.setBackground(warnaNilai);
        garis.setBounds(14, 72, 40, 3);
        kartu.add(garis);
    }

    // Helper cari JLabel value dari kartu
    private JLabel cariValueLabel(JPanel kartu) {
        for (Component c : kartu.getComponents()) {
            if (c instanceof JLabel && "valueLabel".equals(c.getName())) {
                return (JLabel) c;
            }
        }
        return new JLabel(); 
    }
    //filter bar
    private void buatFilterBar(JPanel root) {
        JPanel filterPanel = new JPanel(null);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBounds(20, 214, W - 40, 52);
        filterPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(filterPanel);

        JLabel lBulan = new JLabel("Bulan");
        styleLabel(lBulan);
        lBulan.setBounds(14, 8, 50, 14);
        filterPanel.add(lBulan);

        styleCombo(cbBulan);
        cbBulan.setBounds(14, 24, 150, 22);
        filterPanel.add(cbBulan);

        JLabel lTahun = new JLabel("Tahun");
        styleLabel(lTahun);
        lTahun.setBounds(178, 8, 50, 14);
        filterPanel.add(lTahun);

        styleCombo(cbTahun);
        cbTahun.setBounds(178, 24, 90, 22);
        filterPanel.add(cbTahun);

        btnFilter.setBounds(284, 12, 120, 28);
        filterPanel.add(btnFilter);
    }
    //tabel detail
    private void buatTabelDetail(JPanel root) {
        JLabel tblTitle = new JLabel("Detail Penggajian Pegawai");
        tblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        tblTitle.setForeground(DARK);
        tblTitle.setBounds(20, 278, 400, 20);
        root.add(tblTitle);

        scrollDetail.setBounds(20, 300, W - 40, 320);
        scrollDetail.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scrollDetail);
    }

    private void buatTombolBar(JPanel root) {
        btnKembali.setBounds(20, 634, 160, 32);
        root.add(btnKembali);

        btnRefresh.setBounds(190, 634, 120, 32);
        root.add(btnRefresh);
    }
    //style warna status
    private void styleTable(JTable t) {
        t.setFont(new Font("SansSerif", Font.PLAIN, 12));
        t.setRowHeight(32);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(new Color(66, 133, 244, 50));
        t.setSelectionForeground(DARK);
        t.setBackground(Color.WHITE);
        t.setForeground(DARK);

        // Sembunyikan kolom ID (kolom 0)
        int[] hidden = {0, 1, 8, 9};
            for (int idx : hidden) {
                if (t.getColumnCount() > idx) {
                    t.getColumnModel().getColumn(idx).setMinWidth(0);
                    t.getColumnModel().getColumn(idx).setMaxWidth(0);
                    t.getColumnModel().getColumn(idx).setWidth(0);
                }
            }

        JTableHeader th = t.getTableHeader();
        th.setFont(new Font("SansSerif", Font.BOLD, 11));
        th.setBackground(NAVY);
        th.setForeground(Color.WHITE);
        th.setPreferredSize(new Dimension(0, 34));
        th.setBorder(BorderFactory.createEmptyBorder());

        // Kolom Status (index 9) diberi warna hijau/merah
        int colStatus = t.getColumnCount() - 1;
         t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override public Component getTableCellRendererComponent(
                JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
            super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
            setForeground(DARK);
            setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 247, 252));
            if (sel) setBackground(new Color(66, 133, 244, 50));
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            setFont(new Font("SansSerif", Font.PLAIN, 12));

            if (col == 12 && val != null) { // kolom Status
                if ("Lunas".equals(val.toString())) {
                    setForeground(new Color(30, 160, 80));
                } else {
                    setForeground(DANGER);
                }
                setFont(getFont().deriveFont(Font.BOLD));
            }
            return this;
        }
    });
}

    private void styleLabel(JLabel l) {
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(LABEL_CLR);
    }

    private void styleCombo(JComboBox<?> cb) {
        cb.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cb.setBackground(FIELD_BG);
        cb.setForeground(DARK);
    }

    private void bulanTahunSekarang() {
        Calendar cal = Calendar.getInstance();
        cbBulan.setSelectedIndex(cal.get(Calendar.MONTH));
        cbTahun.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
    }
}

