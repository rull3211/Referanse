import java.util.ArrayList;
/* jeg subklasset kiste for aa holde sekkefunksjonalitet adskilt fra vanlig kiste*/
public class Sekk extends SkattKiste{
    protected static final int sekkStoerrelse = 5; // konstant som bestemmer stoerrelse paa sekk
    protected int antall = 0;
    
    public void plukkGjenstand(Gjenstand tingen){
        antall++; // metode for aa gi bruker mulighet til aa legge ting tilbake
        kiste.add(tingen);
    }
    public int taUt(int ting, SkattKiste selgTil){  // overloadet ta ut som fjerner valgt ting fra sekk og selger til kista så returneres poengene
        Gjenstand tilsalg = kiste.get(ting);
        kiste.remove(ting);
        antall--;
        return selgTil.selg(tilsalg);
    }

    public ArrayList<Gjenstand> getTingISekk(){ // henter sekken
        return kiste;
    }
    @Override
    public String toString(){
        String streng = "";
        for(int i = 0; i < kiste.size(); i++){
            streng += i + ": " + kiste.get(i) + "\n";
        }
        return streng;
    }

    public int getAntallISekk(){ // antall ting i sekken
        return antall;
    }
}