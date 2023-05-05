package zad4;

public class MyThread5 extends Thread{
    private zad4.Licznik licznik;
    public MyThread5(String name, zad4.Licznik licznik) {
        super(name);
        this.licznik = licznik;
    }
    public void run() {
        for(int i = 0; i < 5000000;i++){
           licznik.inc();
        }
    }
    public zad4.Licznik getLicznik() {
        return licznik;
    }
}
