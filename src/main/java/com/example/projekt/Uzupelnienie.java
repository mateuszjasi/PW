package com.example.projekt;

import java.util.concurrent.Semaphore;

public class Uzupelnienie extends Thread{
    Semaphore tankowanie, chron, uzupelnianie, zatankowane;
    int ileUzupelnia;

    public Uzupelnienie(int ileUzupelnia, Semaphore chron, Semaphore tankowanie, Semaphore uzupelnianie, Semaphore zatankowane) {
        this.ileUzupelnia = ileUzupelnia;
        this.chron = chron;
        this.tankowanie = tankowanie;
        this.uzupelnianie = uzupelnianie;
        this.zatankowane = zatankowane;
    }

    @Override
    public void run() {
        while(true) {
            try {
                uzupelnianie.acquire();
                if(ControllerUstawienia.zasoby.koniecPaliwa()) {
                    while(ControllerUstawienia.zasoby.getTankujaceSamochody()!=0) {
                        if(this.isInterrupted())
                            Thread.currentThread().stop();
                    }
                    Thread.sleep(ileUzupelnia);
                    chron.acquire();
                    for (int j = 0; j < ControllerUstawienia.zasoby.getRodzajePaliw().length; j++) {
                        ControllerUstawienia.zasoby.setPozostalePaliwo(ControllerUstawienia.zasoby.getMaxPaliwa(), j);
                        ControllerMain.textPozostalePaliwoList[j].setText(String.valueOf(ControllerUstawienia.zasoby.getPozostalePaliwo(j)));
                    }
                    chron.release();
                }
                tankowanie.release();
                zatankowane.acquire();
            } catch (InterruptedException e) {
                //e.printStackTrace();
                Thread.currentThread().stop();
            }
        }
    }
}
