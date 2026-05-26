package view;

import controller.PenggajianController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import model.Penggajian;

public class PenggajianView extends JFrame {
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

    JTextField tfJamKerja  = new JTextField();
    JTextField tfAbsen     = new JTextField();
    JTextField tfBonus     = new JTextField();
    JTextField tfGajiPokok = new JTextField();
    JTextField tfTunjangan = new JTextField();

    JButton btnHitung = buatTombol("Hitung Gaji", BLUE, Color.WHITE);
    JButton btnCetak  = buatTombol("Cetak Slip",  NAVY, Color.WHITE);

    JTextArea   areaSlip = new JTextArea();
    JScrollPane scroll   = new JScrollPane(areaSlip);

    PenggajianController controller;
    int    idPegawai;
    String namaPegawai;
    String jenisPegawai;

    // Mode edit
    boolean modeEdit     = false;
    int     idPenggajian = -1; // -1 = insert, >0 = edit

    // Constructor INSERT (dari DashboardView)
    public PenggajianView(int id, String nama, String jenis) {
        controller   = new PenggajianController();
        idPegawai    = id;
        namaPegawai  = nama;
        jenisPegawai = jenis;

        double[] gajiDasar = controller.getGajiDasar(idPegawai, jenisPegawai);
        bangunUI(gajiDasar[0], gajiDasar[1], 0, 0); // bonus=0, absen=0
    }

    // Constructor EDIT (dari RekapPenggajianView)
    public PenggajianView(int idPenggajian, int idPegawai, String nama,
                          String jenis, double bonusLama, int absenLama) {
        controller        = new PenggajianController();
        this.idPenggajian = idPenggajian;
        this.idPegawai    = idPegawai;
        this.namaPegawai  = nama;
        this.jenisPegawai = jenis;
        this.modeEdit     = true;

        double[] gajiDasar = controller.getGajiDasar(idPegawai, jenisPegawai);
        bangunUI(gajiDasar[0], gajiDasar[1], bonusLama, absenLama);
    }

