class Spesialist extends Lege implements Godkjenningsfritak {
  protected int kontrollID; //spesialist-lege skal ha en kontrollID for aa kunne skrive narkotisk-resepter

  public Spesialist(String navn, int kID) {
    super(navn); //kaller paa super-klassens konstruktor
    kontrollID = kID;
  }

  @Override
  public int hentKontrollID() { //henter ut kontrollID
    return kontrollID;
  }

  @Override
  public String toString() { //formaterer objektet som tekststreng ved utskrift 
    return
    super.toString() +
    "\nKontrollID: " + hentKontrollID();
  }
}
