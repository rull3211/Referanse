import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Terreng { // er "lenkelista" altsaa beholder for foerste sted 
    protected Sted start = null; // beholder for foerste sted
    protected static final File beskrivesler = new File("beskrivelser.txt");  // konstantene (filene)
    protected static final File gjenstander = new File("gjenstander.txt");
    protected ArrayList<String> listeMedGjenstander = new ArrayList<>(); // beholder for beskrivelsene av gjenstander
    protected Scanner filLeser = null; // skanner for aa lese filene
    protected int antallSteder = 0; // teller antall steder som finnes dersom jeg oensker aa bruke dette
    protected Random intGen = new Random(); // en pseudorandom intgenerator

    public void getGjenstander(){ // foersoeker aa lese inn fila for gjenstandene
        try{
            filLeser = new Scanner(gjenstander);
            while(filLeser.hasNextLine()){
                listeMedGjenstander.add(filLeser.nextLine()); /*
                sender kun med beskrivelse av skattene for aa gi hver skattekiste egene skatter og ikke "flere
                av samme skatt"*/
            }
        }catch(FileNotFoundException e){
            System.out.println("Fant ikke fila for skattene, avslutter programmet.");
            System.exit(1);
        }
    }

    public void setTerreng(){ // oppretter hele lenkelista
        try {
            filLeser = new Scanner(beskrivesler); // leser inn fil
            Sted forrigSted = null; // beholder for forrige sted
            while(filLeser.hasNextLine()){ // sikrer at alle linjer er lest inn
                Sted nySted = new Sted(filLeser.nextLine()); // oppretter sted og gir beskrivelse
                antallSteder++; // teller denne
                nySted.setKiste(listeMedGjenstander); // oppretter skattekista til stedet 
                if(start == null)start = nySted; // setter start dersom denne ikke er suttet
                if(forrigSted != null) forrigSted.setVidere(nySted); // sjekker om vi har en ny sted aa sette neste sted peker paa
                forrigSted = nySted; // forrigestedet settes til bruk ved neste iterasjon
            }
            forrigSted.setVidere(start); // For aa lage terenget syklisk
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke fila for beskrivelser avslutter programmet.");
            System.exit(1);
        }
    }

    public Sted hentStart(){ // returnerer en tilfeldig startsted
        Sted beholder = start;
        for(int i = 0; i < intGen.nextInt(antallSteder); i++){
            beholder = beholder.getNesteSted(1);
        }
        return beholder;
    }

    public void settOpp(){ // metode for aa sette opp spillerens "karakter"
        getGjenstander();
        setTerreng();
    }
}