public class Board
{
    private char [] []  board;
    private int dimension;
    private int numMoves = 0;
    public Board(int [] [] blocks)
    {
        // Declare a char board and set the dimension field
        board = new char  [blocks.length][blocks.length];
        dimension = blocks.length;
        
        //Initialize board
        for (int i = 0; i < blocks.length; i++)
        {
            for (int j = 0; j < blocks.length; j++)
            {
                board[i][j] = (char) blocks[i][j];
            }
        }
    }
    public int dimension()
    {
        return this.dimension;
    }
    
    public int hamming()
    {
        int count = 0;
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if ((int)board[i][j] == 0)
                {
                    //StdOut.println("Should be 0 - " + (int)board[i][j]);
                    continue;
                }
                else if ((int)board[i][j] != (int)((i * dimension) + j) + 1)
                {
                    //StdOut.println("Array Value: " + (int)board[i][j] + " Calculation: " + (((i * dimension) + j) + 1));
                    count++;
                }                    
            }
        }
        return count;
    }
    
    public int manhattan()
    {
       int manhattanDistance = 0;
       for (int i = 0; i < dimension; i++)
       {
           for (int j = 0; j < dimension; j++)
           {
               if((int)board[i][j] == 0)
               {
                   continue;
               }
               else if ((int)board[i][j] != (int)((i * dimension) + j) + 1)
               {
                   //StdOut.println("Working on value: " + (int)board[i][j]);
                   int targetX = ((int)board[i][j] - 1)/ dimension;
                   int targetY = ((int)board[i][j] - 1) % dimension;
                   int dx = i - targetX;
                   int dy = j - targetY;
                   //StdOut.println("dx: " + dx + ", dy:" + dy);
                   manhattanDistance += Math.abs(dx) + Math.abs(dy);
               }
           }
       }
       return manhattanDistance;
    }
    
    public static void main(String [] args)
    {
        In in = new In(args[0]);
        int dim = in.readInt();
        int [] [] array = new int [dim][dim];
        for(int i = 0; i < dim; i++)
        {
            for(int j = 0; j < dim; j++)
            {
                array[i][j] = in.readInt();
            }
        }
        
       /* for(int k = 0; k < dim; k++)
        {
            for(int l = 0; l < dim; l++)
            {
                StdOut.print(array[k][l] + " ");
            }
            StdOut.print("\n");
        }*/
        Board myBoard = new Board(array);
        StdOut.println(myBoard.hamming());
        StdOut.println(myBoard.manhattan());
    }
}