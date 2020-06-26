from random import randint
from celle import Celle


class Spillebrett:
    def __init__(self, rader, kolonner):  # konstruktør
        self._rader = rader  # parameter en fra konstruktør
        self._kolonner = kolonner  # parametet 2 fra konstruktør
        self.rutenett = [[Celle() for i in range(rader)] for j in range(kolonner)]  # definerer rutenett kolonner -
        #  anantall lister av rader - antall celler
        self.generer()  # kaller generermetoden på seg selv for å generere første generasjon
        self.gennummer = 0

    def tegnBrett(self):
        for x in self.rutenett:  # tegner brettet med en nøstet forløkke som for hvert liste printer innholdet til lista
            for k in x:  # hver en og en på hver sin linje (altså en liste på en linje)
                print(k.hentStatusTegn(), end='  ')
            print()

    def oppdatering(self):
        doed = []  # lista for celler som skal være døde i neste generasjon
        levende = []
        for x in range(self._rader):  # går gjennom alle cellene i rutenettet
            for y in range(self._kolonner):
                Lnabo = 0  # hver celle får en levende naboteller
                for k in self.finnNabo(x, y):  # for hver celle kalles finn nabo på og lista gåes gjennom

                    if k.erLevende():  # dersom den aktuelle naboen er levende får lnabo +1
                        Lnabo += 1

                if self.rutenett[x][y].erLevende() and Lnabo == 2 or Lnabo == 3:  # dersom en levende celle har 2 eller
                    # 3 levende naboer blir den i levende lista
                    levende.append(self.rutenett[x][y])
                elif not self.rutenett[x][y].erLevende() and Lnabo == 3:  # dersom cella er død og har nøyaktig 3
                    # levende naboer blir den lagt til i levende lista
                    levende.append(self.rutenett[x][y])
                else:  # resten av cellene vil i dette tilfelle oppfylle kravene for å alle bli lagt til i død lista
                    doed.append(self.rutenett[x][y])

        for x in doed:  # alle i doed lista dør
            x.settDoed()
        for x in levende:  # alle i levende lista blir levende
            x.settLevende()
        self.gennummer += 1  # generasjonsnummer øker med 1

    def finnAntallLevende(self):
        antall_levende = 0  # telleren
        for R in self.rutenett:  # går gjennom alle celler og dersom cella er levende øker telelren med en
            for C in R:
                if C.erLevende():
                    antall_levende += 1
        return antall_levende  # telelr returneres

    def generer(self):
        for r in range(self._rader):
            for t in range(self._kolonner):  # går gjennom alle celler
                lev = randint(0, 2)  # velger et tilfeldig tall mellom 0 og 1
                if lev == 0:  # dersom tallet er 0 skal cella være levende i første generasjon
                    self.rutenett[r][t].settLevende()


    def finnNabo(self, x, y):
        er_nabo = []  # lista med naboer (tom liste)
        for a in range(-1, 2):  # alle potensielle rad naboer
            for k in range(-1, 2):  # alle potensielle kolonne naboer
                NR = a + x  # nabo rad
                NK = k + y  # nabo kolonne
                if NR != x or NK != y:  # disse to er den aktuelle cella
                    if NR >= 0 and NR < self._rader:  # denne og neste linje sikrer at gitte nabocella finnes
                        if NK >= 0 and NK < self._kolonner:
                            er_nabo.append(self.rutenett[NR][NK])  # legger til nabocella
        return er_nabo  # returnerer en liste med naboer

