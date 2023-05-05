package zad2;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Czytelnia czytelnia = new Czytelnia();
        int m=4, n=2;
        Czytelnik[] czytelniks = new Czytelnik[m];
        Pisarz[] pisarzs = new Pisarz[n];
        for (int i = 0; i < n; i++)
            pisarzs[i] = new Pisarz(i, 100, czytelnia);
        for (int i = 0; i < m; i++)
            czytelniks[i] = new Czytelnik(i, 100, czytelnia);
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