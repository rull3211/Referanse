import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
class InnLesningsMetoder{

    private String fil;
    private Stabel<Resept> resepter = new Stabel<>();
    private SortertLenkeliste<Lege> leger = new SortertLenkeliste<>();
    private Lenkeliste<Legemiddel> legemidler = new Lenkeliste<>();
    private Lenkeliste<Pasient> pasienter = new Lenkeliste<>();
    private String type = null;

    public InnLesningsMetoder(String filnavn){
        fil = filnavn;
        lesFil();
    }
    public Stabel<Resept> returR(){
        return resepter;
    }
    public SortertLenkeliste<Lege> returLege(){
        return leger;
    }
    public Lenkeliste<Legemiddel> returL(){
        return legemidler;
    }
    public Lenkeliste<Pasient> returP(){
        return pasienter;
    }
    public void lesFil() {
        Scanner fila = null;
        try { // prøver å åpne fila dersom fila ikke finnes og feilmeldign skrives ut
          fila = new Scanner(new File(fil));
        } catch(FileNotFoundException e) {
          System.out.println("filen finnes ikke i mappa");
          System.exit(1);
        }
        /* denne løkka kjøres så lenge det er flere linjer i fila
        og dersom den neste linja starter med en "#" så vet vi at her er det en ny
        type objekt som starter så da opdateres type og vi sjekker hva slags
        objekt det er og sender inn informasjon til de forskjellige metodene
        basert på det og fyller arraylistene som kan hentes. vi har basicly laget
        en "server som lagrer vår informasjon" */
        while (fila.hasNextLine()) {
            String linje = fila.nextLine();
            if(linje.charAt(0) == '#') {
                type = linje.split(" ")[1];
            }
            else {
                if (type.equals("Pasienter")) {
                    pasienter.leggTil(regPasient(linje));
                }
                else if(type.equals("Legemidler")){
                    try {
                      legemidler.leggTil(medLes(linje));
                    } catch (UnsupportedOperationException e) {
                      continue;
                    }
                }
                else if(type.equals("Leger")){
                    leger.leggTil(regleger(linje));
                }
                else if(type.equals("Resepter")) {
                    try {
                      resepter.leggPaa(res(linje));
                    } catch (UlovligUtskrift e) {
                      //System.out.println(e);
                      continue;
                    } catch(UnsupportedOperationException k){
                      //System.out.println("inndata er feil hopper til neste linje");
                      continue;
                    }
                }
            }
        } fila.close();
    }
    public Pasient regPasient(String linje){
        String[] pasienten = linje.split(",");
        String navn = pasienten[0];
        String fnr = pasienten[1].trim();
        return new Pasient(navn, fnr);
    }
    public Legemiddel medLes(String linje)throws UnsupportedOperationException{
        String[] middel = linje.split(",");
        String navn = middel[0];
        String typ = middel[1].trim();
        double pris = 0;
        double virk = 0;
        int styrke = 0;
        if (!(typ.equals("narkotisk") || typ.equals("vanedannende") || typ.equals("vanlig") )) {
            throw new UnsupportedOperationException("Hopper over linja");
        }
        else if((typ.equals("narkotisk") || typ.equals("vanedannende")) && middel.length != 5){
            throw new UnsupportedOperationException("Hopper over linja");
        }
    // denne metoden tar inn en String og bryter den ned til de forskjellige variablene
        try{
            pris = Double.parseDouble(middel[2].trim()); // pris og virkestoff gjøres til double
            virk = Double.parseDouble(middel[3].trim());
            if (typ.equals("vanedannende") || typ.equals("narkotisk")){ // om medisinen skal bli type a eller b skal den ha en stryke også
                styrke = Integer.parseInt(middel[4].trim());
            }
        }catch(NumberFormatException e){
            System.out.println("jeg kraesjet i medLes");
            throw new UnsupportedOperationException("Hopper over linja");
        }
        if(typ.equals("narkotisk")){ //if og else if tester for om medisinen skal være a elle b og returnerer en av de typene
            return new Narkotisk(navn, pris, virk, styrke);
        }
        else if(typ.equals("vanedannende")){
            return new Vanedannende(navn, pris, virk, styrke);
        }
        else{// ellers returneres en preparat C
            return new Vanlig(navn, pris, virk);
        }
    }

    public Lege regleger(String linje){ // tar inn en string med info om legen
        String[] info = linje.split(",");
        String navn = info[0].trim();
        int id = 0;
        try{
            id = Integer.parseInt(info[1].trim());  // prøver å tilordne iden til legen
        }catch(NumberFormatException e){
            System.out.println("Jeg kraesjet i regleger");
            System.exit(1);
        }
        if (id == 0){  // dersom ID er 0  er det vanlig lege og da returneres en lege med kun navn
            return new Lege(navn);
        }else{
            return new Spesialist(navn, id);// dersom id>0 så returneres en spesialist
        }
    }

    public Resept res(String linje)throws UlovligUtskrift, UnsupportedOperationException{
        /* her tar vi all informasjon til å kunne opprette reseptene, inkludert lister med default:
            allerede eksisterende objektene, og kaster UlovligUtskrift exception videre til metodekall
            alle variablene tilordnes informasjonen de skal ha på seg */
        String[] info = linje.split(",");
        String typ = info[3].trim();
        if (info.length < 4) {
            throw new UnsupportedOperationException("hopper til neste linje for legemiddelnummer er ugyldig i linje 142");
        }
        int medId = -1;
        int pasId = -1;
        String legeNavn = info[1].trim();
        int reit = 0;
        Lege legen= null;
        for(int i = 0; i < leger.stoerrelse() ; i ++){
            if (legeNavn.equals(leger.hent(i).hentNavn())) {
                legen = leger.hent(i);
            } // henter legeobjektet fra legelista med navnet som er i info stringen
        }
        try{
            medId = Integer.parseInt(info[0].trim());// allokerer pasient og medisinid
            pasId = Integer.parseInt(info[2].trim());
            if (!(info[3].equals("p"))) {
                reit = Integer.parseInt(info[4].trim()); // dersom typen ikke er p-resept har den reit
            }
        }catch(NumberFormatException e){
            System.exit(1);
        }
        if ((medId > legemidler.stoerrelse()-1) || (pasId > pasienter.stoerrelse()-1)) {
            throw new UnsupportedOperationException("hopper til neste linje for legemiddelnummer er ugyldig inndata feil");
        } // Hva er dette Bence
        Pasient pasienten = pasienter.hent(pasId);
        Legemiddel legemidelet = legemidler.hent(medId);
        if (typ.equals("blaa")) {
            return legen.skrivBlaaResept(legemidelet, pasienten, reit);
        }
        else if(typ.equals("p")) {

            return legen.skrivPResept(legemidelet, pasienten);
        }
        else if(typ.equals("militaer")) {
            return legen.skrivMillitaerResept(legemidelet, pasienten, reit);
        }
        else {
          return legen.skrivHvitResept(legemidelet, pasienten, reit);  // jeg valgte aa opprette kun hvite resepter pga mangel på info
        }
    }
}
