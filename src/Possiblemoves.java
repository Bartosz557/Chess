import java.lang.Math;
public class Possiblemoves {
    int moving_row, moving_column, target_row, target_column;
    String enemycolor;
    String[][] board;
    public Possiblemoves(String enemycolor,String[][] board,int mrow, int mcolumn,int trow, int tcolumn)
    {
        this.moving_row = mrow;
        this.moving_column = mcolumn;
        this.target_row = trow;
        this.target_column = tcolumn;
        this.enemycolor = enemycolor;
        this.board = board;
    }
    boolean possiblemove()
    {
        if((board[moving_row][moving_column].charAt(0)+"").equals(enemycolor)|board[moving_row][moving_column].equals("empty"))
        {
            return false;
        }
        boolean ret=true;
        String figurename = board[moving_row][moving_column].substring(1);
        String figurecolor = (board[target_row][target_column].charAt(0))+"";
        switch(figurename)
        {
            case "pawn":
                ret= pawn(figurecolor);
                break;
            case "bishop":
                ret=bishop(figurecolor);
                break;
            case "knight":
                ret=knight(figurecolor);
                break;
            case "rook":
                ret=rook(figurecolor);
                break;
            case "queen":
                ret=queen(figurecolor);
                break;
            case "king":
                ret=king();
                break;
        }
        return ret;
    }
    boolean pawn(String figurecolor){
        String pawncolor = (board[moving_row][moving_column].charAt(0))+"";
        if(pawncolor.equals("b"))
            return checkpawn(-1,figurecolor);
        else
            return checkpawn(1,figurecolor);
    }
    boolean checkpawn(int x,String figurecolor)
    {
        if(target_row == moving_row +(-x)&& moving_column == target_column &&board[target_row][target_column].equals("empty"))
        {
            return true;
        } else if (moving_row ==(3.5+(2.5*x))&& target_row == moving_row +(2*(-x))&& moving_column == target_column &&board[target_row][target_column].equals("empty")) {
            return true;
        }else if (target_row == moving_row +(-x)&&(moving_column == target_column -1|| moving_column == target_column +1)&&figurecolor.equals(enemycolor))
            return true;
        else return false;
    }
    boolean knight(String figurecolor){

        if(figurecolor.equals(enemycolor)||board[target_row][target_column].equals("empty")) {
            if (target_row == moving_row - 2 && target_column == moving_column - 1)
                return true;
            else if (target_row == moving_row + 2 && target_column == moving_column - 1)
                return true;
            else if (target_row == moving_row - 2 && target_column == moving_column + 1)
                return true;
            else if (target_row == moving_row + 2 && target_column == moving_column + 1)
                return true;
            else if (target_row == moving_row - 1 && target_column == moving_column - 2)
                return true;
            else if (target_row == moving_row + 1 && target_column == moving_column + 2)
                return true;
            else if (target_row == moving_row - 1 && target_column == moving_column + 2)
                return true;
            else if (target_row == moving_row + 1 && target_column == moving_column - 2)
                return true;
            else
                return false;
        }else
            return false;
    }//znalezc algorytm zamiast ifow
    boolean bishop(String figurecolor){
        if(Math.abs(target_row - moving_row)==Math.abs(target_column - moving_column)&&(figurecolor.equals(enemycolor)||board[target_row][target_column].equals("empty")))
        {
            int xw=(target_row - moving_row)/Math.abs(target_row - moving_row);
            int xk=(target_column - moving_column)/Math.abs(target_column - moving_column);
            for(int i = 1; i<Math.abs(target_row - moving_row); i++)
            {
                if(!board[moving_row +(i*xw)][moving_column +(i*xk)].equals("empty"))
                    return false;
            }
            return true;
        }else
            return false;
    }
    boolean rook(String figurecolor){
        if(target_row != moving_row && target_column == moving_column &&(figurecolor.equals(enemycolor)||board[target_row][target_column].equals("empty")))//gora dol
        {
            int xw=(target_row - moving_row)/Math.abs(target_row - moving_row);
            for(int i = 1; i<Math.abs(target_row - moving_row); i++)
            {
                if(!(board[moving_row +(i*xw)][moving_column].equals("empty"))) {
                    return false;
                }
            }
            return true;
        }else if(target_row == moving_row && target_column != moving_column &&(figurecolor.equals(enemycolor)||board[target_row][target_column].equals("empty")))
        {
            int xk=(target_column - moving_column)/Math.abs(target_column - moving_column);
            for(int i = 1; i<Math.abs(target_column - moving_column); i++)
            {
                if(!board[moving_row][moving_column +(i*xk)].equals("empty"))
                    return false;
            }
            return true;
        }
        else
            return false;
    }
    boolean queen(String figurecolor){
        if(target_row == moving_row || target_column == moving_column)
            return rook(figurecolor);
        else
            return bishop(figurecolor);
    }
    boolean king(){
        if(board[target_row][target_column].equals("empty")&&Math.abs(target_row - moving_row)<2&&Math.abs(target_column - moving_column)<2)
        {
            String eking = enemycolor +"king";
            int[][] perm = {{1,0},{1,1},{1,-1},{0,-1},{0,1},{-1,0},{-1,1},{-1,-1}};
            for(int k=0;k<8;k++)
            {
                if(target_row +perm[k][0]>7|| target_column +perm[k][1]>7|| target_row +perm[k][0]<0|| target_column +perm[k][1]<0)
                {
                    break;
                }
                String king = board[target_row +perm[k][0]][target_column +perm[k][1]];
                if(king.equals(eking))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
