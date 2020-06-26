public class UlovligUtskrift extends Exception { //brukes som feilmelding hvis legen ikke har lov aa skrive ut narkotisk legemiddel
  UlovligUtskrift(Lege l, Legemiddel lm) {
    super("Legen " + l.hentNavn() + " har ikke lov til aa skrive ut " + lm.hentNavn());
  }
}
