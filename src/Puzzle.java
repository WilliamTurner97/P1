import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Random;

/*
Current puzzle state and search algorithms
 */
public class Puzzle {

  // current state of the board
  Node currentNode;
  // max number of nodes to search before error
  int maxNodes;
  // desired state of board
  char[][] goalState = new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}};
  // nodes discovered

  /*
  constructor
   */
  public Puzzle(Node startNode) {

      currentNode = startNode;
      maxNodes = 100000;
  }

  // prints current state
  public void printState() { this.currentNode.printState();
  }

  // setter for maxNodes
  public void setMaxNodes(int x){ this.maxNodes = x;
  }

  /* changes current node to that of corresponding move

   */
  public void move(String s) {

      switch(s) {
            case "up":
                currentNode = currentNode.move(0);
                break;
            case "down":
                currentNode = currentNode.move(1);
                break;
            case "left":
                currentNode = currentNode.move(2);
                break;
            case "right":
                currentNode = currentNode.move(3);
                break;
            default:
                break;
        }
    }

  /*
  setter for current node's board
   */
  public void setState(String s1, String s2, String s3) {

      char[][] newBoard = new char[3][3];
      newBoard[0] = s1.toCharArray();
      newBoard[1] = s2.toCharArray();
      newBoard[2] = s3.toCharArray();

      for(int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
              if(newBoard[i][j] == ('b')) {
                  newBoard[i][j] = '0';
              }
          }
      }
      currentNode.setBoard(newBoard);
      currentNode.calcH();
  }

  /*
  makes n random moves
   */
  public void randomizeState(int n){

      Random r = new Random();
      r.setSeed(24);

      for(int i = 0; i < n; i++) {
          currentNode = currentNode.move(r.nextInt(4));
      }

      currentNode.setParent(null);
      currentNode.setD(0);
  }

  /*
   solves with A* with specified heuristic (1 or 2)
   */
  public Node solveA(int h) {

      Comparator comp;

      switch(h) {

          case 1:
              comp = Comparator.comparing(Node:: calcF1);
              break;
          case 2:
              comp = Comparator.comparing(Node :: calcF2);
              break;
          default:
              comp = Comparator.comparing(Node:: calcF2);
              break;
      }

      PriorityQueue<Node> f = new PriorityQueue<>(1, comp );
      f.add(currentNode);
      HashMap<String, Node> reached = new HashMap<>();
      reached.put(boardString(currentNode.getBoard()), currentNode);

      int i = 0;
      while(f.size() > 0 && i < maxNodes) {

          currentNode = f.poll();
          if (Arrays.deepEquals(currentNode.getBoard(), goalState)) {
              printTrace(currentNode);
              System.out.println("A* H" + h + ":");
              System.out.println("nodes explored: " + i);
              System.out.println("solution depth: " + currentNode.getD());
              return currentNode;
          }

          for(Node n: this.expand(currentNode)) {

              i++;
              char[][] c = n.getBoard();
              if( (!reached.containsKey(boardString(c)))
                      || n.getCost() < reached.get(boardString(c)).getCost() ) {
                  reached.put(boardString(c), n);
                  f.add(n);
              }
          }
      }
      return this.currentNode;
  }

  /*
   solves with local beam search with k states using H2 heuristic
   */
  public Node solveBeam(int k) {

      PriorityQueue<Node> f = new PriorityQueue<>(1, Comparator.comparing(Node :: calcF2));
      f.add(currentNode);
      HashMap<String, Node> reached = new HashMap<>();
      reached.put(boardString(currentNode.getBoard()), currentNode);

      int i = 0;
      while(f.size() > 0 && i < maxNodes) {

          currentNode = f.poll();
          if (Arrays.deepEquals(currentNode.getBoard(), goalState)) {
              printTrace(currentNode);
              System.out.println("local beam search:");
              System.out.println("nodes explored: " + i);
              System.out.println("solution depth: " + currentNode.getD());
              return currentNode;
          }

          int v = Math.min(k, f.size());
          for(int j = 0; j < v + 1; j++) {
              for(Node n: expand(currentNode)) {

                  i++;
                  char[][] c = n.getBoard();
                  if( (!reached.containsKey(boardString(c)))
                          || n.getCost() < reached.get(boardString(c)).getCost() ) {
                      reached.put(boardString(c), n);
                      f.add(n);
                  }
              }
          }
      }
      return currentNode;
  }

  /*
  returns a set of nodes for all possible moves from n
   */
  public Node[] expand(Node n) {

      Node[] nodes = new Node[4];

      for(int i = 0; i < 4; i++) {
          nodes[i] = n.move(i);
      }
      return nodes;
   }

   /*
   represents a board array as a string
    */
   public String boardString(char[][] c) {

      StringBuilder s = new StringBuilder();
      for(int i = 0; i < c.length; i++) {
          s.append(Arrays.toString(c[i]));
      }
      return s.toString();
  }

  /*
  prints the board states of the parent-child line from the head to the specified node
   */
  public void printTrace(Node n) {

      System.out.println("+++++++++++++++++");

      Node track = n;
      Stack<Node> path = new Stack<>();

      while(track.getParent() != null) {

          path.push(track);
          track = track.getParent();
      }

      path.push(track);

      while(!path.empty()) {

          path.pop().printState();
      }

      System.out.println("+++++++++++++++++");
  }
}
