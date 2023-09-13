package HospitalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HospitalApp extends JFrame {
    private BosSayfa bosSayfa;

    public HospitalApp() {
        setTitle("ACIBADEM HASTANESİ");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bosSayfa = new BosSayfa(this);

        add(bosSayfa);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HospitalApp();
            }
        });
    }
}

class BosSayfa extends JPanel {
    private Hastane hastane;
    private HospitalApp parent;
    private JTable hastaListesiTable;

    public BosSayfa(HospitalApp parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        // ACIBADEM HASTANESİ bilgilerini ekleyelim
        hastane = new Hastane("ACIBADEM HASTANESİ", "Kayseri / Melikgazi", "03123763548");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        JButton hastaneBilgileriButton = new JButton("1- Hastane Bilgileri Görüntüle");
        JButton hastaKayitButton = new JButton("2- Hasta Kayıt");
        JButton hastaBulButton = new JButton("3- Hasta Bul");
        JButton hastaListesiButton = new JButton("4- Hasta Listesi");
        JButton anaMenuButton = new JButton("5- Ana Menu");

        buttonPanel.add(hastaneBilgileriButton);
        buttonPanel.add(hastaKayitButton);
        buttonPanel.add(hastaBulButton);
        buttonPanel.add(hastaListesiButton);
        buttonPanel.add(anaMenuButton);

        hastaListesiTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(hastaListesiTable);

        add(buttonPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);

        hastaneBilgileriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bilgi = "Hastane Adı: " + hastane.getAd() + "\nAdres: " + hastane.getAdres() + "\nTelefon No: " + hastane.getTelefonNo();
                JOptionPane.showMessageDialog(null, bilgi, "Hastane Bilgileri", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        hastaListesiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHastaListesi();
            }
        });

        hastaKayitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHastaKayit();
            }
        });

        hastaBulButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHastaBulmaPenceresi();
            }
        });
    }

    private void showHastaListesi() {
        try {
            // Veritabanına bağlan
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "12345678");

            // SQL sorgusu oluştur
            String sql = "SELECT * FROM hastaListesi";

            // Sorguyu çalıştır
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Sonuçları bir JTable'a ekle
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            DefaultTableModel tableModel = new DefaultTableModel();
            for (int column = 1; column <= columnCount; column++) {
                tableModel.addColumn(metaData.getColumnLabel(column));
            }

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int column = 1; column <= columnCount; column++) {
                    row[column - 1] = resultSet.getObject(column);
                }
                tableModel.addRow(row);
            }

            hastaListesiTable.setModel(tableModel);

            // Bağlantıyı kapat
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void showHastaKayit() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel tcNoLabel = new JLabel("TC No:");
        JTextField tcNoField = new JTextField();

        JLabel isimLabel = new JLabel("İsim:");
        JTextField isimField = new JTextField();

        JLabel soyisimLabel = new JLabel("Soyisim:");
        JTextField soyisimField = new JTextField();

        JLabel dYiliLabel = new JLabel("Doğum Yılı:");
        JTextField dYiliField = new JTextField();

        JLabel adresLabel = new JLabel("Adres:");
        JTextField adresField = new JTextField();

        JLabel telNoLabel = new JLabel("Telefon Numarası:");
        JTextField telNoField = new JTextField();

        panel.add(tcNoLabel);
        panel.add(tcNoField);
        panel.add(isimLabel);
        panel.add(isimField);
        panel.add(soyisimLabel);
        panel.add(soyisimField);
        panel.add(dYiliLabel);
        panel.add(dYiliField);
        panel.add(adresLabel);
        panel.add(adresField);
        panel.add(telNoLabel);
        panel.add(telNoField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Hasta Kayıt Formu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String tcNo = tcNoField.getText();
            String isim = isimField.getText();
            String soyisim = soyisimField.getText();
            String dYili = dYiliField.getText();
            String adres = adresField.getText();
            String telNo = telNoField.getText();

            try {
                // Veritabanına bağlan
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "12345678");

                // SQL sorgusu oluştur
                String sql = "INSERT INTO hastaListesi (tcNo, isim, soyisim, dYili, adres, telNo) " +
                        "VALUES ('" + tcNo + "', '" + isim + "', '" + soyisim + "', '" + dYili + "', '" + adres + "', '" + telNo + "')";

                // Sorguyu çalıştır
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                // Bağlantıyı kapat
                statement.close();
                connection.close();

                JOptionPane.showMessageDialog(null, "Hasta Başarıyla Kaydedildi", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Hasta Kaydı Başarısız", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showHastaBulmaPenceresi() {
        HastaBulmaPenceresi bulmaPenceresi = new HastaBulmaPenceresi();
        bulmaPenceresi.setVisible(true);
    }
}

class HastaBulmaPenceresi extends JFrame {
    private JTextField tcNoField;
    private JButton bulButton;

    public HastaBulmaPenceresi() {
        setTitle("Hasta Bulma");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Pencereyi kapatınca sadece bu pencereyi kapatır, ana pencereyi kapatmaz

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel tcNoLabel = new JLabel("TC Kimlik No:");
        tcNoField = new JTextField();
        bulButton = new JButton("Bul");

        panel.add(tcNoLabel);
        panel.add(tcNoField);
        panel.add(new JLabel()); // Boş etiket ekleyerek düzeni dengeliyoruz
        panel.add(bulButton);

        add(panel);

        bulButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tcNo = tcNoField.getText();
                hastaBul(tcNo);
            }
        });
    }

    private void hastaBul(String tcNo) {
        try {
            // Veritabanına bağlan
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "12345678");

            // SQL sorgusu oluştur
            String sql = "SELECT * FROM hastaListesi WHERE tcNo = ?";

            // Sorguyu çalıştır
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tcNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Hasta bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                String tcNoResult = resultSet.getString("tcNo");
                String isim = resultSet.getString("isim");
                String soyisim = resultSet.getString("soyisim");
                String dYili = resultSet.getString("dYili");
                String adres = resultSet.getString("adres");
                String telNo = resultSet.getString("telNo");

                String bilgi = "TC No: " + tcNoResult + "\nİsim: " + isim + "\nSoyisim: " + soyisim + "\nDoğum Yılı: " + dYili + "\nAdres: " + adres + "\nTelefon No: " + telNo;
                JOptionPane.showMessageDialog(null, bilgi, "Hasta Bilgileri", JOptionPane.INFORMATION_MESSAGE);
            }

            // Bağlantıyı kapat
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

class Hastane {
    private String ad;
    private String adres;
    private String telefonNo;

    public Hastane(String ad, String adres, String telefonNo) {
        this.ad = ad;
        this.adres = adres;
        this.telefonNo = telefonNo;
    }

    public String getAd() {
        return ad;
    }

    public String getAdres() {
        return adres;
    }

    public String getTelefonNo() {
        return telefonNo;
    }


}
