abstract class Legemiddel{
  protected String navn;
  protected int id;
  protected double pris;
  protected double mg;
  static int teller = 0;

  public Legemiddel(String n, double p, double mg){ //konstruktoer
    navn = n;
    this.id = teller;
    teller++;
    pris = p;
    this.mg = mg;
  }
  public String hentNavn(){ //henter navnet på legemidlet
    return navn;
  }
  public int hentId(){ //henter ID på legemidlet
    return id;
  }
  abstract String hentType();
  public double hentPris(){ //henter pris på legemidlet
    return pris;
  }
  public double hentVirkestoff(){ //henter antall mg virkestoff i legemidlet
    return mg;
  }
  public void settNyPris(double nyPris){ // setter ny pris på legemiddel, kan skje noe her senere
    pris = nyPris;
  }
  abstract int hentStyrke(); //henter styrke til legemiddel (vanedannende eller narkotisk)
  public String toString(){ //toString metode gjør utskrift av objekt mulig og oversiktlig
    return "Navn: "+navn + "\nID: "+id + "\nPris: "+Math.round(pris)+"kr \nVirkestoff: "+Math.round(mg)+"mg ";
  } //avrunder pris og virkestoff for finere utskrift og visuell representasjon 
}
