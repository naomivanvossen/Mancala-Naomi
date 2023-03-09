package mancala.domain;

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

    public boolean checkIfICanPlayThisBowl(){
        if(getStoneCount() > 0){
            return true; 
        }
        else{
            return false;
        }
    }

    public void playBowl(){
        if (checkIfICanPlayThisBowl()){
            int numberStonesToDistribute = this.stoneCount;
            this.stoneCount = 0;
            (this.getNeighbor()).distributeStones(numberStonesToDistribute, this.getOwner());
        }
        else{
            System.exit(0);
        }
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

    public void pickBowlToPlay(int bowlNumber){
        if(1 <= bowlNumber && bowlNumber <= 6){
            BowlOrKalaha bowlToPlay = (this.goToFirstBowlOnPlayersSide()).lookForBowl(bowlNumber);
            bowlToPlay.playBowl();
        }
        else{
            System.exit(0);
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
            if(wasIEmpty(ownerFirstBowlPlayed)){
                stealIfThisIsMyBowl(ownerFirstBowlPlayed);
            }
            (this.owner).switchPlayer();
            isGameEnded();
        }
        else{
            (this.getNeighbor()).isGameEnded();
        }
    }

    public boolean isGameEnded(){
        if(goToFirstBowlOnPlayersSide().checkIfRowIsEmpty()){
            whoHasWon();
            return true;
        }
        return false;
    }

    public boolean wasIEmpty(Player ownerFirstBowlPlayed){
        if(this.stoneCount == 1){
            return true; 
        }
        return false;
    }

    public void stealIfThisIsMyBowl(Player ownerFirstBowlPlayed){
        if(this.getOwner() == ownerFirstBowlPlayed){
            BowlOrKalaha myKalaha = findMyKalaha();
            BowlOrKalaha oppositeBowl = findOppositeBowl(0, false);
            myKalaha.addToKalaha(this.emptyBowlForKalaha() + oppositeBowl.emptyBowlForKalaha()); 
        }       
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
            return currentBowl.getOwner();
        }
        else if(stoneCountCurrentPlayer < stoneCountOtherPlayer ){
            return (currentBowl.getOwner()).getOpponent();
        }
        else{
            return currentBowl.getOwner();
        }
    }
    
    // Should not be in this file, should do this in the constructor
    public void setBordTest(int[] stonecounts, int countSetBord){
        if(countSetBord < 14){
            this.stoneCount = stonecounts[countSetBord];
            (this.getNeighbor()).setBordTest(stonecounts, countSetBord + 1);
        }
    }
}