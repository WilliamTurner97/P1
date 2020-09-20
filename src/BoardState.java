public class BoardState {

    int[] indexes;

  public BoardState(char[][] board) {

      for(int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
              indexes[Character.getNumericValue(board[i][j])] = i + j;
          }
      }
  }
}
