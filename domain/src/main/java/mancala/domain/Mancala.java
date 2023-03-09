package mancala.domain;


// Make your own mancala implementation using your design.
// You can take this stub as an example how to make a 
// class inside a package and how to test it.

public class Mancala {

    public static void main(String args[]){
        int initStoneCount = 1;
        int count = 5;
        Bowl startBowl = new Bowl(initStoneCount,count);
        startBowl.printBord();
        startBowl.pickBowl();
    }
}