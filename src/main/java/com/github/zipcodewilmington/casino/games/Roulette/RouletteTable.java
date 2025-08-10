package com.github.zipcodewilmington.casino.games.Roulette;

import java.util.HashMap;
import java.util.Map;

class RouletteTable {
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GRAY = "\u001B[90m"; // Using ANSI gray for black numbers
    private static final String ANSI_GREEN = "\u001B[32m"; // Using ANSI green for winning numbers

    private static final Map<Integer, String> numberColors = new HashMap<>();
    static {
        // Red numbers
        numberColors.put(1, "R");
        numberColors.put(3, "R");
        numberColors.put(5, "R");
        numberColors.put(7, "R");
        numberColors.put(9, "R");
        numberColors.put(12, "R");
        numberColors.put(14, "R");
        numberColors.put(16, "R");
        numberColors.put(18, "R");
        numberColors.put(19, "R");
        numberColors.put(21, "R");
        numberColors.put(23, "R");
        numberColors.put(25, "R");
        numberColors.put(27, "R");
        numberColors.put(30, "R");
        numberColors.put(32, "R");
        numberColors.put(34, "R");
        numberColors.put(36, "R");

        // Black numbers
        numberColors.put(2, "B");
        numberColors.put(4, "B");
        numberColors.put(6, "B");
        numberColors.put(8, "B");
        numberColors.put(10, "B");
        numberColors.put(11, "B");
        numberColors.put(13, "B");
        numberColors.put(15, "B");
        numberColors.put(17, "B");
        numberColors.put(20, "B");
        numberColors.put(22, "B");
        numberColors.put(24, "B");
        numberColors.put(26, "B");
        numberColors.put(28, "B");
        numberColors.put(29, "B");
        numberColors.put(31, "B");
        numberColors.put(33, "B");
        numberColors.put(35, "B");

        // Green numbers
        numberColors.put(0, "G");
        numberColors.put(37, "G");
    }

    private final String winningNumber;

