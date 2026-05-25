package view;

import controller.PenggajianController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import model.Penggajian;

public class PenggajianView extends JFrame {

    //Color Palette
    static final Color BG        = new Color(240, 242, 248);
    static final Color NAVY      = new Color(25, 35, 60);
    static final Color BLUE      = new Color(66, 133, 244);
    static final Color LIME      = new Color(180, 220, 60);
    static final Color DARK      = new Color(45, 50, 70);
    static final Color LABEL_CLR = new Color(100, 110, 140);
    static final Color FIELD_BG  = Color.WHITE;
    static final Color FIELD_BOR = new Color(210, 215, 230);

    JLabel lNama     = new JLabel("Nama Pegawai");
    JLabel lJenis    = new JLabel("Jenis Pegawai");
    JLabel lJamKerja = new JLabel("Jam Kerja");
    JLabel lAbsen    = new JLabel("Jumlah Absen");
    JLabel lBonus    = new JLabel("Bonus");

    JLabel vNama  = new JLabel();
    JLabel vJenis = new JLabel();

    JTextField tfJamKerja = new JTextField();
    JTextField tfAbsen    = new JTextField();
    JTextField tfBonus    = new JTextField();

    JButton btnHitung = buatTombol("Hitung Gaji", BLUE,   Color.WHITE);
    JButton btnCetak  = buatTombol("Cetak Slip",  NAVY,   Color.WHITE);

    JTextArea   areaSlip = new JTextArea();
    JScrollPane scroll   = new JScrollPane(areaSlip);

    PenggajianController controller;
    int    idPegawai;
    String namaPegawai;
    String jenisPegawai;

    public PenggajianView(int id, String nama, String jenis) {
        controller   = new PenggajianController();
        idPegawai    = id;
        namaPegawai  = nama;
        jenisPegawai = jenis;

        setTitle("Penggajian");
        setSize(680, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ── Root ─────────────────────────────────────────────────────────────
        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // ── Header ───────────────────────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 680, 56);
        root.add(header);

        JLabel title = new JLabel("Penggajian");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 14, 200, 28);
        header.add(title);

        JLabel sub = new JLabel("Hitung & cetak slip gaji pegawai");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(160, 175, 210));
        sub.setBounds(195, 18, 280, 18);
        header.add(sub);

        JPanel accent = new JPanel();
        accent.setBackground(LIME);
        accent.setBounds(0, 52, 680, 4);
        root.add(accent);

        // ── Panel Info Pegawai ────────────────────────────────────────────────
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBounds(20, 72, 640, 80);
        infoPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(infoPanel);

        JLabel infoTitle = new JLabel("Informasi Pegawai");
        infoTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        infoTitle.setForeground(DARK);
        infoTitle.setBounds(16, 8, 200, 18);
        infoPanel.add(infoTitle);

        styleLabel(lNama);
        lNama.setBounds(16, 34, 120, 20);
        infoPanel.add(lNama);

        vNama.setFont(new Font("SansSerif", Font.BOLD, 13));
        vNama.setForeground(DARK);
        vNama.setText(namaPegawai);
        vNama.setBounds(140, 34, 220, 20);
        infoPanel.add(vNama);

        styleLabel(lJenis);
        lJenis.setBounds(370, 34, 120, 20);
        infoPanel.add(lJenis);

        vJenis.setFont(new Font("SansSerif", Font.BOLD, 13));
        vJenis.setForeground(BLUE);
        vJenis.setText(jenisPegawai);
        vJenis.setBounds(490, 34, 140, 20);
        infoPanel.add(vJenis);

        // ── Panel Input ───────────────────────────────────────────────────────
        JPanel inputPanel = new JPanel(null);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBounds(20, 164, 640, 190);
        inputPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(inputPanel);

        JLabel inputTitle = new JLabel("Input Penggajian");
        inputTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        inputTitle.setForeground(DARK);
        inputTitle.setBounds(16, 10, 200, 18);
        inputPanel.add(inputTitle);

