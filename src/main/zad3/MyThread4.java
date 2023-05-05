package zad3;

public class MyThread4 extends Thread {
    public MyThread4(String name) {
        super(name);
    }
    public void run() {
        Thread thread = new zad3.MyThread3("Thread3 pierwszy");
        thread.start();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(2000);
                System.out.println("Spałem przez 2s -" + getName());
            } catch (InterruptedException e) {
                System.out.println("Zostalem obudzony " + getName());
                thread.interrupt();
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }
    }
}
