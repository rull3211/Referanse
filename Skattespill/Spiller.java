import java.util.ArrayList;
public class Spiller<T extends BrukerGrensesnitt> implements Runnable{// sier til java at T bruker grensesnitt
    protected int penger = 0;
    protected Sekk sekk = new Sekk();
    protected Sted startSted = null; // jeg var usikker paa om startsted skulle holde det aktuelle rommet saa jeg laget en ekstra variabel
    protected Sted aktuellRom = null;
    protected T grensesnitt = null; // generisk grensesnitt for aa kunne bruke baade spiller og robot som begge implementerer interface
    protected int trekkIgjen = 15;
    protected String typSpiller; // brukes til status
    protected String linje = "";
    protected StartSpill kontroll;
    protected boolean utfoert = false;
    public Spiller(Sted start, T brukergrensesnitt, String typen, StartSpill kontroller){
        startSted = start;
        aktuellRom = start;
        grensesnitt = brukergrensesnitt;
        typSpiller = typen;
        kontroll = kontroller;
        for(int i = 0; i < 60 ; i++){
            linje += "-";
            }
        }

    @Override
    public void run() { // saa lenge spilleren har trekk igjen kjoeres denne metoden
        while(trekkIgjen > 0){
            kontroll.nyTrekk(this);
        }
    }
    public void nyttTrekk(){
            
            System.out.println(linje);
            grensesnitt.giStatus(typSpiller + " sin tur " + aktuellRom + typSpiller + " har: " + penger + " poeng"); // printer status
            String[] alternativer = getAltISekk(); // oppretter alternativer for salg av gjenstander til salg
            String[] jaNei = new String[]{"1: ja", "2: nei"}; // ja nei alternativer
            int kommando = -10; // en negativ startkommando (tall som ikke brukes) for aa se om man har feil
            if(sekk.getAntallISekk() !=  5 && sekk.getAntallISekk() != 0) // dersom du har noe i sekk og har plass i sekk så faar du valget om aa gaa videre
                kommando = grensesnitt.beOmKommando("Vil" + typSpiller +  "selge noe?", alternativer);

            else if (sekk.getAntallISekk() ==  5) { // dersom sekken er full maa du selge noe (gjøres for aa balansere roboten saa den og kan vinne)
                kommando = grensesnitt.beOmKommando("Sekken  til " + typSpiller + "er full, hen maa selge noe", alternativer);
            }
            if ( sekk.getAntallISekk() == 5){ // dersom sekken er full trenger man ikke aa ta hoeyde for alternativet om aa gaa videre
                trekkIgjen--; // ifen og elseifen er for salg
                penger += sekk.taUt(kommando, aktuellRom.getKiste());
            }
            else if( kommando > 0 ){ // dersom kommando er mer enn 0 og du ogsaa har valgmulighet om aa gaa videre
                trekkIgjen--; 
                penger += sekk.taUt(kommando -1, aktuellRom.getKiste()); // -1 brukes for aa faa rett indeks i sekken ( vi tar hoeyde for alternativ om aa gaa videre)
            }
            
            
            Gjenstand tingTattUt = aktuellRom.getKiste().taUt(); // trekk for aa plukke opp gjenstand eller legge den tilbake
            if (tingTattUt == null) System.out.println("Skattekista var tom.");
            else{
                kommando = grensesnitt.beOmKommando(typSpiller + " fant " + tingTattUt + ", vil hen ta den med?", jaNei);
                if(kommando == 0) {
                    trekkIgjen--;
                    sekk.plukkGjenstand(tingTattUt);
                }
                else aktuellRom.getKiste().settTilbake(tingTattUt);
            }

            kommando = grensesnitt.beOmKommando(typSpiller + " skal til aa gaa videre, hvor vil hen", new String[]{"1: rett fremm"});
            aktuellRom = aktuellRom.getNesteSted(1);
            trekkIgjen--;
        
            
    }

    public String[] getAltISekk(){ // former valgalternativ for salg bassert paa om sekk er full eller ikke
        String[] byggAlt;
        ArrayList<Gjenstand> ting = sekk.getTingISekk();
        if (sekk.getAntallISekk() < 5){
        byggAlt= new String[ting.size()+1];
        byggAlt[0] = "1: For aa ikke Selge";
        for (int i = 1; i < ting.size()+1; i++) {
            byggAlt[i] =  i + 1 + ": " + ting.get(i-1);
            }
        }
        else{
            byggAlt= new String[ting.size()];
            for (int i = 0; i < ting.size(); i++) {
                byggAlt[i] =  i + 1 + ": " + ting.get(i);
                }
        }

        return byggAlt;
    }

    public int getPenger(){  // returnerer poeng
        return penger;
    }
    public boolean trekkIgjen(){ // sjekk for aa se om spiller har trekk igjen
        return trekkIgjen > 0;
    }
    public String getNavn(){
        return typSpiller;
    }
}