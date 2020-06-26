abstract class Resept{
    protected static int teller = 0;  // teller for aa opprette unik reseptID
    protected int resId; //reseptID skal vaere unik
    protected Legemiddel legemiddel; // resepten tar inn en legemiddel
    protected Pasient pasientID; // en pasientid
    protected int reit;   // skal ha en reit (hvor mange ganger den kan brukes)
    protected Lege lege; // og legen som skreiver den ut


    public Resept(Legemiddel L, Lege LE, Pasient id, int R){
        resId = teller;
        teller ++;
        legemiddel= L;
        pasientID = id;
        reit = R;
        lege = LE;

}
    // de neste fem metodene er trivielle hent metoder
    public int hentId(){
        return resId;
    }

    public Legemiddel hentLegemiddel(){
        return legemiddel;
    }

    public Lege hentLege(){
        return lege;
    }

    public Pasient hentPasientId(){
        return pasientID;
    }

    public int hentReit(){
        return reit;
    }
 // denne forsøker å bruke resepten og returnerer false dersom den er tom
    public boolean bruk(){
        if (reit > 0){
            reit --;
            return true;
        }return false;
    }
    // pris å betale og Farge er abstrakte for de skal brukes senere
    abstract public String farge();

    abstract public double prisAaBetale();

    @Override // overskriver toString() i objektklassen for å skrive ut relevant info
    public String toString(){
        return "Utskrivende lege:\n"+ lege + "\n" +  "\nResept ID: "+ resId + "\nLegemiddlet er: " + legemiddel
         +"\n" +  pasientID +
        "\nGjenvarende reit: "
        + reit + "\n";
    }
    public String hentType(){
        return "";
    }
}
