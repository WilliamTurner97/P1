import java.awt.Point;
import java.util.Arrays;

public class Node {

    char[][] board;
    Point[] indexes = new Point[9];
    Node parent;

    int h1;
    int h2;
    int d;

    public Node(char[][] board) {

        d = 0;
        h1 = 0;
        h2 = 0;
        this.board = board;
        this.updateIndexes();
        this.calcH();
    }

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

    public int getF1() { return d + 2*h1;
    }

    public int getF2() { return d + 2*h2;
    }

    public void updateIndexes() {

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                indexes[Character.getNumericValue(board[i][j])] = new Point(i, j);
            }
        }
    }

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

    public Node move(int m) {

        char[][] newBoard = new char[3][3];
        for(int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
            newBoard[i][j] = board[i][j];
          }
        }

        int x = 0;
        int y = 0;

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

        int newX = Math.max(Math.min(indexes[0].x + x, 2), 0);
        int newY = Math.max(Math.min(indexes[0].y + y, 2), 0);

        newBoard[indexes[0].x][indexes[0].y] = newBoard[newX][newY];
        newBoard[newX][newY] = '0';

        Node output = new Node(newBoard);
        output.setParent(this);
        output.setD(this.getD() + 1);
        return output;
    }

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
