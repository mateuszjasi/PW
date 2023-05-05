package zad1;
import java.util.Random;

public class Dekker extends Thread {
    private int nr, rep, nrDrugiego;
    public volatile static boolean[] chce = {true, true};
    public volatile static int czyjaKolej = 0;
    public volatile static char[] znaki = {'+', '-'};
    private boolean synchr;
    public Dekker(int nr, int rep, boolean synchr) {
        super("Dekker-" + nr);
        this.nr = nr;
        this.rep = rep;
        this.nrDrugiego = (nr + 1) % 2;
        this.synchr = synchr;
    }
    public void run() {
        if(!synchr) {
            dzialanieNiesynchro();
        } else {
            dzialanieSynchro();
        }
    }
    public void dzialanieNiesynchro() {
        Random random = new Random();
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
    public void dzialanieSynchro() {
        Random random = new Random();
        for(int i = 0; i < rep; i++) {
            try {
                sleep(random.nextInt(10) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            chce[nrDrugiego] = false;
            while(!chce[nr]) {
                if(czyjaKolej == nr) {
                    chce[nrDrugiego] = true;
                    while (czyjaKolej == nr);
                    chce[nrDrugiego] = false;
                }
            }
            System.out.println("\nSekcja krytyczna wątku: "  + getName() + ", " + "nr powt.= " + i);
            for(int j = 0; j < 100; j++) {
                System.out.print(znaki[nr]);
            }
            chce[nrDrugiego] = true;
            czyjaKolej = nr;
        }
    }
}
