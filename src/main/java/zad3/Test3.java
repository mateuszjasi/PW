package com.company;

import java.util.concurrent.Semaphore;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        int n = 2, m = 5, K = 3, rep = 1000, liczba_czyt = 0, liczba_pis = 0;
        Semaphore wolne = new Semaphore(K);
        Semaphore pis = new Semaphore(1);
        Czytelnik[] czytelnicy = new Czytelnik[m];
        Pisarz[] pisarze = new Pisarz[n];
        //************************************************************
        for (int i = 0; i < n; i++){
            pisarze[i] = new Pisarz(i, rep, wolne, liczba_czyt, liczba_pis, pis, K);}

           for (int i = 0; i < m; i++) {
           czytelnicy[i] = new Czytelnik(i, rep, wolne, liczba_czyt, liczba_pis, K);}

          for (int i = 0; i < n; i++){pisarze[i].start();}
          for (int i = 0; i < m; i++){czytelnicy[i].start();}
        try {
            Thread.sleep(10000);}
        catch(InterruptedException e ){}
        for (int i = 0; i < n; i++){pisarze[i].interrupt();}
        for (int i = 0; i < m; i++){czytelnicy[i].interrupt();}
        for (int i = 0; i < n; i++){pisarze[i].join();}
        for (int i = 0; i < m; i++){
            czytelnicy[i].join();}


        System.out.print("Koniec programu");
    }
}
