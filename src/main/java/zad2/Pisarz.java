package zad2;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Pisarz extends Thread {
    private int id;
    private Czytelnia czytelnia;
    private Semaphore chron, czyt, pis, mutex_pis;
    private int rep;
    public Pisarz(int id, int rep, Czytelnia czytelnia, Semaphore chron, Semaphore czyt, Semaphore pis, Semaphore mutex_pis) {
        this.id = id;
        this.rep = rep;
        this.pis = pis;
        this.czyt = czyt;
        this.chron = chron;
        this.czytelnia = czytelnia;
        this.mutex_pis = mutex_pis;
    }
    public void run() {
        Random random = new Random();
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(11) + 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                chron.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            czytelnia.setAp(czytelnia.getAp() + 1);
            if (czytelnia.getAc() == 0) {
                czytelnia.setDp(czytelnia.getDp() + 1);
                pis.release();
            }
            chron.release();
            try {
                pis.acquire();
                mutex_pis.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("==> [P-" + id + ", " + i + "] :: [" + czytelnia.getAc() + ", " + czytelnia.getDc() + ", " + czytelnia.getAp() + ", " + czytelnia.getDp() + "]");
            try {
                Thread.sleep(random.nextInt(5) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("<== [P-" + id + ", " + i + "] :: [" + czytelnia.getAc() + ", " + czytelnia.getDc() + ", " + czytelnia.getAp() + ", " + czytelnia.getDp() + "]");
            mutex_pis.release();
            try {
                chron.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            czytelnia.setDp(czytelnia.getDp() - 1);
            czytelnia.setAp(czytelnia.getAp() - 1);
            if (czytelnia.getDp() == 0) {
                while (czytelnia.getDc() < czytelnia.getAc()) {
                    czytelnia.setDc(czytelnia.getDc() + 1);
                    czyt.release();
                }
            }
            chron.release();
        }
    }
}