package zad4;
import java.util.concurrent.Semaphore;

public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        int n = 5, rep = 100;
        Semaphore dopusc = new Semaphore(n);
        Filozof[] filozofs = new Filozof[n];
        Semaphore[] widelec = new Semaphore[n];
        for(int i = 0; i< widelec.length;i++)
            widelec[i] = new Semaphore(1);
        Filozof.setWidelec(widelec);
        for (int i = 0; i < n; i++)
            filozofs[i] = new Filozof(i, rep, n, dopusc);
        for (int i = 0; i < n; i++)
            filozofs[i].start();
        for (int i = 0; i < n; i++)
            filozofs[i].join();
        System.out.println("Koniec programu");
    }
}
