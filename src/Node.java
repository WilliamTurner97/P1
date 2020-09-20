import java.awt.Point;
import java.util.Arrays;

/*
Search node with board, depth, and heuristics
 */
public class Node {

    // associated puzzle state
    char[][] board;
    // board locations of each tile
    Point[] indexes = new Point[9];
    // parent node
    Node parent;
    // number of tiles out of place
    int h1;
    // sum of tile distances to correct positions
    int h2;
    // node depth in tree
    int d;

    /*
    constructor
     */
    public Node(char[][] board) {

        d = 0;
        h1 = 0;
        h2 = 0;
        this.board = board;
        this.updateIndexes();
        this.calcH();
    }

    /*
    setters and getters
     */
    public void setBoard(char[][] c) {
        board = c;
        this.updateIndexes();
    }

    public char[][] getBoard() { return board;
    }

    public void setParent(Node n) { parent = n;
    }

    public Node getParent() { return this.parent;
    }

    public Point[] getIndexes() { return indexes;
    }

    public void setH1(int x) { h1 = x;
    }

    public int getH1() { return h1;
    }

    public void setH2(int x) { h2 = x;
    }

    public int getH2() { return h2;
    }

    public void setD(int x) { d = x;
    }

    public int getD() { return d;
    }

    /*
    f1 and f2 for A* search
     */

    public int calcF1() { return d + 2*h1;
    }

    public int calcF2() { return d + 2*h2;
    }

    /*
    fill out indexes
     */
    public void updateIndexes() {

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                indexes[Character.getNumericValue(board[i][j])] = new Point(i, j);
            }
        }
    }

    /*
    calculate h1 and h2 for current board state
     */
    public void calcH() {

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {

                int k = Character.getNumericValue(board[i][j]);
                if((k != ((3*i) + j)) & (k > 0))  {
                    h1++;
                    h2 = h2 + ( Math.abs((k % 3) - j) + Math.abs((k/3) - i) );
                }
            }
        }
    }

    /*
    returns a new node with the board state that would follow from moving in the specified direction
    (0 = up, 1 = down, 2 = left, 3 = right)
     */
    public Node move(int m) {

        char[][] newBoard = new char[3][3];
        for(int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
            newBoard[i][j] = board[i][j];
          }
        }

        int x = 0;
        int y = 0;

        // determine how to modify x and y
        switch(m) {
            case 0:
                x = -1;
                break;
            case 1:
                x = 1;
                break;
            case 2:
                y = -1;
                break;
            case 3:
                y = 1;
                break;
            default:
                break;
        }

        // bound x and y to valid indices
        int newX = Math.max(Math.min(indexes[0].x + x, 2), 0);
        int newY = Math.max(Math.min(indexes[0].y + y, 2), 0);

        // create new board and new node
        newBoard[indexes[0].x][indexes[0].y] = newBoard[newX][newY];
        newBoard[newX][newY] = '0';

        Node output = new Node(newBoard);
        output.setParent(this);
        output.setD(this.getD() + 1);
        return output;
    }

    // prints to System.out
    public void printState() {

        System.out.println("------");
        char[][] c = board.clone();
        c[indexes[0].x][indexes[0].y] = 'b';

        for(int i = 0; i < 3; i++) {

            System.out.println(Arrays.toString(board[i]).replace(',', ' '));
        }
        System.out.println("------");
    }

}
