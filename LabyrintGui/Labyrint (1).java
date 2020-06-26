import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Labyrint{
    private Rute [][] labyrint;
    private int kolonner = 0; //antall elementer i listene
    private int rader = 0; // antall lister
    private SortertLenkeliste<String> utveier = new SortertLenkeliste<>();
    private int traader = 0;
    private final Lock utveislaas = new ReentrantLock();

    private Labyrint(int r, int k, Rute[] ruter){
        rader = r;
        kolonner = k;
        labyrint = new Rute[rader][kolonner]; // arrayyen med ruter
        for(Rute l: ruter){  // ruter legges til i sine plasser
            labyrint[l.hentRad()][l.hentKol()] = l;
        }
        for(Rute alle: ruter){ // gir rutene labyrint og naboer
            alle.giLabyrint(this);
            alle.finnNaboer();
        }
    }

    public int hentRader(){  // returnerer antall rader
        return rader;
    }
    public int hentKolonner(){ // returnerer antall kolonner
        return kolonner;
    }
    public Rute [][] hentLab(){  // returnerer labyrinten
        return labyrint;
    }
    public Rute hentRute(int rad, int kol){  // henter gitte ruter
        return labyrint[rad][kol];
    }
    @Override
    public String toString(){  // printer labyrinten
        String streng = "";
        for(Rute[] a: labyrint){
            for(Rute r: a){
                streng += r.tilTegn();
            }
            streng += "\n";
        }
        return streng;
    }
    public Lenkeliste<String> finnUtveiFra(int rad, int kol){  // finner utvei fra og returnerer disse utveiene fra gitt posisjon
        utveier = new SortertLenkeliste<>(); // og resetter labyrinten og utveier lista
        hentRute(kol, rad).finnUtvei();
        resetBesoekt();
        return utveier;
    }
    public void resetBesoekt(){
        for(Rute[] a: labyrint){
            for(Rute r: a){
                r.resetBesoekt();
            }
        }
    }
    static Labyrint lesFraFil(File fil)throws FileNotFoundException{
        Scanner fila = new Scanner(fil);
        String[] stoerrelse = fila.nextLine().split(" ");
        int tellRader = 0;  //  teller rader opprettet hittil
        int tellRuter = 0; // teller antall ruter opprettet hittil
        int antRad = 0;  // ant kol og rad er parametere for labyrinten
        int antKol = 0;
        try{
            antRad = Integer.parseInt(stoerrelse[0]);
            antKol = Integer.parseInt(stoerrelse[1]);
        }catch (NumberFormatException e) {
            System.out.println("feil i fil avslutter program");
            System.exit(1);
        }
        Rute[] ruter = new Rute[antRad * antKol];  // antall ruter som skal legges til i labyrint
        while(fila.hasNextLine()){
            String[] linje = fila.nextLine().split("");
            int tellPlass = 0;  // teller kolonne for ruta
            for(String s: linje){ // oppretter alle ruter
                if (s.equals(".") && ((tellRader == 0 || tellRader == antRad-1) || (tellPlass == 0 || tellPlass == linje.length-1))){
                    ruter[tellRuter] = new Aapning(tellRader, tellPlass);
                }
                else if(s.equals("#")){
                    ruter[tellRuter] = new SvartRute(tellRader, tellPlass);
                }
                else{
                    ruter[tellRuter] = new HvitRute(tellRader, tellPlass);
                }
                tellRuter++;
                tellPlass++;
            }
            tellRader ++;
        }
        return new Labyrint(antRad, antKol, ruter); // returnerer labyrinten
    }


    public void leggTilSti(String utvei) { // dette er for aa ikke få noe kluss i lenkelista 
        utveislaas.lock(); // laaser instansvariablene
        try {
            utveier.leggTil(utvei); // legger til utvei
        } finally {
            utveislaas.unlock(); // laaser opp
        }
    }
}
