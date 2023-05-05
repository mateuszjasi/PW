package zad2;
import java.util.concurrent.Semaphore;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        int m = 4, n = 2, rep = 100;
        Czytelnia czytelnia = new Czytelnia(0,0,0,0);
        Semaphore czyt = new Semaphore(0);
        Semaphore pis = new Semaphore(0);
        Semaphore chron = new Semaphore(1);
        Semaphore mutex_pis = new Semaphore(1);
        Pisarz[] pisarzs = new Pisarz[n];
        Czytelnik[] czytelniks = new Czytelnik[m];
        for (int i = 0; i < n; i++)
            pisarzs[i] = new Pisarz(i, rep, czytelnia, chron, czyt, pis, mutex_pis);
        for (int i = 0; i < m; i++)
            czytelniks[i] = new Czytelnik(i, rep, czytelnia, czyt, pis, chron);
        for (int i = 0; i < n; i++)
            pisarzs[i].start();
        for (int i = 0; i < m; i++)
            czytelniks[i].start();
        for (int i = 0; i < n; i++)
            pisarzs[i].join();
        for (int i = 0; i < m; i++)
            czytelniks[i].join();
        System.out.println("Koniec programu");
    }
}