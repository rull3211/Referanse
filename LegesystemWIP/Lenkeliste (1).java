import java.util.Iterator;

class Lenkeliste<T> implements Liste<T>{
    protected Node start = null;
    protected Node slutt = start;
    protected int storrelse = 0;


    protected class ListeIterator<T> implements Iterator<T>{
        private Liste<T> minListe;
        private int indeks = 0;

        public ListeIterator(Liste<T> lx){
            minListe = lx;
        }

        @Override
        public boolean hasNext(){
            return indeks < minListe.stoerrelse();
        }

        public T next(){
            return minListe.hent(indeks++);
        }
    }

    // jeg valgte aa loese denne obligen med en toveis lenkeliste for aa gjoere den me effektiv
    // og det gjoer det mulig for meg aa iterere begge veier i den
    protected class Node{
        // node inneholder en peker til forrige og neste node og data
        protected T data;
        protected Node neste = null;
        protected Node forrige = null;
        public Node(T x){
            data = x;
        }
        @Override
        public String toString(){
            return ""+data;
        }
    }

    public int stoerrelse(){
        return storrelse;
    }
    /*jeg valgte i denne oppgaven aa utfordre meg selv og la vaer aa bruke noen former
    for loekker, jeg valgte heller aa bruke rekursjon, som vil si at jeg
    skrev en metode som iterere frem til oensket Node og returnerer saa denne noden
    dette viste seg aa vaere et sjakktrekk da jeg slapp aa bruke en mangfold av loekker
    */
    private Node iterer(int teller, int posisjon, Node e){
        // dersom posisjonen er stoerre enn teller hopper vi videre til neste noden
        // oeker telleren og gjentar prosessen, naar vi har oensket node returneres den
        int iter = teller;
        if(posisjon > iter){
            iter++;
            return iterer(iter, posisjon, e.neste);
        } else {
            return e;
        }
}
    /* metodene blir litt lenger paa grunn av at vi jeg har en toveis
    lenkeliste hvor jeg maa peke baade frem og tilbake og vaere mer noeye med
    siste-node cornercasen dette vil i gjengjeld gjoere senere oppgaver mye mer
    ryddige og senere metoder mer oversiktlige*/

    public void leggTil(int pos, T x)throws UgyldigListeIndeks{
        if(pos < 0 || pos > storrelse){
            throw new UgyldigListeIndeks(pos);
        }
        if(storrelse == 0 ||(pos == storrelse)) { //legger til på siste eller første plass dersom fi-en stememr
            leggTil(x);
            return;
        }else{
        Node element = iterer(0, pos, start); // finner elementet vi vllegge inn på
        Node ny = new Node(x); // oppretter ny node
        Node forrige = null; // oppretter er forrigevariabel som i utgangspunktet er null
        if (pos > 0) { // hvos posisjonen ikke er null har noden en forrigenabo også
            forrige = element.forrige;
            forrige.neste = ny; // forrige sin nabo blir ny
        }if (pos == 0) start = ny; // hvis pos er 0 så er start ny
        ny.neste = element; // ny sin neste er element
        ny.forrige = forrige; // ny sin forrige er forrige, dersom vi er på plass 0 er dette null
        element.forrige = ny; // element sin forrige  er ny
        storrelse++; // størrelse øken

        }
    }
    // legger til i siste plass, om det er foerste element oppdateres start og slutt
    public void leggTil(T x){
        if (storrelse == 0) { // legger til første element
            start = new Node(x);
            slutt = start;
            storrelse++;
            return;
        }else{ // legger til element på slutten av lista, med hjelp av sluttpeker og toveis perer
            // her slipper jeg altså å  iterere pga 2veis pegere
            Node ny = new Node(x);
            slutt.neste = ny;
            ny.forrige= slutt;
            slutt = ny;
            storrelse++;
            return;
        }
    }
    public void sett(int pos, T x)throws UgyldigListeIndeks{
        if(pos < 0 || pos > storrelse - 1){
            throw new UgyldigListeIndeks(pos);
        }
        // neste og forrige pekere må være null i utgangspunktet pga cornercases
            Node neste = null;
            Node forrige = null;
            Node ny = new Node(x);  // oppretter ny node som skal settes inn
            Node element = iterer(0, pos, start); // itererer frem til ønsket plass
            if(pos > 0){
                forrige = element.forrige; // dersom vi ikke setter inn på startplassen
                forrige.neste = ny;// skal ny ha en forrige peker som vi tar fra
                // element og forrige skal også peke på ny
            }
            if (pos < storrelse -1) {
                neste = element.neste;  // dersom vi ikke setter inn på slutten
                neste.forrige = ny; // skal ny ha en nestepeker og neste skal også peke på ny
            }if (pos == 0)start = ny; // og ny settes inn på start oppdateres startpekeren
            if (pos == storrelse-1)slutt = ny; // dersom elementet settes inn på slutten oppdateres sluttpekeren
            ny.neste = element.neste;  // ny får naboene sine
            ny.forrige = element.forrige;
            }


    public T hent(int pos)throws UgyldigListeIndeks{
        if (pos > storrelse -1 || pos < 0){
            throw new UgyldigListeIndeks(pos);
        }

        return iterer(0, pos, start).data; // hent bruker bare iterer til å hente node på rett plass og returnerer dataen
    }

    public T fjern(int pos)throws UgyldigListeIndeks{
        if(pos < 0 || pos > storrelse-1){
            throw new UgyldigListeIndeks(pos);
        }
        Node fjern = iterer(0, pos, start); // finner noden som skal fjernes
        Node forrige = null;// setter forrige og neste til null mtp cornercases
        Node neste = null;
        if (pos == 0 && storrelse == 1) {
            start= null;  // om vi fjerner eneste element blir både start og slutt null
            slutt = start;
            storrelse--;
            return fjern.data;
        }
        if (pos < storrelse - 1) neste = fjern.neste; // om posisjon ikke er Siste element
        //  har elementet som skal fjernes en neste nabo
        if(pos > 0)forrige = fjern.forrige; // om posisjonene ikke er 0 altså start
        //så har elementet som skal fjernes også en neste nabo
        if(pos == storrelse -1 ){  // om vi fjerner siste så blir
            //forrige slutt og dens neste nabo blir tom
            forrige.neste = null;
            slutt = forrige;
            }
        else if(pos == 0){ // om vi fjerner fra starten blir fjern sin neste
            // nabo start og dens forrigenabo blir null
            start = fjern.neste;
            start.forrige = null;
            }
        else{
            neste.forrige = forrige; // ellers spleises fjern sin forrige og neste
            forrige.neste = neste;
        }
        storrelse--; // størrelse blir trukket fra
        return fjern.data; // og dataen som ble fjernet returneres
    }

    public T fjern(){ // kaller på fjern med 0 altså første element
        return fjern(0);
    }

    public ListeIterator<T> iterator(){
        return new ListeIterator<T>(this);
    }


}
