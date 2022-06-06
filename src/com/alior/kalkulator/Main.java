package com.alior.kalkulator;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
		KalkulatorZdolnosci k = new KalkulatorZdolnosci(80, 4000, 500, 200, 4000);
		ArrayList<OfertaKredytu> oferty = k.pobierzOferty();
		if (oferty.isEmpty()) {
			System.out.println("Brak zdolności kredytowej.");
		} else {
			for (int i = 0; i < oferty.size(); ++i) {
				System.out.println("Oferta #" + (i + 1) + ":");
				oferty.get(i).wypisz();
			}
		}
    }
}
