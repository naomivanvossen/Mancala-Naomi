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

public class BowlOrKalahaTest {
    int initStoneCount = 4;
    int count = 5;
    Bowl startBowlP1 = new Bowl(initStoneCount,count);
    BowlOrKalaha startBowlP2 = (startBowlP1.findMyKalaha()).getNeighbor();

    @Test
    public void testNamePlayer(){
        assertEquals("Player 1", ((startBowlP1.getNeighbor()).getOwner()).getName());
    }

    @Test 
    public void finalStoneCount(){
        assertEquals(24, startBowlP2.getFinalStoneCount(0,1));
    }

    @Test 
    public void testBowlsOwner(){
        assertEquals("Player 2",(startBowlP2.getOwner()).getName());
    }

    @Test
    public void testPlayerAtTurn(){
        assertEquals(true, startBowlP1.getOwner().getHasTurn());
    }

    @Test 
    public void testBowlsStoneCount(){
        assertEquals(4,startBowlP2.getStoneCount());
    }

    @Test
    public void testPlayBowl(){
        (startBowlP1.lookForBowl(4)).playBowl();
        assertEquals(0, (startBowlP1.lookForBowl(4)).getStoneCount());
    }

    @Test
    public void testFindMyKalaha(){
        BowlOrKalaha isThisKalaha = startBowlP2.findMyKalaha();
        assertEquals(true, isThisKalaha instanceof Kalaha);
    }

    @Test
    public void testFindMyKalahaOwner(){
        BowlOrKalaha isThisKalaha = startBowlP2.findMyKalaha();
        assertEquals("Player 2", isThisKalaha.getOwner().getName());
    }


    @Test
    public void testPlayerAllBowls(){
        assertEquals("Player 2", (startBowlP2.lookForBowl(6)).getOwner().getName());
    }

    @Test
    public void testSteal(){
        (startBowlP2.lookForBowl(4)).steal();
        assertEquals(0, (startBowlP1.lookForBowl(3)).getStoneCount()); 
    }

    @Test
    public void testCanIsteal(){
        int stoneCounts[] = {4,4,4,4,0,5,1,5,5,4,4,4,4,0};
        startBowlP1.setBordTest(stoneCounts,0);
        (startBowlP1.lookForBowl(1)).checkIfICanPlayThisBowl();;
        assertEquals(0,(startBowlP2.lookForBowl(2)).getStoneCount());
    }

    @Test
    public void testCanIsteal2(){
        int stoneCounts[] = {4,4,4,4,0,5,1,5,5,4,4,4,4,0};
        startBowlP1.setBordTest(stoneCounts,0);
        (startBowlP1.lookForBowl(1)).checkIfICanPlayThisBowl();;
        assertEquals(0,(startBowlP1.lookForBowl(5)).getStoneCount());
    }

    @Test
    public void testStealToKalaha(){
        (startBowlP2.lookForBowl(4)).steal();
        assertEquals(8, (startBowlP2.findMyKalaha()).getStoneCount()); 
    }

    @Test
    public void testSkipOwnKalaha(){
        int initStoneCount = 8;
        int count = 5;
        Bowl startBowlP1 = new Bowl(initStoneCount,count);
        BowlOrKalaha startBowlP2 = (startBowlP1.findMyKalaha()).getNeighbor();
        (startBowlP2.lookForBowl(6)).playBowl();
        assertEquals(9, (startBowlP2.lookForBowl(1)).getStoneCount()); 
    }

    @Test 
    public void testeEndInMyKalaha(){
        startBowlP2.getOwner().switchPlayer();
        (startBowlP2.lookForBowl( 3)).playBowl();
        assertEquals( true, (startBowlP2.getOwner()).getHasTurn());   
    }
 
    @Test 
    public void testHasGameEnded(){
        int stoneCounts[] = {5,5,4,4,4,4,0,0,0,0,0,0,0,1};
        startBowlP1.setBordTest(stoneCounts,0);
        assertEquals(false, startBowlP2.isGameEnded());
    }

    @Test
    public void testWhoHasWon(){
        int stoneCounts[] = {0,0,0,0,0,0,1,5,5,4,4,4,4,0};
        startBowlP1.setBordTest(stoneCounts,0);
        assertEquals("Player 2", (startBowlP2.whoHasWon()).getName());
    }

    @Test
    public void testFinalStoneCount(){
        int stoneCounts[] = {0,0,0,0,0,0,1,5,5,4,4,4,4,0};
        startBowlP1.setBordTest(stoneCounts,0);
        assertEquals(1, startBowlP1.getFinalStoneCount(0,1));
    }
}