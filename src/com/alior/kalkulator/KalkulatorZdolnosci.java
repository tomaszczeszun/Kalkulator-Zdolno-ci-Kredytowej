package com.alior.kalkulator;
import java.util.ArrayList;

public class KalkulatorZdolnosci {
    private static final int ograniczenieGorneOkresuKredytowania = 100;
    private static final int minOkresKredytowania = 6;
    private static final double maxKwotaKredytu = 150000;
    private static final double minKwotaKredytu = 5000;
    private static final double maxZaangazowanie = 200000;
    private static final double[] DTI = {0.6, 0.6, 0.5, 0.55};
    private static final double[] oprocentowanie = {0.02, 0.03, 0.03, 0.03};
    private int okresZatrudnienia;
    private double DZ;
    private double KU;
    private double ZK;
    private double sumaSald;

    public KalkulatorZdolnosci(int okresZatrudnienia, double dochodMiesieczny, double kosztyUtrzymania, double sumaZobowiazan, double sumaSald) {
        this.okresZatrudnienia = okresZatrudnienia;
        this.DZ = dochodMiesieczny;
        this.KU = kosztyUtrzymania;
        this.ZK = sumaZobowiazan;
        this.sumaSald = sumaSald;
    }

    private ArrayList<Integer> pobierzIndeksyOkresuKredytowania(int okresKredytowania) {
        ArrayList<Integer> indeksy = new ArrayList<Integer>();
        if (okresKredytowania >= 6) {
            indeksy.add(0);
        }
        if (okresKredytowania > 12) {
            indeksy.add(1);
        }
        if (okresKredytowania > 36) {
            indeksy.add(2);
        }
        if (okresKredytowania > 60) {
            indeksy.add(3);
        }
        return indeksy;
    }

    private int[] pobierzPrzedzialKredytowania(int indeks) {
        if (indeks == 0) {
            return new int[] {6, 12};
        }
        if (indeks == 1) {
            return new int[] {12, 36};
        }
        if (indeks == 2) {
            return new int[] {36, 60};
        }
        if (indeks == 3) {
            return new int[] {60, 100};
        }
        return new int[] {-1, -1};
    }

    private double MI(int indeksOkresuKredytowania) {
        if (indeksOkresuKredytowania >= 0 && indeksOkresuKredytowania <= 3) {
            return oprocentowanie[indeksOkresuKredytowania] / 12;
        }
        return -1;
    }

    private int maxOkresKredytowania() {
        int okresKredytowania = Math.min(okresZatrudnienia, ograniczenieGorneOkresuKredytowania);
        return (okresKredytowania >= minOkresKredytowania) ? okresKredytowania : -1;
    }

    private double maxMiesiecznaRata(int indeksOkresuKredytowania) {
        if (indeksOkresuKredytowania >= 0 && indeksOkresuKredytowania <= 3) {
            return Math.min(DZ - KU - ZK, DTI[indeksOkresuKredytowania] * DZ - ZK);
        }
        return -1;
    }

    private double maxKwotaKredytu(double maxMiesiecznaRata, int maxOkresKredytowania, int indeksOkresuKredytowania) {
        if (indeksOkresuKredytowania < 0 || indeksOkresuKredytowania > 3) return -1;
        double mi = MI(indeksOkresuKredytowania);
        double kwota = Math.min(Math.min(maxZaangazowanie - sumaSald, maxKwotaKredytu),
                        (maxMiesiecznaRata * ((1 - Math.pow((1 + mi), -maxOkresKredytowania)) / mi)));
        return kwota >= minKwotaKredytu ? kwota : -1;
    }

    public ArrayList<OfertaKredytu> pobierzOferty() {
        int maxOkresKredytowania = maxOkresKredytowania();
        ArrayList<Integer> indeksy = pobierzIndeksyOkresuKredytowania(maxOkresKredytowania);
        ArrayList<OfertaKredytu> oferty = new ArrayList<>();
        for(int i = 0; i < indeksy.size(); ++i) {
            double maxMiesiecznaRata = maxMiesiecznaRata(indeksy.get(i));
            if (maxMiesiecznaRata == -1) continue;
            double maxKwotaKredytu = maxKwotaKredytu(maxMiesiecznaRata, maxOkresKredytowania, indeksy.get(i));
            if (maxKwotaKredytu == -1) continue;
            int[] przedzial = pobierzPrzedzialKredytowania(indeksy.get(i));
            if (przedzial.length == 2) {
                if (przedzial[0] == -1 || przedzial[1] == -1) continue;
            } else
                continue;
            boolean ostatniIndeks = i == indeksy.size() - 1;
            if (ostatniIndeks)
                przedzial[1] = maxOkresKredytowania;
            oferty.add(new OfertaKredytu(przedzial, maxMiesiecznaRata, maxKwotaKredytu, maxOkresKredytowania, ostatniIndeks));
        }
        return oferty;
    }
}
