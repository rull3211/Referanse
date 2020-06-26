import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class VeiValgTerreng extends Terreng{
  
    
    @Override
    public void setTerreng(){ // oppretter hele lenkelista
        ArrayList<Sted> beholder = new ArrayList<>();
        Random randInt = new Random();
        try {
            filLeser = new Scanner(beskrivesler); // leser inn fil
            Sted forrigSted = null; // beholder for forrige sted
            while(filLeser.hasNextLine()){ // sikrer at alle linjer er lest inn
                VeiValgSted nySted = new VeiValgSted(filLeser.nextLine()); // oppretter sted og gir beskrivelse
                antallSteder++; // teller denne
                nySted.setKiste(listeMedGjenstander); // oppretter skattekista til stedet 
                beholder.add(nySted);
                if(start == null)start = nySted; // setter start dersom denne ikke er suttet
                if(forrigSted != null) forrigSted.setVidere(nySted); // sjekker om vi har en ny sted aa sette neste sted peker paa
                forrigSted = nySted; // forrigestedet settes til bruk ved neste iterasjon
            }
            forrigSted.setVidere(start); // For aa lage terenget syklisk
            for (Sted s : beholder) { // denne her løkka bruker beholder som er opprettet tidligere til å sette de resterende naboene ogsaa etter at alle stedene er opprettet
                for(int i = 0; i < 3; i++){
                    Sted neste = beholder.get(randInt.nextInt(beholder.size()-1));
                    while(s.equals(neste)){ // dette er for aa sikre at stedene ikke linker seg selv sammen
                        neste = beholder.get(randInt.nextInt(beholder.size()-1));
                    }
                    s.setVidere(neste); 
                }
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke fila for beskrivelser avslutter programmet.");
            System.exit(1);
        }
    }
}