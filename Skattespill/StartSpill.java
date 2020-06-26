import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock; 
/* jeg maatte konkuldere med at det ikke lenger var ryddig og god kokeskikk aa ha spillstarten i main lenger derfor laget jeg en ny klasse som er "spillkontrolleren min"*/
public class StartSpill {
    private Scanner input = new Scanner(System.in);
    private Spiller[] spillere;
    private Thread[] traader;
    private ReentrantLock laas = new ReentrantLock();
    public void startSpill(){
        Terreng brett; // deklarerer brett og noen andre variabler som skal oppdateres og brukes for oppretting av spill
        int antRoboter = -1;
        int antSpillere = -1;
        String spillModus = "";
        while(antSpillere < 1){ // oppdaterer antall spillere
            try {
                antSpillere = Integer.parseInt(input("Hvor mange spillere skal du ha med? kun heltall"));
            } catch (NumberFormatException e) {
                System.out.println("Du oppga noe annet en tall");
            }
        }
        while(antRoboter < 0 || antRoboter > antSpillere){ // oppdaterer antall roboter roboter maa minst vaere 0 og kan ikke vaere mer enn ant spillere
            try {
                antRoboter = Integer.parseInt(input("Hvor mange roboter skal du ha med? kun heltall"));
            } catch (NumberFormatException e) {
                System.out.println("Du oppga noe annet en tall");
            }
        }
        spillere = new Spiller[antSpillere]; // oppretter beholder for alle spillerobjektene 
        while(!(spillModus.equals("1") || spillModus.equals("2"))){ // ber bruker om aa velge spillmodus
            spillModus = input("1: for spill med kun 1 retning 2: for spill med VeiValg");
        }
        if(spillModus.equals("1")) brett = new Terreng(); // oppretter korrekt terreng
        else brett = new VeiValgTerreng();
        brett.settOpp(); // setter opp hele brettet (liten forandring paa den for den returnerer ingen ting lenger)
        for (int i = 0; i < antRoboter ; i++) { // oppretter antall roboter
            if(spillModus.equals("1")){
                spillere[i] = new Spiller(brett.hentStart(), new Robot(), input("Hva heter roboten?"), this);
            }
            else{
                spillere[i] = new VeiValgSpiller(brett.hentStart(), new Robot(), input("Hva Heter roboten?"), this);
            }
        }
        for (int i = antRoboter; i < antSpillere ; i++) { // oppretter resten som menneskespillere
            if(spillModus.equals("1")){
                spillere[i] = new Spiller(brett.hentStart(), new Terminal(new Scanner(System.in)), input("Hva heter Spilleren"), this);
            }
            else{
                spillere[i] = new VeiValgSpiller(brett.hentStart(), new Terminal(new Scanner(System.in)), input("Hva heter Spilleren"), this);
            }
        }
        String spoersmaal = input( "Dere har 15 trekk hver, maalet med spillet er aa samle mest poeng som du faar"+ // instruksjon til spiller
        "\n gjennom aa selge ting du finner i kister det aa ta eller aa selge ting bruker trekk"+ 
        "\n naar alle har brukt opp trekkene sine saa er spillet ferdig, man bruker trekk paa a gaa, plukke og selge ting"+
        "\n hvis gjenstanden er for billig kan du miste penger paa a selge saa bruk trekkene dine for siktig"+ 
        "\n lykke til trykk enter for aa starte"); // gir spillbeskrivelse
        traader = new Thread[spillere.length];// oppretter traader for alle spillere
        for(int i = 0; i < spillere.length; i++){
            traader[i] = new Thread(spillere[i]);
            
        }
        for (Thread t : traader) { // starter alle traadene
            t.start();
        }


        boolean notdone = true;
        while(notdone){ // holder maintraaden her imens alle gjoer seg ferdig
            boolean done = true;
            for (Spiller spiller : spillere) {
                if(spiller.trekkIgjen()){
                    done = false;
                }
            }
            notdone = !done;
            
        }
    }
    public Spiller[] getSpillere(){
        return spillere;
    }


    
    public String input(String prompt){ // en inputmetode for aa ha enklere tilgang til aa be om input
        System.out.println(prompt); // Skriver ut parameter til terminal
        return input.nextLine(); 
      }
    
    
    public void nyTrekk(Spiller spilleren){ // jeg laaste hele nytt trekk metoden saa kun en traad har tilgang til dette
        laas.lock();
        try{
            spilleren.nyttTrekk();
        }finally{
            laas.unlock();
        }
    }
}