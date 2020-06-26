import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock; 
public class SkattKiste {
    protected ArrayList<Gjenstand> kiste = new ArrayList<>();
    protected static int antGjenstanderTotalt = 0;
    protected Random intGen = new Random();
    protected ReentrantLock laas = new ReentrantLock();
    public SkattKiste(){
        //
    }

    public Gjenstand taUt(){ // laaser skattkista saa kun en traad har tilgang til den
        laas.lock();
        try{
            antGjenstanderTotalt --;
            if (kiste.size() > 0)
            return kiste.remove(intGen.nextInt(kiste.size())); // velger element, fjerner og returnerer elementet
            return null;
        }finally{
            laas.unlock();
        }

    }

    public int selg(Gjenstand ting){
        laas.lock();
        try{
            antGjenstanderTotalt ++;
            int sannVerdi = ting.getVerdi() ;
            int pris = sannVerdi + intGen.nextInt(30) - intGen.nextInt(30); // legger til og trekker fra et tilfeldig tall
            kiste.add(ting);
            return pris;
        }finally{
            laas.unlock();
        }

    }
    public void settTilbake(Gjenstand tingen){ // setter tingen spilleren fant tilbake dersom spilleren ikke beholdt den
        antGjenstanderTotalt ++;
        kiste.add(tingen);
    }
    public void setFyll(ArrayList<String> muligGjenstand){ // fyller kisten med egene gjenstandobjekter ( ikke bare pekere til objektene)
        int stoerrelse = intGen.nextInt(5);
            antGjenstanderTotalt += stoerrelse; // en statisk metode jeg laget i tilfelle jeg hadde behov for aa vite antall gjenstander som finnes
            for(int i = 0; i < stoerrelse; i++){
                String[] gBeskrivelse = muligGjenstand.get(intGen.nextInt(muligGjenstand.size())).split(" ");
                String navn = gBeskrivelse[0];
                int pris = 0;
                try {
                    pris = Integer.parseInt(gBeskrivelse[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Strengen er ikke tall (linje 18 i skattKiste)" + gBeskrivelse[1]);
                    System.exit(1);
                }
                kiste.add(new Gjenstand(navn, pris));
            }
        }
            
}