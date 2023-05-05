package com.company;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Czytelnik extends Thread {
    public  static  int liczba_czyt, liczba_pis;
    private final int id, rep;
    public static  Semaphore zmiana =  new Semaphore(1);
            public final static int K=3;
    public static  Semaphore wolne= new Semaphore(K);
    public Czytelnik(int id, int rep, Semaphore wolne, int l_czyt, int l_pis, int K) {
        this.id = id;
        //this.K = K;
        this.liczba_czyt = l_czyt;
        this.liczba_pis = l_pis;
        this.rep = rep;
        this.wolne = wolne;
    }
    //**********************************

    public void run() {
        //SPRAWY WLASNE---------------------------------------------------------------------------
        Random random = new Random();
        liczba_czyt=0;
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(11) + 5);

            //KONIEC SPRAW WLASNYCH----------------------------------------------------------

            wolne.acquire();
            zmiana.acquire();
            liczba_czyt++;
            zmiana.release();
            System.out.println(">>> [C-" + id + ", " + i + "] :: [ l_czyt " + liczba_czyt + ", l_pis " + liczba_pis + "]");

            Thread.sleep(random.nextInt(5)+1);


            //*********KONIEC CZYTANIA***********
            zmiana.acquire();
            liczba_czyt--;
            zmiana.release();
            wolne.release();
                System.out.println("<<< [C-" + id + ", " + i + "] :: [ l_czyt " + liczba_czyt + ", l_pis " + liczba_pis + "]");
            } catch (InterruptedException e) {break;}

            //if(i==99)break;
        }}}