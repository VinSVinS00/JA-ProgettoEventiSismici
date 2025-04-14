/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progettoeventisismici;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author vitol
 */
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colonnaData.setCellValueFactory(new PropertyValueFactory("time"));
        colonnaMagnitudo.setCellValueFactory(new PropertyValueFactory("magnitude"));
        colonnaLuogo.setCellValueFactory(new PropertyValueFactory("EventLocationName"));
        
        eventi = FXCollections.observableArrayList();
        
    }    

    @FXML
    private void caricaDati(ActionEvent event) {
        
        try{
            /* una sorta di connessione al sito tramite l'oggetto URL e un casting in un tipo con metodi utili */
            String urlSito = "https://webservices.ingv.it/fdsnws/event/1/query?starttime=2020-11-18T00%3A00%3A00&endtime=2020-11-25T23%3A59%3A59&minmag=2&maxmag=10&mindepth=-10&maxdepth=1000&minlat=-90&maxlat=90&minlon=-180&maxlon=180&minversion=100&orderby=time-asc&format=text&limit=10000";
            URL url = new URL(urlSito);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            reader.readLine(); /*skip prima riga che sono i tipi di dati da leggere*/
            
            while((line = reader.readLine()) != null){
                
                String[] campi = line.split("\\|");
                Scanner s = new Scanner(line);
                
                String evento = s.next();
                String time = s.next();
                double latitude = s.nextDouble();
                double longitude = s.nextDouble();
                double depth = s.nextDouble();
                String author = s.next();
                String catalog = s.next();
                String contributor = s.next();
                String contributorID = s.next();
                String magType = s.next();
                double magnitude = s.nextDouble();
                String magAuthor = s.next();
                String eventLocationName = s.next();
                String eventType = s.next();
                
                eventi.add(new Evento(evento,LocalDateTime.parse(time),latitude,longitude,depth,author,catalog,contributor,contributorID,magType,magnitude,magAuthor,eventLocationName,eventType));
            }
            
        } catch(IOException ex) {
            
        }
    }
    
    
}

//#EventID|Time|Latitude|Longitude|Depth/Km|Author|Catalog|Contributor|ContributorID|MagType|Magnitude|MagAuthor|EventLocationName