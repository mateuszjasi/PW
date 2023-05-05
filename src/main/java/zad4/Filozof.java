package zad4;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Filozof extends Thread {
    private int id, ilosc, rep;
    private volatile  static Semaphore dopusc;
    private volatile static Semaphore[] widelec;
    public static void setWidelec(Semaphore[] widelec) {
        Filozof.widelec = widelec;
    }
    public Filozof(int id, int rep, int ilosc, Semaphore dopusc) {
        this.id = id;
        this.rep = rep;
        this.ilosc = ilosc;
        this.dopusc = dopusc;
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
                dopusc.acquire();
                widelec[id].acquire();
                widelec[(id + 1) % ilosc].acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(">>>[F-" + id + ", " + i + "] :: [" + widelec[0].availablePermits() + ", " + widelec[1].availablePermits() + ", " + widelec[2].availablePermits() + ", " + widelec[3].availablePermits() + ", " + widelec[4].availablePermits() + "]");
            try {
                Thread.sleep(random.nextInt(5) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("<<<[F-" + id + ", " + i + "] :: [" + widelec[0].availablePermits() + ", " + widelec[1].availablePermits() + ", " + widelec[2].availablePermits() + ", " + widelec[3].availablePermits() + ", " + widelec[4].availablePermits() + "]");
            widelec[id].release();
            widelec[(id + 1) % ilosc].release();
            dopusc.release();
        }
    }
}
