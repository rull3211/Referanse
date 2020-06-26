import javafx.application.Application;
import javafx.application.Platform;
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
 // klasse for min overskrevne rektangel som jeg har lagret koordinatene i
class KnappRektangel extends Rectangle{
    private int rad;
    private int kol;
    public KnappRektangel(int rad, int kol){
        super();
        this.rad = rad;
        this.kol = kol;
    }

    public int retRad(){
        return rad;
    }
    public int retKol(){
        return kol;
    }

}