        // Jam Kerja (hanya PartTime)
        styleLabel(lJamKerja);
        lJamKerja.setBounds(16, 42, 140, 20);
        inputPanel.add(lJamKerja);
        styleField(tfJamKerja);
        tfJamKerja.setBounds(160, 38, 220, 32);
        inputPanel.add(tfJamKerja);

        // Absen
        styleLabel(lAbsen);
        lAbsen.setBounds(16, 92, 140, 20);
        inputPanel.add(lAbsen);
        styleField(tfAbsen);
        tfAbsen.setBounds(160, 88, 220, 32);
        inputPanel.add(tfAbsen);

        // Bonus
        styleLabel(lBonus);
        lBonus.setBounds(16, 142, 140, 20);
        inputPanel.add(lBonus);
        styleField(tfBonus);
        tfBonus.setBounds(160, 138, 220, 32);
        inputPanel.add(tfBonus);

        // Tombol Hitung & Cetak di kanan input panel
        btnHitung.setBounds(410, 38, 210, 38);
        btnCetak.setBounds(410, 90, 210, 38);
        inputPanel.add(btnHitung);
        inputPanel.add(btnCetak);

        // ── Panel Slip ────────────────────────────────────────────────────────
        JLabel slipTitle = new JLabel("Slip Gaji");
        slipTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        slipTitle.setForeground(DARK);
        slipTitle.setBounds(20, 366, 200, 18);
        root.add(slipTitle);

        areaSlip.setEditable(false);
        areaSlip.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaSlip.setForeground(DARK);
        areaSlip.setBackground(Color.WHITE);
        areaSlip.setMargin(new Insets(12, 14, 12, 14));

        scroll.setBounds(20, 388, 640, 220);
        scroll.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scroll);

        // ── Cek jenis pegawai (tidak diubah) ─────────────────────────────────
        if (!jenisPegawai.equals("PartTime")) {
            lJamKerja.setVisible(false);
            tfJamKerja.setVisible(false);
        }

        // ── Semua listener (tidak diubah) ────────────────────────────────────
        btnHitung.addActionListener(e -> {
            try {
                double jamKerja = 0;
                if (jenisPegawai.equals("PartTime")) {
                    jamKerja = Double.parseDouble(tfJamKerja.getText());
                }
                int    absen = Integer.parseInt(tfAbsen.getText());
                double bonus = Double.parseDouble(tfBonus.getText());
                double gajiPokok;
                if      (jenisPegawai.equals("Tetap"))   gajiPokok = 5000000;
                else if (jenisPegawai.equals("Kontrak"))  gajiPokok = 3500000;
                else                                      gajiPokok = jamKerja * 50000;

                double bpjs          = 100000;
                double potonganAbsen = absen * 20000;
                double total         = gajiPokok + bonus - bpjs - potonganAbsen;

                areaSlip.setText(
                    "========================\n"
                    + "       SLIP GAJI\n"
                    + "========================\n\n"
                    + "Nama Pegawai  : " + namaPegawai  + "\n"
                    + "Jenis Pegawai : " + jenisPegawai + "\n\n"
                    + "Gaji Pokok    : Rp " + gajiPokok     + "\n"
                    + "Bonus         : Rp " + bonus          + "\n"
                    + "BPJS          : Rp " + bpjs           + "\n"
                    + "Pot. Absen    : Rp " + potonganAbsen  + "\n\n"
                    + "TOTAL GAJI    : Rp " + total          + "\n\n"
                    + "========================"
                );

                Penggajian p = new Penggajian();
                p.setIdPegawai(idPegawai);
                p.setBonus(bonus);
                p.setJumlahTerlambat(absen);
                p.setTotalPotongan(bpjs + potonganAbsen);
                p.setGajiKotor(gajiPokok);
                p.setGajiBersih(total);
                p.setBulan(5);
                p.setTahun(2026);
                controller.insertPenggajian(p);

                JOptionPane.showMessageDialog(null, "Penggajian berhasil");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        btnCetak.addActionListener(e -> {
            try {
                areaSlip.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private void styleLabel(JLabel l) {
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(LABEL_CLR);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(DARK);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(FIELD_BOR, 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
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