package utils;

import java.util.Scanner;

public class errorHandler {
    public static int mintaInputINT(Scanner scan, String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (Exception error) {
                System.out.println(">>> [ERROR] Input harus berupa angka!");
            }
        }
    }

    public static double mintaInputDOUBLE(Scanner scan, String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                double nilai = Double.parseDouble(scan.nextLine().trim());
                if (nilai >= 0)
                    return nilai;
                System.out.println(">>> [ERROR] Nilai tidak boleh negatif!");
            } catch (Exception error) {
                System.out.println(">>> [ERROR] Input harus berupa angka!");
            }
        }
    }

    public static String mintaInputSTRING(Scanner scan, String pesan) {
        while (true) {
            System.out.print(pesan);
            String input = scan.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(">>> [ERROR] Input tidak boleh kosong!");
                continue;
            }

            if (input.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println(">>> [ERROR] Input tidak boleh hanya berupa angka!");
                continue;
            }

            return input;
        }
    }

    public static boolean konfirmasi(Scanner scan, String pesan) {
        while (true) {
            String jawaban = mintaInputSTRING(scan, pesan);
            if (jawaban.equalsIgnoreCase("ya")) {
                return true;
            } else if (jawaban.equalsIgnoreCase("tidak")) {
                return false;
            }
            System.out.println(">>> [ERROR] Input tidak valid! Ketik 'Ya' atau 'Tidak'.");
        }
    }
}