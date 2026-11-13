import java.util.Scanner;

import service.menuService;
import service.pesananService;
import ui.restauranUI;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        menuService dbmenu = new menuService();
        pesananService keranjang = new pesananService();

        restauranUI.jalankanMenuUtama(dbmenu, keranjang, scan);
    }

}
