package com.example.projekt;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Samochod extends Thread{
    int id, promienKierowcy, ileTankuje, ilePlaci, ileUzupelnia, idPaliwa, litry, stanowisko, kasa;
    Semaphore stanowiska, kasy, stacja, tankowanie, chron, uzupelnianie, zatankowane;
    String paliwo;
    boolean[] wolneStanowisko;
    boolean[] wolnaKasa;
    ArrayList<Integer> kolejka;
    Pane pane;
    Circle circle;
    Text text;

    public Samochod(int id, Semaphore stanowiska, Semaphore kasy, Semaphore stacja, int ileTankuje, int ilePlaci,
                    int ileUzupelnia, Semaphore chron, Semaphore tankowanie, Semaphore uzupelnianie, Semaphore zatankowane, ArrayList<Integer> kolejka,
                    boolean[] wolneStanowisko, Pane pane, boolean[] wolnaKasa, int idPaliwa, int litry) {
        this.id = id;
        this.stanowiska = stanowiska;
        this.kasy = kasy;
        this.stacja = stacja;
        this.ileTankuje = ileTankuje;
        this.ilePlaci = ilePlaci;
        this.ileUzupelnia=ileUzupelnia;
        this.idPaliwa=idPaliwa;
        this.paliwo = ControllerUstawienia.zasoby.getPaliwo(idPaliwa);
        this.litry=litry;
        this.chron=chron;
        this.kolejka=kolejka;
        this.wolneStanowisko=wolneStanowisko;
        this.wolnaKasa=wolnaKasa;
        this.pane=pane;
        promienKierowcy=20;
        this.tankowanie=tankowanie;
        this.uzupelnianie = uzupelnianie;
        this.zatankowane = zatankowane;
    }

    private void zajecieStanowiska(int st){
        try {
            chron.acquire();
        } catch (InterruptedException e) {
            return;
        }
        int h=-1;
        for(int i=0; i<Aplikacja.samochodyKolejkaList.size(); i++){
            if(Objects.equals(Aplikacja.samochodyKolejkaList.get(i).getAccessibleText(), String.valueOf(id))){
                h=i;
                break;
            }
        }
        Rectangle samochodKolejka=Aplikacja.samochodyKolejkaList.get(h);
        Text textSamochodKolejka=Aplikacja.textSamochodyKolejkaList.get(h);
        Text textPaliwoSamochodKolejka=Aplikacja.textPaliwoSamochodyKolejkaList.get(h);
        Aplikacja.samochodyKolejkaList.remove(h);
        Aplikacja.textSamochodyKolejkaList.remove(h);
        Aplikacja.textPaliwoSamochodyKolejkaList.remove(h);
        kolejka.remove(h);

        Platform.runLater(()->{
            TranslateTransition translate = new TranslateTransition(Duration.millis(ControllerUstawienia.zasoby.getIleAnim()));
            translate.setToX(ControllerMain.stanowiskaList.get(st).getX()+5-samochodKolejka.getX());
            translate.setToY(ControllerMain.stanowiskaList.get(st).getY()+5-samochodKolejka.getY());
            TranslateTransition translateText = new TranslateTransition(Duration.millis(ControllerUstawienia.zasoby.getIleAnim()));
            translateText.setToX(ControllerMain.stanowiskaList.get(st).getX()+5-samochodKolejka.getX());
            translateText.setToY(ControllerMain.stanowiskaList.get(st).getY()+5-samochodKolejka.getY());
            TranslateTransition translateTextPaliwo = new TranslateTransition(Duration.millis(ControllerUstawienia.zasoby.getIleAnim()));
            translateTextPaliwo.setToX(ControllerMain.stanowiskaList.get(st).getX()+5-samochodKolejka.getX());
            translateTextPaliwo.setToY(ControllerMain.stanowiskaList.get(st).getY()+5-samochodKolejka.getY());
            ParallelTransition transition = new ParallelTransition(samochodKolejka,translate);
            ParallelTransition transitionText = new ParallelTransition(textSamochodKolejka,translateText);
            ParallelTransition transitionTextPaliwo = new ParallelTransition(textPaliwoSamochodKolejka,translateTextPaliwo);
            transition.play();
            transitionText.play();
            transitionTextPaliwo.play();
            transition.setOnFinished(actionEvent -> {
                stworz_Kierowce(id, stanowisko);
                moveKolejka();
            });
        });
    }

    private void moveKolejka(){
        Platform.runLater(()->{
            for(int i=0; i<Aplikacja.samochodyKolejkaList.size(); i++){
                Aplikacja.samochodyKolejkaList.get(i).setX(ControllerMain.kolejkaList.get(i).getX()+5);
                Aplikacja.textSamochodyKolejkaList.get(i).setX(ControllerMain.kolejkaList.get(i).getX()+5+3);
                Aplikacja.textPaliwoSamochodyKolejkaList.get(i).setX(ControllerMain.kolejkaList.get(i).getX()+5+3);
            }
            chron.release();
        });
    }

    private void stworz_Kierowce(int id, int stanowisko){
        Platform.runLater(()->{
            circle=new Circle(ControllerMain.stanowiskaList.get(stanowisko).getX()+5+(float)ControllerMain.bokKwadratu/2,ControllerMain.stanowiskaList.get(stanowisko).getY()+5+(float)ControllerMain.bokKwadratu/2,promienKierowcy);
            Random random = new Random();
            switch (random.nextInt(4)) {
                case 0 -> circle.setFill(Color.WHITE);
                case 1 -> circle.setFill(Color.BROWN);
                case 2 -> circle.setFill(Color.YELLOW);
                case 3 -> circle.setFill(Color.LIGHTPINK);
            }
            circle.setStrokeType(StrokeType.INSIDE);
            circle.setStrokeWidth(1);
            circle.setStroke(Color.BLACK);
            circle.setAccessibleText(String.valueOf(id));
            Aplikacja.kierowcaList.add(circle);
            text=new Text(String.valueOf(id));
            text.setX(circle.getCenterX()-text.getBoundsInLocal().getWidth()/2-2);
            text.setY(circle.getCenterY()+text.getBoundsInLocal().getHeight()/2-3);
            text.setFont(new Font(15));
            text.setFill(Color.BLACK);
            Aplikacja.textKierowcaList.add(text);
            pane.getChildren().add(circle);
            pane.getChildren().add(text);
        });
    }

    private void zajecieKasy(int id, int kasa){
        try {
            chron.acquire();
        } catch (InterruptedException e) {
            return;
        }
        int h=-1;
        for (int i=0; i<Aplikacja.kierowcaList.size(); i++){
            if(Objects.equals(Aplikacja.kierowcaList.get(i).getAccessibleText(), String.valueOf(id))){
                h=i;
                break;
            }
        }
        Circle kierowca=Aplikacja.kierowcaList.get(h);
        Text textKierowca=Aplikacja.textKierowcaList.get(h);
        chron.release();
        Platform.runLater(()->{
            TranslateTransition translate = new TranslateTransition(Duration.millis(ControllerUstawienia.zasoby.getIleAnim()));
            translate.setToX(ControllerMain.kasyList.get(kasa).getX()-textKierowca.getX()+(float)ControllerMain.szerKasy/2-textKierowca.getBoundsInLocal().getWidth()/2);
            translate.setToY(ControllerMain.kasyList.get(kasa).getY()-textKierowca.getY()+(float)ControllerMain.wysKasy/2+textKierowca.getBoundsInLocal().getHeight()/4);
            TranslateTransition translateText = new TranslateTransition(Duration.millis(ControllerUstawienia.zasoby.getIleAnim()));
            translateText.setToX(ControllerMain.kasyList.get(kasa).getX()-textKierowca.getX()+(float)ControllerMain.szerKasy/2-textKierowca.getBoundsInLocal().getWidth()/2);
            translateText.setToY(ControllerMain.kasyList.get(kasa).getY()-textKierowca.getY()+(float)ControllerMain.wysKasy/2+textKierowca.getBoundsInLocal().getHeight()/4);
            ParallelTransition transition = new ParallelTransition(kierowca,translate);
            ParallelTransition transitionText = new ParallelTransition(textKierowca,translateText);
            transition.play();
            transitionText.play();
        });
    }

    private void wyjazd(int id, int kasa, int stanowisko){
        try {
            chron.acquire();
        } catch (InterruptedException e) {
            return;
        }
        int h=-1;
        for(int i=0; i<Aplikacja.kierowcaList.size(); i++){
            if(Objects.equals(Aplikacja.kierowcaList.get(i).getAccessibleText(), String.valueOf(id))){
                h=i;
                break;
            }
        }
        int hSamochod=-1;
        for (int i=0; i<Aplikacja.samochodyList.size(); i++){
            if(Objects.equals(Aplikacja.samochodyList.get(i).getAccessibleText(), String.valueOf(id))){
                hSamochod=i;
                break;
            }
        }
        Circle kierowca = Aplikacja.kierowcaList.get(h);
        Text textKierowca = Aplikacja.textKierowcaList.get(h);
        Rectangle samochod = Aplikacja.samochodyList.get(hSamochod);
        Text textSamochodu = Aplikacja.textSamochodyList.get(hSamochod);
        Text textPaliwoSamochodu = Aplikacja.textPaliwoSamochodyList.get(hSamochod);
        Aplikacja.kierowcaList.remove(h);
        Aplikacja.textKierowcaList.remove(h);
        Aplikacja.samochodyList.remove(hSamochod);
        Aplikacja.textSamochodyList.remove(hSamochod);
        Aplikacja.textPaliwoSamochodyList.remove(hSamochod);
        chron.release();

        Platform.runLater(()->{
            TranslateTransition translate = new TranslateTransition(Duration.millis((float)ControllerUstawienia.zasoby.getIleAnim()/2));
            TranslateTransition translateText = new TranslateTransition(Duration.millis((float)ControllerUstawienia.zasoby.getIleAnim()/2));
            translate.setToX(0);
            translate.setToY(0);
            translateText.setToX(0);
            translateText.setToY(0);
            ParallelTransition transition = new ParallelTransition(kierowca,translate);
            ParallelTransition transitionText = new ParallelTransition(textKierowca,translateText);
            wolnaKasa[kasa]=true;
            kasy.release();
            transition.play();
            transitionText.play();
            transition.setOnFinished(actionEvent -> {
                translate.setToX(0);
                translate.setToY(pane.getHeight() / 2 + promienKierowcy);
                ParallelTransition transition1 = new ParallelTransition(kierowca, translate);
                TranslateTransition translateSamochod = new TranslateTransition(Duration.millis((double) ControllerUstawienia.zasoby.getIleAnim() / 2));
                translateSamochod.setToX(ControllerMain.stanowiskaList.get(stanowisko).getX() - samochod.getX() + 5);
                translateSamochod.setToY(pane.getHeight() - samochod.getY() + (float)ControllerMain.bokKwadratu / 2);
                ParallelTransition transitionSamochod = new ParallelTransition(samochod, translateSamochod);
                transitionSamochod.play();
                transition1.play();
                transition1.setOnFinished(actionEvent2 -> {
                    wolneStanowisko[stanowisko] = true;
                    stanowiska.release();
                    stacja.release();
                });
            });
            transitionText.setOnFinished(actionEvent -> {
                translateText.setToX(0);
                translateText.setToY(pane.getHeight()/2+promienKierowcy);
                ParallelTransition transitionText2 = new ParallelTransition(textKierowca,translateText);
                TranslateTransition translateTextSamochod = new TranslateTransition(Duration.millis((float)ControllerUstawienia.zasoby.getIleAnim()/2));
                translateTextSamochod.setToX(ControllerMain.stanowiskaList.get(stanowisko).getX()-samochod.getX()+5);
                translateTextSamochod.setToY(pane.getHeight()-samochod.getY()+(float)ControllerMain.bokKwadratu/2);
                ParallelTransition transitionTextSamochod = new ParallelTransition(textSamochodu,translateTextSamochod);
                TranslateTransition translateTextPaliwo=new TranslateTransition(Duration.millis((float)ControllerUstawienia.zasoby.getIleAnim()/2));
                translateTextPaliwo.setToX(ControllerMain.stanowiskaList.get(stanowisko).getX()-samochod.getX()+5);
                translateTextPaliwo.setToY(pane.getHeight()-samochod.getY()+(float)ControllerMain.bokKwadratu/2);
                ParallelTransition transitionTextPaliwo = new ParallelTransition(textPaliwoSamochodu,translateTextPaliwo);
                transitionTextPaliwo.play();
                transitionTextSamochod.play();
                transitionText2.play();
            });
        });
    }

    @Override
    public void run() {
        try {
            stanowiska.acquire();
            for(int i=0; i<wolneStanowisko.length; i++){
                if(wolneStanowisko[i]){
                    wolneStanowisko[i]=false;
                    stanowisko=i;
                    break;
                }
            }
            zajecieStanowiska(stanowisko);
            uzupelnianie.release();
            tankowanie.acquire();
            chron.acquire();
            ControllerUstawienia.zasoby.setTankujaceSamochody(ControllerUstawienia.zasoby.getTankujaceSamochody()+1);
            ControllerUstawienia.zasoby.setPozostalePaliwo(ControllerUstawienia.zasoby.getPozostalePaliwo(idPaliwa) - litry, idPaliwa);
            ControllerMain.textPozostalePaliwoList[idPaliwa].setText(String.valueOf(ControllerUstawienia.zasoby.getPozostalePaliwo(idPaliwa)));
            chron.release();
            zatankowane.release();
            Thread.sleep((long) ileTankuje * litry + ControllerUstawienia.zasoby.getIleAnim());
            chron.acquire();
            ControllerUstawienia.zasoby.setTankujaceSamochody(ControllerUstawienia.zasoby.getTankujaceSamochody()-1);
            chron.release();

            kasy.acquire();
            for (int i=0; i<wolnaKasa.length; i++){
                if(wolnaKasa[i]){
                    wolnaKasa[i]=false;
                    kasa=i;
                    break;
                }
            }
            zajecieKasy(id,kasa);
            Thread.sleep(ilePlaci + ControllerUstawienia.zasoby.getIleAnim());
            wyjazd(id,kasa,stanowisko);
            Aplikacja.samochody.remove(Samochod.this);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            stacja.release();
            Thread.currentThread().stop();
        }
    }
}
