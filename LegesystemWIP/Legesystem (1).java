import java.util.Scanner; //importerer scanner for aa ta input
import java.util.ArrayList; //importerer ArrayList til lenkelistene
import java.io.FileWriter; //importerer fileWriter for aa kunne skrive til fil
import java.io.IOException; //brukes her til filskriving (hvis fil ikke er funnet)

class Legesystem {
  public Legesystem(){ //Konstruktoeren til Legesystem
    fil = new InnLesningsMetoder("txt.txt");
    resepter = fil.returR();
    leger = fil.returLege();
    legemidler = fil.returL();
    pasienter = fil.returP();
  }
  InnLesningsMetoder fil;
  Stabel<Resept> resepter;
  SortertLenkeliste<Lege> leger;
  Lenkeliste<Legemiddel> legemidler;
  Lenkeliste<Pasient> pasienter;

  public String input(String prompt){ // Scanner metode for aa ta inn input
    System.out.println(prompt); // Sender ut parameter til terminal
    Scanner input = new Scanner(System.in); // tar inn input
    return input.nextLine().trim().toLowerCase(); // returnerer string i lower case
  }

  public void Kommandoloekke(){ //Kommandoloekke som kjorer til brukeren avslutter
     boolean paa = true;
     while(paa){
        String kommando = input("Velkommen til vaart legesystem!" +
        "\n[1] = Oversikt \n[2] = Legg til nytt element \n[3] = Bruk resept"
        + "\n[4] = Statistikk \n[5] = Skriv data til fil \n[6] = Avslutt programmet");

        if(kommando.equals("1")){ //Skriver ut oversikter over hele eller deler av legesystemet
          System.out.println("Hva oensker du oversikt over? ");
           kommando = input("\n[1] = Oversikt over hele legesystemet" +
           "\n[2] = Oversikt over alle legemidler \n[3] = Oversikt over alle resepter"
           +"\n[4] = Oversikt over alle leger \n[5] = Oversikt over alle pasienter");
           if(kommando.equals("1")){
            skrivUtLegesystem();
           } else if(kommando.equals("2")){
            skrivUtLegemidler();
           } else if(kommando.equals("3")){
            skrivUtResepter();
           } else if(kommando.equals("4")){
            skrivUtLeger();
           } else if(kommando.equals("5")){
            skrivUtPasienter();
           } else {
            System.out.println("Feil kommando, gaar tilbake til hovedmeny");
           }
        }
        else if(kommando.equals("2")){ //Legge til nytt element
          System.out.println("\nHvilket element oensker du aa legge til?");
          String elementKommando = input("[1] = Legg til ny pasient"
          +"\n[2] = Legg til ny lege"
          +"\n[3] = Legg til nytt legemiddel"
          +"\n[4] = Legg til ny resept");
          if(elementKommando.equals("1")){
            leggTilPasient();
            System.out.println(linje(45));
          }
          else if(elementKommando.equals("2")){
            leggTilLege();
            System.out.println(linje(45));
          }
          else if(elementKommando.equals("3")){
            leggTilLegemiddel();
            System.out.println(linje(45));
          }
          else if(elementKommando.equals("4")){
            leggTilResept();
            System.out.println(linje(45));
          }
          else{
            System.out.println("Feil input, gaar tilbake til hovedmeny");
            //skal ikke gjoere noe, men gaa tilbake til hovedmeny
          }
        }
        else if(kommando.equals("3")){ //Bruke en resept
          brukResept();
        }
        else if(kommando.equals("4")){ //Skrive ut forskjellige statistikk
          System.out.println("\nHvilken statistikk oensker du oversikt over? ");
          String statKommando = input("[1] = Statistikk over totalt utskrevne vanedannende resepter"
          +"\n[2] = Statistikk over totalt utskrevne narkotiske resepter"
          +"\n[3] = Statistikk over leger og antall narkotiske legemidler skrevet ut"
          +"\n[4] = Statistikk over pasienter og totalt antall resepter paa narkotiske legemidler");
          if(statKommando.equals("1")){
            skrivStatistikk("0");
            System.out.println(linje(45));
          }
          else if(statKommando.equals("2")){
            skrivStatistikk("1");
            System.out.println(linje(45));
          }
          else if(statKommando.equals("3")){
            statistikkMisbruk("0");
            System.out.println(linje(45));
          }
          else if(statKommando.equals("4")){
            statistikkMisbruk("1");
            System.out.println(linje(45));
          }
          else{
            System.out.println("Feil input, gaar tilbake til hovedmeny");
            //skal ikke gjoere noe, men gaa tilbake til hovedmeny
          }
        }
        else if(kommando.equals("5")){ //Skriver dataen i legesystemet til fil
          skrivTilFil();
          System.out.println("Data er lagret til filen \"txt.txt\"");
        }
        else if(kommando.equals("6")){ //Avslutter programmet
          String avslutter = input("Du er i ferd med aa avslutte, oensker du aa lagre til fil?"
          +"\n[1] = Ja \n[2] = Nei");
          boolean avslutt = false;
          while(!avslutt){
            if(avslutter.equals("1")) {
              skrivTilFil();
              System.out.println("Data er lagret til filen \"txt.txt\"");
              paa = false;
              avslutt = true;
            }
            else if(avslutter.equals("2")) {
              paa = false;
              avslutt = true;
            }
            else{
              System.out.println("Feil input, prov paa nytt");
              avslutt = false;
              avslutter = input("Oensker du aa lagre til fil?"
              +"\n[1] = Ja \n[2] = Nei");
            }
          }
        }

        else{
           System.out.println("Feil input, prov igjen");
        }
      }
    }

