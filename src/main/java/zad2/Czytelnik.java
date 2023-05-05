package zad2;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Czytelnik extends Thread {
    private int id;
    private Czytelnia czytelnia;
    private Semaphore czyt, pis, chron;
    private int rep;
    public Czytelnik(int id, int rep,Czytelnia czytelnia, Semaphore czyt, Semaphore pis, Semaphore chron) {
        this.id = id;
        this.rep = rep;
        this.czyt = czyt;
        this.czytelnia = czytelnia;
        this.pis= pis;
        this.chron = chron;
    }
    public void run() {
        Random random = new Random();
        for (int i = 0; i < rep; i++) {
            try {
                Thread.sleep(random.nextInt(5)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                chron.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            czytelnia.setAc(czytelnia.getAc()+1);
            if (czytelnia.getAp() == 0) {
                czytelnia.setDc(czytelnia.getDc()+1);
                czyt.release();
            }
            chron.release();
            try {
                czyt.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(">>> [C-"+ id +", " + i + "] :: [" + czytelnia.getAc() + ", " + czytelnia.getDc() + ", " + czytelnia.getAp() + ", " + czytelnia.getDp() + "]");
            try {
                Thread.sleep(random.nextInt(5)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("<<< [C-"+ id +", " + i + "] :: [" + czytelnia.getAc() + ", " + czytelnia.getDc() + ", " + czytelnia.getAp() + ", " + czytelnia.getDp() + "]");
            try {
                chron.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            czytelnia.setDc(czytelnia.getDc()-1);
            czytelnia.setAc(czytelnia.getAc()-1);
            if(czytelnia.getDc()==0){
                while(czytelnia.getDp()< czytelnia.getAp()){
                    czytelnia.setDp(czytelnia.getDp()+1);
                    pis.release();
                }
            }
                chron.release();
        }
    }
}
