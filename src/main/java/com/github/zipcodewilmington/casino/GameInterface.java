package com.github.zipcodewilmington.casino;

/**
 * Created by leon on 7/21/2020.
 */
public interface GameInterface extends Runnable {
    /**
     * adds a player to the game
     * @param player the player to be removed from the game
     */
    boolean add(Player player);

    /**
     * removes a player from the game
     * @param player the player to be removed from the game
     */
    boolean remove(Player player);

    /**
     * specifies how the game will run
     */

    void play();
 
    boolean isGamblingGame();
 
    String getGameName();
 
    int getMinimumBet();
 
    int getMaximumBet();

    void launch(Player primaryPlayer);

}
