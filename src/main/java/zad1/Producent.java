package zad1;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producent extends Thread {
    private int id, wartosc;
    private String[] bufor;
    private Semaphore wolne, zajete, chron_j;
    private int rep, iloscProducentow;
    private int j;
    public Producent(int id, int rep, String[] bufor, int j, Semaphore wolne, Semaphore zajete, Semaphore chron_j, int iloscProducentow) {
        this.id = id;
        this.rep = rep;
        this.bufor = bufor;
        this.j = j;
        this.wolne = wolne;
        this.zajete = zajete;
        this.chron_j = chron_j;
        this.iloscProducentow = iloscProducentow;
    }
    public void run() {
        Random random = new Random();
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(10) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                wolne.acquire();
                chron_j.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wartosc = random.nextInt(100);
            bufor[j] = String.format("Dana=[P-%d, %d, %d]", id, i, wartosc);
            zajete.release();
            j = (j + 1) % iloscProducentow;
            chron_j.release();
        }
    }
}