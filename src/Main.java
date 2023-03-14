import java.util.Scanner;
import java.io.IOException;

public class Main
{
    public static void main(String args[]) throws IOException
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Kontynuuj? - 1 \nNowa gra - 2");
        int b = Integer.parseInt(myObj.nextLine());
        if(b==2)
        {
            reset.reset();
        }
        Chessboard a = new Chessboard();
        while(true) {
            System.out.print("\033[H\033[2J");
            a.update();
        }




    }
}