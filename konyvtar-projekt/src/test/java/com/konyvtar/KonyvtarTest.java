package com.konyvtar;

import com.konyvtar.model.Dokumentum;
import com.konyvtar.model.Folyoirat;
import com.konyvtar.model.HibasAdatException;
import com.konyvtar.model.Konyv;
import com.konyvtar.persistence.AdatKezelo;
import com.konyvtar.service.DokumentumFactory;
import com.konyvtar.service.KonyvtarKezelo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KonyvtarTest {

    @Test
    void testPolimorfizmusEsFactory() {
        Dokumentum k = DokumentumFactory.letrehoz("konyv", "Egri csillagok", 3500, "Gárdonyi Géza");
        Dokumentum f = DokumentumFactory.letrehoz("folyoirat", "PC World", 1200, "32");

        assertTrue(k instanceof Konyv);
        assertTrue(f instanceof Folyoirat);
        assertTrue(k.getTipusLeiras().contains("Gárdonyi Géza"));
        assertTrue(f.getTipusLeiras().contains("32"));
    }

    @Test
    void testValidacioKivetel() {
        assertThrows(HibasAdatException.class, () -> new Konyv("", 1000, "Szerző"));
        assertThrows(HibasAdatException.class, () -> new Konyv("Cím", -50, "Szerző"));
        assertThrows(HibasAdatException.class, () -> DokumentumFactory.letrehoz("ismeretlen", "Cím", 1000, "extra"));
    }

    @Test
    void testRendezesArSzerint() {
        KonyvtarKezelo kezelo = new KonyvtarKezelo();
        kezelo.hozzaad(new Konyv("Drága könyv", 5000, "A"));
        kezelo.hozzaad(new Folyoirat("Olcsó újság", 1000, 5));

        kezelo.rendezArSzerint();
        List<Dokumentum> lista = kezelo.getDokumentumok();

        assertEquals(1000, lista.get(0).getAr());
        assertEquals(5000, lista.get(1).getAr());
    }

    @Test
    void testPerzisztencia(@TempDir Path tempDir) throws IOException {
        Path fajl = tempDir.resolve("teszt.csv");
        List<Dokumentum> eredeti = List.of(
                new Konyv("Dűne", 4800, "Frank Herbert"),
                new Folyoirat("Természet Világa", 1500, 155)
        );

        AdatKezelo.mentes(fajl.toString(), eredeti);
        List<Dokumentum> betoltott = AdatKezelo.betoltes(fajl.toString());

        assertEquals(2, betoltott.size());
        assertEquals("Dűne", betoltott.get(0).getCim());
        assertEquals(155, ((Folyoirat) betoltott.get(1)).getEvfolyam());
    }
}
