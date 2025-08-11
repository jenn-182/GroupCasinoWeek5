package com.github.zipcodewilmington.casino;

import java.util.List;

/**
 * Created by leon on 7/21/2020.
 */
public interface GameInterface extends Runnable {
    /**
     * adds a player to the game
     * @param player the player to be removed from the game
     */
    public boolean add(Player player);

    /**
     * removes a player from the game
     * @param player the player to be removed from the game
     */
    public boolean remove(Player player);

    /**
     * specifies how the game will run
     */

    public void play();

    public boolean isGamblingGame();

    public String getGameName();

    public int getMinimumBet();

    public int getMaximumBet();

    public void launch(Player primaryPlayer);

    public void launchMultiplayer(List<Player> players);

    public void loadQuestionsFromFile(String filename);

}
