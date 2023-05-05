package zad1;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int N;
    private String[] pula;
    private int wej;
    private int wyj;
    private Lock lock = new ReentrantLock();
    private Condition pelny = lock.newCondition();
    private Condition pusty = lock.newCondition();
    private int licz;
    public Buffer(int N) {
        this.N = N;
        this.pula = new String[N];
        this.wej = 0;
        this.wyj = 0;
        this.licz = 0;
    }
    public void wstaw(String dana) {
        lock.lock();
        try {
            while (licz == N) {
                try {
                    pelny.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pula[wej] = dana;
            wej = (wej + 1) % N;
            licz++;
            pusty.signal();
        } finally {
            lock.unlock();
        }
    }
    public String pobierz() {
        lock.lock();
        String wartosc = "";
        try {
            while (licz == 0) {
                try {
                    pusty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            wartosc = pula[wyj];
            wyj = (wyj + 1) % N;
            licz--;
            pelny.signal();
        } finally {
            lock.unlock();
        }
        return wartosc;
    }
}