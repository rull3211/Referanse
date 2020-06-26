class Stabel<T> extends Lenkeliste<T>{
    public void leggPaa(T x){
        leggTil(x);  // legger til element på siste plass
    }

    public T taAv(){
        return fjern(storrelse-1); // fjerner siste element fra lenkelista
    }
}
