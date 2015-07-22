public class Board
{
    private static boolean unsolvableChecked = false;
    private char [] []  board;
    private int dimension;
    private int moves;
    
    public Board(int [] [] blocks)
    {
        this(blocks, 0);
    }
    private Board(int [] [] blocks, int moves)
    {
        // Initialize instance fields
        board = new char  [blocks.length][blocks.length];
        dimension = blocks.length;
        this.moves = moves;
        
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
                if ((int) board[i][j] == 0)
                {
                    //StdOut.println("Should be 0 - " + (int)board[i][j]);
                    continue;
                }
                else if ((int) board[i][j] != (int) ((i * dimension) + j) + 1)
                {
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
               if ((int) board[i][j] == 0)
               {
                   continue;
               }
               else if ((int) board[i][j] != (int) ((i * dimension) + j) + 1)
               {
                   //StdOut.println("Working on value: " + (int)board[i][j]);
                   int targetX = ((int) board[i][j] - 1)/ dimension;
                   int targetY = ((int) board[i][j] - 1) % dimension;
                   int dx = i - targetX;
                   int dy = j - targetY;
                   //StdOut.println("dx: " + dx + ", dy:" + dy);
                   manhattanDistance += Math.abs(dx) + Math.abs(dy);
               }
           }
       }
       return manhattanDistance;
    }
    
    public boolean isGoal()
    {
        return this.hamming() == 0;
    }
    
    
    public Board twin()
    {
        int[] [] newBoard = new int[dimension][dimension];
        for (int row = 0; row < dimension; row++)
        {
            for (int col = 0; col < dimension; col++)
            {
                newBoard[row][col] = board[row][col];
            }
        }
        
        
        int rowSwap = 0;
        if (newBoard[0][0] == 0 || newBoard[0][1] == 0)
        {
            rowSwap = 1;
        }
        int temp = newBoard[rowSwap][0];
        newBoard[rowSwap][0] = newBoard[rowSwap][1];
        newBoard[rowSwap][1] = temp;
        
        return new Board(newBoard, moves);
    }
    
    
     public boolean equals(Object y) 
     {
         if (y == null) 
         {
             return false;
         }
     
         if (this == y)
         {
             return true;
         }
     
         if (this.getClass() != y.getClass()) 
         {
             return false;
         }
     
         Board that = (Board) y;
     
         if (this.dimension() != that.dimension()) 
         {
             return false;
         }  
         for (int row = 0; row < this.dimension(); row++) 
         {
             for (int col = 0; col < this.dimension(); col++) 
             {
                 if (this.board[row][col] != that.board[row][col])
                 {
                     return false;
                 }
             }
         }     
         return true;
    }
      
     
    private boolean isSolvable()
    {
        int [] oneBoard = new int [dimension * dimension];
        int count = 0;
        int inversions = 0;
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                oneBoard[count] = board[i][j];
                count++;
            }
        }
        
        for (int k = 0; k < oneBoard.length; k++)
        {
            for (int j = k + 1; j < oneBoard.length; j++)
            {
                if (oneBoard[k] > oneBoard[j] && oneBoard[k] != 0   
                   && oneBoard[j] != 0)
                {
                    inversions++;
                }
            }
        }
        //StdOut.println("INVERSIONS: " + inversions);
        int blankRow = 0;
        int blankCol = 0;
        for (int row = 0; row < dimension; row++)
        {
            for (int col = 0; col < dimension; col++)
            {
                if (board[row][col] == 0)
                {
                    blankRow = row;
                    blankCol = col;
                    break;
                }
            }
        }        
        if (dimension % 2 != 0 && inversions % 2 == 0)
        {
            return true;
        }
        else if (dimension % 2 == 0 && (dimension - blankRow) % 2 == 0 
                     && inversions % 2 != 0)
        {
            return true;
        }
        else if (dimension % 2 == 0 && (dimension - blankRow) % 2 != 0 
                     && inversions % 2 == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public String toString()
    {
       if (!unsolvableChecked)
       {
           unsolvableChecked = true;
           if (!isSolvable())
           {
               return "unsolvable";
           }
       }

       StringBuilder s = new StringBuilder();
       s.append(dimension + "\n");
       for (int i = 0; i < dimension; i++) 
       {
           for (int j = 0; j < dimension; j++) 
           {
            s.append((int) board[i][j] + " ");
           }
        s.append("\n");
       }
           return s.toString();
    }
    
    
    public Iterable<Board> neighbors()
    {
        Stack<Board> myStack = new Stack<Board>();
        int spaceRowPos = 0;
        int spaceColPos = 0;
        //Find space in puzzle
        for (int row = 0; row < dimension; row++)
        {
            for (int col = 0; col < dimension; col++)
            {
                if (board[row][col] == 0)
                {
                    spaceRowPos = row;
                    spaceColPos = col;
                    break;
                }
            }
        }
        
        //Check moving a block upwards from below the space
        if (spaceRowPos < dimension - 1)
        {
            int [] [] upBoard = new int[dimension][dimension];
            for (int row = 0; row < dimension; row++)
            {
                for (int col = 0; col < dimension; col++)
                {
                    upBoard[row][col] = board[row][col];
                }
            }
            
            int temp = upBoard[spaceRowPos][spaceColPos];
            upBoard[spaceRowPos][spaceColPos] = upBoard[spaceRowPos + 1]
                                                       [spaceColPos];
            upBoard[spaceRowPos + 1][spaceColPos] = temp;
            myStack.push(new Board(upBoard, moves + 1));
        }
        
        //Check moving a block downwards from above the space
        if (spaceRowPos > 0)
        {
            int [][] downBoard = new int[dimension][dimension];
            for (int row = 0; row < dimension; row++)
            {
                for (int col = 0; col < dimension; col++)
                {
                    downBoard[row][col] = board[row][col];
                }
            }
            
            int temp = downBoard[spaceRowPos][spaceColPos]; 
            downBoard[spaceRowPos][spaceColPos] = downBoard[spaceRowPos - 1]
                                                           [spaceColPos];
            downBoard[spaceRowPos - 1][spaceColPos] = temp;
            myStack.push(new Board(downBoard, moves + 1));
        }
        
        //Check moving a block rightways from the left
        if (spaceColPos > 0)
        {
            int [][] rightBoard = new int[dimension][dimension];
            for (int row = 0; row < dimension; row++)
            {
                for (int col = 0; col < dimension; col++)
                {
                    rightBoard[row][col] = board[row][col];
                }
            }
            
            int temp = rightBoard[spaceRowPos][spaceColPos]; 
            rightBoard[spaceRowPos][spaceColPos] = rightBoard[spaceRowPos]
                                                             [spaceColPos - 1];
            rightBoard[spaceRowPos][spaceColPos - 1] = temp;
            myStack.push(new Board(rightBoard, moves + 1));            
        }
        
        //Check moving a block leftways from the right
        if (spaceColPos < dimension - 1)
        {
            int [][] leftBoard = new int[dimension][dimension];
            for (int row = 0; row < dimension; row++)
            {
                for (int col = 0; col < dimension; col++)
                {
                    leftBoard[row][col] = board[row][col];
                }
            }
            
            int temp = leftBoard[spaceRowPos][spaceColPos]; 
            leftBoard[spaceRowPos][spaceColPos] = leftBoard[spaceRowPos]
                                                           [spaceColPos + 1];
            leftBoard[spaceRowPos][spaceColPos + 1] = temp;
            myStack.push(new Board(leftBoard, moves + 1));                
        }
        
        return myStack;
    }
    
    public static void main(String [] args)
    {
        In in = new In(args[0]);
        int dim = in.readInt();
        int [] [] array = new int [dim][dim];
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                array[i][j] = in.readInt();
            }
        }
        
        Board myBoard = new Board(array);
        StdOut.println(myBoard.toString());
        Iterable<Board> allBoards = myBoard.neighbors();
        for (Board board : allBoards)
        {
            StdOut.println(board);
        }
    }
}