
class Cell:

    def __init__(self):
        self.__nBr = []
        self.__status = False
        self.__statusInd = "O"
        self.__livingnBr = 0
        

    def setStatus(self):
        self.__status = not self.__status
        if self.__status:
            self.__statusInd = "X"
        else:
            self.__statusInd = "O"
        for e in self.__nBr:
            e.setLivingnBr(self.__status)

    
    def setNbr(self, cell):
        self.__nBr.append(cell)

    def getStatus(self):
        return self.__statusInd

    def setLivingnBr(self, bool):
        if bool:
            self.__livingnBr += 1
        else:
            self.__livingnBr -= 1

    def checkCycle(self):
        if self.__livingnBr == 3 and not self.__status:
            self.setStatus()
        if self.__status and (self.__livingnBr < 2 or self.__livingnBr > 3):
            self.setStatus() 
        return self.__status
        
