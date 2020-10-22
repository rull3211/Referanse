from random import randint
from cell import Cell

class Spillbrett:
    def __init__(self, r, k):
        self.__rows = r
        self.__kols = k
        self.__board = [[Cell() for k in range(self.__kols)] for r in range(self.__rows)]
        self.__genNr = 0
        self.__living = 0

    def setNbrs(self):
        for k in range(self.__kols):
            for r in range(self.__rows):
                for i in range(-1, 2):
                    for a in range(-1, 2):
                        ki = k + i
                        ra = r + a
                        if ((ki > -1) and (ki < self.__kols)) and ((ra > -1) and (ra < self.__rows)):
                            if i + a != 0:
                                self.__board[k] [r].setNbr(self.__board[ki] [ra])

    def generate(self):
        for k in self.__board:
            for r in k:
                rand = randint(1, 3)
                if rand == 1:
                    self.__living += 1
                    r.setStatus()
    
    def printBoard(self):
        print("Generation: " + str(self.__genNr) + "Living cells: " + str(self.__living))
        for k in self.__board:
            for r in k:
                print(r.getStatus(), end = "   ")
            print()
            print()

    def checkCycle(self):
        living = 0
        for k in self.__board:
            for r in k:
                if r.checkCycle():
                    living += 1
        self.__living = living
        self.__genNr += 1
        

