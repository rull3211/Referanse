class Aapning extends HvitRute implements Runnable{
    public Aapning(int r, int k){
        super(r,k);
        return;
    }
    @Override
    public void gaa(String stiHittil){
        String sti = stiHittil + kordRad + "," + kordKol;
        labyrint.leggTilSti(sti); // fullforerer  strengen og legger til strengen i lavyrintens Lenkeliste
        return;
    }
}
