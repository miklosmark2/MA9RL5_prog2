package com.konyvtar.ui;

import com.konyvtar.model.Dokumentum;
import com.konyvtar.model.HibasAdatException;
import com.konyvtar.persistence.AdatKezelo;
import com.konyvtar.service.DokumentumFactory;
import com.konyvtar.service.KonyvtarKezelo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class KonzolUI {
    private static final String FAJL_NEV = "adatok.csv";
    private final KonyvtarKezelo kezelo = new KonyvtarKezelo();
    private final Scanner scanner = new Scanner(System.in);

    public void indit() {
        betoltIndulaskor();
        boolean fut = true;
        while (fut) {
            menuKiir();
            String valasztas = scanner.nextLine().trim();
            try {
                switch (valasztas) {
                    case "1" -> konyvFelvetel();
                    case "2" -> folyoiratFelvetel();
                    case "3" -> listazas();
                    case "4" -> {
                        kezelo.rendezArSzerint();
                        System.out.println("Rendezve ár szerint!");
                        listazas();
                    }
                    case "5" -> {
                        kezelo.rendezCimSzerint();
                        System.out.println("Rendezve cím szerint!");
                        listazas();
                    }
                    case "0" -> {
                        mentesKilepeskor();
                        fut = false;
                    }
                    default -> System.out.println("Érvénytelen menüpont!");
                }
            } catch (HibasAdatException | NumberFormatException e) {
                System.out.println("Hiba: " + e.getMessage());
            }
        }
    }

    private void menuKiir() {
        System.out.println("\n=== KÖNYVTÁRI NYILVÁNTARTÓ ===");
        System.out.println("1. Új könyv hozzáadása");
        System.out.println("2. Új folyóirat hozzáadása");
        System.out.println("3. Összes dokumentum listázása");
        System.out.println("4. Rendezés ár szerint");
        System.out.println("5. Rendezés cím szerint");
        System.out.println("0. Mentés és kilépés");
        System.out.print("Választás: ");
    }

    private void konyvFelvetel() {
        System.out.print("Cím: ");
        String cim = scanner.nextLine();
        System.out.print("Szerző: ");
        String szerzo = scanner.nextLine();
        System.out.print("Ár (Ft): ");
        int ar = Integer.parseInt(scanner.nextLine().trim());
        Dokumentum k = DokumentumFactory.letrehoz("konyv", cim, ar, szerzo);
        kezelo.hozzaad(k);
        System.out.println("Könyv sikeresen hozzáadva.");
    }

    private void folyoiratFelvetel() {
        System.out.print("Cím: ");
        String cim = scanner.nextLine();
        System.out.print("Évfolyam: ");
        String evfolyam = scanner.nextLine();
        System.out.print("Ár (Ft): ");
        int ar = Integer.parseInt(scanner.nextLine().trim());
        Dokumentum f = DokumentumFactory.letrehoz("folyoirat", cim, ar, evfolyam);
        kezelo.hozzaad(f);
        System.out.println("Folyóirat sikeresen hozzáadva.");
    }

    private void listazas() {
        List<Dokumentum> lista = kezelo.getDokumentumok();
        if (lista.isEmpty()) {
            System.out.println("A nyilvántartás jelenleg üres.");
            return;
        }
        for (Dokumentum d : lista) {
            System.out.println("- " + d.getTipusLeiras());
        }
    }

    private void betoltIndulaskor() {
        try {
            List<Dokumentum> betoltott = AdatKezelo.betoltes(FAJL_NEV);
            for (Dokumentum d : betoltott) {
                kezelo.hozzaad(d);
            }
            if (!betoltott.isEmpty()) {
                System.out.println(betoltott.size() + " dokumentum betöltve az előző munkamenetből.");
            }
        } catch (IOException | HibasAdatException e) {
            System.out.println("Nem sikerült a korábbi adatok betöltése: " + e.getMessage());
        }
    }

    private void mentesKilepeskor() {
        try {
            AdatKezelo.mentes(FAJL_NEV, kezelo.getDokumentumok());
            System.out.println("Adatok elmentve a(z) " + FAJL_NEV + " fájlba. Viszlát!");
        } catch (IOException e) {
            System.out.println("Hiba a mentés során: " + e.getMessage());
        }
    }
}
