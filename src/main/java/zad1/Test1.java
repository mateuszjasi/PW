package zad1;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        int N = 10, m = 4, n = 5;
        Buffer monitor = new Buffer(N);
        Producent[] producents = new Producent[m];
        Konsument[] konsuments = new Konsument[n];
        for (int i = 0; i < m; i++) {
            producents[i] = new Producent(i, 50, monitor);
            producents[i].start();
        }
        for (int i = 0; i < n; i++) {
            konsuments[i] = new Konsument(i, 40, monitor);
            konsuments[i].start();
        }
        for (int i = 0; i < m; i++)
            producents[i].join();
        for (int i = 0; i < n; i++)
            konsuments[i].join();
        System.out.println("Koniec programu");
    }
}