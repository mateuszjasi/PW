package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Pisarz extends Thread {
    private final int id, rep, K;

    private static  Semaphore  pis;
    public Pisarz(int id, int rep, Semaphore wolne, int l_czyt, int l_pis, Semaphore pis, int K ) {
        this.id = id;
        this.rep = rep;
        this.pis = pis;
        this.K = K;
    }//*****************************************************************
    public void run() {
        //SPRAWY WLASNE
        Random random = new Random();
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(11) + 5);

            //================================================================
            pis.acquire();


            for (int j = 0; j < K; j++) {
                Czytelnik.wolne.acquire();
                }
            //**************PISANIE*********
            Czytelnik.liczba_pis++;
            System.out.println("==> [P-" + id + ", " + i + "] :: [l_czyt " + Czytelnik.liczba_czyt + ", l_pis " + Czytelnik.liczba_pis + "]");
           Thread.sleep(random.nextInt(5)+1);
            //****************KONIEC PISANIA********************
            for (int j = 0; j < K; j++){
               Czytelnik.wolne.release();
                }
            Czytelnik.liczba_pis--;
            System.out.println("<== [P-" + id + ", " + i + "] :: [l_czyt " + Czytelnik.liczba_czyt + ", l_pis " + Czytelnik.liczba_pis + "]");
            pis.release();
            //if(i==99)break;
        }catch (InterruptedException e) {break;}}
}
}