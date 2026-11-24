package com.konyvtar.service;

import com.konyvtar.model.Dokumentum;
import com.konyvtar.model.Folyoirat;
import com.konyvtar.model.HibasAdatException;
import com.konyvtar.model.Konyv;

public class DokumentumFactory {
    public static Dokumentum letrehoz(String tipus, String cim, int ar, String extra) {
        if (tipus == null) {
            throw new HibasAdatException("A típus nem lehet null!");
        }
        if (tipus.equalsIgnoreCase("konyv")) {
            return new Konyv(cim, ar, extra);
        } else if (tipus.equalsIgnoreCase("folyoirat")) {
            try {
                int evfolyam = Integer.parseInt(extra.trim());
                return new Folyoirat(cim, ar, evfolyam);
            } catch (NumberFormatException e) {
                throw new HibasAdatException("A folyóirat évfolyama egész szám kell legyen!");
            }
        }
        throw new HibasAdatException("Ismeretlen dokumentum típus: " + tipus);
    }
}
