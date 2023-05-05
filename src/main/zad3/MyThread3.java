package zad3;

public class MyThread3 extends Thread {
    public MyThread3(String name) {
        super(name);
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Spałem przez 1s -" + getName());
            } catch (InterruptedException e) {
                System.out.println("Zostalem obudzony " + getName());
                return;
            }
        }
    }
}
