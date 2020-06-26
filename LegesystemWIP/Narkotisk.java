class Narkotisk extends Legemiddel{
 int styrke; //narkotisk styrke paa legemidlet 

 public Narkotisk(String n, double p, double mg, int sty){
   super(n, p, mg);
   styrke = sty; //styrke er unikt for Narkotisk og vanedannende
 }
 @Override
 public int hentStyrke(){ //returnerer heltall styrke
   return styrke;
 }
 @Override
 public String toString(){
     return super.toString() + "\nNarkotisk styrke: " + styrke;
 }
 @Override
 public String hentType(){
     return "narkotisk";
 }
}
