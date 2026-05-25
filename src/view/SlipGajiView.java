package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SlipGajiView extends JFrame {

    JTextArea area = new JTextArea();

    public SlipGajiView() {

        setTitle("Slip Gaji");

        setSize(400, 500);

        setLocationRelativeTo(null);

        add(new JScrollPane(area));

        area.setText(
                "========================\\n"
                + "       SLIP GAJI\\n"
                + "========================\\n\\n"
                + "Nama Pegawai :\\n"
                + "Jabatan      :\\n"
                + "Gaji Bersih  :\\n\\n"
                + "========================"
        );
    }
}