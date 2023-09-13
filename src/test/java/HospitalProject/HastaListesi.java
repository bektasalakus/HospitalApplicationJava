package HospitalProject;

import javax.swing.*;

public class HastaListesi {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HospitalApp();
            }
        });
    }
}
