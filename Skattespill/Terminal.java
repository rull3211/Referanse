import java.util.Scanner;
public class Terminal implements BrukerGrensesnitt { // terminal for spilleren
    protected Scanner input = null;
    public Terminal(Scanner brukerInp){
         input = brukerInp;
    }
    
    public void giStatus(String Status){
        System.out.println(Status);
    }
    
   
    public int beOmKommando(String spoersmaal, String[] alternativer) { // ber om kommando og returnerer korrekt index
        System.out.println(spoersmaal); // skriver ut spoersmaal til spiller
        for (String s : alternativer) { // skriver ut alternativene
            System.out.println(s);
        }
        String svar = input.nextLine(); // tar en input fra brukeren
        while(true){ // looper i loekke til bruker oppgir en korrekt svar (gjoeres emd returnstatementen)
            for(int i = 0; i < alternativer.length; i++){ // looper gjennom alternativene
                if(alternativer[i].split(":")[0].equals(svar)){  // dersom alternativnummeret (tallet som kommer foer kolonne i alle alt) er lik paa svaret returneres indexen til alternativet
                    return i;
                }
            }System.out.println("Feil input proev paa nytt"); // sier ifra til brukeren at oppgitte svar er feil
            svar = input.nextLine(); // tar neste svar
        }
    }


}