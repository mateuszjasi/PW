package zad3;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Widelce {
    private Condition[] widelec;
    private boolean[] zajety;
    private Lock lock = new ReentrantLock();
    private Condition lokaj = lock.newCondition();
    private int jest;
    private int N;
    public boolean[] getZajety() { return zajety; }
    public Widelce() {
        this.N = 5;
        this.zajety = new boolean[N];
        this.widelec = new Condition[N];
        this.jest = 0;
        for (int i = 0; i < N; i++) {
            this.zajety[i] = false;
            this.widelec[i] = lock.newCondition();
        }
    }
    public void WEZ(int nr, int i) {
        try {
            lock.lock();
            System.out.println(">>> (1) [F-" + nr + ", " + i + "] :: [" + zajety[0] + ", " + zajety[1] + ", " + zajety[2] + ", " + zajety[3] + ", " + zajety[4] + "] - " + jest);
            while (jest == 4)
                lokaj.await();
            jest++;
            while (zajety[nr])
                widelec[nr].await();
            zajety[nr] = true;
            while (zajety[(nr + 1) % N])
                widelec[(nr + 1) % N].await();
            zajety[(nr + 1) % N] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">>> (2) [F-" + nr + ", " + i + "] :: [" + zajety[0] + ", " + zajety[1] + ", " + zajety[2] + ", " + zajety[3] + ", " + zajety[4] + "] - " + jest);
            lock.unlock();
        }
    }
    public void ODLOZ(int nr, int i) {
        try {
            lock.lock();
            System.out.println("<<< (1) [F-" + nr + ", " + i + "] :: [" + zajety[0] + ", " + zajety[1] + ", " + zajety[2] + ", " + zajety[3] + ", " + zajety[4] + "] - " + jest);
            zajety[nr] = false;
            widelec[nr].signal();
            zajety[(nr + 1) % N] = false;
            widelec[(nr + 1) % N].signal();
            jest--;
            lokaj.signal();
        } finally {
            System.out.println("<<< (2) [F-" + nr + ", " + i + "] :: [" + zajety[0] + ", " + zajety[1] + ", " + zajety[2] + ", " + zajety[3] + ", " + zajety[4] + "] - " + jest);
            lock.unlock();
        }
    }
}