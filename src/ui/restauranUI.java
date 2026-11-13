package ui;

import model.*;
import service.menuService;
import service.pesananService;
import utils.notFoundException;
import utils.errorHandler;
import java.util.Scanner;

public class restauranUI {
    public static void jalankanMenuUtama(menuService dbMenu, pesananService keranjang, Scanner scan) {
        int pilihan;
        do {
            System.out.println("\n ======== RESTORAN WARKOPLIN ======= ");
            System.out.println("1. Tambah item menu baru");
            System.out.println("2. Tampilkan menu restoran yang tersedia");
            System.out.println("3. Buat pesanan baru (khusus pelanggan)");
            System.out.println("4. Hitung, cetak dan simpan struk ke file");
            System.out.println("5. Scan riwayat struk yang tersimpan dari file");
            System.out.println("6. Keluar dari program");

            pilihan = errorHandler.mintaInputINT(scan, "Pilih aksi dari 1-6 :\t");
            switch (pilihan) {
                case 1:
                    menuTambahItem(dbMenu, scan);
                    break;
                case 2:
                    dbMenu.tampilkanMenu();
                    break;
                case 3:
                    menuBuatPesanan(dbMenu, keranjang, scan);
                    break;
                case 4:
                    keranjang.cetakSimpanStruk();
                    keranjang.resetKeranjang();
                    break;
                case 5:
                    keranjang.bacaStruk();
                    break;
                case 6:
                    System.out.println("=== Sistem di tutup, terima kasih ===");
                    break;
                default:
                    System.out.println(">>> Piihan tidak tersesia, Silahkan pilih (1-6)");
            }

        } while (pilihan != 6);
    }

    private static void menuTambahItem(menuService dbmenu, Scanner scan) {
        boolean tambahLagi;

        do {
            System.out.println("\nPilih tipe item:");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            System.out.println("3. Diskon");

            int tipe = errorHandler.mintaInputINT(scan, "Pilihan (1-3): ");

            if (tipe < 1 || tipe > 3) {
                System.out.println(">>> [ERROR] Tipe item tidak valid!");
                tambahLagi = errorHandler.konfirmasi(scan, "Ingin mencoba lagi? (Ya/Tidak): ");
                continue;
            }

            String nama = errorHandler.mintaInputSTRING(scan, "Nama item: ");
            menuItem itemBaru = null;

            if (tipe == 1) {
                double harga = errorHandler.mintaInputDOUBLE(scan, "Harga: ");
                itemBaru = new makanan(nama, harga);
            } else if (tipe == 2) {
                double harga = errorHandler.mintaInputDOUBLE(scan, "Harga: ");
                itemBaru = new minuman(nama, harga);
            } else if (tipe == 3) {
                double diskon = errorHandler.mintaInputDOUBLE(scan, "Besaran diskon (%): ");
                itemBaru = new diskon(nama, diskon);
            }

            if (errorHandler.konfirmasi(scan, "Apakah Anda yakin ingin menambahkan menu ini? (Ya/Tidak): ")) {
                dbmenu.tambahItem(itemBaru);
                System.out.println(">>> Menu berhasil ditambahkan!");
            } else {
                System.out.println(">>> Penambahan menu dibatalkan.");
            }

            tambahLagi = errorHandler.konfirmasi(scan, "Ingin menambah menu lain sekaligus? (Ya/Tidak): ");

        } while (tambahLagi);
    }

    private static void menuBuatPesanan(menuService dbmenu, pesananService keranjang, Scanner scan) {
        if (dbmenu.isEmpty()) {
            System.out.println(">>> [INFO] Menu masih kosong. Silakan tambahkan item menu terlebih dahulu!");
            return;
        }

        dbmenu.tampilkanMenu();

        // FASE 1: Tanya dan proses voucher di awal
        if (errorHandler.konfirmasi(scan, "\nApakah Anda punya voucher diskon? (Ya/Tidak): ")) {
            while (true) {
                String kodeVoucher = errorHandler.mintaInputSTRING(scan,
                        "Masukkan nama voucher (atau ketik 'batal'): ");
                if (kodeVoucher.equalsIgnoreCase("batal")) {
                    System.out.println(">>> Penggunaan voucher dibatalkan.");
                    break;
                }

                try {
                    menuItem itemVoucher = dbmenu.cariItem(kodeVoucher);

                    if (itemVoucher instanceof diskon) {
                        keranjang.tambahKeranjang(itemVoucher, 1);
                        System.out.println(">>> Voucher '" + itemVoucher.getNama() + "' berhasil diterapkan!");
                        break;
                    } else {
                        System.out.println(
                                ">>> [ERROR] '" + kodeVoucher + "' adalah menu makanan/minuman, bukan voucher!");
                    }
                } catch (notFoundException error) {
                    System.out.println(error.getMessage());
                }
            }
        }

        System.out.println("\n[Ketik 'selesai' untuk mengakhiri pesanan]");
        while (true) {
            String input = errorHandler.mintaInputSTRING(scan, "Masukkan nama makanan/minuman: ");
            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            try {
                menuItem itemDitemukan = dbmenu.cariItem(input);

                if (itemDitemukan instanceof diskon) {
                    System.out.println(">>> [INFO] Voucher hanya dapat diklaim pada pertanyaan di awal tadi.");
                } else {
                    int porsi = errorHandler.mintaInputINT(scan, "Berapa porsi?: ");
                    if (porsi > 0) {
                        keranjang.tambahKeranjang(itemDitemukan, porsi);
                        System.out.println(
                                ">>> " + itemDitemukan.getNama() + " (" + porsi + " porsi) ditambahkan ke keranjang!");
                    } else {
                        System.out.println(">>> [BATAL] Jumlah porsi minimal 1. Item tidak ditambahkan.");
                    }
                }
            } catch (notFoundException error) {
                System.out.println(error.getMessage());
            }
        }
    }
}
