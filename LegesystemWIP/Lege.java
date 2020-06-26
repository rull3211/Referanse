class Lege implements Comparable<Lege> { //implementerer compareTo for aa kunne sammenlikne to legenavn
  protected String navn; //navn paa lege
  private Lenkeliste<Resept> reseptliste = new Lenkeliste<>(); //liste med resepter

  public Lege(String nvn) { //konstruktor
    navn = nvn;
  }

  public String hentNavn() { //returnerer navnet paa legen
    return navn;
  }

  public int hentKontrollID() { //vanlig lege skal ikke vaere spesialist
    return 0;
  }

  @Override
  public int compareTo(Lege navn) { //sammenlikner navnene paa to legeobjekter
    return this.navn.compareTo(navn.hentNavn());
  }

  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      throw new UlovligUtskrift(this, legemiddel); //hvis legen ikke har lov aa skrive ut narkotisk legemiddel skrives feilmelding
    }

    HvitResept nyRes = new HvitResept(legemiddel, this, pasient, reit);

    pasient.leggTilResept(nyRes); //legger til resept i listen over resepter i lege-objektet
    reseptliste.leggTil(nyRes);
    return nyRes;
  }

  public Militarresept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      throw new UlovligUtskrift(this, legemiddel);
    }
    Militarresept nyRes = new Militarresept(legemiddel, this, pasient, reit);

    pasient.leggTilResept(nyRes);
    reseptliste.leggTil(nyRes);
    return nyRes;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      throw new UlovligUtskrift(this, legemiddel);
    }
    PResept nyRes = new PResept(legemiddel, this, pasient);

    pasient.leggTilResept(nyRes);
    reseptliste.leggTil(nyRes);
    return nyRes;
  }



  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk && hentKontrollID() == 0) {
      throw new UlovligUtskrift(this, legemiddel);
    }
    BlaaResept nyRes = new BlaaResept(legemiddel, this, pasient, reit);

    pasient.leggTilResept(nyRes);
    reseptliste.leggTil(nyRes);
    return nyRes;
  }
  public Lenkeliste hentReseptListe(){ //henter ut listen med resepter
    return reseptliste;
  }
  @Override
  public String toString() { //toString som formaterer lege-objektet 
    return "\nLege: " + navn;
  }
}
