import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class enpassant {
    int direction, moving_row, target_row, moving_column, target_column;
    String [][] board;
    String enemycolor;

    public enpassant(String[][] board,String enemycolor,int mrow, int mcolumn,int trow, int tcolumn)
    {
        this.moving_row = mrow;
        this.moving_column = mcolumn;
        this.target_row = trow;
        this.target_column = tcolumn;
        this.enemycolor = enemycolor;
        this.board = board;
    }
    boolean checking()throws IOException
    {
        if(!possibleep())
            return false;

        if(enemycolor.equals("b"))
            direction=1;
        else
            direction=-1;
        if((moving_column - target_column ==1|| moving_column - target_column ==-1)&& moving_row - target_row ==direction)
            {
                if(board[target_row +direction][target_column].equals(enemycolor +"pawn"))
                {
                    return true;
                }
            }
        return false;
    }

    boolean possibleep()throws IOException
    {
        String ifpossible;
        File file = new File(".\\files\\"+enemycolor + target_column +".txt");
        String path = file.getAbsolutePath();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(path));
            ifpossible = fileReader.readLine();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
        if(ifpossible.equals("1"))
            return true;
        else
            return false;
    }
}
