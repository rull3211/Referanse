class Vanedannende extends Legemiddel{
  int styrke; //vanedanende styrke paa legemidlet

  public Vanedannende(String n, double p, double mg, int sty){
    super(n, p, mg);
    styrke = sty;
  }
  @Override
  public int hentStyrke(){ //henter ut vanedannende styrke
    return styrke;
  }
  @Override
  public String toString(){ //formaterer objektet til String ved utskrift
      return super.toString() + "\nVanedannedannende styrke: " + styrke;

  }
  @Override
  public String hentType(){ //henter ut type paa legemidlet 
      return "vanedannende";
  }
}
