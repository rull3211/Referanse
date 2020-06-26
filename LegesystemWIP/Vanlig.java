class Vanlig extends Legemiddel{ //vanlig legemiddel som ikke skal ha noe styrke (slik som narkotisk og vanedannende)

  public Vanlig(String n, double p, double mg){
    super(n, p, mg);
  }
  @Override
  public int hentStyrke(){ //Vanlig legemidde skal ikke har styrke og returnerer derfor 0
    return 0;
  }
  @Override
  public String hentType(){ //henter type paa legemidlet 
      return "vanlig";
  }
}
