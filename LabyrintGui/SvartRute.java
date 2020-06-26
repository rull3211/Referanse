class SvartRute extends Rute implements Runnable{
    public SvartRute(int r, int k){
        super(r,k);
        return;
    }
    public char tilTegn(){
        return '#';
    }
    @Override
    public boolean godkjent(){
        return false;
    }
    @Override
    public void gaa(String stiHittil){
        return; // avslutter itrasjon av rekrsjonen
        //System.out.println("moette vegg fra retningen;" + retning);
    }
}
