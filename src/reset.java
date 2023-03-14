import java.io.*;

public class reset {
    public static void reset()throws IOException
    {
        String startingboard;
        File file = new File(".\\files\\"+"startboard.txt");
        String path = file.getAbsolutePath();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(path));
            startingboard = fileReader.readLine();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
        File file2 = new File(".\\files\\"+"board.txt");
        path = file2.getAbsolutePath();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(startingboard);
        writer.close();
        File player = new File(".\\files\\"+"nextmove.txt");
        String path2 = player.getAbsolutePath();
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(path2));
        writer2.write("b");
        writer2.close();
        String[] files = {"8","4","15","11","12","19","5","6","7","18","13","10"};
//      8 - krol gora
//      15 - krol dol
//      4 - lewo gora
//      12 - prawo gora
//      11 - lewo dol
//      19 - prawo dol
        for(int i=0;i<6;i++)
        {
            File ifclasted = new File(".\\files\\"+files[i]+".txt");
            String path3 = ifclasted.getAbsolutePath();
            BufferedWriter writer3 = new BufferedWriter(new FileWriter(path3));
            writer3.write("0");
            writer3.close();

        }
        String color="b";
        int a=0;
        for(int i=0;i<16;i++)
        {

            if(i>7) {
                color = "w";
                a=8;
            }
            File possibleep = new File(".\\files\\"+color+(i-a)+".txt");
            String path4 = possibleep.getAbsolutePath();
            BufferedWriter writer4 = new BufferedWriter(new FileWriter(path4));
            writer4.write("0");
            writer4.close();
        }

    }
}
