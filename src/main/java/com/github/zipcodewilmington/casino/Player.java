package com.github.zipcodewilmington.casino;

/**
 * Created by leon on 7/21/2020.
 * All players of a game should abide by `PlayerInterface`.
 * All players must have reference to the `ArcadeAccount` used to log into the `Arcade` system.
 * All players are capable of `play`ing a game.
 */
public class Player {
    
    private String username;
    private CasinoAccount casinoAccount;

    public Player(String username, CasinoAccount account) {
        this.username = username;
        this.casinoAccount = account;
    }


    public String getUsername() {
        return username;
    }


    public CasinoAccount getAccount() {
        return casinoAccount;
    }

    public void setAccount(CasinoAccount account) {
        this.casinoAccount = account;
    }


    @Override
    public String toString() {
        return "Player{" +
                "Username='" + username + '\'' +
                '}';
    }

    // /**
    //  * @return the `ArcadeAccount` used to log into the `Arcade` system to play this game
    //  */
    // CasinoAccount getArcadeAccount() {
    //     return casinoAccount;
    // }

    /**
     * Defines how a specific implementation of `PlayerInterface` plays their respective game.
     * @param <SomeReturnType> specify any return type you would like here
     * @return whatever return value you would like
     */
    <SomeReturnType> SomeReturnType play();
}
