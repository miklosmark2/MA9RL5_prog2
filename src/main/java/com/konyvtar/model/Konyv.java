package com.konyvtar.model;

public class Konyv extends Dokumentum {
    private String szerzo;

    public Konyv(String cim, int ar, String szerzo) {
        super(cim, ar);
        if (szerzo == null || szerzo.trim().isEmpty()) {
            throw new HibasAdatException("A szerző nem lehet üres!");
        }
        this.szerzo = szerzo.trim();
    }

    public String getSzerzo() {
        return szerzo;
    }

    @Override
    public String getTipusLeiras() {
        return "Könyv | Cím: " + getCim() + " | Szerző: " + szerzo + " | Ár: " + getAr() + " Ft";
    }
}