  public void leggTilPasient() { //legger til ny pasient i listen med pasienter
    String skrivUt = input("\nVil du skrive ut eksisterende pasienter?" +
    "\n [1] = ja \n [2] = nei");
    if (skrivUt.equals("1")) { // Dersom brukeren skriver 0 saa skrives ut alle pasientene
      skrivUtPasienter();
    }
    else if(!(skrivUt.equals("2") || skrivUt.equals("1"))){
      System.out.println("[Error] - Feil input, prov paa nytt");
      leggTilPasient();
      return;
    }
    String navn = input("\nSkriv inn navn til pasienten: ");
    if (navn.equals("")) {
        System.out.println("[error] - navn er ikke fylt inn:");
        leggTilPasient();
        return;
    }
    String fnr = input("\nSkriv inn foedeselsnummer til pasienten: ");

    // Sjekker om personnummer er 11 tegn

    while(fnr.length() != 11){
      System.out.println("\n[Error] - Ugyldig foedeselsnummer");
      fnr = input("\nSkriv inn foedeselsnummer til pasienten: ");
    }
    // Gaar igjennom liste med alle pasienter og sjekker om navn og personnummer
    // er likt det som ble sendt inn. I saa fall faar brukeren muligheter til aa
    // proeve med nytt navn eller avslutte og gaa tilbake til menyen
    for (Pasient p : pasienter) {
      if (p.hentNavn().toLowerCase().equals(navn) && p.hentFnr().equals(fnr)) { // Dersom navn og fnr er det samme som allerede er registrert
        String valg = input("[Error] - Pasienten er allerede i systemet. oensker du aa legge inn en annen pasient?" +
        "\n [0] = ja \n [Enter] = nei");
        if (valg.equals("0")) {
          leggTilPasient(); // Rekursjon
          return;
        }
        return;
      }
    }
    pasienter.leggTil(new Pasient(navn, fnr)); // Legger ny pasistent i listen
    System.out.println("Pasienten er lagt inn i systemet");
    return;
  }

