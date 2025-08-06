package com.github.zipcodewilmington.casino.games.Poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;

public class Poker implements GameInterface{

    private List<Player> players;
    private List<String> computers;
    private Deck dealerDeck;
    private List<Hand> hands;
    private int curPlayerIndex;

    private double pot;
    private double currentBet;
    private boolean gameActive;
    private int round;



    Poker(List<Player> players) {
        dealerDeck = new Deck();
        this.players = new ArrayList<>();
        computers = new ArrayList<>();
        hands = new ArrayList<>();
        curPlayerIndex = 0;
        
        pot = 0.0;
        currentBet = 0.0;
        gameActive = true;
        round = 0;

        addComputerPlayers();
        initializeHands();
        play();
    }



    private void initializeHands() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initializeHands'");
    }



    private void addComputerPlayers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addComputerPlayers'");
    }


    @Override
    public void remove(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }



    @Override
    public void add(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }



    @Override
    public void play() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }



    @Override
    public boolean isGamblingGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGamblingGame'");
    }



    @Override
    public String getGameName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameName'");
    }



    @Override
    public int getMinimumBet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMinimumBet'");
    }



    @Override
    public int getMaximumBet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaximumBet'");
    }

}
