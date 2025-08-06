package com.github.zipcodewilmington.casino.games.Roulette;

public class RouletteNumber {
    private int storedNumber = 0;
    private String colorLanded = "";
    


    public RouletteNumber(int storedNumber, String colorLanded) {
        this.storedNumber = storedNumber;
        this.colorLanded = colorLanded;
    }

    public int getNumber() {
        return storedNumber;
    }
    
    public String getColor() {
        return colorLanded;
    }

    

}