  public void leggTilLege() { //legger til ny lege i listen med leger
    String skrivUt = input("Vil du skrive ut eksisterende leger?" +
    "\n [1] = ja \n [2] = nei");
    if (skrivUt.equals("1")) {
      skrivUtLeger(); // skriver ut alle legene ved hjelp av metodekall
    }
    else if(!(skrivUt.equals("1") || skrivUt.equals("2"))){
      System.out.println("[Error] - Feil input, prov paa nytt");
      leggTilLege();
      return;
    }

    String navn = input("Skriv inn navn til legen");

    if (navn.equals("")) {
      System.out.println("[Error] - Ugyldig navn");
      leggTilLege();
      return;
    }


    // Gaar igjennom listen med leger og sjekker om legen enten
    // ligger alt i systemet eller ikke er lagt inn
    for (Lege l : leger) { // Gaar igjennom listen med leger
        boolean bryt = false;
      while (!bryt) { // Saa lenge bryt er false
        boolean ingen = true;
        if (l.hentNavn().toLowerCase().trim().equals(navn)) { // Sjekker om navnet som ble skrevet ligger i listen over leger
          navn = input("\nLegen er allerede i systemet. Skriv inn nytt navn" +
          "\n [Enter] = for aa gaa tilbake til meny");
          ingen = false;
          if (navn.equals("")) { // Dersom enter er blitt trykket saa avsluttes metoden
            return;
          }
          if (!l.hentNavn().toLowerCase().trim().equals(navn)) { // Dersom ingen av navnene stemmer overens med navnet som ble skrevet
            ingen = true; // settes ingen til true
          }
        }
        if (ingen) { // ingen er true -> bryt blir satt til true som igjen foerer til at while loopen stoper
          bryt = true;
        }
      }
    }

    // Her oensker vi aa sjekke om legen ogsaa er en spesialist
    // i saa fall maa vi ogsaa ha kontrollID som kan sendes sammen med
    // navnet ved opprettelse av ny spesialist
    String spesialist = input("\nEr legen en spesialist?" +
    "\n [1] = ja \n [2] = nei");
    int kID = 0;
    while(!(spesialist.equals("1") || spesialist.equals("2"))){
      System.out.println("[Error] - Feil input, prov paa nytt");
      spesialist = input("\nEr legen en spesialist?" +
      "\n [1] = ja \n [2] = nei");
    }
    if (spesialist.equals("1")) { // dersom legen som skal legges inn er en spesialist
      boolean godkjent = false;

      while (!godkjent) { // saa lenge godkjent ikke er true
        try {
          kID = Integer.parseInt(input("\nSkriv kontrollID, kun positive heltall")); // forsoeker her aa gjoere string om til int
          godkjent = true; // dersom det lykkes settes godkjent til true
          if (kID <= 0) {
            godkjent = false;
            System.out.println("[Error] - Ugyldig kontrollID");
          }
        } catch (NumberFormatException e) { // viss ikke sender vi ut feilmelding
          System.out.println("\nKontrollID inneholder noe annet en heltall eller mer enn 10 siffer");
        }
      }
      leger.leggTil(new Spesialist(navn, kID)); // legger til spesialist med navn og kontrollID i listen
      System.out.println(navn + " er lagt til i listen med leger");
      return;
    } else if (spesialist.equals("2")) {
      leger.leggTil(new Lege(navn)); // viss ikke legen var en spesialist oppretter vi bare et nytt lege objekt og legger denne inn
      System.out.println(navn + " er lagt til i listen med leger");
      return;
    }
    System.out.println("[Error] - Ugyldig input, gaar tilbake til meny");
    return;
  }

