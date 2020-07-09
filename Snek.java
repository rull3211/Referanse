import java.util.Random;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;
import javafx.scene.input.KeyCode;
public class Snek extends Application{
    GridPane brett = new GridPane();
    Rute[][] brettRep = new Rute[31][31];
    ArrayList<Rute> dSnek = new ArrayList<>();
    Rute head = null;
    Rute fruit = null;
    Random rng = new Random();
    boolean finn = false;
    int side = -1;
    int vert = 0; 
    @Override
    public void start(Stage theater){
        for (int r = 0; r < 31; r ++) {
            for (int re = 0; re < 31; re++) {
                Rute neu = new Rute(r, re);
                neu.setWidth(30);
                neu.setHeight(30);
                neu.setFill(Color.BLACK);
                brettRep[r][re] = neu;
                brett.add(neu, r, re);

            }
        }
        head = brettRep[15][15];
        fruit = brettRep[rng.nextInt(31)][rng.nextInt(31)];
        fruit.setFill(Color.RED);
        head.setFill(Color.GREEN);

        Pane kulisse = new Pane();
        kulisse.getChildren().add(brett);
        Scene scene = new Scene(kulisse);
        theater.setTitle("Snek");
        theater.setScene(scene);


        Thread game = new Thread(() -> {
            while (!finn) {
                move();
                try {
                    Thread.sleep(200);
                } catch (Exception ex) {
                }
            }
        });
        scene.setOnKeyPressed(event -> {
            KeyCode k = event.getCode();
            switch (k) {
                case W:
                    vert = -1;
                    side = 0;
                    break;
                case A:
                    vert = 0;
                    side = -1;
                    break;
                case S:
                    vert = 1;
                    side = 0;
                    break;
                case D:
                    vert = 0;
                    side = 1;
                    break;
            }
        });

        game.start();
        theater.show();
    }
    public void move(){
        if(head.equals(fruit)){
            dSnek.add(brettRep[head.getR()-side][head.getK()-vert]);
            fruit = brettRep[rng.nextInt(30)][rng.nextInt(30)];
            fruit.setFill(Color.RED);
        }
        head.setFill(Color.BLACK);
        try {
            head = brettRep[head.getR() + side ][head.getK() + vert];
            if(dSnek.size()> 0){
                dSnek.add(0, brettRep[head.getR()-side][head.getK()-vert]);
                dSnek.get(dSnek.size()-1).setFill(Color.BLACK);
                dSnek.remove(dSnek.size()-1);

            }
            color();
            if(dSnek.contains(head)){
                 finn = true;
                 loose();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            finn = true;
            loose();
        }
    }
    public void color(){
        head.setFill(Color.GREEN);
        for (Rute r : dSnek) {
            r.setFill(Color.BLUE);
        }
    }
    public void loose(){
        head.setFill(Color.BLACK);
        for (Rute r : dSnek) {
            r.setFill(Color.BLACK);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
class Rute extends Rectangle{
    int r;
    int k;
    public Rute(int r, int k){
        super();
        this.r = r;
        this.k = k;
    }
    public int getK(){
        return k;
    }
    public int getR(){
        return r;
    }
}