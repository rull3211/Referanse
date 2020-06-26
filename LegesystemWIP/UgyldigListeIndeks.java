class UgyldigListeIndeks extends RuntimeException{ //brukes i lenkelister 
  UgyldigListeIndeks(int indeks){
    super("Ugyldig indeks: "+indeks);
  }
}
