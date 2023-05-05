package zad4;
import java.util.concurrent.Semaphore;

public class Test {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        Thread[] sem = new Thread[5];
        for(int i = 0; i< 5;i++) {
            sem[i] = new Sem(i, 100, semaphore, true);
        }
        for(int i = 0; i< 5;i++) {
            sem[i].start();
        }
        for(int i = 0; i< 5;i++) {
            try {
                sem[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
