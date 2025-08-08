package com.github.zipcodewilmington.Roulette;

public class RouletteGameTest {
    public static void main(String[] args) {
        System.out.println("Testing Roulette Components...");
        
        // Test 1: Create wheel
        try {
            Roulette testWheel = new Roulette();
            System.out.println("✅ Wheel created");
            
            testWheel.createWheel();
            System.out.println("✅ Wheel initialized");
            
            RouletteNumber result = testWheel.spin();
            if (result != null) {
                System.out.println("✅ Spin works: " + result.getNumber() + " " + result.getColor());
            } else {
                System.out.println("❌ Spin returned null");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
