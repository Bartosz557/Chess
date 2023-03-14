import java.io.*;


public class Chessboard {

    String[][] board = new String[8][8];
    String[] order;
    Chessboarddisplay save = new Chessboarddisplay(board);
    Move move = new Move(board);
    static String fields;
    void update() throws IOException
    {
        readfile();
        move.move();
    }

    //sytuacja na szachownicy jest zapisana w pliku txt, kolejnosc pol od A8 do H1, litera na poczatku figury oznacza jej kolor.
    void readfile () throws IOException
    {
        File file = new File(".\\files\\"+"board.txt");
        String path = file.getAbsolutePath();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(path));
            fields = fileReader.readLine();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
        int a=0;

        order = fields.split(" ");
        for(int i=0;i<8;i++ )
        {
            for(int j=0;j<8;j++ )
            {
                board[i][j]=order[a];
                a++;
            }
        }
        save.display();
    }
}