  public void leggTilLegemiddel(){ //legger til nytt legemiddel i listen med legemidler
    String skrivUt = input("Vil du skrive ut eksisterende legemidler?" +
    "\n [1] = ja \n [2] = nei");
    while(!(skrivUt.equals("1") || skrivUt.equals("2"))){
      System.out.println("[Error] - Feil input, prov paa nytt");
      skrivUt = input("Vil du skrive ut eksisterende legemidler?" +
      "\n [1] = ja \n [2] = nei");
    }
    if (skrivUt.equals("1")) {
      skrivUtLegemidler(); // skriver ut alle legene ved hjelp av metodekall
    }
    String navn = input("Skriv navnet paa legemidlet: ");
    if(navn.equals("")){
      System.out.println("[Error] - Ugyldig navn");
      leggTilLegemiddel();
      return;
    }
    int styrke = 0;
    double pris = 0;
    double virkestoff = -1;
    while(pris < 1){ //prover aa lagre pris
      String prisInput = input("Skriv prisen paa legemidlet [desimaltall med punktum]: ");
      if(prisInput.equals("0")){
        System.out.println("[Error] - Ugyldig input");
      }
      else{
        try{
          pris = Double.parseDouble(prisInput);
        }
        catch(NumberFormatException e){
          prisInput = input("[Error] - Ugyldig pris, tast inn pris paa nytt [desimaltall med punktum]");
        }
      }
      if(pris < 1){
        System.out.println("[Error] - Ugyldig input, prov paa nytt");
      }
    }

    while(virkestoff < 0){ //prover aa lagre mengde virkestoff
      String virkestoffInput = input("Skriv antall mg virkestoff [desimaltall med punktum]:");
      if(virkestoffInput.equals("0")){
        System.out.println("[Error] - Ugyldig input");
      }
      else{
        try{
          virkestoff = Double.parseDouble(virkestoffInput);
        }
        catch(NumberFormatException e){
          virkestoffInput = input("[Error] - Ugyldig mengde virkestoff, tast inn mengde paa nytt [desimaltall med punktum]");
        }
      }
      if(virkestoff < 0){
        System.out.println("[Error] - Ugyldig input, prov paa nytt");
      }
    }
    String type = input("Velg type legemiddel: \n[1] = Vanlig \n[2] = Narkotisk \n[3] = Vanedannende");
    while(!(type.equals("1") || type.equals("2") || type.equals("3"))){ //prover aa lagre type
      System.out.println("[Error] - Ugyldig type legemiddel, prov igjen");
      type = input("Velg type legemiddel: \n[1] = Vanlig \n[2] = Narkotisk \n[3] = Vanedannende");

    }
    if(!type.equals("1")){ //prover aa sette styrke hvis narkotisk/vanedannende legemiddel
      while(styrke < 1){
        try{
          if(type.equals("2")){
            styrke = Integer.parseInt(input("Skriv inn narkotisk styrke paa legemidlet [heltall]"));
          }
          else if(type.equals("3")){
            styrke = Integer.parseInt(input("Skriv inn vanedannende styrke paa legemidlet [heltall]"));
          }
        }
        catch(NumberFormatException e){
          System.out.println("Ugyldig styrke, prov paa nytt");
        }
      }
    }

    if(type.equals("1")) {
      legemidler.leggTil(new Vanlig(navn, pris, virkestoff));
      System.out.println("Legemidlet ble lagt til");
    }
    else if(type.equals("2")) {
      legemidler.leggTil(new Narkotisk(navn, pris, virkestoff, styrke));
      System.out.println("Legemidlet ble lagt til");
    }
    else if(type.equals("3")) {
      legemidler.leggTil(new Vanedannende(navn, pris, virkestoff, styrke));
      System.out.println("Legemidlet ble lagt til");
    }
    else {
      System.out.println("[Error] - Noe gikk galt..");
      return;
    }
  }

