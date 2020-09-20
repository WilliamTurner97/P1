import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOManager {

    public static void main (String[] args) {

        Puzzle p = new Puzzle(new Node(new char[][]{{'0','0','0'},{'0','0','0'},{'0','0','0'}}));

        try {

            Scanner readIn = new Scanner(new FileInputStream(args[0]));

            while(readIn.hasNext()) {

                switch (readIn.next()) {

                    case "setState":
                        p.setState(readIn.next(), readIn.next(), readIn.next());
                        break;
                    case "printState":
                        p.printState();
                        break;
                    case "move":
                        p.move(readIn.next());
                        break;
                    case "randomizeState":
                        p.randomizeState(Integer.parseInt(readIn.next()));
                        break;
                    case "solve":
                        if (readIn.next().equals("A-Star")) {
                            p.solveA(Integer.valueOf(readIn.next()) );
                        }
                        break;
                    default:
                        break;


                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
