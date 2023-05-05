package zad2;

public class Test {
    public static void main(String[] args) {
        Peterson peterson0 = new Peterson(0, 100, true);
        Peterson peterson1 = new Peterson(1, 100, true);
        peterson0.start();
        peterson1.start();
        try {
            peterson0.join();
            peterson1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