  public void leggTilResept(){ //legger til ny resept i listen med resepter
    Legemiddel legemiddel = null;
    Lege lege = null;
    Pasient pasient;
    int reit = -1;

    String legemiddelInput = input("Skriv navnet eller ID paa legemidlet eller [Enter] for aa skrive ut liste over legemidler");
    if(legemiddelInput.equals("")) {
      skrivUtLegemidler();
      legemiddelInput = input("Skriv navnet eller ID paa legemidlet");
    }
    while (legemiddel == null){
      for(Legemiddel l : legemidler){ //henter legemiddel-objekt
        if(legemiddelInput.equals(l.hentId()+"") || l.hentNavn().toLowerCase().equals(legemiddelInput)){
          legemiddel = l;

        }
      }
      if(legemiddel == null){
        System.out.println("[Error] - feil input, fant ikke legemidlet");
        legemiddelInput = input("Skriv navn paa legemidlet eller [Enter] for aa skrive ut liste over legemidler");
        if(legemiddelInput.equals("")){
          skrivUtLegemidler();
          legemiddelInput = input("Skriv navn paa legemidlet: ");
        }
      }
    }
    String legeInput = input("Skriv navnet paa legen som skriver ut resepten eller [Enter] for aa skrive ut liste over leger");
    if(legeInput.equals("")){
      skrivUtLeger();
      legeInput = input("Skriv navnet paa legen som skriver ut resepten");
    }
    while (lege == null){ //henter lege-objekt
      for(Lege l: leger){
        if(legeInput.equals(l.hentNavn().toLowerCase())){
          lege = l;
        }
      }
      if(lege == null){
        System.out.println("[Error] - feil input, fant ikke legen");
        legeInput = input("Skriv navn paa legen eller [Enter] for aa skrive ut liste over leger");
        if(legeInput.equals("")){
          skrivUtLeger();
          legeInput = input("Skriv navn paa legen");
        }
      }
    }

    if(legemiddel instanceof Narkotisk && lege.hentKontrollID() == 0){
      System.out.println("[Error] - Legen er ikke spesialist og har ikke lov aa skrive resept paa denne typen legemiddel");
      System.out.println("Returnerer til meny");
      return;
    }

    String type = input("Hvilken type resept vil du legge til?" +
     "\n[1] = Blaaresept \n[2] = Hvitresept \n[3] = Militaerrespet \n[4] = P-resept");
    while(!(type.equals("1") || type.equals("2") || type.equals("3") || type.equals("4"))){
      System.out.println("[Error] - Feil input");
      type = input("Hvilken type resept vil du legge til?" +
       "\n[1] = Blaaresept \n[2] = Hvitresept \n[3] = Militaerrespet \n[4] = P-resept");
    }

    pasient = hentPasient();

    if (pasient == null)return;

    if(!(type.equals("4"))){
      while(reit < 0){
        boolean k = false;
        try{
          reit = Integer.parseInt(input("Skriv in antall reit paa resepten"));
        }
        catch(NumberFormatException e){
          System.out.println("[Error] - Ugyldig reit, prov paa nytt");
          k = true;
        }
        if(reit < 0 && !(k)){
          System.out.println("[Error] - Ugydlig reit, prov paa nytt");
        }
      }
    }
    try{ //legger til resept i resept-liste
      if (type.equals("1")) { //blaa
        resepter.leggTil(lege.skrivBlaaResept(legemiddel, pasient, reit));
        return;
      }
      else if (type.equals("2")) { //hvit
        resepter.leggTil(lege.skrivHvitResept(legemiddel, pasient, reit));
        return;
      }
      else if (type.equals("3")) { //militaer
        resepter.leggTil(lege.skrivMillitaerResept(legemiddel, pasient, reit));
        return;
      }
      else if (type.equals("4")) { //p
        resepter.leggTil(lege.skrivPResept(legemiddel, pasient));
        return;
      }
      else {
        System.out.println("[Error] - Noe gikk galt, gaar tilbake til meny");
        return;
      }
    }
    catch(UlovligUtskrift e){
      System.out.println("Legen har ikke lov aa skrive ut respten");
      return;
    }
  }

  public void skrivUtLeger() { //skriver ut listen med leger
    System.out.println(linje(30));
    System.out.println("Skriver ut leger: ");
    for (Lege l : leger) {
      Lenkeliste<Resept> legeListe = l.hentReseptListe();
      System.out.println("\n" + l);
    }
  }

  public void skrivUtLegemidler() { //skriver ut listen med legemidler
    System.out.println(linje(30));
    System.out.println("Skriver ut legemidler: ");
    for (Legemiddel lm : legemidler) {
      System.out.println("\n" + lm);
    }
  }

  public void skrivUtResepter() { //skriver ut listen med resepter
    System.out.println(linje(30));
    System.out.println("Skriver ut resepter: ");
    for (Resept r : resepter) {
      System.out.println("\n" + r);
    }
  }

