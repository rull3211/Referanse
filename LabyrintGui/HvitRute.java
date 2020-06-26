class HvitRute extends Rute implements Runnable{
    protected char tegn = '.';
    public HvitRute(int r, int k){
        super(r,k);
        return;
    }

    @Override
    public char tilTegn(){
        return tegn;
    }
}
