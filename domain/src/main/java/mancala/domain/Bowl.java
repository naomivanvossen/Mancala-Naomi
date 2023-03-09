package mancala.domain;

public class Bowl extends BowlOrKalaha{

    public Bowl(int initStoneCount, int count) {
        int kalahaCount = 0;
        Player startPlayer = new Player("Player 1");
        this.stoneCount = initStoneCount;
        this.owner = startPlayer; 
        this.neighbor = new Bowl(initStoneCount, count-1, kalahaCount, startPlayer, this);
    }

    public Bowl(int initStoneCount, int count, int kalahaCount, Player initOwner, Bowl startBowl){
        this.stoneCount = initStoneCount;
        this.owner = initOwner;
        if(count > 0){
            this.neighbor = new Bowl(initStoneCount, count-1, kalahaCount, initOwner, startBowl);
        }
        if(count == 0){
            this.neighbor = new Kalaha(initStoneCount, count -1, kalahaCount, initOwner, startBowl);
        }
    }

}
