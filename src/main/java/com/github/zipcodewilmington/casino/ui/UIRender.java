package com.github.zipcodewilmington.casino.ui;

import com.github.zipcodewilmington.casino.Player;

public class UIRender {

        String red = "\u001B[31m";
        String white = "\u001B[97m"; // Changed to bright white text
        String reset = "\u001B[0m";
        String black = "\u001B[40m";
        String blue = "\u001B[34m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String green = "\u001B[32m";
        String purple = "\u001B[35m";

        // Clears the console screen (works on most terminals)
        public void flushScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

    public void displayWelcomeHeader() {


        System.out.println("       " + white + "█████" + reset + "                    " + white + "█████" + reset
                + "                          " + white + "█████" + reset + "   ");
        System.out.println("      " + red + "░░" + white + "███" + reset + "                    " + red + "░░" + white
                + "███" + reset + "                          " + red + "░░" + white + "███" + reset + "    ");
        System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "██████" + reset + "    "
                + white + "██████" + reset + "  " + red + "░" + white + "███" + reset + " " + white + "█████" + reset
                + " " + white + "████████" + reset + "   " + white + "██████" + reset + "  " + white + "███████" + reset
                + "  ");
        System.out.println("       " + red + "░" + white + "███" + reset + "  " + red + "░░░░░" + white + "███" + reset
                + "  " + white + "███" + red + "░░" + white + "███" + reset + " " + red + "░" + white + "███" + red
                + "░░" + white + "███" + reset + " " + red + "░░" + white + "███" + red + "░░" + white + "███" + reset
                + " " + white + "███" + red + "░░" + white + "███" + red + "░░░" + white + "███" + red + "░" + reset
                + "   ");
        System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "███████" + reset + " " + red
                + "░" + white + "███" + reset + " " + red + "░░░" + reset + "  " + red + "░" + white + "██████" + red
                + "░" + reset + "   " + red + "░" + white + "███" + reset + " " + red + "░" + white + "███" + red + "░"
                + white + "███" + reset + " " + red + "░" + white + "███" + reset + "  " + red + "░" + white + "███"
                + reset + "    ");
        System.out.println(" " + white + "███" + reset + "   " + red + "░" + white + "███" + reset + "  " + white
                + "███" + red + "░░" + white + "███" + reset + " " + red + "░" + white + "███" + reset + "  " + white
                + "███" + reset + " " + red + "░" + white + "███" + red + "░░" + white + "███" + reset + "  " + red
                + "░" + white + "███" + reset + " " + red + "░" + white + "███" + red + "░" + white + "███" + reset
                + " " + red + "░" + white + "███" + reset + "  " + red + "░" + white + "███" + reset + " " + white
                + "███" + reset);
        System.out.println(red + "░░" + white + "████████" + reset + "  " + red + "░░" + white + "████████" + red + "░░"
                + white + "██████" + reset + "  " + white + "████" + reset + " " + white + "█████" + reset + " " + red
                + "░" + white + "███████" + reset + " " + red + "░░" + white + "██████" + reset + "   " + red + "░░"
                + white + "█████" + reset + " ");
        System.out.println(" " + red + "░░░░░░░░" + reset + "    " + red + "░░░░░░░░" + reset + "  " + red + "░░░░░░"
                + reset + "  " + red + "░░░░" + reset + " " + red + "░░░░░" + reset + "  " + red + "░" + white + "███"
                + red + "░░░" + reset + "   " + red + "░░░░░░" + reset + "     " + red + "░░░░░" + reset + "  ");
        System.out.println("       " + white + "█████" + reset + "                               " + red + "░" + white
                + "███" + reset + "          " + white + "███" + reset + "           ");
        System.out.println("      " + red + "░░" + white + "███" + reset + "                                " + white
                + "█████" + reset + "        " + white + "██████" + reset + "         ");
        System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "██████" + reset + "   "
                + white + "█████" + reset + " " + white + "█████" + reset + "  " + white + "██████" + red + "░░░░░"
                + reset + "        " + white + "███" + red + "░░░" + reset + "          ");
        System.out.println(
                "       " + red + "░" + white + "███" + reset + "  " + red + "░░░░░" + white + "███" + reset + " " + red
                        + "░░" + white + "███" + reset + " " + red + "░░" + white + "███" + reset + "  " + red + "░░░░░"
                        + white + "███" + reset + "           " + red + "░░" + white + "█████" + reset + "          ");
        System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "███████" + reset + "  "
                + red + "░" + white + "███" + reset + "  " + red + "░" + white + "███" + reset + "   " + white
                + "███████" + reset + "            " + red + "░░░░" + white + "███" + reset + "         ");
        System.out.println(" " + white + "███" + reset + "   " + red + "░" + white + "███" + reset + "  " + white
                + "███" + red + "░░" + white + "███" + reset + "  " + red + "░░" + white + "███" + reset + " " + white
                + "███" + reset + "   " + white + "███" + red + "░░" + white + "███" + reset + "            " + white
                + "██████" + reset + "          ");
        System.out.println(red + "░░" + white + "████████" + reset + "  " + red + "░░" + white + "████████" + reset
                + "  " + red + "░░" + white + "█████" + reset + "   " + red + "░░" + white + "████████" + reset
                + "          " + red + "░░░" + white + "███" + reset + "           ");
        System.out.println(" " + red + "░░░░░░░░" + reset + "    " + red + "░░░░░░░░" + reset + "    " + red + "░░░░░"
                + reset + "     " + red + "░░░░░░░░" + reset + "             " + red + "░░░" + reset + "            ");
        System.out.println("  ");
        System.out.println("  ");

    }

    public void displayMainMenuHeader() {
    flushScreen();
    displayWelcomeHeader();
    String borderColor = "\u001B[97m";     // White borders
    String titleColor = "\u001B[97m";      // White title
    String welcomeColor = "\u001B[93m";    // gold welcome text
    String optionNumbers = "\u001B[91m";   // Bright red numbers
    String optionText = "\u001B[97m";      // White option text
    String background = "\u001B[40m";      // Black background
    
    System.out.println(borderColor + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
    System.out.println(borderColor + "║" + background + optionNumbers + "                         CASINO MAIN MENU                            " + reset + borderColor +  " ║" + reset);
    System.out.println(borderColor + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
    System.out.println(borderColor + "║" + background + welcomeColor + "                   Welcome to Jackpot Java Casino!                   " + reset + borderColor + " ║" + reset);
    System.out.println(borderColor + "║" + background + welcomeColor + "                     Ready to test your luck?                        " + reset + borderColor + " ║" + reset);
    System.out.println(borderColor + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
    System.out.println();
    
    System.out.println(borderColor + "┌──────────────────────────────────────────────────────────────────────┐" + reset);
    System.out.println(borderColor + "│" + background + welcomeColor + "  SELECT AN OPTION:                                                   " + reset + borderColor + "│" + reset);
    System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
    System.out.println(borderColor + "│" + background + optionNumbers + "  [1] " + optionText + "Register New Account                                            " + reset + borderColor + "│" + reset);
    System.out.println(borderColor + "│" + background + optionNumbers + "  [2] " + optionText + "Login to Existing Account                                       " + reset + borderColor + "│" + reset);
    System.out.println(borderColor + "│" + background + optionNumbers + "  [3] " + optionText + "Exit Casino                                                     " + reset + borderColor + "│" + reset);
    System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
    System.out.println(borderColor + "└──────────────────────────────────────────────────────────────────────┘" + reset);
}

public void displayCasinoMenuHeader(String username) {
        flushScreen();
        String borderColor = "\u001B[32m";     // Green borders (casino theme)
        String titleColor = "\u001B[93m";      // Gold title
        String welcomeColor = "\u001B[97m";    // White welcome text
        String optionNumbers = "\u001B[91m";   // Bright red numbers
        String optionText = "\u001B[97m";      // White option text
        String background = "\u001B[40m";      // Black background
        
        System.out.println(borderColor + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
        System.out.println(borderColor + "║" + background + titleColor + "                            CASINO FLOOR                              " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
        System.out.println(borderColor + "║" + background + welcomeColor + "                       Welcome back, " + username + "!                         " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "║" + background + welcomeColor + "                        Let the games begin!                          " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
        System.out.println();
        
        System.out.println(borderColor + "┌──────────────────────────────────────────────────────────────────────┐" + reset);
        System.out.println(borderColor + "│" + background + titleColor + "  MAIN MENU:                                                          " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [1] " + optionText + "Play Game                                                       " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [2] " + optionText + "Banking & Account Management                                    " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [3] " + optionText + "Gaming Profile & History                                        " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [4] " + optionText + "Logout                                                          " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "└──────────────────────────────────────────────────────────────────────┘" + reset);
    }

    public void displayGameSelectionHeader() {
        flushScreen();
        String borderColor = "\u001B[35m";     // Purple borders (games theme)
        String titleColor = "\u001B[93m";      // Gold title
        String welcomeColor = "\u001B[96m";    // Cyan welcome text
        String optionNumbers = "\u001B[91m";   // Bright red numbers
        String optionText = "\u001B[97m";      // White option text
        String background = "\u001B[40m";      // Black background
        
        System.out.println(borderColor + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
        System.out.println(borderColor + "║" + background + titleColor + "                           GAME SELECTION                             " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
        System.out.println(borderColor + "║" + background + optionText + "                       Choose your adventure!                         " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "║" + background + optionText + "                     May the odds be in your favor                    " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
        System.out.println();
        
        System.out.println(borderColor + "┌──────────────────────────────────────────────────────────────────────┐" + reset);
        System.out.println(borderColor + "│" + background + titleColor + "  AVAILABLE GAMES:                                                    " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [1] " + optionText + "Roulette                                                        " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [2] " + optionText + "Poker                                                           " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [3] " + optionText + "Craps                                                           " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [4] " + optionText + "Trivia                                                          " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [5] " + optionText + "Number Guess                                                    " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [6] " + optionText + "Back to Main Menu                                               " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "└──────────────────────────────────────────────────────────────────────┘" + reset);
    }

    public void displayBankingMenuHeader(String username, double balance) {
        String borderColor = "\u001B[34m";     // Blue borders (banking theme)
        String titleColor = "\u001B[93m";      // Gold title
        String welcomeColor = "\u001B[97m";    // White welcome text
        String balanceColor = "\u001B[33m";    // Yellow for balance
        String optionNumbers = "\u001B[91m";   // Bright red numbers
        String optionText = "\u001B[97m";      // White option text
        String background = "\u001B[40m";      // Black background
        
        System.out.println(borderColor + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
        System.out.println(borderColor + "║" + background + titleColor + "                     BANKING & ACCOUNT MANAGEMENT                     " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
        System.out.println(borderColor + "║" + background + welcomeColor + "                       Account Holder: " + username + "                        " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "║" + background + balanceColor + "                      Current Balance: $" + String.format("%.2f", balance) + "                        " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
        System.out.println();
        
        System.out.println(borderColor + "┌──────────────────────────────────────────────────────────────────────┐" + reset);
        System.out.println(borderColor + "│" + background + titleColor + "  BANKING OPTIONS:                                                    " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [1] " + optionText + "Deposit Money                                                   " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [2] " + optionText + "Withdraw Money                                                  " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [3] " + optionText + "View Transaction History                                        " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [4] " + optionText + "Back to Main Menu                                               " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "└──────────────────────────────────────────────────────────────────────┘" + reset);
    }

    public void displayGamingProfileHeader(Player currentPlayer) {
        flushScreen();
        String borderColor = "\u001B[36m";     // Cyan borders (profile theme)
        String titleColor = "\u001B[93m";      // Gold title
        String welcomeColor = "\u001B[97m";    // White welcome text
        String optionNumbers = "\u001B[91m";   // Bright red numbers
        String optionText = "\u001B[97m";      // White option text
        String background = "\u001B[40m";      // Black background
        String balanceColor = "\u001B[33m";    // Yellow for balance
        String statusColor = "\u001B[96m";     // Cyan for status
        
        // Profile Information
        String username = currentPlayer.getUsername();
        double balance = currentPlayer.getAccount().getBalance();
        java.util.List<String> gameHistory = currentPlayer.getAccount().getGameHistory();
        int totalGames = gameHistory.size();
        String playerStatus = gameHistory.isEmpty() ? "New Player - No games played yet" : "Active Gamer";
        
        System.out.println(borderColor + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
        System.out.println(borderColor + "║" + background + titleColor + "                      GAMING PROFILE & HISTORY                        " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
        System.out.println(borderColor + "║" + background + welcomeColor + "                        Player:  " + username + "                              " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "║" + background + balanceColor + "                     Account Balance: $" + String.format("%.2f", balance) + "                         " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "║" + background + welcomeColor + "                     Total Games Played: " + totalGames + "                            " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "║" + background + titleColor + "                     Player Status: " + playerStatus + "           " + reset + borderColor + "║" + reset);
        System.out.println(borderColor + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
        System.out.println();
        
        System.out.println(borderColor + "┌──────────────────────────────────────────────────────────────────────┐" + reset);
        System.out.println(borderColor + "│" + background + titleColor + "  PROFILE OPTIONS:                                                    " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [1] " + optionText + "View Game History                                               " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [2] " + optionText + "View Gaming Statistics                                          " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + optionNumbers + "  [3] " + optionText + "Back to Main Menu                                               " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "│" + background + "                                                                      " + reset + borderColor + "│" + reset);
        System.out.println(borderColor + "└──────────────────────────────────────────────────────────────────────┘" + reset);
    }
}
