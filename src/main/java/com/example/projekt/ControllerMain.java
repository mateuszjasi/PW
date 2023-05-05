package com.example.projekt;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerMain {
    public Button btnStart;
    public Button btnStop;
    public Button btnSettings;
    public Pane pane;
    Zasoby zasoby;
    static ArrayList<Rectangle> kolejkaList;
    static ArrayList<Rectangle> stanowiskaList;
    static ArrayList<Rectangle> kasyList;
    ArrayList<Text> textkolejkaList;
    static Text[] textPozostalePaliwoList;

    Aplikacja aplikacja;

    static int bokKwadratu;
    static int wysKasy;
    static int szerKasy;

    static boolean flagConfig=false;

    static Button btnStartS;
    static Button btnStopS;
    static Button btnSettingS;

    public void initialize(){
        btnStartS=btnStart;
        btnStopS=btnStop;
        btnSettingS=btnSettings;
    }

    public void startSymulation(Event actionEvent) throws IOException {
        if (flagConfig) {
            btnStart.setDisable(true);
            btnStop.setDisable(false);
            btnSettings.setDisable(true);
            aplikacja = new Aplikacja(pane);
            aplikacja.start();
        } else zasoby(actionEvent);
    }

    public void stopSymulation(Event actionEvent) {
        btnStart.setDisable(false);
        btnStop.setDisable(true);
        btnSettings.setDisable(false);
        flagConfig=false;
        aplikacja.interrupt();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Koniec Symulacji");
        alert.setHeaderText(null);
        alert.setContentText("Symulacja zakończona!");
        alert.show();

        final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
        runnable.run();
    }

    public void zasoby(Event actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("ustawienia.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 510);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Ustawienia");
        stage.setScene(scene);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                pane.getChildren().clear();
                zasoby=ControllerUstawienia.zasoby;
                bokKwadratu = 75;
                int odstep = 10;
                Rectangle rec;

                //kolejka
                kolejkaList = new ArrayList<>();
                textkolejkaList = new ArrayList<>();
                double startingPointX = pane.getWidth() / 2 - zasoby.getN() * (bokKwadratu+10) / 2 - (zasoby.getN() - 1) * odstep / 2;
                double startingPointY = 20;
                for (int i = 0; i < zasoby.getN(); i++) {
                    rec = new Rectangle(startingPointX + i * odstep + i * (bokKwadratu+10), startingPointY, (bokKwadratu+10), (bokKwadratu+10));
                    kolejkaList.add(rec);
                }
                for (Rectangle x : kolejkaList) {
                    x.setFill(Color.LIGHTBLUE);
                    x.setStrokeType(StrokeType.INSIDE);
                    x.setStrokeWidth(1);
                    x.setStroke(Color.BLACK);
                    pane.getChildren().add(x);
                }

                //stanowiska
                stanowiskaList = new ArrayList<>();
                startingPointX=pane.getWidth()/2-(float)zasoby.getM()*(bokKwadratu+10)/2-(float)(zasoby.getM()-1)*odstep/2;
                startingPointY=pane.getHeight()/2-(float)(bokKwadratu+10)/2;
                for(int i=0; i<zasoby.getM(); i++){
                    rec=new Rectangle(startingPointX+i*odstep+i*(bokKwadratu+10), startingPointY, (bokKwadratu+10), (bokKwadratu+10));
                    stanowiskaList.add(rec);
                }
                for(Rectangle x : stanowiskaList){
                    x.setFill(Color.WHITE);
                    x.setStrokeType(StrokeType.INSIDE);
                    x.setStrokeWidth(1);
                    x.setStroke(Color.BLACK);

                    pane.getChildren().add(x);
                }

                //kasy
                wysKasy=75;
                szerKasy=50;
                kasyList = new ArrayList<>();
                startingPointX=pane.getWidth()-szerKasy;
                startingPointY=pane.getHeight()/2-zasoby.getK()*wysKasy/2-(zasoby.getK()-1)*odstep/2;
                for(int i=0; i<zasoby.getK(); i++){
                    rec=new Rectangle(startingPointX, startingPointY+i*odstep+i*wysKasy, szerKasy, wysKasy);
                    kasyList.add(rec);
                }
                for(Rectangle x : kasyList){
                    x.setFill(Color.ORANGERED);
                    x.setStrokeType(StrokeType.INSIDE);
                    x.setStrokeWidth(1);
                    x.setStroke(Color.BLACK);
                    pane.getChildren().add(x);
                }

                //pozostale paliwa
                Text text1;
                int max=0;
                for(String x : zasoby.getRodzajePaliw()) {
                    if (max < x.length()) max = x.length();
                }
                Text text;
                textPozostalePaliwoList=new Text[zasoby.getRodzajePaliw().length];
                for(int i=0; i<zasoby.getRodzajePaliw().length; i++){
                    text=new Text(zasoby.getRodzajePaliw()[i]+":");
                    text.setFont(Font.font(16));
                    text.setX(3);
                    text.setY(pane.getHeight()-10-i*text.getBoundsInLocal().getHeight());

                    text1=new Text(String.valueOf(zasoby.getPozostalePaliwo(i)));
                    text1.setFont(Font.font(16));
                    text1.setX(max*16);
                    text1.setY(text.getY());
                    text1.setAccessibleText(text.getText().replace(":",""));

                    pane.getChildren().add(text);
                    pane.getChildren().add(text1);
                    textPozostalePaliwoList[i]=text1;
                }
                flagConfig=true;
            }
        });
        stage.show();
    }
}