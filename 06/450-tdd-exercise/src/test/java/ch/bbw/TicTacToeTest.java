package ch.bbw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class TicTacToeTest {

    @BeforeEach
    void setUp() {
    }

    public boolean testfirstrow() {
        TicTacToe game = new TicTacToe();
        return game.checkWin() == 'X';
    }
    @AfterEach
    void tearDown() {
    }
}
