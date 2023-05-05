package com.example.projekt;

import java.util.Arrays;

public class Zasoby {
    private int M; //liczba stanowisk z dystrybutorami
    private int N; //maksymalna liczba samochodów które mogą przebywać na stacji (liczba stanowisk + kolejka)
    private int K; //liczba kas
    private int ileUzupelnia; //czas uzupełniania zasobów (blokowania dystrybutorów)
    private int ileTankuje; //czas potrzebny do zatankowania 1L paliwa
    private int ilePlaci; //czas potrzebny na zapłacenie
    private int maxPaliwa; //maksymalna ilość paliwa w zasobach
    private int ileAnim;
    private int TankujaceSamochody, Auta;
    private String[] rodzajePaliw; //rodzaje paliwa
    private int[] pozostalePaliwo;

    public Zasoby() {
        this.TankujaceSamochody=0;
        this.Auta = 0;
        this.M = 4;
        this.N = 6;
        this.K = 3;
        this.ileUzupelnia = 4000;
        this.ileTankuje = 50;
        this.ilePlaci = 1500;
        this.maxPaliwa = 300;
        this.ileAnim = 500;
        this.rodzajePaliw = new String[]{"95", "ON", "LPG"};
    }

    boolean koniecPaliwa(){
        for(int j=0; j<rodzajePaliw.length; j++){
            if(pozostalePaliwo[j]<60) return true;
        }
        return false;
    }

    public String[] getRodzajePaliw() {
        return rodzajePaliw;
    }

    public String getPaliwo(int i){
        return rodzajePaliw[i];
    }

    public int getTankujaceSamochody() {return this.TankujaceSamochody;}

    public void setTankujaceSamochody(int TankujaceSamochody) {this.TankujaceSamochody = TankujaceSamochody;}

    public int getPozostalePaliwo(int i) {
        return pozostalePaliwo[i];
    }

    public void setPozostalePaliwo(int pozostalePaliwo, int i) {
        this.pozostalePaliwo[i] = pozostalePaliwo;
    }

    public int getIleUzupelnia() {
        return ileUzupelnia;
    }

    public void setIleUzupelnia(int ileUzupelnia) {
        this.ileUzupelnia = ileUzupelnia;
    }

    public int getIleTankuje() {
        return ileTankuje;
    }

    public void setIleTankuje(int ileTankuje) {
        this.ileTankuje = ileTankuje;
    }

    public int getIlePlaci() {
        return ilePlaci;
    }

    public void setIlePlaci(int ilePlaci) {
        this.ilePlaci = ilePlaci;
    }

    public int getMaxPaliwa() {
        return maxPaliwa;
    }

    public void setMaxPaliwa(int maxPaliwa) {
        this.maxPaliwa = maxPaliwa;
    }

    public int getAuta() {
        return Auta;
    }

    public void setAuta(int Auta) {
        this.Auta = Auta;
    }

    public int getIleAnim() {
        return ileAnim;
    }

    public void setIleAnim(int ileAnim) {
        this.ileAnim = ileAnim;
    }

    public void setRodzajePaliw(String[] rodzajePaliw) {
        this.rodzajePaliw = rodzajePaliw;

        this.pozostalePaliwo=new int[rodzajePaliw.length];
        Arrays.fill(this.pozostalePaliwo, this.maxPaliwa);
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }
}
