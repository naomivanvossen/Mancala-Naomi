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

public class BowlTest {
    int initStoneCount = 4;
    int count = 5;
    Bowl startBowl = new Bowl(initStoneCount,count);

    @Test
    public void testNamePlayer(){
        assertEquals("Player 1", ((startBowl.getNeighbor()).getOwner()).getName());
    }

}