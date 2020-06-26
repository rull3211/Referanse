class Militarresept extends HvitResept{  // denne er en utvidelse av hvitresept som utvider resept
    public Militarresept(Legemiddel L, Lege LE, Pasient id, int R){
        super(L,LE,id,R);
    }
    @Override // prisen på Militaerresept er 100% avslag så vi bare returnerer null
    public double prisAaBetale(){
    return 0;
}
    @Override // overskriver torstring fra hvit resept og skriver resterende nødvendig informasjon
    public String toString(){
        return lege +  "\nResept ID: "+ resId + "\nLegemiddlet er: " + legemiddel.toString()
         +  "\nPasient iden er: " + pasientID +
        "\nGjenvarende reit: "
        + reit + "\n"+ "Dette er en: "+ farge()+ "\nAv typen Militaerresept\nSummen som skal betales er:  " + Math.round(prisAaBetale())+ " kr";
}
    @Override
    public String hentType(){
        return "militaer";
    }
}
