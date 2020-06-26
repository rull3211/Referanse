import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.text.Text;
import javafx.application.Platform;

public class Spill  extends Application implements Runnable{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run(){
        try {
            Thread.sleep(5000);
            Platform.exit();
        } catch (InterruptedException e) {
            
        }
    }
    public void start(Stage theater){
        StartSpill spill = new StartSpill();
        spill.startSpill();
        Spiller[] spillere = spill.getSpillere();

        Spiller vinneren = null;
        int poeng = -1;
        for (Spiller spiller : spillere) {
            if (spiller.getPenger() > poeng) {
                vinneren = spiller;
                poeng = spiller.getPenger();
            }
        }
        String vinner = vinneren.getNavn();
        int plass = 60;
        
        
        Text score3 = new Text(vinner + " er vinneren." );
        Pane kulisse = new Pane();
        Button avslutt = new Button("Avslutt");
        Avslutter avslutter = new Avslutter();
        avslutt.setOnAction(avslutter);
        for (Spiller spiller : spillere) {
            Text t = new Text(spiller.getNavn() + " hadde Poeng" + spiller.getPenger());
            t.relocate(20, plass);
            kulisse.getChildren().add(t);
            plass += 20;
        }
        score3.relocate(20, plass);
        avslutt.relocate(20, 20);
        kulisse.setPrefSize(300, (spillere.length * 40) + 40);
        kulisse.getChildren().add(score3);
        kulisse.getChildren().add(avslutt);
        Scene scene = new Scene(kulisse);
        theater.setTitle("Scoreboard");
        theater.setScene(scene);
        Thread avsluttTraad = new Thread(this);
        avsluttTraad.start();
        theater.show();
        
    }
    class Avslutter implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Platform.exit();
            }
        }
        
    
}