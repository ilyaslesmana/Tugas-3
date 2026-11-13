package model;

public abstract class menuItem {
    private String nama, kategori;
    private double harga;

    public menuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getkategori() {
        return kategori;
    }

    public abstract void tampilMenu();

    public abstract String formatFile();
}
