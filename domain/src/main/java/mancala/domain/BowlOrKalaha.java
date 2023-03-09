package mancala.domain;
import java.util.*;

public abstract class BowlOrKalaha {

    int stoneCount;  
    Player owner;
    BowlOrKalaha neighbor;

    public int getStoneCount() {
        return stoneCount;
    }

    public Player getOwner(){
        return owner;
    }

    public BowlOrKalaha getNeighbor(){
        return neighbor;
    }

    public BowlOrKalaha findMyKalaha(){
        if(this instanceof Kalaha){
            return this;
        }
        else{
            return (this.neighbor).findMyKalaha();
        }
    }

    public void playBowl(){
        int numberStonesToDistribute = this.stoneCount;
        this.stoneCount = 0;
        (this.getNeighbor()).distributeStones(numberStonesToDistribute, this.getOwner());
    }

    public BowlOrKalaha goToFirstBowlOnPlayersSide(){
        if((this.getOwner()).getHasTurn()){
            return (((this.findMyKalaha()).getNeighbor()).findMyKalaha()).getNeighbor();
        }
        else{
            return (this.findMyKalaha()).getNeighbor();
        }
    }

    public BowlOrKalaha lookForBowl(int steps){
        if(steps > 1){
            return (this.getNeighbor()).lookForBowl(steps - 1);
        }
        else{
            return this;
        }
    }

    public void checkIfICanPlayThisBowl(){
        if(getStoneCount() > 0){
            System.out.println("SpeelBowl");
            playBowl();
        }
        else{
            System.out.println("Play another bowl, this one is empty...");
            pickBowl();
        }
    }

    public void pickBowl(){
        Scanner scan = new Scanner(System.in);
        String playerAtTurn = ((this.getOwner()).getPlayerWhosAtTurn()).getName();
        System.out.print(playerAtTurn + ", choose a bowl 1-6: ");
        int bowlNumber = scan.nextInt();
        if(1 <= bowlNumber && bowlNumber <= 6){
            ((this.goToFirstBowlOnPlayersSide()).lookForBowl(bowlNumber)).checkIfICanPlayThisBowl();
        }
        else{
            System.out.println("This is not a valid input, choose again!");
            pickBowl();
        }
    }

    public void distributeStones(int numberStonesToDistribute, Player ownerFirstBowlPlayed){
        this.stoneCount = getStoneCount() +1;
        if(numberStonesToDistribute > 1){
            distributeMoreThanOneStones(numberStonesToDistribute, ownerFirstBowlPlayed);
        }
        else{
            lastStoneDistributed(ownerFirstBowlPlayed);
        }
    }

    public void distributeMoreThanOneStones(int numberStonesToDistribute, Player ownerFirstBowlPlayed){
        BowlOrKalaha myNeighbor = this.getNeighbor();
        if((myNeighbor instanceof Bowl) || ((myNeighbor instanceof Kalaha) && myNeighbor.owner == ownerFirstBowlPlayed)){
            (myNeighbor).distributeStones(numberStonesToDistribute - 1, ownerFirstBowlPlayed);
        }
        else{
            ((myNeighbor).neighbor).distributeStones(numberStonesToDistribute - 1, ownerFirstBowlPlayed);
        }
    }

    public void lastStoneDistributed(Player ownerFirstBowlPlayed){
        if((this instanceof Bowl)){
            wasIEmpty(ownerFirstBowlPlayed);
            printBord();
            (this.owner).switchPlayer();
            isGameEnded();
            //pickBowl();
        }
        else{
            (this.getNeighbor()).printBord();
            (this.getNeighbor()).isGameEnded();
            //(this.getNeighbor()).pickBowl();
        }
    }

    public boolean isGameEnded(){
        if(goToFirstBowlOnPlayersSide().checkIfRowIsEmpty()){
            whoHasWon();
            //System.exit(0);
            return true;
        }
        return false;
    }

    public void wasIEmpty(Player ownerFirstBowlPlayed){
        if(this.stoneCount == 1){
            canISteal(ownerFirstBowlPlayed);
        }
    }

    public void canISteal(Player ownerFirstBowlPlayed){
        if(this.getOwner() == ownerFirstBowlPlayed){
            steal();
        }
    }

    public void steal(){
        BowlOrKalaha myKalaha = findMyKalaha();
        BowlOrKalaha oppositeBowl = findOppositeBowl(0, false);
        myKalaha.addToKalaha(this.emptyBowlForKalaha() + oppositeBowl.emptyBowlForKalaha());        
    }

