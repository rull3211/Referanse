import java.lang.NullPointerException;
import java.util.concurrent.*;
import java.util.ArrayList;

abstract class Rute implements Runnable  {
    protected int kordRad; // antall lister
    protected int kordKol; //antall elementer i listene
    protected Labyrint labyrint;
    protected Rute nNord = null;
    protected Rute nSoer = null;
    protected Rute nVest = null;
    protected Rute nOest = null;
    protected boolean besoekt = false;
    protected String lagretSti = "";
    public Rute(int r, int k){
        kordRad = r;
        kordKol = k;

    }
    public int hentRad(){
        return kordRad;
    }
    public int hentKol(){
        return kordKol;
    }
    public void giLabyrint(Labyrint l){
        labyrint = l;
    }
    public void finnNaboer(){
        Rute[][] labyrinten = labyrint.hentLab();
        if(kordRad != 0) nNord = labyrinten[kordRad - 1][kordKol];
        if(kordRad != labyrint.hentRader()-1) nSoer = labyrinten[kordRad + 1][kordKol];
        if(kordKol != 0) nVest = labyrinten[kordRad][kordKol - 1];
        if(kordKol != labyrint.hentKolonner()-1) nOest = labyrinten[kordRad][kordKol + 1];
    }
    public void skrivUtNaboer(){ // bare en "test metode" for å sjekke registrerte naboer
        System.out.println("naboene til" +"(" + kordRad + "," + kordKol + ")") ;
        System.out.println("nord: "+nNord);
        System.out.println("soer: " + nSoer);
        System.out.println("vest: " + nVest);
        System.out.println("oest: " + nOest);
    }
    public boolean godkjent(){
        if(!besoekt)return true;
        return false;
    }

    abstract public char tilTegn();

    public void run(){
        gaa(lagretSti);
    }
    public int antGodkjente(){ // metode for aa telle hvor mange gyldige ruter man har videre
        int teller = 0;
        if (nNord.godkjent()) {
            teller++;
        }if (nSoer.godkjent()) {
            teller++;
        }if (nVest.godkjent()) {
            teller++;
        }if (nOest.godkjent()) {
            teller++;
        }return teller;
    }
    public void lagreSti(String stien){ // hjelpemetode for aa lagre stien hittil i naboens instans for aa fa dataen videre til traader
        lagretSti = stien;
    }
    public void gaa(String stiHittil){
        if(besoekt)return;  // dersom ruta er besøkt returnerer vi
        String sti = stiHittil + "" + kordRad + "," + kordKol + ":"; // bygger stien
        besoekt = true; // setter besoekt til true og kaller gaa på alle naboene
        if(nNord.godkjent())nNord.gaa(sti);
        if(nSoer.godkjent())nSoer.gaa(sti);
        if(nVest.godkjent())nVest.gaa(sti);
        if(nOest.godkjent())nOest.gaa(sti);
        besoekt = false;
    }

    public void finnUtvei(){  // finer utveier fra gitte tture
        gaa("");
    }
    public void resetBesoekt(){ // resetter rutas esoekt
        besoekt = false;
    }
    @Override
    public String toString(){
        return "(" + kordKol + "," + kordRad + ")";
    }
}
