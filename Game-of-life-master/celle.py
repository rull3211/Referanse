class Celle:
    def __init__(self,):
        self.status = False  # cellen er død i utgangspunktet

    def settDoed(self):  # denne dreper cellen
        self.status = False

    def settLevende(self):  # gjennopliver celle
        self.status = True

    def erLevende(self):  # returnerer cellens status altså true eller false
        return self.status

    def hentStatusTegn(self):
        if self.erLevende():
            return "O"  # om cellen er levende retunreres O ellers en .
        else:
            return "."
