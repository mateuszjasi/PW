package com.example.projekt;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Aplikacja extends Thread {
    static int bokKwadratu = 75;
    Rectangle rec;
    Text text, text1;
    static ArrayList<Rectangle> samochodyList;
    static ArrayList<Rectangle> samochodyKolejkaList;
    static ArrayList<Text> textSamochodyKolejkaList;
    static ArrayList<Text> textSamochodyList;
    static ArrayList<Text> textKierowcaList;
    static ArrayList<Circle> kierowcaList;
    static Pane pane;
    static ArrayList<Text> textPaliwoSamochodyList;
    static ArrayList<Text> textPaliwoSamochodyKolejkaList;
    ArrayList<Integer> kolejka;
    Semaphore chron, stacja;
    int N, idPaliwa, litry;
    Uzupelnienie uzupelnienie;
    static ArrayList<Samochod> samochody;

    public Aplikacja(Pane pane) {
        samochodyKolejkaList = new ArrayList<>();
        samochodyList = new ArrayList<>();
        textSamochodyKolejkaList = new ArrayList<>();
        textSamochodyList = new ArrayList<>();
        kierowcaList=new ArrayList<>();
        textKierowcaList=new ArrayList<>();
        Aplikacja.pane=pane;
        kolejka = new ArrayList<>();
        textPaliwoSamochodyList=new ArrayList<>();
        textPaliwoSamochodyKolejkaList=new ArrayList<>();
        chron = new Semaphore(1);
        samochody=new ArrayList<>();
    }

    synchronized private void stworz_samochod(int id, int idPaliwa, int litry){
        //samochody
        try {
            chron.acquire();
        } catch (InterruptedException e) {
            return;
        }
        Platform.runLater(()->{
            rec=new Rectangle(ControllerMain.kolejkaList.get(samochodyKolejkaList.size()).getX()+5, ControllerMain.kolejkaList.get(samochodyList.size()).getY()+5, bokKwadratu, bokKwadratu);
            Random random = new Random();
            switch (random.nextInt(7)) {
                case 0 -> rec.setFill(Color.RED);
                case 1 -> rec.setFill(Color.GREEN);
                case 2 -> rec.setFill(Color.YELLOW);
                case 3 -> rec.setFill(Color.WHITE);
                case 4 -> rec.setFill(Color.PINK);
                case 5 -> rec.setFill(Color.BLUE);
                case 6 -> rec.setFill(Color.LIGHTBLUE);
                case 7 -> rec.setFill(Color.LIGHTGREY);
            }
            rec.setStrokeType(StrokeType.INSIDE);
            rec.setStrokeWidth(1);
            rec.setStroke(Color.BLACK);
            rec.setAccessibleText(String.valueOf(id));
            samochodyKolejkaList.add(rec);
            samochodyList.add(rec);

            text=new Text(String.valueOf(id));
            text.setX(rec.getX()+2);
            text.setY(rec.getY()+text.getBoundsInLocal().getHeight()-3);
            text.setFill(Color.BLACK);

            text1=new Text(ControllerUstawienia.zasoby.getRodzajePaliw()[idPaliwa]+": "+litry+"L");
            text1.setX(rec.getX()+3);
            text1.setY(rec.getY()+bokKwadratu-3);
            text1.setFill(Color.BLACK);

            textSamochodyKolejkaList.add(text);
            textSamochodyList.add(text);
            textPaliwoSamochodyKolejkaList.add(text1);
            textPaliwoSamochodyList.add(text1);

            pane.getChildren().add(rec);
            pane.getChildren().add(text);
            pane.getChildren().add(text1);
            chron.release();
        });
    }

    private void poczatek() {
        int M=ControllerUstawienia.zasoby.getM(); //liczba stanowisk z dystrybutorami
        N=ControllerUstawienia.zasoby.getN(); //maksymalna liczba samochodów które mogą przebywać na stacji (liczba stanowisk + kolejka)
        int K=ControllerUstawienia.zasoby.getK(); //liczba kas
        int ileUzupelnia=ControllerUstawienia.zasoby.getIleUzupelnia(); //czas uzupełniania zasobów (blokowania dystrybutorów)
        int ileTankuje=ControllerUstawienia.zasoby.getIleTankuje(); //czas potrzebny do zatankowania 1L paliwa
        int ilePlaci=ControllerUstawienia.zasoby.getIlePlaci(); //czas potrzebny na zapłacenie

        Semaphore stanowiska = new Semaphore(M); // liczba stanowisk
        Semaphore kasy = new Semaphore(K); // liczba kas
        stacja = new Semaphore(N); // liczba samochodów na stacji
        Semaphore tankowanie = new Semaphore(0); // powiadamia obiekt samochod, że może zacząć tankowanie
        Semaphore uzupelnianie = new Semaphore(0); // sprawdza, czy potrzebne jest uzupełnienie zapasów paliwa na stacji
        Semaphore zatankowane = new Semaphore(0); // powiadamia wątek "uzupelnianie", że zaktualizowano ilość paliwa

        boolean[] wolneStanowisko=new boolean[M];
        boolean[] wolnaKasa=new boolean[K];
        for (int j=0; j<M; j++)
            wolneStanowisko[j]=true;
        for (int j=0; j<K; j++)
            wolnaKasa[j]=true;

        Samochod samochod;
        uzupelnienie = new Uzupelnienie(ileUzupelnia, chron, tankowanie, uzupelnianie, zatankowane);
        uzupelnienie.start();
        int i=0;
        boolean loop=true;
        while(loop){
            try {
                stacja.acquire();
                Random random = new Random();
                idPaliwa=random.nextInt(ControllerUstawienia.zasoby.getRodzajePaliw().length);
                litry = random.nextInt(60)+1; //<1, 60>
                chron.acquire();
                kolejka.add(i);
                chron.release();
                stworz_samochod(i, idPaliwa, litry);
                samochod = new Samochod(i, stanowiska, kasy, stacja, ileTankuje, ilePlaci, ileUzupelnia, chron,
                        tankowanie, uzupelnianie, zatankowane, kolejka, wolneStanowisko, pane, wolnaKasa, idPaliwa, litry);
                samochody.add(samochod);
                samochod.start();
                if(ControllerUstawienia.zasoby.getAuta()>0 && i==ControllerUstawienia.zasoby.getAuta()-1)
                    loop=false;
                i++;
            } catch (InterruptedException e) {
                uzupelnienie.interrupt();
                for (Samochod x : samochody){
                    x.interrupt();
                }
                Thread.currentThread().stop();
            }
        }
    }

    public void koniec(){
        while (stacja.availablePermits()!=N);
        uzupelnienie.interrupt();
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Koniec Symulacji");
            alert.setHeaderText(null);
            alert.setContentText("Symulacja zakończona!");
            alert.show();

            final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
            runnable.run();
            ControllerMain.btnStartS.setDisable(false);
            ControllerMain.btnStopS.setDisable(true);
            ControllerMain.btnSettingS.setDisable(false);
            ControllerMain.flagConfig=false;
        });
    }

    @Override
    public void run(){
        poczatek();
        koniec();
    }
}