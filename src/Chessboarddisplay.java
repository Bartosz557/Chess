import java.text.MessageFormat;

public class Chessboarddisplay    {

    String printcolor;
    String[][] figures;
    Piecesprinting print = new Piecesprinting();
    public Chessboarddisplay(String[][] board)
    {
        figures = board;
    }
    void display()
    {
        System.out.println("        A         B         C         D         E         F         G         H    ");
        for (int row = 0; row < 8; row++) //kazdy rzad szachownicy sklada sie z 5 wierszow
        {
            System.out.println("   ---------------------------------------------------------------------------------");
            for(int i=0; i<5; i++)
            {
                if(i==2)
                {
                    String b = MessageFormat.format(" {0} ", String.valueOf(Math.abs(row-8)));
                    System.out.print(b);
                }else
                {
                    System.out.print("   ");
                }
                for (int column = 0; column < 8; column++) {
                    System.out.print("|");
                    figureprint(row,column,i);
                }
                System.out.print("|");
                System.out.println();
            }
        }
        System.out.print("   ---------------------------------------------------------------------------------");
        System.out.println("\n        A         B         C         D         E         F         G         H    ");
    }
    void figureprint(int row, int column,int i) //metoda wyswietla dokladny wiersz odpowiedniej figury na danym polu
    {
        String figuretype = figures[row][column].substring(1);
        String figurecolor = (figures[row][column].charAt(0))+"";
        // uzylem kolorow ANSI escape code, poniewaz ASCI nie dziala w konsoli inteliJ, ANSI dziala w srodowisku jednak zazwyczaj
        // nie dziala domyslnie w cmd z powodu braku klucza rejestru VirtualTerminalLevel, ktory trzeba stworzyc.
        if(figurecolor.equals("b")) {
            printcolor = "\u001B[31m";  //zmiana koloru tekstu na czerwony (bierki czarne) w konsoli - ANSI escape code
        }
        else {
            printcolor = "\u001B[0m";   //zmiana koloru tekstu na bialy w konsoli - ANSI escape code
        }
        switch(figuretype)
        {
            case "pawn":
                print.pawn(i,printcolor);
                break;
            case "bishop":
                print.bishop(i,printcolor);
                break;
            case "knight":
                print.knight(i,printcolor);
                break;
            case "rook":
                print.rook(i,printcolor);
                break;
            case "queen":
                print.queen(i,printcolor);
                break;
            case "king":
                print.king(i,printcolor);
                break;
            case "mpty":
                System.out.print("         ");
                break;
        }
    }


}
