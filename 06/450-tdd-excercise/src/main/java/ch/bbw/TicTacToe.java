package ch.bbw;

public class TicTacToe {

    /**
     * Determine who won on the given 3x3 board.
     * Returns 'X' if X has a winning row/column/diagonal,
     * 'O' if O has a winning row/column/diagonal,
     * '-' if there is no winner.
     *
     * For the Task 2 (rows) tests we only need to check rows for now.
     */
    public static char whoWon(char[][] board) {
        if (board == null || board.length != 3)
            return '-';
        // Check rows using helper
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            char rowWinner = checkRow(board, rowIndex);
            if (rowWinner != '-') {
                return rowWinner;
            }
        }

        // Check columns using helper
        for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
            char columnWinner = checkColumn(board, columnIndex);
            if (columnWinner != '-') {
                return columnWinner;
            }
        }
        // Check main diagonal (top-left to bottom-right)
        char mainDiagonalWinner = checkDiagonal(board, 0, 0, 1, 1, 2, 2);
        if (mainDiagonalWinner != '-') {
            return mainDiagonalWinner;
        }

        // Check anti-diagonal (top-right to bottom-left)
        char antiDiagonalWinner = checkDiagonal(board, 0, 2, 1, 1, 2, 0);
        if (antiDiagonalWinner != '-') {
            return antiDiagonalWinner;
        }
        return '-';
    }

    private static char checkRow(char[][] board, int rowIndex) {
        char firstCellInRow = board[rowIndex][0];
        char secondCellInRow = board[rowIndex][1];
        char thirdCellInRow = board[rowIndex][2];
        if (allEqualNonSpace(firstCellInRow, secondCellInRow, thirdCellInRow)) {
            return firstCellInRow;
        }
        return '-';
    }

    private static char checkColumn(char[][] board, int columnIndex) {
        char firstCellInColumn = board[0][columnIndex];
        char secondCellInColumn = board[1][columnIndex];
        char thirdCellInColumn = board[2][columnIndex];
        if (allEqualNonSpace(firstCellInColumn, secondCellInColumn, thirdCellInColumn)) {
            return firstCellInColumn;
        }
        return '-';
    }

    private static boolean allEqualNonSpace(char first, char second, char third) {
        return first != ' ' && first == second && second == third;
    }

    private static char checkDiagonal(char[][] board,
                                       int firstRow, int firstColumn,
                                       int secondRow, int secondColumn,
                                       int thirdRow, int thirdColumn) {
        char firstCell = board[firstRow][firstColumn];
        char secondCell = board[secondRow][secondColumn];
        char thirdCell = board[thirdRow][thirdColumn];
        if (allEqualNonSpace(firstCell, secondCell, thirdCell)) {
            return firstCell;
        }
        return '-';
    }

}
