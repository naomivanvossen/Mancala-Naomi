package mancala.domain;

public class Kalaha extends BowlOrKalaha{

    public Kalaha(int initStoneCount,int count, int kalahaCount, Player initOwner, Bowl startBowl){
        this.stoneCount = 0;
        this.owner = initOwner;
        count = 5;
        if(kalahaCount < 1){
            this.neighbor = new Bowl(initStoneCount, count, kalahaCount +1, initOwner.getOpponent(), startBowl);
        }
        else{
            this.neighbor = startBowl;
        }
    }
}