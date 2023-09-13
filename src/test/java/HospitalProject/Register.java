package HospitalProject;

import java.sql.SQLException;
import java.util.Scanner;

public class Register {

    static Scanner scan = new Scanner(System.in);

    public static void Menu() throws InterruptedException, SQLException, ClassNotFoundException {

        String SEC = "";
        do {
            System.out.println("============= ACIBADEM HASTANESİ =============\n" +
                    "==============  ANA MENU ==============\n" +
                    "\n" +
                    "\t   1- Hastane Bilgilerini Goruntule\n" +
                    "\t   2- Hasta Menu\n" +
                    "\t   3- Laboratuvar Menu\t\t \n" +
                    "\t   Q- CİKİS\n");


            System.out.println("Tercih Yapiniz");
            SEC = scan.nextLine();

            switch (SEC) {
                case "1":  // ACIBADEM HASTANE BİLGİLERİ
                    Register.hastaneBilgileri();
                    break;
                case "2":  // HASTA MENU
                    HastaMenu.hastaMenu();
                    break;
                case "3":  // LABORATUVAR MENU

                    break;
                case "q":
                case "Q":
                    break;
                default:
                    System.out.println("Tercih Yapiniz");
            }
            System.out.println("   \n"+"   \n");
        } while (!SEC.equalsIgnoreCase("q"));

        Register.STOP();
    }

    public static void hastaneBilgileri() throws InterruptedException, SQLException, ClassNotFoundException {
        System.out.println("=============" + " " + HastaneBilgileri.hastaneName + " " + "=============\n" +
                "\t\t Adres : " + HastaneBilgileri.adres +
                "\n\t\t Telefon : " + HastaneBilgileri.telefon);
        String SEC = "";
        System.out.println("3- Ana Menu");
        SEC = scan.nextLine();

        switch (SEC) {
            case "3":  // ACIBADEM HASTANE BİLGİLERİ
                Register.Menu();
                break;

            default:
                System.out.println("Tercih Yapiniz");
        }

    }

    public static void STOP() {
        System.out.println("Sistemden Cıkıs Yapıldı");
        System.exit(0);
    }

}
