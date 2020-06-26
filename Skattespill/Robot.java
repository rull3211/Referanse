import java.util.Random;
public class Robot implements BrukerGrensesnitt{
    protected Random intGen = new Random();
 
    
    public int beOmKommando(String spoersmaal, String[] alternativer) {  // robotklasse som implementerer interface og velger tilfeldig alternativ
        return intGen.nextInt(alternativer.length);
        
    }

    
    public void giStatus(String Status){
        System.out.println(Status);
    }
    


}