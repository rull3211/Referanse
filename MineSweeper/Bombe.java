
class Bombe extends IkkeBombe{
    public Bombe(int kolonne, int rad){
        super(kolonne, rad);
    }
    @Override
    public void setTegn(){
        tegn = "*";
    }
}
