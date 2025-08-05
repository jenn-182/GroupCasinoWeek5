package com.github.zipcodewilmington.casino.games.Roulette;


public class Roulette {
    private RouletteNumber[] wheel = new RouletteNumber[38];

    public void createWheel() {
        wheel[0] = new RouletteNumber(0, "Green");
    }
    

    // public static void main(String[] args) {
    //     RouletteNumber red7 = new RouletteNumber(7, "RED");
    //     System.out.println("Number: " + red7.getNumber() + ", Color: " + red7.getColor());
    // }
    
}