    public RouletteTable(int winningNumber) {
        if (winningNumber < 0 || (winningNumber > 36 && winningNumber != 37)) {
            this.winningNumber = null; // or throw an exception
        } else {
            this.winningNumber = (winningNumber == 37) ? "00" : String.valueOf(winningNumber);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("┌──────────────────────────" + ANSI_YELLOW + " ROULETTE TABLE " + ANSI_RESET + "───────────────────────────┐\n");
        sb.append("│                                                                     │\n");

        // Top row with 0 and 00
        String zero = formatCell("0", "G", 3);
        String doubleZero = formatCell("00", "G", 3);
        sb.append(String.format("│%29s%s%2s%s%26s      │\n", "", zero, "", doubleZero, ""));

        sb.append("│     ┌────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┐   │\n");

        // Main number grid
        for (int i = 3; i >= 1; i--) {
            sb.append("│     │");
            for (int j = 0; j < 12; j++) {
                int number = i + (j * 3);
                String numberString = String.valueOf(number);
                String color = numberColors.get(number);
                sb.append(formatCell(numberString, color, 4));
                sb.append("│");
            }
            sb.append("   │    \n"); // Column for 2-to-1 bets
        }

        sb.append("│     └────┴────┴────┴────┴────┴────┴────┴────┴────┴────┴────┴────┘   │\n");

        // Dozens bets
        String first12 = getWinningCategoryHighlight("1st 12", 1, 12, 10);
        String second12 = getWinningCategoryHighlight("2nd 12", 13, 24, 10);
        String third12 = getWinningCategoryHighlight("3rd 12", 25, 36, 10);

        sb.append("│          ┌────────────┬────────────┬────────────┐                   │\n");
        sb.append(String.format("│          │%s│%s│%s│                   │\n", first12, second12, third12));
        sb.append("│          └────────────┴────────────┴────────────┘                   │\n");

        // Even/Odd, Red/Black, High/Low bets
        String range18 = getWinningCategoryHighlight("1-18", 1, 18, 4);
        String even = getWinningCategoryHighlight("EVEN", -1, -1, 4);
        String red = getWinningCategoryHighlight("RED", -1, -1, 4);
        String black = getWinningCategoryHighlight("BLK", -1, -1, 4);
        String odd = getWinningCategoryHighlight("ODD", -1, -1, 4);
        String range36 = getWinningCategoryHighlight("19-36", 19, 36, 4);

        // The column bets section from your table layout
        String col1 = getWinningCategoryHighlight("1st", 1, 34, "col1", 3);
        String col2 = getWinningCategoryHighlight("2nd", 2, 35, "col2", 3);
        String col3 = getWinningCategoryHighlight("3rd", 3, 36, "col3", 3);

        sb.append("│      ┌──────┬──────┬──────┬──────┬──────┬───────┐                   │\n");
        sb.append(String.format("│      │%s│%s│%s│%s│%s│%s│                   │\n",
                range18, even, red, black, odd, range36));
        sb.append("│      └──────┴──────┴──────┴──────┴──────┴───────┘                   │\n");

        sb.append("│      ┌───┬───┬───┐                                                  │\n");
        sb.append(String.format("│      │%s│%s│%s│                                                  │\n", col1, col2,
                col3));
        sb.append("│      └───┴───┴───┘                                                  │\n");

        sb.append("│                                                                     │\n");
        sb.append("└─────────────────────────────────────────────────────────────────────┘\n");

        return sb.toString();
    }

    private String formatCell(String number, String color, int width) {
        String coloredNumber;
        if (number.equals(this.winningNumber)) {
            coloredNumber = ANSI_YELLOW + String.format("%-" + (width - 1) + "s", number) + ANSI_RESET + "*";
        } else if ("R".equals(color)) {
            coloredNumber = ANSI_RED + String.format("%-" + width + "s", number) + ANSI_RESET;
        } else if ("B".equals(color)) {
            coloredNumber = ANSI_GRAY + String.format("%-" + width + "s", number) + ANSI_RESET;
        } else { // 0, 00, or any other
            coloredNumber = String.format("%-" + width + "s", number);
        }
        return coloredNumber;
    }

    private String getWinningCategoryHighlight(String category, int min, int max, int width) {
        String content = category;
        int winningNum = -1;
        try {
            winningNum = Integer.parseInt(this.winningNumber);
        } catch (NumberFormatException e) {
            if (this.winningNumber != null && this.winningNumber.equals("00")) {
                winningNum = 37;
            }
        }
        // If still -1, treat as "00"
        if (winningNum == -1) {
            winningNum = 37;
        }

        boolean isWinning = false;

        // Check for Dozens and High/Low
        if (min != -1 && max != -1 && winningNum >= min && winningNum <= max) {
            isWinning = true;
        }

        // Check for Even/Odd, Red/Black
        if (category.equals("EVEN")) {
            isWinning = (winningNum != 0 && winningNum != 37 && winningNum % 2 == 0);
        } else if (category.equals("ODD")) {
            isWinning = (winningNum != 0 && winningNum != 37 && winningNum % 2 != 0);
        } else if (category.equals("RED")) {
            isWinning = "R".equals(numberColors.get(winningNum));
        } else if (category.equals("BLK")) {
            isWinning = "B".equals(numberColors.get(winningNum));
        }

        if (isWinning) {
            return ANSI_YELLOW + String.format(" %-" + width + "s ", content) + ANSI_RESET;
        }
        return String.format(" %-" + width + "s ", content);
    }

    private String getWinningCategoryHighlight(String category, int startNum, int endNum, String colIdentifier,
            int width) {
        String content = category;
        int winningNum = -1;
        try {
            winningNum = Integer.parseInt(this.winningNumber);
        } catch (NumberFormatException e) {
            if (this.winningNumber != null && this.winningNumber.equals("00")) {
                winningNum = 37;
            }
        }
        // If still -1, treat as "00"
        if (winningNum == -1) {
            winningNum = 37;
        }

        boolean isWinning = false;
        if (winningNum > 0 && winningNum < 37) {
            if (winningNum % 3 == 1 && colIdentifier.equals("col1")) {
                isWinning = true;
            }
            if (winningNum % 3 == 2 && colIdentifier.equals("col2")) {
                isWinning = true;
            }
            if (winningNum % 3 == 0 && colIdentifier.equals("col3")) {
                isWinning = true;
            }
        }

        if (isWinning) {
            return ANSI_YELLOW + String.format("%-" + width + "s", content) + ANSI_RESET;
        }
        return String.format("%-" + width + "s", content);
    }
}