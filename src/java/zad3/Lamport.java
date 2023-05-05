package zad3;
import java.util.Random;

public class Lamport extends Thread {
    private int nr, rep;
    private boolean synchr;
    public volatile static boolean[] wybieranie = {false};
    public volatile static int[] numerek = {0};
    public static char[] znaki = {'-', '+', '=', '.', '/'};
    public Lamport(int nr, int rep, boolean synchr) {
        super("Lamport-" + nr);
        this.nr = nr;
        this.rep = rep;
        this.synchr = synchr;
    }
    public void run() {
        if(synchr) {
            dzialanieSynchro();
        } else {
            dzialanieNiesynchro();
        }
    }
    public void dzialanieNiesynchro() {
        Random random = new Random();
        for(int p : numerek) {
            for(int i = 0; i < rep; i++) {
                try {
                    sleep(random.nextInt(10) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\nSekcja krytyczna wątku: "  + getName() + ", " + "nr powt.= " + i);
                for(int j = 0; j < 100; j++) {
                    System.out.print(znaki[nr]);
                }
            }
        }
    }
    public void dzialanieSynchro() {
        Random random = new Random();
        for(int p : numerek) {
            for(int i = 0; i < rep; i++) {
                try {
                    sleep(random.nextInt(10) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wybieranie[p] = true;
                int max = numerek[0];
                for(int i1 : numerek){
                    if(i1 > max){
                        max = i1;
                    }
                }
                numerek[p] = max + 1;
                wybieranie[p] = false;
                for(int j = 0; j < numerek.length; j++) {
                    while (wybieranie[j]);

                    while (numerek[j] != 0 && (numerek[j] < numerek[p]));
                }
                System.out.println("\nSekcja krytyczna wątku: "  + getName() + ", " + "nr powt.= " + i);
                for(int j = 0; j < 100; j++) {
                    System.out.print(znaki[nr]);
                }
                numerek[p] = 0;
            }
        }
    }
}