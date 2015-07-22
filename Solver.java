import java.util.Comparator;

public class Solver
{
   private static Comparator<Node> boardComparator = new Comparator<Node>()
    {
        public int compare(Node o1, Node o2)
        {
            return o1.board.manhattan() - o2.board.manhattan();
        }
    };
    private MinPQ<Node> queue;
    private MinPQ<Node> swapQueue;
    private boolean solvable;
    private boolean swapSolvable;
    private Node endNode;   
    private class Node
    {
        private Board board;
        private Node parent;
        private Node(Board board, Node parent)
        {
            this.board = board;
            this.parent = parent;
        }
    }
      
    public Solver(Board initial)
    {
        if (initial == null)
        {
            throw new java.lang.NullPointerException();
        }
        solvable = false;
        swapSolvable = false;
        
        queue = new MinPQ<Node>(boardComparator);
        swapQueue = new MinPQ<Node>(boardComparator);
        
        Node initialNode = new Node(initial, null);
        queue.insert(initialNode);
        
        Node initialSwap = new Node(initial.twin(), null);
        swapQueue.insert(initialSwap);
        if (initialNode.board.toString().equals("unsolvable"))
        {
            endNode = initialNode;
            return;
        }
         
        while (!solvable && !swapSolvable)
        {
            solvable = solveStep(queue);
            swapSolvable = solveStep(swapQueue);
        }
    }
    
    
    private boolean solveStep(MinPQ<Node> q)
    {
        Node current = q.delMin();
        if (current.board.isGoal())
        {
            endNode = current;
            return true;
        }
        for (Board b: current.board.neighbors())
        {
            if (current.parent == null || !b.equals(current.parent.board))
            {
                Node neighbor = new Node(b, current);
                q.insert(neighbor);
            }
        }
        return false;
    }
    
    public boolean isSolvable()
    {
        return endNode.board.toString().equals("unsolvable");
    }
    
    public int moves()
    {
        if (endNode.board.toString().equals("unsolvable"))
        {
            return -1;
        }
        if (isSolvable())
        {
            Node current = endNode;
            int moves = 0;
            while (current.parent != null)
            {
                moves++;
                current = current.parent;
            }
            return moves;
        }
        else
        {
            return -1;
        }
    }
    
    public Iterable<Board> solution()
    {
        if (endNode.board.toString().equals("unsolvable"))
        {
            return null;
        }
        if (isSolvable())
        {
            Stack<Board> solution = new Stack<Board>();
            Node current = endNode;
            solution.push(endNode.board);
            while (current.parent != null)
            {
                solution.push(current.parent.board);
                current = current.parent;
            }
            return solution;
        }
        else
        {
            return null;
        }
    }
    
    
    public static void main(String [] args)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        int[] [] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
        {
            StdOut.println("No solution possible");
        }
        else
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board: solver.solution())
            {
                StdOut.println(board);
            }
        }
    }
}