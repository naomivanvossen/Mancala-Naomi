package mancala.domain;

// Make your own mancala implementation using your design.
// You can take this stub as an example how to make a 
// class inside a package and how to test it.

class Player{

    private String name;  
    private boolean hasTurn;
    private Player opponent;

    public Player(String namePlayer) {    
        this.name = namePlayer;
        this.hasTurn = true;
        this.opponent = new Player("Player 2", this, false);
    }

    private Player(String namePlayer, Player otherPlayer, boolean Hasturn){
        this.name = namePlayer;
        this.hasTurn = false;
        this.opponent = otherPlayer;
    }

    public String getName() {
        return name;
    }

    public boolean getHasTurn() {
        return hasTurn;
    }

    public Player getOpponent(){
        return opponent;
    }

    public void switchPlayer(){
        (this.opponent).hasTurn = this.hasTurn;
        this.hasTurn = !this.hasTurn;
    }

    public Player getPlayerWhosAtTurn(){
        if(this.hasTurn){
            return this;
        }
        else{
            return this.getOpponent();
        }
    }
    
}
