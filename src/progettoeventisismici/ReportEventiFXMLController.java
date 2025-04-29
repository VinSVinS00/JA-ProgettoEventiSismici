/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progettoeventisismici;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ReportEventiFXMLController implements Initializable {

    @FXML
    private TableView<Evento> table;
    @FXML
    private TableColumn<Evento, String> colonnaData;
    @FXML
    private TableColumn<Evento, Double> colonnaMagnitudo;
    @FXML
    private TableColumn<Evento, String> colonnaLuogo;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private TextField txfLimiteRis;
    @FXML
    private Button caricaBtn;
    @FXML
    private ProgressBar progrBar;
    
    private ObservableList<Evento> eventi;
    Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colonnaData.setCellValueFactory(new PropertyValueFactory("time"));
        colonnaMagnitudo.setCellValueFactory(new PropertyValueFactory("magnitude"));
        colonnaLuogo.setCellValueFactory(new PropertyValueFactory("eventLocationName"));
        
        eventi = FXCollections.observableArrayList();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        MenuItem stampaItem = new MenuItem("Salva selezione");
        
        stampaItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Evento> selezionati = table.getSelectionModel().getSelectedItems();
                try{
                    FileChooser fc = new FileChooser();
                    fc.setTitle("Scegli dove salvare");
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
                    fc.setInitialFileName("eventi.csv");
                    Stage stage = (Stage) table.getScene().getWindow();
                    File file = fc.showSaveDialog(stage);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    for(Evento e : selezionati){
                        writer.write(e.getTime() + " | " + e.getMagnitude() + " | " + e.getEventLocationName() + "\n");
                    }
                    writer.close();
                    
                } catch(IOException ex){
                    
                }
            }
        });
        
        ContextMenu contextMenu = new ContextMenu(stampaItem);
        table.setContextMenu(contextMenu);

        
    }    

    @FXML
    private void caricaDati(ActionEvent event) {
        
        eventi.clear();
        
        if(dateStart.getValue() == null || dateEnd.getValue() == null){
            alert.setHeaderText("Errore");
            alert.setContentText("Selezionare una data di inizio e una data fine");
            alert.showAndWait();
            return;
        }
        
        if(txfLimiteRis.getText().isEmpty()){
            txfLimiteRis.setText("1000");
        }
        
        try {
            /* una sorta di connessione al sito tramite l'oggetto URL e un casting in un tipo con metodi utili */
            String urlSito = "https://webservices.ingv.it/fdsnws/event/1/query?starttime=2020-11-18T00%3A00%3A00&endtime=2020-11-25T23%3A59%3A59&minmag=2&maxmag=10&mindepth=-10&maxdepth=1000&minlat=-90&maxlat=90&minlon=-180&maxlon=180&minversion=100&orderby=time-asc&format=text&limit=10000";
            
            URL url = new URL(urlSito);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            reader.readLine(); /*skip prima riga che sono i tipi di dati da leggere*/
            
            List<String> righe = new ArrayList<>();
            while((line = reader.readLine()) != null){
                righe.add(line);
            }
            
            int righeTot = righe.size();
            int righeLette = 0;
            
            for(String riga : righe){
                
                String[] campi = riga.split("\\|");
                
                String evento = campi[0];
                String time = campi[1];
                double latitude = Double.parseDouble(campi[2]);
                double longitude = Double.parseDouble(campi[3]);
                double depth = Double.parseDouble(campi[4]);
                String author = campi[5];
                String catalog = campi[6];
                String contributor = campi[7];
                String contributorID = campi[8];
                String magType = campi[9];
                double magnitude = Double.parseDouble(campi[10]);
                String magAuthor = campi[11];
                String eventLocationName = campi[12];
                String eventType = campi[13];
                
                LocalDateTime dataOra = LocalDateTime.parse(time);
                LocalDate soloData = dataOra.toLocalDate();
                
                
                
                if(eventi.size() < Integer.parseInt(txfLimiteRis.getText())){
                    
                    if(soloData.isEqual(dateStart.getValue()) || soloData.isEqual(dateEnd.getValue())){
                        eventi.add(new Evento(evento,dataOra,latitude,longitude,depth,author,catalog,contributor,contributorID,magType,magnitude,magAuthor,eventLocationName,eventType));
                        righeLette++;
                    } else if(soloData.isAfter(dateStart.getValue())){
                        if(soloData.isBefore(dateEnd.getValue())){
                            eventi.add(new Evento(evento,dataOra,latitude,longitude,depth,author,catalog,contributor,contributorID,magType,magnitude,magAuthor,eventLocationName,eventType));
                            righeLette++;
                        }
                    }
                }
                
                
                progrBar.setProgress((double) righeLette / righeTot);
                
            }
            
            table.setItems(eventi);
            
        } catch(IOException ex) {
            
        }
    }
    
}

//#EventID|Time|Latitude|Longitude|Depth/Km|Author|Catalog|Contributor|ContributorID|MagType|Magnitude|MagAuthor|EventLocationName