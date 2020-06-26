import java.util.ArrayList;
import javafx.scene.control.Button;

class IkkeBombe extends Button{
    int kolonne = 0;
    int rad = 0;
    ArrayList<IkkeBombe> naboer = new ArrayList<>();
    IkkeBombe [][] brett;
    String tegn = "0";
    boolean flagget = false;
    boolean aapnet = false;
    String farge;
    public IkkeBombe(int kolonne, int rad){
        super();
        this.kolonne = kolonne;
        this.rad = rad;
    }
    public void setBrett(IkkeBombe [][] brett){
        this.brett = brett;
    }
    public ArrayList<IkkeBombe> sjekkNaboer(){
        ArrayList<IkkeBombe> trygge = new ArrayList<>();
        for (IkkeBombe b : naboer) {
            if (!b.getTegn().equals("*")) {
                if(b.gyldig()){
                    trygge.add(b);
                    b.aapn();
                }
            }
        }
        return trygge;
    }
    public void setTegn(){
        int teller = 0;
        for(Button b: naboer){
            if(b instanceof Bombe)teller++;
        }
        if (teller > 0) tegn = ""+ teller;
        if (teller== 0){
            farge = "-fx-background-color: green;";
        }
        if (teller == 3) farge = "-fx-background-color: orange;";
        if (teller == 1) farge = "-fx-background-color: blue;";
        if (teller == 2) farge = "-fx-background-color: yellow;";
        if (teller >= 4) farge = "-fx-background-color: red;";
    }
    public void setNabo(){
        for(int h = -1; h < 2; h++){
            for(int b = -1; b < 2 ; b++){
                int nRad = rad + h;
                int nKol = kolonne + b;
                if ((nRad != rad)||(nKol != kolonne))
                    if(nRad >= 0 && nRad < 20)
                        if(nKol >= 0 && nKol < 20)
                            naboer.add(brett[nRad][nKol]);

            }
        }
    }
    public void flagg(){
        flagget = !flagget;
    }
    public String getTegn(){
        return tegn;
    }
    public ArrayList<IkkeBombe> getNaboer(){
        return naboer;
    }
    public void aapn(){
        aapnet = true;
    }
    public boolean gyldig(){
        if(!aapnet && !flagget) return true;
        return false;
    }
    public boolean getFlagget(){
        return flagget;
    }
    public boolean getAapnet(){
        return aapnet;
    }
    public String getColor(){
        return farge;
    }
}
