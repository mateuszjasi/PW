package zad1;

public class Test {
    public static void main(String[] args){
        Dekker dekker0 = new Dekker(0, 100, true);
        Dekker dekker1 = new Dekker(1, 100, true);
        dekker0.start();
        dekker1.start();
        try {
            dekker0.join();
            dekker1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
