class PResept extends HvitResept{// hvit resept har en egen reit som alltid er 3 og derfor har jeg i
    public PResept(Legemiddel L, Lege LE, Pasient id){// super konstruktøren sendt inn 3 istedenfor en parameter
        super(L, LE, id, 3); // ellers er den lig på de andre reseptene
    }

    @Override
    public double prisAaBetale(){  // på denne resepten er det 108 kr avslag så dersom prsen er
        if (legemiddel.hentPris() < 108) { // lavere enn 108 så er den gratis
            return 0;
        }
            return legemiddel.hentPris() - 108; // ellers er prisen prisen -108
// grunnen til at jeg ikke endrer prisen på noen av legemidlene er fordi legemiddelen har en egen pris og resepten vil ha en egen pris
// og forskjellige resepter vil ha forskjellig pris på samme legemiddel
    }
    @Override // overrider tostring for å få med tillegg relevant info
    public String toString(){
        return "Utskrivende lege er: \n" +  lege + "\n" +  "\nResept ID: "+ resId + "\nLegemiddlet er: " + legemiddel.toString()
         +pasientID +
        "\nGjenvarende reit: "
        + reit + "\n"+ "Dette er en: " + farge() + "\nAv typen P-resept\nSummen som skal betales er: "+ Math.round(prisAaBetale())+ " kr";
    }
    @Override
    public String hentType(){ //henter ut typen paa resepten
        return "p";
    }
}