    // UI
    private void bangunUI(double gajiPokokDB, double tunjanganDB,
                          double bonusAwal, int absenAwal) {

        setTitle(modeEdit ? "Edit Penggajian" : "Penggajian");
        setSize(680, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // Header
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 680, 56);
        root.add(header);

        JLabel title = new JLabel(modeEdit ? "Edit Penggajian" : "Penggajian");
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

        // Info Pegawai 
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBounds(20, 72, 640, 60);
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

        // Input
        int inputPanelH = "Tetap".equals(jenisPegawai) ? 240
                        : "PartTime".equals(jenisPegawai) ? 240 : 190;

        JPanel inputPanel = new JPanel(null);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBounds(20, 144, 640, inputPanelH);
        inputPanel.setBorder(BorderFactory.createLineBorder(FIELD_BOR, 1));
        root.add(inputPanel);

        JLabel inputTitle = new JLabel("Input Penggajian");
        inputTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        inputTitle.setForeground(DARK);
        inputTitle.setBounds(16, 10, 200, 18);
        inputPanel.add(inputTitle);

        btnHitung.setBounds(410, 38, 210, 38);
        btnCetak .setBounds(410, 90, 210, 38);
        inputPanel.add(btnHitung);
        inputPanel.add(btnCetak);

        int y = 38;

        if ("Tetap".equals(jenisPegawai)) {
            JLabel lGP = new JLabel("Gaji Pokok");
            styleLabel(lGP);
            lGP.setBounds(16, y, 140, 20);
            inputPanel.add(lGP);
            styleField(tfGajiPokok);
            tfGajiPokok.setText(String.valueOf((long) gajiPokokDB));
            tfGajiPokok.setBounds(160, y - 4, 220, 32);
            inputPanel.add(tfGajiPokok);
            y += 50;

            JLabel lTunj = new JLabel("Tunjangan");
            styleLabel(lTunj);
            lTunj.setBounds(16, y, 140, 20);
            inputPanel.add(lTunj);
            styleField(tfTunjangan);
            tfTunjangan.setText(String.valueOf((long) tunjanganDB));
            tfTunjangan.setBounds(160, y - 4, 220, 32);
            inputPanel.add(tfTunjangan);
            y += 50;

        } else if ("Kontrak".equals(jenisPegawai)) {
            JLabel lUpah = new JLabel("Upah/Bulan");
            styleLabel(lUpah);
            lUpah.setBounds(16, y, 140, 20);
            inputPanel.add(lUpah);
            styleField(tfGajiPokok);
            tfGajiPokok.setText(String.valueOf((long) gajiPokokDB));
            tfGajiPokok.setBounds(160, y - 4, 220, 32);
            inputPanel.add(tfGajiPokok);
            y += 50;

        } else { // PartTime
            JLabel lUpahJam = new JLabel("Upah/Jam");
            styleLabel(lUpahJam);
            lUpahJam.setBounds(16, y, 140, 20);
            inputPanel.add(lUpahJam);
            styleField(tfGajiPokok);
            tfGajiPokok.setText(String.valueOf((long) gajiPokokDB));
            tfGajiPokok.setBounds(160, y - 4, 220, 32);
            inputPanel.add(tfGajiPokok);
            y += 50;

            styleLabel(lJamKerja);
            lJamKerja.setBounds(16, y, 140, 20);
            inputPanel.add(lJamKerja);
            styleField(tfJamKerja);
            tfJamKerja.setBounds(160, y - 4, 220, 32);
            inputPanel.add(tfJamKerja);
            y += 50;
        }

        styleLabel(lAbsen);
        lAbsen.setBounds(16, y, 140, 20);
        inputPanel.add(lAbsen);
        styleField(tfAbsen);
        tfAbsen.setText(absenAwal > 0 ? String.valueOf(absenAwal) : "");
        tfAbsen.setBounds(160, y - 4, 220, 32);
        inputPanel.add(tfAbsen);
        y += 50;

        styleLabel(lBonus);
        lBonus.setBounds(16, y, 140, 20);
        inputPanel.add(lBonus);
        styleField(tfBonus);
        tfBonus.setText(bonusAwal > 0 ? String.valueOf((long) bonusAwal) : "");
        tfBonus.setBounds(160, y - 4, 220, 32);
        inputPanel.add(tfBonus);

        // ── Slip Panel ────────────────────────────────────────────────────────
        int slipY = 144 + inputPanelH + 10;

        JLabel slipTitle = new JLabel("Slip Gaji");
        slipTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        slipTitle.setForeground(DARK);
        slipTitle.setBounds(20, slipY, 200, 18);
        root.add(slipTitle);

        areaSlip.setEditable(false);
        areaSlip.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaSlip.setForeground(DARK);
        areaSlip.setBackground(Color.WHITE);
        areaSlip.setMargin(new Insets(12, 14, 12, 14));

        scroll.setBounds(20, slipY + 22, 640, 185);
        scroll.setBorder(BorderFactory.createLineBorder(FIELD_BOR));
        root.add(scroll);

        // Hitung
        btnHitung.addActionListener(e -> {
            try {
                double upah  = Double.parseDouble(tfGajiPokok.getText());
                double tunj  = "Tetap".equals(jenisPegawai)
                               ? Double.parseDouble(tfTunjangan.getText()) : 0;
                int    absen = Integer.parseInt(tfAbsen.getText());
                double bonus = tfBonus.getText().isEmpty() ? 0
                               : Double.parseDouble(tfBonus.getText());

                double gajiBruto;
                if ("Tetap".equals(jenisPegawai)) {
                    gajiBruto = upah + tunj + bonus;
                } else if ("Kontrak".equals(jenisPegawai)) {
                    gajiBruto = upah + bonus;
                } else {
                    double jamKerja = Double.parseDouble(tfJamKerja.getText());
                    gajiBruto = (upah * jamKerja) + bonus;
                }

                double bpjs          = 100000;
                double potonganAbsen = absen * 20000;
                double totalPotongan = bpjs + potonganAbsen;
                double gajiBersih    = gajiBruto - totalPotongan;

                // Tampilkan slip
                StringBuilder slip = new StringBuilder();
                slip.append("==============================\n");
                slip.append("          SLIP GAJI\n");
                slip.append("==============================\n\n");
                slip.append("Nama Pegawai  : ").append(namaPegawai).append("\n");
                slip.append("Jenis Pegawai : ").append(jenisPegawai).append("\n\n");
                if ("Tetap".equals(jenisPegawai)) {
                    slip.append("Gaji Pokok    : Rp ").append(String.format("%,.0f", upah)).append("\n");
                    slip.append("Tunjangan     : Rp ").append(String.format("%,.0f", tunj)).append("\n");
                } else if ("Kontrak".equals(jenisPegawai)) {
                    slip.append("Upah/Bulan    : Rp ").append(String.format("%,.0f", upah)).append("\n");
                } else {
                    slip.append("Upah/Jam      : Rp ").append(String.format("%,.0f", upah)).append("\n");
                }
                slip.append("Bonus         : Rp ").append(String.format("%,.0f", bonus)).append("\n");
                slip.append("BPJS          : Rp ").append(String.format("%,.0f", bpjs)).append("\n");
                slip.append("Pot. Absen    : Rp ").append(String.format("%,.0f", potonganAbsen)).append("\n\n");
                slip.append("TOTAL GAJI    : Rp ").append(String.format("%,.0f", gajiBersih)).append("\n\n");
                slip.append("==============================");
                areaSlip.setText(slip.toString());

                // Simpan gaji dasar ke tabel detail
                controller.simpanGajiDasar(idPegawai, jenisPegawai, upah, tunj);

                // Insert/update
                if (modeEdit) {
                    controller.updatePenggajian(
                            idPenggajian,
                            gajiBruto,
                            totalPotongan,
                            gajiBersih,
                            bonus,
                            absen
                    );
                    JOptionPane.showMessageDialog(null, "Penggajian berhasil diupdate!");
                } else {
                    Penggajian p = new Penggajian();
                    p.setIdPegawai(idPegawai);
                    p.setBonus(bonus);
                    p.setJumlahTerlambat(absen);
                    p.setTotalPotongan(totalPotongan);
                    p.setGajiKotor(gajiBruto);
                    p.setGajiBersih(gajiBersih);
                    p.setBulan(java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1);
                    p.setTahun(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
                    controller.insertPenggajian(p);
                    JOptionPane.showMessageDialog(null, "Penggajian berhasil disimpan!");
                }

                dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input tidak valid: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
        });

        btnCetak.addActionListener(e -> {
            try { 
                areaSlip.print(); 
                JOptionPane.showMessageDialog(null,"Slip gaji berhasil dicetak.");
            }catch (Exception ex) { 
                JOptionPane.showMessageDialog(null, ex.getMessage()); }
        });
    }
    
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
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed()  ? bg.darker()
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