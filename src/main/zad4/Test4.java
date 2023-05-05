package zad4;

public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        zad4.MyThread5[] threads = new zad4.MyThread5[10];
        zad4.Licznik licznik = new zad4.Licznik();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new zad4.MyThread5("Wątek " + i, licznik);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        System.out.println("Stan licznika = " + licznik.getLicznik());
    }
}
