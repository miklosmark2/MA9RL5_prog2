package com.konyvtar.model;

public class Folyoirat extends Dokumentum {
    private int evfolyam;

    public Folyoirat(String cim, int ar, int evfolyam) {
        super(cim, ar);
        if (evfolyam <= 0) {
            throw new HibasAdatException("Az évfolyam pozitív szám kell legyen!");
        }
        this.evfolyam = evfolyam;
    }

    public int getEvfolyam() {
        return evfolyam;
    }

    @Override
    public String getTipusLeiras() {
        return "Folyóirat | Cím: " + getCim() + " | Évfolyam: " + evfolyam + " | Ár: " + getAr() + " Ft";
    }
}
