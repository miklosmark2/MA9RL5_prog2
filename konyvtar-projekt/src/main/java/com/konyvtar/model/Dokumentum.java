package com.konyvtar.model;

public abstract class Dokumentum {
    private String cim;
    private int ar;

    public Dokumentum(String cim, int ar) {
        if (cim == null || cim.trim().isEmpty()) {
            throw new HibasAdatException("A cím nem lehet üres!");
        }
        if (ar < 0) {
            throw new HibasAdatException("Az ár nem lehet negatív!");
        }
        this.cim = cim.trim();
        this.ar = ar;
    }

    public String getCim() {
        return cim;
    }

    public int getAr() {
        return ar;
    }

    public abstract String getTipusLeiras();
}
