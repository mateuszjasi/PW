package zad1;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new zad1.MyThread1("Wątek nr " + i );
        }
        for(int i = 0 ; i < threads.length;i++){
            threads[i].start();
        }
        for(int i = 0 ; i < threads.length;i++){
            threads[i].join();
        }
        System.out.println("Koniec");
    }
}
