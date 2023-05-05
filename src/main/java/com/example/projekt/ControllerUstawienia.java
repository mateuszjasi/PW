package com.example.projekt;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.Arrays;

public class ControllerUstawienia {
    public TextField tfM, tfK, tfN, tfIleUzupelnia, tfIleTankuje, tfIlePlaci, tfMaxPaliwa, tfRodzajePaliw, tfIleAnim, tfAuta;
    public static Zasoby zasoby = new Zasoby();
    public void initialize(){
        initializeSettings();
    }

    private void initializeSettings(){
        tfAuta.setText(String.valueOf(zasoby.getAuta()));
        tfM.setText(String.valueOf(zasoby.getM()));
        tfK.setText(String.valueOf(zasoby.getK()));
        tfN.setText(String.valueOf(zasoby.getN()));
        tfIleUzupelnia.setText(String.valueOf(zasoby.getIleUzupelnia()));
        tfIleTankuje.setText(String.valueOf(zasoby.getIleTankuje()));
        tfIlePlaci.setText(String.valueOf(zasoby.getIlePlaci()));
        tfMaxPaliwa.setText(String.valueOf(zasoby.getMaxPaliwa()));
        tfIleAnim.setText(String.valueOf(zasoby.getIleAnim()));
        tfRodzajePaliw.setText(Arrays.toString(zasoby.getRodzajePaliw()).replaceAll("[\\[\\]]",""));
    }

    private void saveSetting(){
        zasoby.setAuta(Integer.parseInt(tfAuta.getText()));
        zasoby.setM(Integer.parseInt(tfM.getText()));
        zasoby.setK(Integer.parseInt(tfK.getText()));
        zasoby.setN(Integer.parseInt(tfN.getText()));
        zasoby.setIleUzupelnia(Integer.parseInt(tfIleUzupelnia.getText()));
        zasoby.setIleTankuje(Integer.parseInt(tfIleTankuje.getText()));
        zasoby.setIlePlaci(Integer.parseInt(tfIlePlaci.getText()));
        zasoby.setMaxPaliwa(Integer.parseInt(tfMaxPaliwa.getText()));
        zasoby.setIleAnim(Integer.parseInt(tfIleAnim.getText()));
        zasoby.setRodzajePaliw(tfRodzajePaliw.getText().replaceAll(" ","").split(","));
    }

    public void setSettings(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        saveSetting();
        thisStage.close();
    }

    public static void writeXml(Document document, OutputStream outputStream) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(outputStream);
        transformer.transform(source, result);
    }

    public void saveToFileSettings(ActionEvent actionEvent) throws IOException, ParserConfigurationException, TransformerException {
        saveSetting();

        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz ustawienia");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Ustawienia", "*.xml"));
        File file = fileChooser.showSaveDialog(thisStage);
        if(file==null) return;

        System.out.println(file.getAbsolutePath());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement("stacja");
        document.appendChild(rootElement);
        Element Auta = document.createElement("Auta");
        Auta.setTextContent(String.valueOf(zasoby.getAuta()));
        rootElement.appendChild(Auta);
        Element M = document.createElement("M");
        M.setTextContent(String.valueOf(zasoby.getM()));
        rootElement.appendChild(M);
        Element K = document.createElement("K");
        K.setTextContent(String.valueOf(zasoby.getK()));
        rootElement.appendChild(K);
        Element N = document.createElement("N");
        N.setTextContent(String.valueOf(zasoby.getN()));
        rootElement.appendChild(N);
        Element IleUzupelnia = document.createElement("IleUzupelnia");
        IleUzupelnia.setTextContent(String.valueOf(zasoby.getIleUzupelnia()));
        rootElement.appendChild(IleUzupelnia);
        Element IleTankuje = document.createElement("IleTankuje");
        IleTankuje.setTextContent(String.valueOf(zasoby.getIleTankuje()));
        rootElement.appendChild(IleTankuje);
        Element IlePlaci = document.createElement("IlePlaci");
        IlePlaci.setTextContent(String.valueOf(zasoby.getIlePlaci()));
        rootElement.appendChild(IlePlaci);
        Element MaxPaliwa = document.createElement("MaxPaliwa");
        MaxPaliwa.setTextContent(String.valueOf(zasoby.getMaxPaliwa()));
        rootElement.appendChild(MaxPaliwa);
        Element IleAnim = document.createElement("ileAnim");
        IleAnim.setTextContent(String.valueOf(zasoby.getIleAnim()));
        rootElement.appendChild(IleAnim);
        Element RodzajePaliw = document.createElement("RodzajePaliw");
        RodzajePaliw.setTextContent(Arrays.toString(zasoby.getRodzajePaliw()));
        rootElement.appendChild(RodzajePaliw);

        FileOutputStream outputStream = new FileOutputStream(file);
        writeXml(document, outputStream);

        thisStage.close();
    }

    public void loadFromFileSettings(ActionEvent actionEvent) throws ParserConfigurationException, IOException, SAXException {
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj ustawienia");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Ustawienia", "*.xml"));
        File file = fileChooser.showOpenDialog(thisStage);

        if(file==null) return;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        zasoby.setAuta(Integer.parseInt(document.getElementsByTagName("Auta").item(0).getTextContent()));
        zasoby.setM(Integer.parseInt(document.getElementsByTagName("M").item(0).getTextContent()));
        zasoby.setK(Integer.parseInt(document.getElementsByTagName("K").item(0).getTextContent()));
        zasoby.setN(Integer.parseInt(document.getElementsByTagName("N").item(0).getTextContent()));
        zasoby.setIleUzupelnia(Integer.parseInt(document.getElementsByTagName("IleUzupelnia").item(0).getTextContent()));
        zasoby.setIleTankuje(Integer.parseInt(document.getElementsByTagName("IleTankuje").item(0).getTextContent()));
        zasoby.setIlePlaci(Integer.parseInt(document.getElementsByTagName("IlePlaci").item(0).getTextContent()));
        zasoby.setMaxPaliwa(Integer.parseInt(document.getElementsByTagName("MaxPaliwa").item(0).getTextContent()));
        zasoby.setIleAnim(Integer.parseInt(document.getElementsByTagName("ileAnim").item(0).getTextContent()));
        zasoby.setRodzajePaliw(document.getElementsByTagName("RodzajePaliw").item(0).getTextContent().replaceAll("[\\[\\]]","").replaceAll(" ","").split(","));
        initializeSettings();

        thisStage.close();
    }

    public void validate(KeyEvent keyEvent) {
        char input=keyEvent.getCharacter().charAt(0);
        Object source = keyEvent.getSource();
        if(Character.isLetter(input)){
            String Oldtext = ((TextField)source).getText();
            String NewText = Oldtext.replaceAll("[^\\d]", "");
            tfM.setText(NewText);
            tfM.positionCaret(NewText.length());
        }
    }
}
