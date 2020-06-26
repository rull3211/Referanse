import java.lang.Comparable;
class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T>{
    private int pos = 0;
    @Override
    public void sett(int pos, T x){
        throw new UnsupportedOperationException("du kan ikke velge posisjon for dette er en sortert lenkeliste");
    }
    @Override
    public void leggTil(int pos, T x){
        throw new UnsupportedOperationException("du kan ikke bruke Legg til pos her siden dette er en sortert lenkeliste");
    }

    @Override // bruker en rekursiv funksjon for å finne posisjonen man skal legge
    // legge inn en ny node på. om størrelse er ny legges det inn på første plass
    // dersom funksjonen returnerer null legges noden inn på siste
    // ellers legges den inn på posisjon som blir "vedlikeholdt" av den rekursive funksjonen
    public void leggTil(T x){
        pos = 0; // pos resettes hver gang legg til kalles.
        Node element = start;
        if (storrelse == 0) {
            super.leggTil(x);
            return;
        }
        if (sammenlign(0, x, element) == null){
            super.leggTil(x);
            return;
        }
        else{
            super.leggTil(pos, x);
            return;
        }
    }

    @Override
    public T fjern(){ // fjerner med posisjon som er siste posisjon
        return fjern(storrelse-1);
    }

    public Node sammenlign(int iterer, T ny, Node element){
        // en rekursiv funksjon som finner rette posisjon å sette inn elementet på
        // ellers returnerer den objekt null dersom elementet skal settes inn på siste plass
           int teller = iterer;
        if (element != null){
            if (ny.compareTo(element.data) > 0) {
                teller ++;
                pos++; // øker pos for hver rekursjonen
                return sammenlign(teller, ny, element.neste);
            }
        }
        return element;
    }

}
