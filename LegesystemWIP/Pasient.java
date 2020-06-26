class Pasient{
   private String navn; //navn paa pasienten 
   private String fnr; //fodselsnummer skal vaere unik
   private int id; //skal vaere unik
   private Stabel<Resept> reseptStabel = new Stabel<Resept>(); //stabel extends lenkeliste - sist inn sist ut LILO
   public static int idteller = 0; //statisk metode som oeker for hvert objekt som blir opprettet

   public Pasient(String n, String nr){ //tar inn navn og fødselsnummer, ID settes automatisk av statisk idteller
      navn = n;
      fnr = nr;
      id = idteller;
      idteller++; //teller opp
   }
   public void leggTilResept(Resept r){ //skal legge til resept i stabel-lenkelisten
      reseptStabel.leggPaa(r);
   }
   public Stabel hentReseptliste(){ //Henter ut listen(Lenkeliste type stabel) med resepter
      return reseptStabel;
   }
   public int hentID(){
      return id;
   } //henter unik pasientID
   public String hentNavn(){
      return navn;
   } //henter navn på pasient
   public String hentFnr(){
      return fnr;
   } // henter foedselsnummer på pasient
   public String toString(){ //toString som formaterer utskrift av pasient-objekt
      return "\nNavn: " + navn + "\nFodselsnummer: " + fnr + "\nID: " + id;
   }
}
