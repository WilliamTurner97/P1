import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

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
  ArrayList<Node> frontier = new ArrayList<>();

  /*
  constructor
   */
  public Puzzle(Node startNode) {

      currentNode = startNode;
      maxNodes = 10000;
  }

  // prints current state
  public void printState() { this.currentNode.printState();
  }

  // setter for maxNodes
  public void setMaxNodes(int x){ this.maxNodes = x;
  }

  // changes current node to that of corresponding move
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

  // setter for current node's board
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
  }

  // makes n random moves
  public void randomizeState(int n){

      for(int i = 0; i < n; i++) {
          currentNode = currentNode.move(ThreadLocalRandom.current().nextInt(0, 4));
      }
  }

  // solves with A* with specified heuristic (1 or 2)
  public Node solveA(int h) {

      PriorityQueue<Node> f = new PriorityQueue<>(1, Comparator.comparing(Node::calcF1) );
      f.add(currentNode);

      HashMap<char[][], Node> reached = new HashMap<>();
      reached.put(currentNode.getBoard(), currentNode);

      int i = 0;
      while(f.size() > 0 && i < maxNodes) {

          this.printState();

          i++;
          currentNode = f.poll();

          if (Arrays.deepEquals(currentNode.getBoard(), goalState)) {
              return currentNode;
          }


          for(Node n: this.expand(currentNode)) {

              char[][] c = n.getBoard();

              if( (!reached.containsKey(c)) || n.getD() < reached.get(c).getD() ) {

                  reached.put(c, n);
                  f.add(n);
              }
          }
      }

      this.printState();

      /*

      frontier.clear();
      reached.clear();
      frontier.add(currentNode);
      reached.put(currentNode.getBoard(), currentNode);

      int i = 0;
      while( frontier.size() > 0 && i < maxNodes){

          currentNode = frontier.get(0);
          frontier.remove(0);

          if(Arrays.deepEquals(currentNode.getBoard(), goalState)) {
              return currentNode;
          }

          for(Node n: this.expand()) {

              if( (!reached.containsKey(n.getBoard()))  |
                      ( reached.containsKey(n.getBoard()) && n.getD() <  reached.get(n.getBoard()).getD() ) ) {
                  reached.put(n.getBoard(), n);
                  frontier.add(n);
              }
          }

          switch (h) {

              case 1:
                  frontier.sort(Comparator.comparing(Node::calcF1));
                  break;
              case 2:
                  frontier.sort(Comparator.comparing(Node::calcF2));
                  break;
              default:
                  break;
          }
          i++;
          this.printState();
      }
      this.printState();

       */
      return this.currentNode;
  }

  // solves with local beam search with k states using H2 heuristic
  public void solveBeam(int k) {

      frontier.clear();

      this.expand(currentNode);
      int i = 0;
      while( (!(Arrays.deepEquals(currentNode.getBoard(), goalState)) && (i < maxNodes) ) ){

          System.out.println("++++++++++");
          int v = Math.min(k, frontier.size());

          // expand k best nodes
          for(int j = 0; j < v; j++) {

              if( frontier.get((j)) != null) {
                  currentNode = frontier.get(j);
                  System.out.println("*******");
                  this.printState();
                  this.expand(currentNode);
              }
          }
          frontier.sort(Comparator.comparing(Node::getH2));
          currentNode = frontier.get(0);
          i++;
      }
      this.printState();
  }

  //
  public Node[] expand(Node n) {

      Node[] nodes = new Node[4];

      for(int i = 0; i < 4; i++) {
          nodes[i] = n.move(i);
      }

      return nodes;
   }
}