  public void skrivUtPasienter() { //skriver ut listen med pasienter
    System.out.println(linje(30));
    System.out.println("Skriver ut Pasieter: ");
    for (Pasient p : pasienter) {
      System.out.println("\n" + p);
    }
  }

  public void skrivUtLegesystem() { //skriver ut listen med legesystemer
    skrivUtLeger();
    skrivUtLegemidler();
    skrivUtResepter();
    skrivUtPasienter();
  }

  public void statistikkMisbruk(String input) { //skriver ut statistikk over leger og resepter eller pasienter og aktive resepter
    if (input.equals("0")) {
      for (Lege l : leger) {
        int teller = 0;
        Lenkeliste<Resept> resepter = l.hentReseptListe();
        for (Resept r : resepter) {
          if (r.hentLegemiddel() instanceof Narkotisk) {
            teller++;
          }
        }
        if (teller > 0) {
          System.out.println(l.hentNavn() + " har skrevet ut " + teller + " resepter paa narkotiske legemidler");
        }
      }
    }
    if (input.equals("1")) {
      for (Pasient p : pasienter) {
        int teller = 0;
        Lenkeliste<Resept> resepter = p.hentReseptliste();
        for (Resept r : resepter) {
          if (r.hentLegemiddel() instanceof Narkotisk && (r.hentReit() > 0)) {
            teller++;
          }
        }
        if (teller > 1) {
          System.out.println(p.hentNavn() + " har " + teller + " aktive resepter paa narkotiske legemidler");
        } else {
          System.out.println(p.hentNavn() + " har 1 aktiv resept paa narkotisk legemiddel");
        }
      }
    }
  }

  public void skrivStatistikk(String input) { //skriver ut statistikk over vanedannende eller narkotiske legemidler
    int teller = 0;

    if (input.equals("0")) {
      for (Resept r : resepter) {
        if (r.hentLegemiddel() instanceof Vanedannende) {
          teller++;
        }
      }
      System.out.println("Total antall utskrevende vanedannende legemidler: " + teller);
      return;
    } else if (input.equals("1")) {
      for (Resept r : resepter) {
        if (r.hentLegemiddel() instanceof Narkotisk) {
          teller++;
        }
      }
      System.out.println("Total antall utskrevende narkotiske legemidler: " + teller);
      return;
    } else {
      System.out.println("[Error] - Feil input, gaar tilbake til meny");
      return;
    }
  }

  public String linje(int i){ //brukes til aa skrive ut stiplet linje i menyer
      String linja = "";
      for(int k = 0; k<i; k++){
          linja += "-";
      }
      return linja;
  }

  public void brukResept(){ //metode for aa bruke resept
        String skrivUt = input("\nDu skal bruke en resept, oensker du aa skrive ut alle pasienter?" +
        "\n [1] = ja \n [2] = nei");
        while(!(skrivUt.equals("1") || skrivUt.equals("2"))){
          System.out.println("[Error] - Ugyldig input, prov paa nytt");
          skrivUt = input("\nDu skal bruke en resept, oensker du aa skrive ut alle pasienter?" +
          "\n [1] = ja \n [2] = nei");
        }
        if (skrivUt.equals("1")) { // Dersom brukeren skriver 0 saa skrives ut alle pasientene
          skrivUtPasienter();
        }
        Pasient pasient = hentPasient();
        if (pasient == null) return;
        Lenkeliste<Resept> resepter = pasient.hentReseptliste();
        if (resepter.stoerrelse() == 0){
            System.out.println("[Error] - brukeren har ingen resepter returnerer til meny");
            return;
        }
        System.out.println("Hvilket resept vil du bruke?");
        for (int i = 0;i < resepter.stoerrelse(); i++) {
            Resept resept = resepter.hent(i);
            Legemiddel legemiddel = resept.hentLegemiddel();
            String legemiddelnavn = legemiddel.hentNavn();
            int antreit = resept.hentReit();
            System.out.println("[" + i + "] - " + legemiddelnavn + " reit: "+ antreit);
        }
        String tilBruk = "";
        int indeksTilBruk = 0;
        boolean godkjent = false;
        while(!godkjent){
        tilBruk = input("Hvilken legemiddel vil du bruke?");
        for (int i = 0; i < resepter.stoerrelse(); i++){
            if(tilBruk.equals(i+"")){ godkjent= true;
                indeksTilBruk = i;
                }
            }
            if(!godkjent)System.out.println("[Error] - Ugyldig input");
        }
        if(resepter.hent(indeksTilBruk).bruk()){
            System.out.println("Brukte resepten, gjenvaerende reit: " + resepter.hent(indeksTilBruk).hentReit());
            return;
        }
        System.out.println("Resepten hadde ikke flere reit");
        return;
    }

