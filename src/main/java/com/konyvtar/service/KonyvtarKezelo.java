package com.konyvtar.service;

import com.konyvtar.model.Dokumentum;
import com.konyvtar.model.HibasAdatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KonyvtarKezelo {
    private final List<Dokumentum> dokumentumok = new ArrayList<>();

    public void hozzaad(Dokumentum d) {
        if (d == null) {
            throw new HibasAdatException("Null dokumentum nem adható hozzá!");
        }
        dokumentumok.add(d);
    }

    public List<Dokumentum> getDokumentumok() {
        return Collections.unmodifiableList(dokumentumok);
    }

    public void rendezArSzerint() {
        dokumentumok.sort(Comparator.comparingInt(Dokumentum::getAr));
    }

    public void rendezCimSzerint() {
        dokumentumok.sort(Comparator.comparing(Dokumentum::getCim, String.CASE_INSENSITIVE_ORDER));
    }
}
