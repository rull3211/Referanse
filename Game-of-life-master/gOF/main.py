from spillebrett import Spillbrett

def Main():
    k = ""
    r = ""
    while(True):
        if not k.isdigit():
            k = input("Enter kollum count: ")
            continue
        if not r.isdigit():
            r = input("Enter row count: ")
            continue
        break
    

    s = Spillbrett(int(r), int(k))
    s.setNbrs()
    s.generate()
    s.printBoard()
    
    while(True):
        command = input("Press enter to generate a new cycle og *q* to quit: ")
        if command == "q":
            break
        s.checkCycle()
        s.printBoard()


Main()