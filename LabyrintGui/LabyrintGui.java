//NB guien er ikke optimalisert, og er VELDIG treg på grunn av at jeg bruker bilder :) NB
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import javafx.scene.paint.*;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.layout.StackPane;
import javafx.scene.image.*;
import javafx.scene.transform.Rotate;
import javafx.scene.SnapshotParameters;
public class LabyrintGui extends Application{
    private Labyrint nyLab = null;
    private Lenkeliste<String> utvei = null; // utveiene
    private KnappRektangel[][] nettReferanse = null; // en 2d representasjon knappene
    private int printet = 0;
    private Text utveier = new Text("");
    // Bildene som brukes
    private Image stein = new Image("stonebrick.png");
    private Image bein = new Image("foetter.png");
    private Image dirt = new Image("dirtBlock.png");
    //
    private final Lock utveislaas = new ReentrantLock();
    public class KlikkBehandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e){ // behandler for knappene i labyrinten
            if (utvei != null && utvei.stoerrelse() != 0){ // resetter labyrinten ved nytt trykk
                 setRoute(utvei.hent(printet), 1);
                 printet = 0;
             }
            KnappRektangel knapp = (KnappRektangel) e.getSource(); // henter knapp så koordinatene kan brukes
            utveislaas.lock(); // laaser instansvariablene
            try {
                utvei = nyLab.finnUtveiFra(knapp.retKol(), knapp.retRad());
            } finally {
                utveislaas.unlock(); // laaser opp
            }
            if(utvei.stoerrelse() != 0){ // printer størrelsen til abyrinten
                setRoute(utvei.hent(0), 0);
                utveier.setText("Antall utveier fra ruta er: " +  utvei.stoerrelse());
            }
        }
    }
    public class NesteKnapp implements EventHandler<ActionEvent> { // viser neste utvei
    @Override
    public void handle(ActionEvent e) {
        if (utvei.stoerrelse() != 0) {
            if(printet == utvei.stoerrelse()-1){
                setRoute(utvei.hent(printet), 1);
                printet = 0;
                setRoute(utvei.hent(printet), 0);
            }else{
                setRoute(utvei.hent(printet), 1);
                printet++;
                setRoute(utvei.hent(printet), 0);
                }
            }
        }
    }
    public void setRoute(String ruta, int kommando){  // fargelegger ruta ut (NB er den virkelig trege metoden)
        // pga at det er mange bilder som skal brukes NB men jeg ville ha det litt gøy
        String rute = ruta;
        String[] sti = rute.split(":");
        int teller = 0;
        int nesteKord = 0;

        Image rotertBilde = null;
        for(String s : sti ){
            nesteKord++; // her finner vi vilke koordinater som skal fargelegges
            String[] kord = s.split(",");
            int rad = Integer.parseInt(kord[0]);
            int kol = Integer.parseInt(kord[1]);
            if(nesteKord < sti.length){
            String[] kordN = sti[nesteKord].split(",");
            int nesteRad = Integer.parseInt(kordN[0]);
            int nesteKol = Integer.parseInt(kordN[1]);
            if(nesteRad > rad){ // disse ifsjekkene er for å rotere foettene
                ImageView nyBilde = new ImageView(bein);
                nyBilde.setRotate(180);
                rotertBilde = nyBilde.snapshot(new SnapshotParameters(), null);
            }else if(nesteRad < rad){
                rotertBilde = bein;
            }else if(nesteKol > kol){
                ImageView nyBilde = new ImageView(bein);
                nyBilde.setRotate(90);
                rotertBilde = nyBilde.snapshot(new SnapshotParameters(), null);
            }else if(nesteKol < kol){
                ImageView nyBilde = new ImageView(bein);
                nyBilde.setRotate(270);
                rotertBilde = nyBilde.snapshot(new SnapshotParameters(), null);
            }
            }

            if (kommando == 0)nettReferanse[rad][kol].setFill(new ImagePattern(rotertBilde));
            else if(kommando == 1) nettReferanse[rad][kol].setFill(new ImagePattern(dirt));
        }

    }
    public void start(Stage theater){

    KlikkBehandler click = new KlikkBehandler();
    File file = new FileChooser().showOpenDialog(theater); // velger fila
        try{
        nyLab = Labyrint.lesFraFil(file);
        }catch(FileNotFoundException e) {
            System.out.println("brøt sammen her");
            System.exit(1);
        }
        int rader = nyLab.hentRader(); // dimensjonene på labyrinten
        int kolonner = nyLab.hentKolonner();
        Rute [][] labyrinten = nyLab.hentLab();
        KnappRektangel[][] nettRef = new KnappRektangel[rader][kolonner]; // oppretter rutenett for knappene til lagring
        GridPane rutenett = new GridPane();
        rutenett.setPrefSize(800,800);  // setter størrelse på labyrint
        rutenett.setGridLinesVisible(true);
        for(Rute[] r: labyrinten){
            for(Rute l: r){
                KnappRektangel e = new KnappRektangel( l.hentRad(), l.hentKol());
                // oppretter knappene og skalerer dem etter hvor mange som skal være
                e.setWidth(800/kolonner);
                e.setHeight(800/rader);
                e.setOnMouseClicked(click);
                if (l instanceof SvartRute){
                    e.setFill(new ImagePattern(stein));
                    // legger dem inn og saa settes mouseclicked actionen
                }else e.setFill(new ImagePattern(dirt));
                nettRef[e.retRad()] [e.retKol()] = e;
                rutenett.add(e, l.hentKol(), l.hentRad());
            }
        }
        // de siste linjene setter alt sammen
        utveier.setLayoutX(150);
        utveier.setLayoutY(830);
        utveier.setFont(new Font(20));
        nettReferanse = nettRef;
        Button neste = new Button("Neste");
        neste.setLayoutX(10);
        neste.setLayoutY(800);
        NesteKnapp nesteKnappen = new NesteKnapp();
        neste.setOnAction(nesteKnappen);
        Pane kulisse = new Pane();
        kulisse.setPrefSize(800, 850);
        kulisse.getChildren().add(neste);
        kulisse.getChildren().add(rutenett);
        kulisse.getChildren().add(utveier);
        Scene scene = new Scene(kulisse);
        theater.setTitle("Labyrint");
        theater.setScene(scene);
        theater.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
