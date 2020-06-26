import java.util.ArrayList;
public class Sted {
    protected Sted nesteSted = null; // peker til neste sted i lenken
    protected String beskrivelse; // holder paa beskrivelser
    protected SkattKiste kiste = null; // holder paa kisten til stedet

    public Sted(String b){ // konstruktoer
        beskrivelse = b;
    }

    public void setKiste(ArrayList<String> muligeGjenstander){ // oppretter kista paa stedet
        kiste = new SkattKiste();
        kiste.setFyll(muligeGjenstander);
    }

    public void setVidere(Sted nySted){
        nesteSted = nySted;       
    }

    public SkattKiste getKiste(){ // returnerer kista
        return kiste;
    }
    public String[] getVeiAlternativer(){ // samme her metoden muliggjør polymofisk bruk derfor den returnerer en stringarray
        return new String[]{"1: Fremover"};
    }
    public Sted getNesteSted(int index){ //jeg har lagt til en parameter som kun brukes av VeiValgSted, dette gjorde jeg saa polymorfien skulle kunnebrukes for global metodekall
        // returnerer neste sted
        return nesteSted;
    }

   
    @Override
    public String toString(){ // returnerer beskrivelsen av stedet
        return beskrivelse;
    }
}