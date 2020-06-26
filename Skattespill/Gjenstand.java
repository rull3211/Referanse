public class Gjenstand {
    protected String navn;
    protected int verdi;

    public Gjenstand(String n, int v){ // gjenstandklasse med enkle metoder
        navn = n;
        verdi = v;
    }

    public String getNavn(){
        return navn;
    }

    public int getVerdi(){
        return verdi;
    }

    @Override
    public String toString(){
        return navn + " Verdi:" + verdi;
    }
}