import java.util.*;
import java.io.*;
public class Move {
    String[][] board;
    Scanner myObj = new Scanner(System.in);

    boolean castling, enpassant;
    boolean mistake;
    static int check, matecheck;   //Obydwie zmienne pelnia te sama funkcje co castling oraz enpassant wiec powinny byc typu boolean.
    // Na poczatku tworzenia programu utworzylem je w typie int i aktualnie z powodu zbyt wielu rzeczy do ew zmiany pozostawilem je w tym typie.
    int[] board_row = new int[2];
    int[] board_column = new int [2];
    Dictionary positions = new Hashtable();  //slownik zamieniajacy litere kolumny na index
    String player, enemycolor, movingcolor, figurechanging;
    public Move(String[][] board)
    {
        this.board = board;
    }
    static void cavalue()
    {
        check=0;
    }

    String readfile(String filename)throws IOException
    {
        filename=".\\files\\"+filename+".txt";
        String retstring;
        File file = new File(filename);
        String path = file.getAbsolutePath();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(path));
            retstring = fileReader.readLine();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return retstring;
    }
    void move() throws IOException  //metoda move jest o wiele za dluga i zbyt obszerna jednak poczatkowo nie myslalem ze bede musial ja tak rozbudowac
    {                               //dlatego wszystkie te rzeczy znajduja sie tutaj zamiast byc podzielone na odpowiednie metody
        if(matecheck==1)
        {
            Movecheck.print();
        }
        positions.put("a", 0);
        positions.put("b", 1);
        positions.put("c", 2);
        positions.put("d", 3);
        positions.put("e", 4);
        positions.put("f", 5);
        positions.put("g", 6);
        positions.put("h", 7);
        enemycolor =readfile("nextmove");
        if(enemycolor.equals("b"))//ustawianie koloru aktualnego ruchu
        {
            movingcolor ="w";
        }else
            movingcolor ="b";
        if(!possiblemoves(0))//Sprawdzanie czy na szachownicy nie wystepuje sytuacja patowa
        {
            Chessboard a = new Chessboard();
            a.readfile();
            System.out.println("Koniec gry\nPat");
            reset.reset();
            try {
                Thread.sleep(999999);
            } catch(InterruptedException e) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n...");
            }
        }
        Movecheck ifcheck = new Movecheck(board, enemycolor);
        if(enemycolor.equals("b"))//zmienna color okresla kolor nastepnego ruchu nie aktualnego
            player="biale";
        else
            player="czarne";
        System.out.println("\nTeraz wykonuja ruch "+player);
        check=0;
        while(check==0) { //sprawdzanie czy podany ruch moze zostac wykonany
            System.out.println("podaj ruch: (pole na ktorym znajduje sie bierka (enter) i pole docelowe)");
            mistake=true;
            while(mistake)//sprawdzanie czy uzytkownik prawidlowo wprowadzil ruch
            {
                mistake=false;
                for (int i = 0; i < 2; i++) {
                    String position = myObj.nextLine();
                    if (position.length() != 2) {
                        System.out.println("podaj ruch w odpowiednim formacie np:  A2 (enter) A3");
                        mistake = true;
                        break;
                    }
                    else if (!Character.isLetter(position.charAt(0)) || !Character.isDigit(position.charAt(1))) {
                        System.out.println("podaj ruch w odpowiednim formacie np:  A2 (enter) A3");
                        mistake = true;
                        break;
                    }
                    position = position.toLowerCase();
                    String[] str = position.split("");
                    board_row[i] = Math.abs(Integer.parseInt(str[1]) - 8);
                    board_column[i] = (int) positions.get(str[0]);
                }
            }

            figurechanging="no"; //sprawdzanie czy pionek znajduje sie na pierwszym polu przeciwnika w celu wymiany bierki
            if(board[board_row[0]][board_column[0]].equals("wpawn")&& board_row[1]==0)
            {
                figurechanging="w";
            }else if(board[board_row[0]][board_column[0]].equals("bpawn")&& board_row[1]==7)
            {
                figurechanging="b";
            }
            if(board[board_row[0]][board_column[0]].equals(movingcolor +"pawn")&& board_column[0]!= board_column[1])//Sprawdzanie bicia w przelocie
            {
                enpassant checkingep = new enpassant(board, enemycolor, board_row[0], board_column[0], board_row[1], board_column[1]);
                enpassant = checkingep.checking();
                if(enpassant)
                {
                    int direction;
                    if(enemycolor.equals("b"))
                        direction=1;
                    else
                        direction=-1;
                    board[board_row[1]+direction][board_column[1]]="empty";
                }
            }
            if(board[board_row[0]][board_column[0]].equals(movingcolor +"king")&& board[board_row[1]][board_column[1]].equals("empty")&&castlingfield())//Sprawdzanie roszady
            {
                castling=ifcastling();
            }else
                castling=false;
            check = 1;
            Possiblemoves ifcorrectmove = new Possiblemoves(enemycolor, board, board_row[0], board_column[0], board_row[1], board_column[1]);
            if (!ifcorrectmove.possiblemove()&&!castling&&!enpassant) //sprawdzanie poprawnosci ruchu uwzgledniajac potencjalna roszade lub bicie w przelocie
            {
                System.out.println("nieprawidlowy ruch\n");
                check = 0;
            } else if(!castling){
                Movecheck checking = new Movecheck(movingcolor, board, board_row[0], board_column[0], board_row[1], board_column[1]);//sprawdzanie czy ruch jest mozliwy ze wzgledu na ew szach
                checking.checking();
            }
        }
        if(!figurechanging.equals("no")) //jesli pionek znajduje sie na pierwszym polu przeciwnika (wymiana piona)
        {
            figurechange pawnchange = new figurechange();
            board[board_row[1]][board_column[1]]=pawnchange.pawnchanging(figurechanging);
        }

        matecheck = ifcheck.ifcheck(); //sprawdzanie czy po wykonaniu ruchu nie wystepuje szach na przeciwniku
        Saveboard save = new Saveboard(board, enemycolor);
        save.savetofile();
        if(matecheck==1) //sprawdzanie czy nie wystepuje mat w wypadku szachu
        {
            if(!possiblemoves(1))
            {
                Chessboard a = new Chessboard();
                a.readfile();
                System.out.println("koniec gry\nSzach mat");
                reset.reset();
                try {
                    Thread.sleep(999999);
                } catch(InterruptedException e) {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n...");
                }
            }
        }
    }
    boolean castlingfield() //sprawdzanie czy roszada chce byc wykonana na odpowiednich polach, dwa poprzednie warunki moga byc spelnione w nieodpowiednich polach
    {
        if((board_row[1]==0|| board_row[1]==7)&&(board_column[1]==2| board_column[1]==6))
        {
            return true;
        }
        return false;
    }
    boolean ifcastling()throws IOException //sprawdzanie czy roszada moze zostac wykonanna
    {
            if(readfile(String.valueOf(board_row[0]+(board_column[0]*2))).equals("0")&&readfile(String.valueOf(board_row[1]+(2* board_column[1]))).equals("0"))
            {
                int direction,rookcolumn;
                if(board_column[1]==2)
                {
                    rookcolumn=0;
                    direction=(-1);
                }else
                {
                    rookcolumn=7;
                    direction=1;
                }
                for(int i=1;i<3;i++) //sprawdzanie kazdej kolejnej pozycji krola podczas wykonywania roszady
                {
                    if(!(board[board_row[0]][board_column[0]+(i*direction)].equals("empty"))&&!(board[board_row[0]][board_column[0]+(i*direction)].equals(movingcolor +"king")))
                    {
                        return false;
                    }
                    board[board_row[0]][board_column[0]+((i-1)*direction)]="empty";
                    board[board_row[0]][board_column[0]+(i*direction)]= movingcolor +"king";
                    Movecheck checkingmate = new Movecheck(board, movingcolor); //sprwadzanie czy w kazdej kolejnej pozycji nie wystepuje szach przeciwnika
                    if(checkingmate.ifcheck()==1)
                    {
                        board[board_row[0]][board_column[0]] = movingcolor +"king";
                        board[board_row[0]][board_column[0]+(direction)]="empty";
                        board[board_row[0]][board_column[0]+(2*direction)]="empty";
                        return false;
                    }
                }
                board[board_row[1]][rookcolumn]="empty";
                board[board_row[0]][board_column[0]+(direction)]= movingcolor +"rook";
                Saveboard.Savecastling(String.valueOf(board_row[0]+(board_column[0]*2)));   //zmiana parametru w plik decydujaca czy roszada moze byc wykonana przez dana figure
                Saveboard.Savecastling(String.valueOf(board_row[1]+(2* board_column[1])));
                return true;
            }
            return false;
    }
    boolean possiblemoves(int whichcolor) throws IOException //sprawdzanie czy istnieja mozliwe ruchu dla danego koloru bierek
    {
        String c1,c2;
        if(whichcolor==1) //ustawianie tego czy w tej metodzie maja byc sprawdzane mozliwe ruchy wykonujacego czy przeciwnika
        {
            c1= movingcolor;
            c2= enemycolor;
        }
        else
        {
            c1= enemycolor;
            c2= movingcolor;
        }
        int possibilities =0;
        for(int row=0; row<8; row++) {
            for (int column = 0; column < 8; column++) {
                for(int row2=0; row2<8; row2++) {
                    for (int column2 = 0; column2 < 8; column2++) {
                        Possiblemoves ifpossiblemove = new Possiblemoves(c1, board, row, column, row2, column2);
                        Movecheck ifcorrectmove = new Movecheck(c2, board, row, column, row2, column2);
                        if(ifpossiblemove.possiblemove()&&ifcorrectmove.correctmove())
                        {
                            possibilities++;
                        }
                    }
                }

            }
        }
        if(possibilities>0)
        {
            return true;
        }else
            return false;
    }
}
