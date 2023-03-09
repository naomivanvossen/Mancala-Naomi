package mancala.domain;

// Your test class should be in the same 
// package as the class you're testing.
// Usually the test directory mirrors the
// main directory 1:1. So for each class in src/main,
// there is a class in src/test.

// Import our test dependencies. We import the Test-attribute
// and a set of assertions.
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    Player player1 = new Player("Player 1");

    @Test
    public void testNamePlayer(){
        assertEquals("Player 1", player1.getName());
    }

    @Test
    public void testTurnPlayer(){
        assertEquals(true, player1.getHasTurn());
    }

    @Test
    public void testOpponentName(){
        assertEquals("Player 2", (player1.getOpponent()).getName());
    }

    @Test
    public void testOpponentTurn(){
        assertEquals(false , (player1.getOpponent()).getHasTurn());
    }

    @Test
    public void testOpponentOpponentName(){
        assertEquals("Player 1", ((player1.getOpponent()).getOpponent()).getName());
    }

    @Test
    public void testOpponentOpponentHasTurn(){
        assertEquals(true, ((player1.getOpponent()).getOpponent()).getHasTurn());
    }

    @Test 
    public void testSwitchPlayer(){
        player1.switchPlayer();
        assertEquals(true, (player1.getOpponent()).getHasTurn());
    }
}
