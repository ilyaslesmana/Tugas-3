package service;

import model.*;
import utils.notFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class menuService {
    private ArrayList<menuItem> daftarMenu;
    private final String FILENAME = "menu_restoran.txt";

    public menuService() {
        daftarMenu = new ArrayList<>();
        muatFile();
    }

    private void muatFile() {
        try {
            File file = new File(FILENAME);
            if (!file.exists())
                return;

            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split("\\|");
                if (data.length < 3)
                    continue;

                if (data[0].equalsIgnoreCase("Makanan"))
                    daftarMenu.add(new makanan(data[1], Double.parseDouble(data[2])));
                else if (data[0].equalsIgnoreCase("Minuman"))
                    daftarMenu.add(new minuman(data[1], Double.parseDouble(data[2])));
                else if (data[0].equalsIgnoreCase("Diskon"))
                    daftarMenu.add(new diskon(data[1], Double.parseDouble(data[2])));
            }
            reader.close();

        } catch (FileNotFoundException error) {
            System.out.println(">>> [Error] File tidak ditemukan");
        }
    }

    public void tambahItem(menuItem item) {
        daftarMenu.add(item);
        simpanFile();
    }

    private void simpanFile() {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            for (menuItem item : daftarMenu)
                writer.write(item.formatFile() + "\n");
        } catch (IOException error) {
            System.out.println(">>> [ERROR] Gagal menyimmpan menu!");
        }
    }

    public boolean isEmpty() {
        return daftarMenu.isEmpty();
    }

    public void tampilkanMenu() {
        System.out.println("================================================");
        System.out.println("            KATALOG MENU RESTORAN               ");
        System.out.println("================================================");
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu masih kosong");
        } else {
            for (menuItem item : daftarMenu) {
                item.tampilMenu();
            }
        }
        System.out.println("================================================");
    }

    public menuItem cariItem(String nama) throws notFoundException {
        for (menuItem item : daftarMenu) {
            if (item.getNama().equalsIgnoreCase(nama))
                return item;

        }
        throw new notFoundException("Maaf, item No exception of'" + nama + "' tidak ada di menu!");
    }
}
