class BlaaResept extends Resept{
    public BlaaResept(Legemiddel L, Lege LE, Pasient id, int R){
        super(L,LE, id, R);
    }
    @Override  // returnerer et tall av 25% legemiddelets pris som pasienten skal betale
    public double prisAaBetale(){
        return legemiddel.hentPris() * 0.25;
    }
    @Override  // resepten får fargen blå
    public String farge(){
        return "BlaaResept";
    }
    @Override  // overrider tostring i resept for å printe resterende info
    public String toString(){
        return super.toString() + "Summen som skal betales er: "+ Math.round(prisAaBetale())+"kr"
        + "\nresepten er en "
        + farge();
    }
    @Override
    public String hentType(){
        return "blaa";
    }
}
