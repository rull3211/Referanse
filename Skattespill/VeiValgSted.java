public class VeiValgSted extends Sted{
    protected Sted[] naboSteder = new Sted[4]; // peker til de neste stedene i lenken
    protected String[] veiAlternativer = new String[]{"1: Frem", "2: Tilbake", "3: Hoyre", "4: Venstre"};
    // paabygg Sted forrigeSted = null;
    protected String beskrivelse; // holder paa beskrivelser
    protected SkattKiste kiste = null; // holder paa kisten til stedet
    protected int plass = 0; // en hjelpevariabel for setVidere
    public VeiValgSted(String b){
        super(b);
    }
    public String[] getVeiAlternativer(){
        return veiAlternativer;
    }
    public Sted getNesteSted(int index) {
        return naboSteder[index];
    }
    @Override
    public void setVidere(Sted nySted){ // set videre setter paa plass alle stedene i naboSteder
        naboSteder[plass] = nySted;       
        plass++;
    }
    public int getPlass(){
        return plass;
    }
}