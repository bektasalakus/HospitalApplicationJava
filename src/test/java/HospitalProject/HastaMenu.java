package HospitalProject;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class HastaMenu {
    static Scanner scan = new Scanner(System.in);

    public static void hastaMenu() throws SQLException, ClassNotFoundException, InterruptedException {

        String SEC = "";
        System.out.println("============= ACIBADEM HASTANESİ =============\n" +
                "==============  HASTA MENU ==============\n" +
                "\n" +
                "\t   1- Hasta Kayıt\n" +
                "\t   2- Hasta Bul\n" +
                "\t   3- Hasta Listesi\t\t \n" +
                "\t   4- Ana Menu\n");

        System.out.println("Tercih Yapiniz");
        SEC = scan.nextLine();

        switch (SEC) {
            case "1":  // HASTA KAYİT
                HastaMenu.hastaKayit();
                break;
            case "2":  // HASTA BUL
                HastaMenu.hastaBul();
                break;
            case "3":  // HASTA LİSTESİ
                HastaMenu.hastalarListesi();
                break;
            case "4":
                Register.Menu();
            default:
                System.out.println("Tercih Yapiniz");
        }
    }

    public static void hastaBul() throws ClassNotFoundException, SQLException, InterruptedException {

        Connection conn = null;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "12345678");

            System.out.print("Aranacak TC kimlik numarasını girin: ");
            String tcNO = scan.nextLine();
            System.out.println("  \n");
            String selectQuery = "SELECT * FROM hastaListesi WHERE TcNO = ?";

            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            preparedStatement.setString(1, tcNO);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Hasta bulunamadı.");
                String SEC = "";
                System.out.println("  \n");
                System.out.println("2- Yeni Sorgu");
                System.out.println("3- Hasta Menu");
                SEC = scan.nextLine();

                switch (SEC) {
                    case "2":
                        HastaMenu.hastaBul();
                    case "3":
                        HastaMenu.hastaMenu();
                        break;
                    default:
                        System.out.println("Tercih Yapiniz");
                }

            } else {
                System.out.println("  \n");
                System.out.println("   TcNo         Isim            Soyisim        D.Yili     Adres        TelNO");

                do {
                    String sutun1 = resultSet.getString(1);
                    String sutun2 = resultSet.getString(2);
                    String sutun3 = resultSet.getString(3);
                    String sutun4 = resultSet.getString(4);
                    String sutun5 = resultSet.getString(5);
                    String sutun6 = resultSet.getString(6);

                    System.out.printf("%-15s %-15s %-15s %-10s %-15s %-10s\n", sutun1, sutun2, sutun3, sutun4, sutun5, sutun6);
                    String SEC = "";
                    System.out.println("  \n");
                    System.out.println("2- Yeni Sorgu");
                    System.out.println("3- Hasta Menu");
                    SEC = scan.nextLine();

                    switch (SEC) {
                        case "2":
                            HastaMenu.hastaBul();
                        case "3":
                            HastaMenu.hastaMenu();
                            break;
                        default:
                            System.out.println("Tercih Yapiniz");
                    }
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Illegal operation on empty result set")) {
                System.out.println("Hasta bulunamadı.");
            } else {
                e.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void hastaKayit() throws ClassNotFoundException, SQLException, InterruptedException {
        System.out.println("Tc no");
        String tcNo = scan.nextLine();
        System.out.println("isim");
        String isim = scan.nextLine();
        System.out.println("soyisim");
        String soyisim = scan.nextLine();
        System.out.println("Dogum yili");
        String dYili = scan.nextLine();
        System.out.println("Adres");
        String adres = scan.nextLine();
        System.out.println("Telefon Numarası");
        String telNo = scan.nextLine();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "12345678");
        Statement st = con.createStatement();

        String Data = "insert into hastaListesi values(" + "'" + tcNo + "'" + "," +
                "'" + isim + "'" + "," + "'" +
                soyisim + "'" + "," + "'" +
                dYili + "'" + "," + "'" +
                adres + "'" + "," + "'" +
                telNo + "')";
        st.executeUpdate(Data);
        System.out.println("  \n");
        System.out.println("Hasta Basariyla Kayit Edildi");
        String SEC = "";
        System.out.println("   \n");
        System.out.println("2- Yeni Hasta Kayit");
        System.out.println("3- Hasta Menu");
        SEC = scan.nextLine();

        switch (SEC) {
            case "2":
                HastaMenu.hastaKayit();
                break;
            case "3":
                HastaMenu.hastaMenu();
                break;

            default:
                System.out.println("Tercih Yapiniz");
        }
    }

    public static void hastalarListesi() throws ClassNotFoundException, SQLException, InterruptedException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "12345678");
        Statement st = con.createStatement();

        String selectQuery = "SELECT * FROM hastaListesi";

        ResultSet hastaList = st.executeQuery(selectQuery);

        System.out.println("=========== HASTA LİSTESİ ============\n" +
                "   TcNo         Isim            Soyisim        D.Yili     Adres        TelNO");

        while (hastaList.next()) {
            String sutun1 = hastaList.getString(1);
            String sutun2 = hastaList.getString(2);
            String sutun3 = hastaList.getString(3);
            String sutun4 = hastaList.getString(4);
            String sutun5 = hastaList.getString(5);
            String sutun6 = hastaList.getString(6);

            System.out.printf("%-15s %-15s %-15s %-7s %-11s %-10s\n", sutun1, sutun2, sutun3, sutun4, sutun5, sutun6);
        }

        String SEC = "";
        System.out.println("  \n");
        System.out.println("3- Hasta Menu");
        SEC = scan.nextLine();

        switch (SEC) {
            case "3":
                HastaMenu.hastaMenu();
                break;
            default:
                System.out.println("Tercih Yapiniz");
        }
    }
}



