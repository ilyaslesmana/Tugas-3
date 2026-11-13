package model;

import java.util.Locale;

public class minuman extends menuItem {
    public minuman(String nama, double harga) {
        super(nama, harga, "minuman");
    }

    @Override
    public void tampilMenu() {
        System.out.printf(Locale.forLanguageTag("id-ID"), "%-10s %-20s Rp %,.0f%n", "[MINUMAN]", getNama(), getHarga());
    }

    @Override
    public String formatFile() {
        return "MINUMAN|" + getNama() + "|" + getHarga();
    }
}
