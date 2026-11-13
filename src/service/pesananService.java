package service;

import model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class pesananService {
    private ArrayList<itemPesanan> dafItemPesanan;
    private final String FILENAME = "Struk_pesanan.txt";

    public pesananService() {
        dafItemPesanan = new ArrayList<>();
    }

    public void tambahKeranjang(menuItem item, int jumlah) {
        dafItemPesanan.add(new itemPesanan(item, jumlah));
    }

    public void cetakSimpanStruk() {
        if (dafItemPesanan.isEmpty()) {
            System.out.println(">>> Belum ada pesanan");
            return;
        }

        double totalBiaya = 0;
        double totalDiskonPersen = 0;
        String namaVoucher = null;
        Locale localeID = Locale.forLanguageTag("id-ID");
        StringBuilder struk = new StringBuilder();

        struk.append("\n================================================\n");
        struk.append("                 STRUK PESANAN                  \n");
        struk.append("================================================\n");

        // 1. Loop HANYA untuk mendata item & menjumlahkan harga
        for (itemPesanan p : dafItemPesanan) {
            if (p.item instanceof diskon) {
                totalDiskonPersen += ((diskon) p.item).persenDiskon();
                namaVoucher = p.item.getNama();
            } else {
                double subtotal = p.item.getHarga() * p.jumlah;
                totalBiaya += subtotal;
                struk.append(String.format(localeID, "%-22s (x%-2d)   Rp %,.0f\n",
                        p.item.getNama(), p.jumlah, subtotal));
            }
        } // <-- Kurung tutup loop berhenti di sini

        // 2. Hitung diskon & total akhir SETELAH semua item selesai dijumlahkan
        double potongan = 0;
        if (totalDiskonPersen > 0) {
            potongan = totalBiaya * (totalDiskonPersen / 100.0);
        }
        double totalBayar = totalBiaya - potongan;

        struk.append("------------------------------------------------\n");
        if (namaVoucher != null) {
            struk.append(String.format("Voucher Dipakai        : %s (%.0f%%)\n", namaVoucher, totalDiskonPersen));
        }
        struk.append(String.format(localeID, "Subtotal Belanja       : Rp %,.0f\n", totalBiaya));
        struk.append(String.format(localeID, "Potongan Diskon        : Rp %,.0f\n", potongan));
        struk.append("------------------------------------------------\n");
        struk.append(String.format(localeID, "Total Bayar            : Rp %,.0f\n", totalBayar));
        struk.append("================================================\n");

        // 3. Cetak ke konsol & simpan ke file CUKUP SEKALI
        System.out.println(struk.toString());
        simpanFile(struk.toString());
    }

    private void simpanFile(String struk) {
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(struk + "\n");
            System.out.println(">>> [I/O SUKSES] Struk belanja telah disimpan ke '" + FILENAME + "'");
        } catch (IOException error) {
            System.out.println(">>> [ERROR] Gagal menyimpan struk ke '" + FILENAME + "'");
        }
    }

    public void resetKeranjang() {
        dafItemPesanan.clear();
    }

    public void bacaStruk() {
        try {
            File file = new File(FILENAME);
            if (!file.exists()) {
                System.out.println(">>> [INFO] Belum ada data riwayat struk pesanan yang tersimpan.");
                return;
            }
            System.out.println("\n>>> [MEMBACA FILE: " + FILENAME + "] <<<");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
            reader.close();
            System.out.println(">>> [I/O SUKSES!] Berhasil membaca riwayat struk.");
        } catch (FileNotFoundException error) {
            System.out.println(">>> [ERROR] File struk tidak ditemukan!");
        }
    }
}