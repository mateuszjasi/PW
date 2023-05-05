package zad1;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Konsument extends Thread {
    private int id;
    private String[] bufor;
    private Semaphore wolne, zajete, chron_k;
    private int rep, iloscProducentow;
    private int k;
    public Konsument(int id, int rep, String[] bufor, int k, Semaphore wolne, Semaphore zajete, Semaphore chron_k, int iloscProducentow) {
        this.id = id;
        this.rep = rep;
        this.bufor = bufor;
        this.k = k;
        this.wolne = wolne;
        this.chron_k = chron_k;
        this.zajete = zajete;
        this.iloscProducentow = iloscProducentow;
    }
    public void run() {
        Random random = new Random();
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(11) + 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(zajete.availablePermits()>0){
                try {
                    zajete.acquire();
                    chron_k.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[K-" + id + ", " + i + "] >> " + bufor[k]);
                wolne.release();
                k = (k + 1) % iloscProducentow;
                chron_k.release();
            }
        }
    }
}