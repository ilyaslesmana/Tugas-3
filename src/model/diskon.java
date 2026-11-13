package model;

public class diskon extends menuItem {
    private double persenDiskon;

    public diskon(String nama, double persenDiskon) {
        super(nama, 0, "Diskon");
        this.persenDiskon = persenDiskon;
    }

    public double persenDiskon() {
        return persenDiskon;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("%-10s %-20s Potongan %.0f%%%n", "[DISKON]", getNama(), getHarga());
    }

    @Override
    public String formatFile() {
        return "Diskon|" + getNama() + "|0|" + persenDiskon;
    }
}