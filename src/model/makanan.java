package model;

import java.util.Locale;

public class makanan extends menuItem {
    public makanan(String nama, double harga) {
        super(nama, harga, "makanan");
    }

    @Override
    public void tampilMenu() {
        System.out.printf(Locale.forLanguageTag("id-ID"), "%-10s %-20s Rp %,.0f%n", "[MAKANAN]", getNama(), getHarga());
    }

    @Override
    public String formatFile() {
        return "MAKANAN|" + getNama() + "|" + getHarga();
    }

}
