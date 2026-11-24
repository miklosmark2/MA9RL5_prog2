package com.konyvtar.persistence;

import com.konyvtar.model.Dokumentum;
import com.konyvtar.model.Folyoirat;
import com.konyvtar.model.Konyv;
import com.konyvtar.service.DokumentumFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AdatKezelo {

    public static void mentes(String fajlNev, List<Dokumentum> lista) throws IOException {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fajlNev), StandardCharsets.UTF_8))) {
            for (Dokumentum d : lista) {
                if (d instanceof Konyv k) {
                    pw.println("KONYV;" + k.getCim() + ";" + k.getAr() + ";" + k.getSzerzo());
                } else if (d instanceof Folyoirat f) {
                    pw.println("FOLYOIRAT;" + f.getCim() + ";" + f.getAr() + ";" + f.getEvfolyam());
                }
            }
        }
    }

    public static List<Dokumentum> betoltes(String fajlNev) throws IOException {
        List<Dokumentum> lista = new ArrayList<>();
        File fajl = new File(fajlNev);
        if (!fajl.exists()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fajl), StandardCharsets.UTF_8))) {
            String sor;
            while ((sor = br.readLine()) != null) {
                if (sor.trim().isEmpty()) continue;
                String[] reszek = sor.split(";", 4);
                if (reszek.length == 4) {
                    String tipus = reszek[0];
                    String cim = reszek[1];
                    int ar = Integer.parseInt(reszek[2]);
                    String extra = reszek[3];
                    lista.add(DokumentumFactory.letrehoz(tipus, cim, ar, extra));
                }
            }
        }
        return lista;
    }
}
