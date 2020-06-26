class HvitResept extends Resept{ // ingen nye egenskaper utenom at den er hvit
    public HvitResept(Legemiddel L, Lege LE, Pasient id, int R){
        super(L, LE, id, R);
    }
    @Override  // resepten får en farge
    public String farge(){
        return "HvitResept";
    }

    @Override  // en pris å betale
    public double prisAaBetale(){
        return legemiddel.hentPris();
    }
    @Override  // overrider tostring i resept og legger til relevant info
    public String toString(){
        return super.toString() + "Dette er en: " + farge() + "\nSummen som skal betales er: " + legemiddel.hentPris();
    }
    @Override
    public String hentType(){
        return "hvit";
    }
}
