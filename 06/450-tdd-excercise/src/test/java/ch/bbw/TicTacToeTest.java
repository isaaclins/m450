package ch.bbw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicTacToeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    // Task 2 - Rows: tests for each row (3 X -> X wins, 3 O -> O wins, neither ->
    // no winner)



    @Test
    void firstRow_threeX_shouldReturnX() {
        char[][] board = {
                { 'X', 'X', 'X' },
                { 'O', ' ', 'O' },
                { ' ', ' ', ' ' }
        };
        assertEquals('X', TicTacToe.whoWon(board), "First row with three X should return 'X'");
    }

    @Test
    void firstRow_threeO_shouldReturnO() {
        char[][] board = {
                { 'O', 'O', 'O' },
                { 'X', ' ', 'X' },
                { ' ', ' ', ' ' }
        };
        assertEquals('O', TicTacToe.whoWon(board), "First row with three O should return 'O'");
    }

    @Test
    void firstRow_noThree_shouldReturnDash() {
        char[][] board = {
                { 'X', 'O', 'X' },
                { 'O', 'X', ' ' },
                { ' ', ' ', ' ' }
        };
        assertEquals('-', TicTacToe.whoWon(board), "First row without three identical marks should return '-'");
    }

    @Test
    void secondRow_threeX_shouldReturnX() {
        char[][] board = {
                { 'O', ' ', ' ' },
                { 'X', 'X', 'X' },
                { 'O', ' ', ' ' }
        };
        assertEquals('X', TicTacToe.whoWon(board), "Second row with three X should return 'X'");
    }

    @Test
    void secondRow_threeO_shouldReturnO() {
        char[][] board = {
                { 'X', ' ', ' ' },
                { 'O', 'O', 'O' },
                { 'X', ' ', ' ' }
        };
        assertEquals('O', TicTacToe.whoWon(board), "Second row with three O should return 'O'");
    }

    @Test
    void secondRow_noThree_shouldReturnDash() {
        char[][] board = {
                { 'X', ' ', ' ' },
                { 'X', 'O', 'X' },
                { ' ', ' ', ' ' }
        };
        assertEquals('-', TicTacToe.whoWon(board), "Second row without three identical marks should return '-'");
    }

    @Test
    void thirdRow_threeX_shouldReturnX() {
        char[][] board = {
                { 'O', ' ', ' ' },
                { 'O', ' ', ' ' },
                { 'X', 'X', 'X' }
        };
        assertEquals('X', TicTacToe.whoWon(board), "Third row with three X should return 'X'");
    }

    @Test
    void thirdRow_threeO_shouldReturnO() {
        char[][] board = {
                { 'X', ' ', ' ' },
                { 'X', ' ', ' ' },
                { 'O', 'O', 'O' }
        };
        assertEquals('O', TicTacToe.whoWon(board), "Third row with three O should return 'O'");
    }

    @Test
    void thirdRow_noThree_shouldReturnDash() {
        char[][] board = {
                { ' ', ' ', ' ' },
                { 'X', ' ', ' ' },
                { 'X', 'O', 'X' }
        };
        assertEquals('-', TicTacToe.whoWon(board), "Third row without three identical marks should return '-'");
    }

    // Task 3 - Columns: failing tests (red)

    @Test
    void firstColumn_threeX_shouldReturnX() {
        char[][] board = {
                { 'X', 'O', ' ' },
                { 'X', 'O', ' ' },
                { 'X', ' ', ' ' }
        };
        assertEquals('X', TicTacToe.whoWon(board), "First column with three X should return 'X'");
    }

    @Test
    void firstColumn_threeO_shouldReturnO() {
        char[][] board = {
                { 'O', 'X', ' ' },
                { 'O', 'X', ' ' },
                { 'O', ' ', ' ' }
        };
        assertEquals('O', TicTacToe.whoWon(board), "First column with three O should return 'O'");
    }

    @Test
    void firstColumn_noThree_shouldReturnDash() {
        char[][] board = {
                { 'X', ' ', ' ' },
                { 'O', ' ', ' ' },
                { 'X', ' ', ' ' }
        };
        assertEquals('-', TicTacToe.whoWon(board), "First column without three identical marks should return '-'");
    }

    // Task 4 - Diagonals: failing tests (red)

    @Test
    void mainDiagonal_threeX_shouldReturnX() {
        char[][] board = {
                { 'X', 'O', ' ' },
                { 'O', 'X', ' ' },
                { ' ', ' ', 'X' }
        };
        assertEquals('X', TicTacToe.whoWon(board), "Main diagonal with three X should return 'X'");
    }

    @Test
    void mainDiagonal_threeO_shouldReturnO() {
        char[][] board = {
                { 'O', 'X', ' ' },
                { 'X', 'O', ' ' },
                { ' ', ' ', 'O' }
        };
        assertEquals('O', TicTacToe.whoWon(board), "Main diagonal with three O should return 'O'");
    }

    @Test
    void mainDiagonal_noThree_shouldReturnDash() {
        char[][] board = {
                { 'X', ' ', ' ' },
                { ' ', 'O', ' ' },
                { ' ', ' ', 'X' }
        };
        assertEquals('-', TicTacToe.whoWon(board), "Main diagonal without three identical marks should return '-'");
    }

    @Test
    void antiDiagonal_threeX_shouldReturnX() {
        char[][] board = {
                { ' ', 'O', 'X' },
                { 'O', 'X', ' ' },
                { 'X', ' ', ' ' }
        };
        assertEquals('X', TicTacToe.whoWon(board), "Anti-diagonal with three X should return 'X'");
    }

    @Test
    void antiDiagonal_threeO_shouldReturnO() {
        char[][] board = {
                { ' ', 'X', 'O' },
                { 'X', 'O', ' ' },
                { 'O', ' ', ' ' }
        };
        assertEquals('O', TicTacToe.whoWon(board), "Anti-diagonal with three O should return 'O'");
    }

    @Test
    void antiDiagonal_noThree_shouldReturnDash() {
        char[][] board = {
                { ' ', 'X', 'X' },
                { 'X', 'O', ' ' },
                { 'O', ' ', ' ' }
        };
        assertEquals('-', TicTacToe.whoWon(board), "Anti-diagonal without three identical marks should return '-'");
    }
}