    public BowlOrKalaha findOppositeBowl(int countOppBowl, boolean foundKalaha){
        if(this instanceof Kalaha){
            foundKalaha = true;
            return (this.neighbor).findOppositeBowl(countOppBowl - 1, foundKalaha);
        }
        if(!foundKalaha){
            return (this.neighbor).findOppositeBowl(countOppBowl + 1, foundKalaha);
        }
        else{
            if(countOppBowl > 0){
                return (this.neighbor).findOppositeBowl(countOppBowl - 1, foundKalaha);
            }
        }
        return this;
    }

    public int emptyBowlForKalaha(){
        int stonesForKalaha = this.getStoneCount();
        this.stoneCount = 0;
        return stonesForKalaha;
    }

    public void addToKalaha(int numberStonesForKalaha){
        this.stoneCount = this.stoneCount + numberStonesForKalaha;
    }

    public boolean checkIfRowIsEmpty(){
        if(!(this instanceof Kalaha)){
            if(this.stoneCount > 0){
                return false;
            }
            else{
                return (this.getNeighbor()).checkIfRowIsEmpty();
            }
        }
        return true; 
    }

    public int getFinalStoneCount(int stoneCountCurrentPlayer, int countGetFinalStoneCount){
        if(countGetFinalStoneCount <= 7){
            stoneCountCurrentPlayer = stoneCountCurrentPlayer + this.getStoneCount();
            return (this.getNeighbor()).getFinalStoneCount(stoneCountCurrentPlayer, countGetFinalStoneCount+1);
        }
        return stoneCountCurrentPlayer;
    }
    
    public Player whoHasWon(){
        BowlOrKalaha currentBowl = goToFirstBowlOnPlayersSide();
        int stoneCountCurrentPlayer = currentBowl.getFinalStoneCount(0,1);
        int stoneCountOtherPlayer = ((currentBowl.findMyKalaha()).getNeighbor()).getFinalStoneCount(0,1);
        if(stoneCountCurrentPlayer > stoneCountOtherPlayer ){
            System.out.println( (currentBowl.getOwner()).getName() + " has won! He has " + stoneCountCurrentPlayer  + " stones and " + ((currentBowl.getOwner()).getOpponent()).getName() + " has " + stoneCountOtherPlayer + " stones.");
            return currentBowl.getOwner();
        }
        else if(stoneCountCurrentPlayer < stoneCountOtherPlayer ){
            System.out.println( ((currentBowl.getOwner()).getOpponent()).getName() + " has won! He has " + stoneCountOtherPlayer  + " stones and " + (currentBowl.getOwner()).getName() + " has " + stoneCountCurrentPlayer  + " stones.");
            return (currentBowl.getOwner()).getOpponent();
        }
        else{
            System.out.println( "It's a tie! Both players have " + stoneCountCurrentPlayer + " stones.");
            return currentBowl.getOwner();
        }
    }
    
    public BowlOrKalaha goToFirstBowlPlayer2ForPrintBord(){
        if((this.getOwner()).getName() == "Player 1"){
            return (this.findMyKalaha()).getNeighbor();
        }
        else{
            return (((this.findMyKalaha()).getNeighbor()).findMyKalaha()).getNeighbor();
        }
    }

    public void printBord(){
        int countPrintBord = 1; 
        BowlOrKalaha currentBowl = this.goToFirstBowlPlayer2ForPrintBord();
        
        int[] stoneCounts = new int[7];
        while(countPrintBord <= 7){
            stoneCounts[ countPrintBord - 1] = currentBowl.getStoneCount();
            countPrintBord ++;
            currentBowl = currentBowl.getNeighbor();
        }
        System.out.print("\n    ");
        for(int i = 1; i < 7; i++){
            System.out.print( (7 - i) % 7  + ":" + stoneCounts[ 6 - i] + " ");
        }
        countPrintBord = 1;
        int[] stoneCounts2 = new int[7];
        while(countPrintBord <= 7){
            stoneCounts2[ countPrintBord - 1] = currentBowl.getStoneCount();
            countPrintBord ++;
            currentBowl = currentBowl.getNeighbor();
        }
        System.out.println("\nK:" + stoneCounts[6] + "                         " + "K:" + stoneCounts2[6]);
        System.out.print("    ");
        for(int i = 0; i < 6; i++){
            System.out.print( (i + 1) % 7  + ":" + stoneCounts2[ i] + " ");
        }
        System.out.println("\n");
    }

    public void setBordTest(int[] stonecounts, int countSetBord){
        if(countSetBord < 14){
            this.stoneCount = stonecounts[countSetBord];
            (this.getNeighbor()).setBordTest(stonecounts, countSetBord + 1);
        }
    }
}