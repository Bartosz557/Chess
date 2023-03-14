import java.util.Arrays;
import java.util.Scanner;

public class figurechange {

    String[] pieces = {"pawn","rook","bishop","queen","knight"};
    String figure;
    String pawnchanging(String color)
    {
        boolean var=true;
        while(var) {
            System.out.println("podaj na jaka figure chcesz zamienic piona?");
            System.out.println("pawn, rook, bishop, queen, knight");
            Scanner myObj = new Scanner(System.in);
            figure = myObj.nextLine();
            if (Arrays.asList(pieces).contains(figure)) {
                var = false;
            } else
                System.out.println("bledna figura");
        }
        return (color+figure);
    }
}
