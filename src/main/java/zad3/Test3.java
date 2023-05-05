package zad3;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        Filozof[] filozofs = new Filozof[5];
        Widelce widelce = new Widelce();
        for (int i = 0; i < 5; i++)
            filozofs[i] = new Filozof(i, 100, widelce);
        for (int i = 0; i < 5; i++)
            filozofs[i].start();
        for (int i = 0; i < 5; i++)
            filozofs[i].join();
        System.out.println("Koniec programu");
    }
}
