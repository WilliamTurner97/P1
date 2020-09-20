import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Puzzle {

  Node currentNode;
  int maxNodes;
  char[][] goalState = new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}};

  ArrayList<Node> frontier = new ArrayList();

  public Puzzle(Node startNode) {

      currentNode = startNode;
      maxNodes = 1000;
  }

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

  public void setMaxMoves(int x){
      this.maxNodes = x;
  }

  public void move(String s) {

      currentNode = currentNode.move(s);
  }

  public void randomizeState(int n){

      String[] directions = new String[] {"up", "down", "left", "right"};

      for(int i = 0; i < n; i++) {
          move(directions[ThreadLocalRandom.current().nextInt(0, 4)]);
      }
  }

  public void printState() {

      System.out.println("------");

      char[][] c= new char[3][3];
      for(int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
              c[i][j] = currentNode.getBoard()[i][j];
          }
      }
      c[currentNode.getIndexes()[0].x][currentNode.getIndexes()[0].y] = 'b';

      for(int i = 0; i < 3; i++) {
          StringBuilder line = new StringBuilder("");
          for(int j = 0; j < 3; j++) {
              line.append(c[i][j]);
              line.append(' ');
          }
          System.out.println(line.toString());
      }

      System.out.println("------");
  }

  public void solveA(int h) {

      int i = 0;
      while( (!(Arrays.deepEquals(currentNode.getBoard(), goalState)) && (i < maxNodes) ) ){

          this.expand();
          switch (h) {

              case 1:
                  frontier.sort(Comparator.comparing(Node::getF1));
                  break;
              case 2:
                  frontier.sort(Comparator.comparing(Node::getF2));
                  break;
              default:
                  break;

          }

          i++;
          currentNode = frontier.get(0);
          //this.printState();
      }

      System.out.println(i);
      this.printState();
  }

  public void solveBeam(int k) {

      int i = 0;

      this.expand();

      while( (!(Arrays.deepEquals(currentNode.getBoard(), goalState)) && (i < maxNodes) ) ){

          System.out.println("++++++++++");

          int v = Math.min(k, frontier.size());

          for(int j = 0; j < v; j++) {

              if( frontier.get((j)) != null) {
                  currentNode = frontier.get(j);
                  System.out.println("*******");
                  this.printState();
                  this.expand();
              }
          }

          frontier.sort(Comparator.comparing(Node::getF2));

          currentNode = frontier.get(0);
          i++;


          for(int j = k + 1; j < frontier.size(); j++) {

              frontier.remove(j);
          }

      }

      this.printState();
  }

  public void expand() {

      String[] directions = new String[] {"up", "down", "left", "right"};

      for(int i = 0; i < 4; i++) {

          boolean b = true;

          for(int j = 0; j < frontier.size(); j++) {

              if(Arrays.deepEquals(frontier.get(j).getBoard(), currentNode.move(directions[i]).getBoard())) {
                  b = false;
              }
          }

          if(b)
              frontier.add(currentNode.move(directions[i]));
      }

      /*
      frontier.add(currentNode.move("up"));
      frontier.add(currentNode.move("down"));
      frontier.add(currentNode.move("left"));
      frontier.add(currentNode.move("right"));

       */

  }
}
