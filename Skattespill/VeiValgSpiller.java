public class VeiValgSpiller<T extends BrukerGrensesnitt> extends Spiller{

    public VeiValgSpiller(Sted start, T brukergrensesnitt, String typen, StartSpill kontroller){
        super(start, brukergrensesnitt, typen, kontroller);
    }
    
    @Override
    public void nyttTrekk(){  // veivalg spiller har ikke mange forandringer enn at linje 39 printer alle mulige retningene fra sted
            
            System.out.println(linje);
            grensesnitt.giStatus(typSpiller + " sin tur " + aktuellRom + typSpiller + " har: " + penger + " poeng"); // printer status
            String[] alternativer = getAltISekk(); // oppretter alternativer for salg av gjenstander til salg
            String[] jaNei = new String[]{"1: ja", "2: nei"}; // ja nei alternativer
            int kommando = -10; // en negativ startkommando (tall som ikke brukes) for aa se om man har feil
            if(sekk.getAntallISekk() !=  5 && sekk.getAntallISekk() != 0) // dersom du har noe i sekk og har plass i sekk så faar du valget om aa gaa videre
                kommando = grensesnitt.beOmKommando("Vil du selge noe i sekken din?", alternativer);

            else if (sekk.getAntallISekk() ==  5) { // dersom sekken er full maa du selge noe (gjøres for aa balansere roboten saa den og kan vinne)
                kommando = grensesnitt.beOmKommando("Sekken din er full, du maa selge noe", alternativer);
            }
            if ( sekk.getAntallISekk() == 5){ // dersom sekken er full trenger man ikke aa ta hoeyde for alternativet om aa gaa videre
                trekkIgjen--; // ifen og elseifen er for salg
                penger += sekk.taUt(kommando, aktuellRom.getKiste());
            }
            else if( kommando > 0 ){ // dersom kommando er mer enn 0 og du ogsaa har valgmulighet om aa gaa videre
                trekkIgjen--; 
                penger += sekk.taUt(kommando -1, aktuellRom.getKiste()); // -1 brukes for aa faa rett indeks i sekken ( vi tar hoeyde for alternativ om aa gaa videre)
            }
            Gjenstand tingTattUt = aktuellRom.getKiste().taUt(); // trekk for aa plukke opp gjenstand eller legge den tilbake
            
            if (tingTattUt == null) System.out.println("Skattekista var tom.");
            else{
                kommando = grensesnitt.beOmKommando(typSpiller + " fant " + tingTattUt + ", vil hen ta den med?", jaNei);
                if(kommando == 0) {
                    trekkIgjen--;
                    sekk.plukkGjenstand(tingTattUt);
                }
                else aktuellRom.getKiste().settTilbake(tingTattUt);
            }

            kommando = grensesnitt.beOmKommando(typSpiller + " skal til aa gaa videre, hvor vil hen?", aktuellRom.getVeiAlternativer());
            aktuellRom = aktuellRom.getNesteSted(kommando);
            
    }
}