  public Pasient hentPasient(){ //henter ut pasient, som brukes i andre metoder
      Pasient pasient = null;
      String pasientInput = input("Skriv navn/pasient-ID/fodselsnummer eller [Enter] for aa skrive ut liste over pasienter");
      if(pasientInput.equals("")) {
        skrivUtPasienter();
        pasientInput = input("Skriv navn/pasient-ID/fodselsnummer eller [Enter] for aa skrive ut liste over pasienter");
      }
      while(pasient == null){ //henter pasient-objekt
        int teller = 0;
        Pasient pasientHolder = null;
        for(Pasient p : pasienter){
          if (p.hentNavn().toLowerCase().equals(pasientInput)) {
            teller++;
            pasientHolder = p;
          }
          else if (pasientInput.equals(p.hentID()+"")) { //+"" hentPasientId gjør om til string
            teller++;
            pasientHolder = p;
          }
          else if (p.hentFnr().equals(pasientInput)) {
            teller++;
            pasientHolder = p;
          }
        }
        if(teller == 1){
          pasient = pasientHolder;
        }
        else{
          System.out.println("[Error] - feil input, tast inn fodselsnummer");
          pasientInput = input("Skriv navn/pasient-ID/fodselsnummer eller [Enter] for aa skrive ut liste over pasienter, \n[9] - for aa returnere til meny");
          if (pasientInput.equals("9"))return null;
          if(pasientInput.equals("")){
            skrivUtPasienter();
            pasientInput = input("Skriv navn/pasient-ID/fodselsnummer eller [Enter] for aa skrive ut liste over pasienter");
          }
        }
      }
      return pasient;
  }

  public void skrivTilFil(){ //skriver naavaerende data til fil
      try{
          FileWriter minskriver = new FileWriter("txt.txt");
          minskriver.write("# Pasienter");
          for(Pasient p: pasienter){
              minskriver.write("\n" + p.hentNavn() + "," + p.hentFnr());
          }
          minskriver.write("\n# Legemidler");
          for(Legemiddel l: legemidler){
              if (l instanceof Vanlig) {
                  minskriver.write("\n" + l.hentNavn() + ","  + l.hentType() + "," + l.hentPris() + "," + l.hentVirkestoff());
              }else{
              minskriver.write("\n" + l.hentNavn() + "," +  l.hentType() + ","+ l.hentPris() + "," + l.hentVirkestoff() + "," + l.hentStyrke());
          }
          }
          minskriver.write("\n# Leger");
          for (Lege le: leger ) {
              minskriver.write("\n"+ le.hentNavn() + "," + le.hentKontrollID());
          }
          minskriver.write("\n# Resepter");
          for(Resept r: resepter){
              int medId = r.hentLegemiddel().hentId();
              String legeNavn = r.hentLege().hentNavn();
              int pasID = r.hentPasientId().hentID();
              if(r.hentType().equals("p"))minskriver.write("\n" + medId + "," + legeNavn + "," + pasID + "," + r.hentType());
              else{
              minskriver.write("\n" + medId + "," + legeNavn + "," + pasID + "," + r.hentType() + "," + r.hentReit());
          }
          }

          minskriver.close();
      }catch (IOException e) {
          System.out.println("feil feil");
      }
  }
}
