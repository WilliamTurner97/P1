import junit.framework.TestCase;

public class PuzzleTests extends TestCase {

  public void testMain() {

      String[] s = new String[] {"text file/text1.txt"};
      IOManager.main(s);
  }

  public void testD() {

      Node n1 = new Node(new char[][]{{'0','0','0'},{'0','0','0'},{'0','0','0'}});
      Node n2 = n1.move(0);
      Node n3 = n2.move(1);
      Node n4 = n1.move(3);

      assertEquals("n1", n1.getD(), 0);
      assertEquals("n2", n2.getD(), 1);
      assertEquals("n3", n3.getD(), 2);
      assertEquals("n4", n4.getD(), 1);
  }

  public void testH() {

    Node n1 = new Node(new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}});
    Node n2 = new Node(new char[][]{{'8','7','6'},{'5','4','3'},{'2','1','0'}});

    assertEquals("n1h1", n1.getH1(), 0);
    assertEquals("n1h2", n1.getH2(), 0);
    assertEquals("n2h1", n2.getH1(), 7);
    assertEquals("n2h2", n2.getH2(), 20);
  }

  public void testF() {


      Node n1 = new Node(new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}});
      Puzzle p = new Puzzle(n1);

      char[][] c1 = new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}};
      char[][] c2 = new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}};

      p.randomizeState(500);
      p.printState();
      //p.solveA(2);
      //p.solveBeam(4);


  }
}
