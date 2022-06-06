package com.alior.kalkulator;

public class OfertaKredytu {
    private int[] przedzialKredytowania;
    private double maxMiesiecznaRata;
    private double maxKwotaKredytu;
    private int maxOkresKredytowania;
    private boolean ostatniIndeks;

    public OfertaKredytu(int[] przedzialKredytowania, double maxMiesiecznaRata, double maxKwotaKredytu, int maxOkresKredytowania, boolean ostatniIndeks) {
        this.przedzialKredytowania = przedzialKredytowania;
        this.maxMiesiecznaRata = maxMiesiecznaRata;
        this.maxKwotaKredytu = maxKwotaKredytu;
        this.ostatniIndeks = ostatniIndeks;
        this.maxOkresKredytowania = maxOkresKredytowania;
    }

    public void wypisz() {
        System.out.println("Maksymalny okres kredytowania: " + this.maxOkresKredytowania + " miesięcy.");
        System.out.println("Przedział kredytowania w miesiącach: " + "[" + this.przedzialKredytowania[0] + ", " + this.przedzialKredytowania[1] + (ostatniIndeks ? "]" : ")") + ".");
        System.out.println("Maksymalna miesięczna rata: " + String.format("%.2f", this.maxMiesiecznaRata) + " PLN.");
        System.out.println("Maksymalna kwota kredytu: " + String.format("%.2f", this.maxKwotaKredytu) + " PLN.\n");
    }
}
