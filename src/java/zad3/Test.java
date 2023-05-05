package zad3;

public class Test {
    public static void main(String[] args) {
        Thread[] lamport = new Thread[5];
        for(int i = 0; i< 5;i++) {
            lamport[i] = new Lamport(i, 100, true);
        }
        for(int i = 0; i< 5;i++) {
            lamport[i].start();
        }
        for(int i = 0; i< 5;i++) {
            try {
                lamport[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
