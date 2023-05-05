package zad2;
import java.util.Random;

public class Pisarz extends Thread {
    private int id;
    private Czytelnia czytelnia;
    private int rep;
    public Pisarz(int id, int rep, Czytelnia czytelnia) {
        this.id = id;
        this.rep = rep;
        this.czytelnia = czytelnia;
    }
    public void run() {
        Random random = new Random();
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(11) + 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            czytelnia.POCZ_PISARZ(id, i);
            try {
                Thread.sleep(random.nextInt(5) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            czytelnia.KON_PISARZ(id, i);
        }
    }
}