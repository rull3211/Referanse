from spillebrett import Spillebrett


def Main():
    bredde = int(input("hvor bred skal rutenettet være?"))  # lar bruker velge bredde på brett
    lengde = int(input("hvor lang skal rutenettet være?"))  # lar bruker velge lengde på brett
    spillebrett1 = Spillebrett(bredde, lengde)  # oppretter objektet spillebrett med de to angitte parametere
    spillebrett1.tegnBrett()  # tegner gen 0
    print("Generasjon:" + " " + str(spillebrett1.gennummer) + " - " + "Antall levende celler:"
          + str(spillebrett1.finnAntallLevende()))  # printer antall levende celler og gen nummer
    kommando = "" # oppretter kommandovariabel for den kontinuerlig kjørende løkka ( programmet)

    while kommando != "q":  # man skriver 1 i kommando for å avslutte
        kommando = input("trykk enter for aa generere og skriv q for aa avslutte")  # tar input som kommando
        if kommando != "q":  # om kommando ikke er q utføres opdateringen ellers avsluttes program for man looper til topp
            spillebrett1.oppdatering()  # oppdaterer spillebrett objekt
            spillebrett1.tegnBrett()  # tegner opp det nye brettet
            print("Generasjon:" + " " + str(spillebrett1.gennummer) + " - " + "Antall levende celler:"
                  + str(spillebrett1.finnAntallLevende())) # printer antall levende celler og gen nummer


Main()
