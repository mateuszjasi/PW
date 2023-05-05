package zad1;
import java.util.concurrent.Semaphore;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        int m = 4, n = 5, j = 1, k = 1, rep = 33;
        Semaphore wolne = new Semaphore(m);
        Semaphore zajete = new Semaphore(0);
        Semaphore chron_j = new Semaphore(1);
        Semaphore chron_k = new Semaphore(1);
        String[] bufor = new String[m];
        Producent[] producents = new Producent[m];
        Konsument[] konsuments = new Konsument[n];
        for (int i = 0; i < m; i++)
            producents[i] = new Producent(i, rep, bufor, j, wolne, zajete, chron_j, m);;
        for (int i = 0; i < n; i++)
            konsuments[i] = new Konsument(i, rep, bufor, k, wolne, zajete, chron_k, m);;
        for (int i = 0; i < m; i++)
            producents[i].start();
        for (int i = 0; i < n; i++)
            konsuments[i].start();
        for (int i = 0; i < m; i++)
            producents[i].join();
        for (int i = 0; i < n; i++)
            konsuments[i].join();
        System.out.println("Koniec programu");
    }
}