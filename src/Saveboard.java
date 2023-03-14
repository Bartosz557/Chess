import java.io.*;

public class Saveboard {
    String[][]field;
    String board,color;
    public Saveboard(String[][] a,String color)
    {
        field=a;
        this.color=color;
    }

    void savetofile() throws IOException
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j<8;j++)
            {
                sb.append(field[i][j]+" ");
            }
        }
        board=sb.toString();
        File file = new File(".\\files\\"+"board.txt");  //zapisywanie szachownicy do pliku
        String path = file.getAbsolutePath();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.append(board);
        writer.close();

        File player = new File(".\\files\\"+"nextmove.txt"); //zapisywanie koloru przeciwnika do pliku/koloru nastepnego ruchu
        String path2 = player.getAbsolutePath();
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(path2));
        if(color.equals("b"))
            color="w";
        else
            color="b";
        writer2.write(color);
        writer2.close();
    }
    static void Savecastling(String piece) throws IOException //zapisywanie parametru roszady dla danej figury
    {
        File file = new File(".\\files\\"+piece+".txt");
        String path = file.getAbsolutePath();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.append("1");
        writer.close();
    }
    static void writetofile(String movingcolor,int moving_column)throws IOException //zapisywanie paramtetru bicia w przelocie dla danego piona
    {
        File file = new File(".\\files\\"+movingcolor + moving_column +".txt");
        String path = file.getAbsolutePath();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write("1");
        writer.close();
    }
}
