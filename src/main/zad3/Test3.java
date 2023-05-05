package zad3;

public class Test3 {
    public static void main(String[] args) {
      Thread thread = new zad3.MyThread4("Thread4");
      thread.start();
        try {
            Thread.sleep(4500);
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        System.out.println("Koniec");
    }
}
