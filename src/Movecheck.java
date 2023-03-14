import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Dictionary;
import java.util.Hashtable;

public class Movecheck {
    String[][] board;
    boolean ifprint; //zmienna decyduje czy wypisywac powiadomienie o szachu
    int moving_row, moving_column, target_row, target_column,check;
    static String print;
    String moving_piece, movingcolor,x,target,enemycolor;

    public Movecheck(String color,String[][] board,int mrow,int mcolumn,int trow,int tcolumn) //konstruktor wzywany gdy sprawdzamy czy dany ruch jest mozliwy ze wzgledu na ew szach
    {
        this.movingcolor =color;
        this.board = board;
        this.moving_row = mrow;
        this.moving_column = mcolumn;
        this.target_row = trow;
        this.target_column = tcolumn;
        this.moving_piece = this.board[mrow][mcolumn];
        this.target = this.board[trow][tcolumn];
        this.ifprint = true;
    }
    public Movecheck(String[][] field,String color) //konstruktor wzywany gdy sprawdzamy czy na aktualnej sytuacji na szachownicy nie wystepuje szach
    {
        this.movingcolor =color;
        board = field;
        ifprint = true;
    }

    void checking() throws IOException
    {
        board[moving_row][moving_column]="empty";       //wykonanie ruchu na szachownicy
        board[target_row][target_column]= moving_piece;
        ifcheck();
        if (check == 1) {
            String ret = MessageFormat.format("Ruch bierka {0} niemozliwy.", moving_piece.substring(1));
            System.out.println(ret);
            board[moving_row][moving_column]= moving_piece;  //przywrocenie sytuacji przed ruchem jesli jest on nie mozliwy
            board[target_row][target_column]= target;
            Move.cavalue();   //przywrocenie podstawowej wartosci 0 dla zmiennej check
        }
        if(moving_piece.substring(1).equals("pawn"))   //ustawianie parametru dla bicia w przelocie dla danego piona
        {
            if(moving_row - target_row ==2| moving_row - target_row ==-2) {
                Saveboard.writetofile(movingcolor, moving_column);
            }
        }
        if(moving_piece.substring(1).equals("rook")|| moving_piece.substring(1).equals("king"))  //ustawianie parametru dla mozliwosci roszady dla danej figury
        {
            int distance;
            if(moving_column ==0)
            {
                distance=2;
            }else
            {
                distance=-1;
            }
            Saveboard.Savecastling(String.valueOf(moving_row +(2*(moving_column +distance))));
        }
    }
    boolean correctmove() //metoda wywolywana tylko przy sprawdzaniu czy jest sytuacja matowa, sprawdza kazdy kolejny mozliwy ruch
    {
        ifprint=false;
        board[moving_row][moving_column]="empty";
        board[target_row][target_column]= moving_piece;
        if(ifcheck()==0)
        {
            board[moving_row][moving_column]= moving_piece;
            board[target_row][target_column]=target;
            return true;
        }else
        {
            board[moving_row][moving_column]= moving_piece;
            board[target_row][target_column]=target;
            return false;
        }
    }
    int ifcheck()
    {
        if(movingcolor.equals("b"))
        {
            enemycolor="w";
        }else
            enemycolor="b";
        for(int row=0; row<8; row++) {
            for (int column = 0; column < 8; column++) {
                x = board[row][column].substring(1);
                String figurecolor = (board[row][column].charAt(0)) + "";
                if (figurecolor.equals(enemycolor)) {
                    switch (x) {
                        case ("pawn"):
                            check = pawn(row, column);
                            break;
                        case ("bishop"):
                            check = bishop(row, column);
                            break;
                        case "knight":
                            check = knight(row, column);
                            break;
                        case "rook":
                            check = rook(row, column);
                            break;
                        case "queen":
                            check = queen(row, column);
                            break;
                        default:
                    }
                }
                if(check==1) {
                    if(ifprint) {
                        Dictionary positions = new Hashtable();  //slownik zamieniajacy litere kolumny na index
                        positions.put(0, "a");
                        positions.put(1, "b");
                        positions.put(2, "c");
                        positions.put(3, "d");
                        positions.put(4, "e");
                        positions.put(5, "f");
                        positions.put(6, "g");
                        positions.put(7, "h");
                        print = MessageFormat.format("\n\nSzach bierki {0} na pozycji {1}{2}", x, positions.get(column).toString(), Math.abs(row - 8));
                        print();
                    }
                    return 1;
                }

            }
        }
        return 0;
    }
    static void print()
    {
        System.out.println(print);
    }
    int pawn(int row,int column)
    {
        if(movingcolor.equals("w")) {
            if(row+1<8&&column+1<8&&column-1>-1)
            {
                if (board[row + 1][column + 1].equals(movingcolor +"king"))
                    return 1;
                else if(board[row + 1][column - 1].equals(movingcolor + "king"))
                    return  1;
                else
                    return  0;
            }
        }else
        {
            if(row-1>-1&&column+1<8&&column-1>-1)
            {
                if (board[row - 1][column + 1].equals("king"))
                return 1;
                else if(board[row - 1][column - 1].equals(movingcolor + "king"))
                return  1;
            else
                return  0;
            }
        }
        return 0;
    }
    int bishop(int row,int column)
    {
        int qq=0,ww=0,ee=0,rr=0;
        for(int i=1;i<8;i++) {
            if(column+i<8&&row+i<8&&qq==0) {
                if (!board[row + i][column + i].equals("empty") && !board[row + i][column + i].equals(movingcolor + "king")) {
                    qq = 1;
                }else if (board[row + i][column + i].equals(movingcolor + "king")) {
                    return 1;
                    }
                }
                if(column-i>-1&&row-i>-1&&ww==0) {
                    if (!board[row - i][column - i].equals("empty") && !board[row - i][column - i].equals(movingcolor + "king")) {
                        ww = 1;
                    }
                    else if (board[row - i][column - i].equals(movingcolor + "king")) {
                        return 1;
                    }
                }
                if(column-i>-1&&row+i<8&&ee==0) {
                    if (!board[row + i][column - i].equals("empty") && !board[row + i][column - i].equals(movingcolor + "king")) {
                        ee = 1;
                    }
                    else if (board[row + i][column - i].equals(movingcolor + "king")) {
                        return 1;
                    }
                }
                if(column+i<8&&row-i>-1&&rr==0) {
                    if (!board[row - i][column + i].equals("empty") && !board[row - i][column + i].equals(movingcolor + "king")) {
                        rr = 1;
                    }
                    else if (board[row - i][column + i].equals(movingcolor + "king")) {
                        return 1;
                    }
                }
        }
        return 0;
    }
    int knight(int row,int column)  //cos innego niz 8 ifow
    {
        if(row + 2 < 8 && column + 1 < 8) {
            if (board[row + 2][column + 1].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row + 2 < 8 && column - 1 > -1) {
            if (board[row + 2][column - 1].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row - 2 > -1 && column + 1 < 8) {
            if (board[row - 2][column + 1].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row - 2 > -1 && column - 1 > -1) {
            if (board[row - 2][column - 1].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row + 1 < 8 && column + 2 < 8) {
            if (board[row + 1][column + 2].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row - 1 > -1 && column + 2 < 8) {
            if (board[row - 1][column + 2].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row + 1 < 8 && column - 2 > -1) {
            if (board[row + 1][column - 2].equals(movingcolor + "king")) {
                return 1;
            }
        }
        if(row - 1 > -1 && column - 2 > -1) {
            if (board[row - 1][column - 2].equals(movingcolor + "king")) {
                return 1;
            }
        }
        return 0;

    }
    int rook(int row,int column)
    {
        int q=0,w=0,e=0,r=0;
        for(int i=1;i<8;i++) {
            if(column+i<8&&q==0) {
                if (!board[row][column + i].equals("empty") && !board[row][column + i].equals(movingcolor + "king"))
                    q=1;
                else if (board[row][column + i].equals(movingcolor + "king")) {
                    return 1;
                }
            }
            if((column- i) > (-1)&&w==0) {
                if (!board[row][column - i].equals("empty") && !board[row][column - i].equals(movingcolor + "king"))
                    w=1;
                else if (board[row][column - i].equals(movingcolor + "king")) {
                    return 1;
                }
            }
            if(row+i<8&&e==0) {
                if (!board[row + i][column].equals("empty") && !board[row + i][column].equals(movingcolor + "king"))
                    e=1;
                else if (board[row + i][column].equals(movingcolor + "king")) {
                    return 1;
                }
            }
            if(row-i>-1&&r==0) {
                if (!board[row - i][column].equals("empty") && !board[row - i][column].equals(movingcolor + "king"))
                    r=1;
                else if (board[row - i][column].equals(movingcolor + "king")) {
                    return 1;
                }
            }
        }
        return 0;
    }
    int queen(int row,int column)
    {
        if(rook(row,column)==1||bishop(row,column)==1) {
            return 1;
        }else
            return 0;
    }

}
