public class Piecesprinting {

    String reset = "\u001B[0m"; //resetowanie koloru tekstu w konsoli na bialy, aby "ramki" szachownicy nie byly czerwone w niektorych przypadkach - ANSI escape code
    String[] pawns = {"         ","    ^    ","   / \\   ","   |_|   ","         ",};
    String[] bishops = {"         ","    ^    ","   / \\   ","   |||   ","   /_\\   "};
    String[] knights = {"         ","    ___  ","   <' /  ","    | \\  ","    /_\\  "};
    String[] rooks = {"   ____  ","   ||||  ","   ||||  ","   ||||  ","   |__|  "};
    String[] queen = {"         ","  * * *  ","   \\|/   ","    X    ","   /_\\   "};
    String[] king = {"    +    " , "   /|\\   "   , "   \\|/   " , "    ^    " , "   /_\\   "};

    void pawn(int i, String printcolor)
    {
        System.out.print(printcolor+pawns[i]+reset);
    }
    void bishop(int i, String printcolor)
    {
        System.out.print(printcolor+bishops[i]+reset);
    }
    void knight(int i, String printcolor)
    {
        System.out.print(printcolor+knights[i]+reset);
    }
    void rook(int i, String printcolor)
    {
        System.out.print(printcolor+rooks[i]+reset);
    }
    void queen(int i, String printcolor)
    {
        System.out.print(printcolor+queen[i]+reset);
    }
    void king(int i, String printcolor)
    {
        System.out.print(printcolor+king[i]+reset);
    }

}
