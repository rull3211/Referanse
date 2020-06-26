import java.util.Random;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.text.Text;
import java.util.ArrayList;
import javafx.scene.input.MouseButton;
import javafx.scene.image.*;
public class MineSweeper extends Application{
    int bomber = 0;
    int flagg = 0;
    int bomberFlagget = 0;
    boolean victory = false;
    boolean loose = false;
    Text state = new Text();
    ImageView flagget = new ImageView(new Image("norskflagg.png"));
    public static void main(String[] args) {
            launch(args);
        }
    int rader = 20;
    int kolonner = 20;
    IkkeBombe[][] repButtons = new IkkeBombe[rader][kolonner];
    Random bombelager = new Random();
    ArrayList<Bombe> bombene = new ArrayList<>();
    public MineSweeper(){}
    public class Click implements EventHandler<MouseEvent> { // viser neste utvei
    @Override
    public void handle(MouseEvent e) {
        if (!loose){
            MouseButton button = e.getButton();
            IkkeBombe knapp = (IkkeBombe) e.getSource();
            if(button == MouseButton.PRIMARY && !knapp.getFlagget()){
            knapp.setText(knapp.getTegn());
            knapp.setStyle(knapp.getColor());
            ArrayList<IkkeBombe> trygge = knapp.sjekkNaboer();
            if(knapp.getTegn().equals("0"))
            klikkNaboer(trygge);
            if (knapp instanceof Bombe){
                state.setText("Du tapte! :(");
                lossSequence();
                loose = true;
            }
        }
            if(button == MouseButton.SECONDARY && flagg <= bomber && !knapp.getAapnet()){
            knapp.flagg();
            if (knapp.getFlagget()){
            flagget.setFitWidth(40);
            flagget.setFitHeight(40);
            knapp.setStyle("-fx-background-color: grey;");
            flagg++;
            }
            if (!knapp.getFlagget()){
            knapp.setText("");
            knapp.setStyle("-fx-background-color: white;");
            flagg--;
            }
            if (knapp instanceof Bombe && knapp.getFlagget()) bomberFlagget++;
            else if (knapp instanceof Bombe && !knapp.getFlagget()) bomberFlagget--;
            if (bomberFlagget == bomber)state.setText("Gratulerer Du vant! :D");
                }
            }
        }
        public void klikkNaboer(ArrayList<IkkeBombe> trygge){
            for (IkkeBombe k : trygge ) {
                k.setText(k.getTegn());
                String farge = k.getColor();
                k.setStyle(farge);
            }
            for (IkkeBombe k :trygge ) {
                if(k.getTegn().equals("0")){
                klikkNaboer(k.sjekkNaboer());
                }
            }
        }
    }
    public void lossSequence(){
        for (Bombe b : bombene ) {
            b.setText(b.getTegn());
        }
    }
    public void start(Stage theater){
        GridPane brett = new GridPane();
        brett.relocate(0, 100);
        brett.setGridLinesVisible(true);
        Click click = new Click();
        for(int r = 0; r < rader; r++){
            for(int k = 0; k < kolonner; k++){
                if(bombelager.nextInt(9) == 1){
                    Bombe knapp = new Bombe(k, r);
                    knapp.setMinSize(40, 40);
                    knapp.setOnMouseClicked(click);
                    repButtons[r][k] = knapp;
                    brett.add(knapp, r, k);
                    knapp.setMaxSize(40, 40);
                    bombene.add(knapp);
                    bomber++;

                }else{
                    IkkeBombe knapp = new IkkeBombe(k, r);
                    repButtons[r][k] = knapp;
                    brett.add(knapp, r, k);
                    knapp.setMinSize(40, 40);
                    knapp.setMaxSize(40, 40);
                    knapp.setOnMouseClicked(click);
                }
            }
        }
        for(IkkeBombe[] l: repButtons){
            for(IkkeBombe b: l){
                b.setBrett(repButtons);
            }
        }
        for(IkkeBombe[] l: repButtons){
            for(IkkeBombe b: l){
                b.setNabo();
            }
        }
        for(IkkeBombe[] l: repButtons){
            for(IkkeBombe b: l){
                b.setTegn();
            }
        }
        state.relocate(350, 20);
        Pane kulisse = new Pane();
        kulisse.setPrefSize(800, 1000);
        kulisse.getChildren().add(state);
        kulisse.getChildren().add(brett);
        Scene scene = new Scene(kulisse);
        theater.setTitle("MineSweeper");
        theater.setScene(scene);
        theater.show();
    }
    public void Bflagget(){
        bomberFlagget++;
    }

}
