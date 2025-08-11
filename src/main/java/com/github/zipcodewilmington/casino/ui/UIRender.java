package com.github.zipcodewilmington.casino.ui;

import com.github.zipcodewilmington.casino.Player;

public class UIRender {

        String red = "\u001B[31m";
        String white = "\u001B[97m";
        String reset = "\u001B[0m";
        String black = "\u001B[40m";
        String blue = "\u001B[34m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String green = "\u001B[32m";
        String purple = "\u001B[35m";
        public static final String PURPLE = "\u001B[35m";
        public static final String RESET = "\u001B[0m";
        public static final String GREEN = "\u001B[32m";
        public static final String CYAN = "\u001B[36m";
        public static final String YELLOW = "\u001B[33m";
        public static final String RED = "\u001B[31m";
        public static final String WHITE = "\u001B[37m";
        public static final String BLACK = "\u001B[40m";
        public static final String MAGENTA = "\u001B[35m";

        public void flushScreen() {
                System.out.print("\033[H\033[2J");
                System.out.flush();
        }

        public void displayWelcomeHeader() {
                System.out.println(
                                "       " + white + "█████" + reset + "                    " + white + "█████" + reset
                                                + "                          " + white + "█████" + reset + "   ");
                System.out.println("      " + red + "░░" + white + "███" + reset + "                    " + red + "░░"
                                + white
                                + "███" + reset + "                          " + red + "░░" + white + "███" + reset
                                + "    ");
                System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "██████" + reset
                                + "    "
                                + white + "██████" + reset + "  " + red + "░" + white + "███" + reset + " " + white
                                + "█████" + reset
                                + " " + white + "████████" + reset + "   " + white + "██████" + reset + "  " + white
                                + "███████" + reset
                                + "  ");
                System.out.println("       " + red + "░" + white + "███" + reset + "  " + red + "░░░░░" + white + "███"
                                + reset
                                + "  " + white + "███" + red + "░░" + white + "███" + reset + " " + red + "░" + white
                                + "███" + red
                                + "░░" + white + "███" + reset + " " + red + "░░" + white + "███" + red + "░░" + white
                                + "███" + reset
                                + " " + white + "███" + red + "░░" + white + "███" + red + "░░░" + white + "███" + red
                                + "░" + reset
                                + "   ");
                System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "███████" + reset
                                + " " + red
                                + "░" + white + "███" + reset + " " + red + "░░" + reset + "  " + red + "░" + white
                                + "██████" + red
                                + "░" + reset + "   " + red + "░" + white + "███" + reset + " " + red + "░" + white
                                + "███" + red + "░"
                                + white + "███" + reset + " " + red + "░" + white + "███" + reset + "  " + red + "░"
                                + white + "███"
                                + reset + "    ");
                System.out.println(
                                " " + white + "███" + reset + "   " + red + "░" + white + "███" + reset + "  " + white
                                                + "███" + red + "░░" + white + "███" + reset + " " + red + "░" + white
                                                + "███" + reset + "  " + white
                                                + "███" + reset + " " + red + "░" + white + "███" + red + "░░" + white
                                                + "███" + reset + "  " + red
                                                + "░" + white + "███" + reset + " " + red + "░" + white + "███" + red
                                                + "░" + white + "███" + reset
                                                + " " + red + "░" + white + "███" + reset + "  " + red + "░" + white
                                                + "███" + reset + " " + white
                                                + "███" + reset);
                System.out.println(red + "░░" + white + "████████" + reset + "  " + red + "░░" + white + "████████"
                                + red + "░░"
                                + white + "██████" + reset + "  " + white + "████" + reset + " " + white + "█████"
                                + reset + " " + red
                                + "░" + white + "███████" + reset + " " + red + "░░" + white + "██████" + reset + "   "
                                + red + "░░"
                                + white + "█████" + reset + " ");
                System.out.println(" " + red + "░░░░░░░░" + reset + "    " + red + "░░░░░░░░" + reset + "  " + red
                                + "░░░░░░"
                                + reset + "  " + red + "░░░░" + reset + " " + red + "░░░░░" + reset + "  " + red + "░"
                                + white + "███"
                                + red + "░░░" + reset + "   " + red + "░░░░░░" + reset + "     " + red + "░░░░░" + reset
                                + "  ");
                System.out.println("       " + white + "█████" + reset + "                               " + red + "░"
                                + white
                                + "███" + reset + "          " + white + "███" + reset + "           ");
                System.out.println("      " + red + "░░" + white + "███" + reset + "                                "
                                + white
                                + "█████" + reset + "        " + white + "██████" + reset + "         ");
                System.out.println("       " + red + "░" + white + "███" + reset + "   " + white + "██████" + reset
                                + "   "
                                + white + "█████" + reset + " " + white + "█████" + reset + "  " + white + "██████"
                                + red + "░░░░░"
                                + reset + "        " + white + "███" + red + "░░░" + reset + "          ");
                System.out.println(
                                "       " + red + "░" + white + "███" + reset + "  " + red + "░░░░░" + white + "███"
                                                + reset + " " + red
                                                + "░░" + white + "███" + reset + " " + red + "░░" + white + "███"
                                                + reset + "  " + red + "░░░░░"
                                                + white + "███" + reset + "           " + red + "░░" + white + "█████"
                                                + reset + "          ");
                System.out.println(
                                "       " + red + "░" + white + "███" + reset + "   " + white + "███████" + reset + "  "
                                                + red + "░" + white + "███" + reset + "  " + red + "░" + white + "███"
                                                + reset + "   " + white
                                                + "███████" + reset + "            " + red + "░░░░" + white + "███"
                                                + reset + "         ");
                System.out.println(
                                " " + white + "███" + reset + "   " + red + "░" + white + "███" + reset + "  " + white
                                                + "███" + red + "░░" + white + "███" + reset + "  " + red + "░░" + white
                                                + "███" + reset + " " + white
                                                + "███" + reset + "   " + white + "███" + red + "░░" + white + "███"
                                                + reset + "            " + white
                                                + "██████" + reset + "          ");
                System.out.println(
                                red + "░░" + white + "████████" + reset + "  " + red + "░░" + white + "████████" + reset
                                                + "  " + red + "░░" + white + "█████" + reset + "   " + red + "░░"
                                                + white + "████████" + reset
                                                + "          " + red + "░░░" + white + "███" + reset + "           ");
                System.out.println(" " + red + "░░░░░░░░" + reset + "    " + red + "░░░░░░░░" + reset + "    " + red
                                + "░░░░░"
                                + reset + "     " + red + "░░░░░░░░" + reset + "             " + red + "░░░" + reset
                                + "            ");
                System.out.println("  ");
                System.out.println("  ");
        }

        public void displayMainMenuHeader() {
                flushScreen();
                displayWelcomeHeader();
                String borderColor = white;
                String titleColor = white;
                String welcomeColor = yellow;
                String optionNumbers = red;
                String optionText = white;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(borderColor + "║" + background + optionNumbers + centerText("CASINO MAIN MENU", 70)
                                + reset
                                + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╠══════════════════════════════════════════════════════════════════════╣"
                                                + reset);
                System.out.println(borderColor + "║" + background + welcomeColor
                                + centerText("Welcome to Jackpot Java Casino!", 70) + reset + borderColor + "║"
                                + reset);
                System.out.println(borderColor + "║" + background + welcomeColor
                                + centerText("Ready to test your luck?", 70)
                                + reset + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(borderColor + "│" + background + welcomeColor + centerText("SELECT AN OPTION:", 70)
                                + reset
                                + borderColor + "│" + reset);
                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);

                String[] options = {
                                "[1] Register New Account",
                                "[2] Login to Existing Account",
                                "[3] Exit Casino"
                };

                for (String option : options) {
                        int spaceIdx = option.indexOf(' ');
                        String numberPart = (spaceIdx > 0) ? option.substring(0, spaceIdx) : option;
                        String textPart = (spaceIdx > 0) ? option.substring(spaceIdx) : "";
                        System.out.println(borderColor + "│" + background
                                        + red + numberPart
                                        + white + padToWidth(textPart, 70 - numberPart.length())
                                        + reset + borderColor + "│" + reset);
                }

                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
        }

        public void displayCasinoMenuHeader(String username) {
                flushScreen();
                String borderColor = green;
                String titleColor = yellow;
                String welcomeColor = white;
                String optionNumbers = red;
                String optionText = white;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(borderColor + "║" + background + titleColor + centerText("CASINO FLOOR", 70) + reset
                                + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╠══════════════════════════════════════════════════════════════════════╣"
                                                + reset);

                String welcomeMessage = "Welcome back, " + username + "!";
                System.out.println(
                                borderColor + "║" + background + welcomeColor + centerText(welcomeMessage, 70) + reset
                                                + borderColor + "║" + reset);

                String gameMessage = "Let the games begin!";
                System.out.println(borderColor + "║" + background + welcomeColor + centerText(gameMessage, 70) + reset
                                + borderColor + "║" + reset);

                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(borderColor + "│" + background + titleColor + centerText("MAIN MENU:", 70) + reset
                                + borderColor + "│" + reset);
                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);

                String[] options = {
                                "[1] Play Game",
                                "[2] Banking & Account Management",
                                "[3] Gaming Profile & History",
                                "[4] Logout"
                };
                for (String option : options) {
                        int spaceIdx = option.indexOf(' ');
                        String numberPart = (spaceIdx > 0) ? option.substring(0, spaceIdx) : option;
                        String textPart = (spaceIdx > 0) ? option.substring(spaceIdx) : "";
                        System.out.println(borderColor + "│" + background
                                        + red + numberPart
                                        + white + padToWidth(textPart, 70 - numberPart.length())
                                        + reset + borderColor + "│" + reset);
                }

                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
        }

        public void displayGameSelectionHeader() {
                flushScreen();
                String borderColor = purple;
                String titleColor = yellow;
                String optionNumbers = red;
                String optionText = white;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(
                                borderColor + "║" + background + titleColor + centerText("GAME SELECTION", 70) + reset
                                                + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╠══════════════════════════════════════════════════════════════════════╣"
                                                + reset);
                System.out.println(borderColor + "║" + background + optionText + centerText("Choose your game!", 70)
                                + reset
                                + borderColor + "║" + reset);
                System.out.println(borderColor + "║" + background + optionText
                                + centerText("May the odds be in your favor", 70)
                                + reset + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(
                                borderColor + "│" + background + titleColor + centerText("AVAILABLE GAMES:", 70) + reset
                                                + borderColor + "│" + reset);
                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);

                String[] games = {
                                "[1] Roulette",
                                "[2] Poker",
                                "[3] Craps",
                                "[4] Trivia",
                                "[5] Number Guess",
                                "[6] Back to Main Menu"
                };
                for (String game : games) {
                        int spaceIdx = game.indexOf(' ');
                        String numberPart = (spaceIdx > 0) ? game.substring(0, spaceIdx) : game;
                        String textPart = (spaceIdx > 0) ? game.substring(spaceIdx) : "";
                        System.out.println(borderColor + "│" + background
                                        + red + numberPart
                                        + white + padToWidth(textPart, 70 - numberPart.length())
                                        + reset + borderColor + "│" + reset);
                }

                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
        }

        public void displayBankingMenuHeader(String username, double balance) {
                String borderColor = blue;
                String titleColor = yellow;
                String welcomeColor = white;
                String balanceColor = yellow;
                String optionNumbers = red;
                String optionText = white;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(borderColor + "║" + background + titleColor
                                + centerText("BANKING & ACCOUNT MANAGEMENT", 70)
                                + reset + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╠══════════════════════════════════════════════════════════════════════╣"
                                                + reset);

                String accountMessage = "Account Holder: " + username;
                System.out.println(
                                borderColor + "║" + background + welcomeColor + centerText(accountMessage, 70) + reset
                                                + borderColor + "║" + reset);

                String balanceMessage = "Current Balance: $" + String.format("%.2f", balance);
                System.out.println(
                                borderColor + "║" + background + balanceColor + centerText(balanceMessage, 70) + reset
                                                + borderColor + "║" + reset);

                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(
                                borderColor + "│" + background + titleColor + centerText("BANKING OPTIONS:", 70) + reset
                                                + borderColor + "│" + reset);
                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);

                String[] options = {
                                "[1] Deposit Money",
                                "[2] Withdraw Money",
                                "[3] View Transaction History",
                                "[4] Back to Main Menu"
                };
                for (String option : options) {
                        int spaceIdx = option.indexOf(' ');
                        String numberPart = (spaceIdx > 0) ? option.substring(0, spaceIdx) : option;
                        String textPart = (spaceIdx > 0) ? option.substring(spaceIdx) : "";
                        System.out.println(borderColor + "│" + background
                                        + red + numberPart
                                        + white + padToWidth(textPart, 70 - numberPart.length())
                                        + reset + borderColor + "│" + reset);
                }

                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
        }

        public void displayGamingProfileHeader(Player currentPlayer) {
                flushScreen();
                String borderColor = cyan;
                String titleColor = yellow;
                String welcomeColor = white;
                String optionNumbers = red;
                String optionText = white;
                String background = black;
                String balanceColor = yellow;

                String username = currentPlayer.getUsername();
                double balance = currentPlayer.getAccount().getBalance();
                java.util.List<String> gameHistory = currentPlayer.getAccount().getGameHistory();
                int totalGames = gameHistory.size();
                String playerStatus = gameHistory.isEmpty() ? "New Player - No games played yet" : "Active Gamer";

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(
                                borderColor + "║" + background + titleColor + centerText("GAMING PROFILE & HISTORY", 70)
                                                + reset + borderColor + "║" + reset);
                System.out.println(
                                borderColor + "╠══════════════════════════════════════════════════════════════════════╣"
                                                + reset);

                System.out.println(borderColor + "║" + background + welcomeColor + centerText("Player: " + username, 70)
                                + reset
                                + borderColor + "║" + reset);
                System.out.println(borderColor + "║" + background + balanceColor
                                + centerText("Account Balance: $" + String.format("%.2f", balance), 70) + reset
                                + borderColor + "║"
                                + reset);
                System.out.println(borderColor + "║" + background + welcomeColor
                                + centerText("Total Games Played: " + totalGames, 70) + reset + borderColor + "║"
                                + reset);
                System.out.println(borderColor + "║" + background + titleColor
                                + centerText("Player Status: " + playerStatus, 70) + reset + borderColor + "║" + reset);

                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(
                                borderColor + "│" + background + titleColor + centerText("PROFILE OPTIONS:", 70) + reset
                                                + borderColor + "│" + reset);
                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);

                String[] options = {
                                "[1] View Game History",
                                "[2] View Gaming Statistics",
                                "[3] Back to Main Menu"
                };
                for (String option : options) {
                        int spaceIdx = option.indexOf(' ');
                        String numberPart = (spaceIdx > 0) ? option.substring(0, spaceIdx) : option;
                        String textPart = (spaceIdx > 0) ? option.substring(spaceIdx) : "";
                        System.out.println(borderColor + "│" + background
                                        + red + numberPart
                                        + white + padToWidth(textPart, 70 - numberPart.length())
                                        + reset + borderColor + "│" + reset);
                }

                System.out.println(borderColor + "│" + background + padToWidth("", 70) + reset + borderColor + "│"
                                + reset);
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
        }

        // SCREEN TEMPLATES
        public void displaySimpleHeader(String title, String borderColor) {
                String titleColor = yellow;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(borderColor + "║" + background + titleColor + centerText(title, 70) + reset
                                + borderColor
                                + "║" + reset);
                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();
        }

        public void displayInfoHeader(String title, String info, String borderColor) {
                String titleColor = yellow;
                String infoColor = white;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(borderColor + "║" + background + titleColor + centerText(title, 70) + reset
                                + borderColor
                                + "║" + reset);
                System.out.println(
                                borderColor + "╟──────────────────────────────────────────────────────────────────────╢"
                                                + reset);
                System.out.println(
                                borderColor + "║" + background + infoColor + centerText(info, 70) + reset + borderColor
                                                + "║" + reset);
                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();
        }

        public void displayDataHeader(String title, String[] dataLines, String borderColor) {
                String titleColor = yellow;
                String dataColor = white;
                String background = black;

                System.out.println(
                                borderColor + "╔══════════════════════════════════════════════════════════════════════╗"
                                                + reset);
                System.out.println(borderColor + "║" + background + titleColor + centerText(title, 70) + reset
                                + borderColor
                                + "║" + reset);
                System.out.println(
                                borderColor + "╠══════════════════════════════════════════════════════════════════════╣"
                                                + reset);

                if (dataLines != null) {
                        for (String line : dataLines) {
                                System.out.println(borderColor + "║" + background + dataColor + centerText(line, 70)
                                                + reset
                                                + borderColor + "║" + reset);
                        }
                }

                System.out.println(
                                borderColor + "╚══════════════════════════════════════════════════════════════════════╝"
                                                + reset);
                System.out.println();
        }

        public void displayListHeader(String title, String borderColor) {
                String titleColor = yellow;
                String background = black;

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(borderColor + "│" + background + titleColor + centerText(title, 70) + reset
                                + borderColor
                                + "│" + reset);
                System.out.println(
                                borderColor + "├──────────────────────────────────────────────────────────────────────┤"
                                                + reset);
        }

        public void displayListItem(String item, String borderColor) {
                String itemColor = white;
                String background = black;

                System.out.println(borderColor + "│" + background + itemColor + "  " + padToWidth(item, 68) + reset
                                + borderColor + "│" + reset);
        }

        public void displayListFooter(String borderColor) {
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
                System.out.println();
        }

        public void displayEmptyMessage(String message, String borderColor) {
                String messageColor = white;
                String background = black;

                System.out.println(
                                borderColor + "┌──────────────────────────────────────────────────────────────────────┐"
                                                + reset);
                System.out.println(borderColor + "│" + background + messageColor + centerText(message, 70) + reset
                                + borderColor
                                + "│" + reset);
                System.out.println(
                                borderColor + "└──────────────────────────────────────────────────────────────────────┘"
                                                + reset);
                System.out.println();
        }

        // COLOR CONSTANTS FOR INTERNAL SCREENS
        public static final String LOGIN_COLOR = "\u001B[33m";
        public static final String HISTORY_COLOR = "\u001B[36m";
        public static final String MONEY_COLOR = "\u001B[34m";
        public static final String STATS_COLOR = "\u001B[35m";

        private String centerText(String text, int totalWidth) {
                if (text.length() >= totalWidth) {
                        return text.substring(0, totalWidth);
                }
                int padding = totalWidth - text.length();
                int leftPad = padding / 2;
                int rightPad = padding - leftPad;
                return String.format("%" + leftPad + "s%s%" + rightPad + "s", "", text, "");
        }

        private String padToWidth(String text, int totalWidth) {
                if (text.length() >= totalWidth) {
                        return text.substring(0, totalWidth);
                }
                return text + String.format("%" + (totalWidth - text.length()) + "s", "");
        }

        public void displayRegistrationHeader() {
                String borderColor = LOGIN_COLOR;
                String titleColor = yellow;
                String background = black;
                String messageColor = white;

                System.out.println(borderColor
                                + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
                System.out.println(borderColor + "║" + background + titleColor + centerText("REGISTER NEW ACCOUNT", 70)
                                + reset + borderColor + "║" + reset);
                System.out.println(borderColor
                                + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
                String registerMsg = "Please enter your details to create a new account.";
                System.out.println(borderColor + "║" + background + messageColor + centerText(registerMsg, 70) + reset
                                + borderColor + "║" + reset);
                System.out.println(borderColor
                                + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
                System.out.println();
        }

        public void displayConfirmationHeader(String title, String message, String borderColor) {
                String titleColor = yellow;
                String background = black;
                String messageColor = white;

                System.out.println(borderColor
                                + "╔══════════════════════════════════════════════════════════════════════╗" + reset);
                System.out.println(borderColor + "║" + background + titleColor
                                + centerText(title, 70) + reset + borderColor + "║" + reset);
                System.out.println(borderColor
                                + "╠══════════════════════════════════════════════════════════════════════╣" + reset);
                System.out.println(borderColor + "║" + background + messageColor + centerText(message, 70) + reset
                                + borderColor + "║" + reset);
                System.out.println(borderColor
                                + "╚══════════════════════════════════════════════════════════════════════╝" + reset);
                System.out.println();
        }

        public void displayGameWelcomeHeader(String gameName, String color) {
                String background = black;
                String textColor = yellow;
                System.out.println(color + background
                                + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(color + background + "║" + textColor
                                + centerText("WELCOME TO " + gameName.toUpperCase(), 70) + RESET + color + background
                                + "║" + RESET);
                System.out.println(color + background
                                + "╠══════════════════════════════════════════════════════════════════════╣" + RESET);
                System.out.println(color + background + "║" + textColor + centerText("1. Single Player", 70) + RESET
                                + color + background + "║" + RESET);
                System.out.println(color + background + "║" + textColor + centerText("2. Multiplayer", 70) + RESET
                                + color + background + "║" + RESET);
                System.out.println(color + background + "║" + textColor + centerText("3. View Rules", 70) + RESET
                                + color + background + "║" + RESET);
                System.out.println(color + background
                                + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
                System.out.println();
        }

        public void displayGameWelcomeHeaderSinglePlayer(String gameName, String color) {
                String background = black;
                String textColor = yellow;
                System.out.println(color + background
                                + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(color + background + "║" + textColor
                                + centerText("WELCOME TO " + gameName.toUpperCase(), 70) + RESET + color + background
                                + "║" + RESET);
                System.out.println(color + background
                                + "╠══════════════════════════════════════════════════════════════════════╣" + RESET);
                System.out.println(color + background + "║" + textColor + centerText("1. Single Player", 70) + RESET
                                + color + background + "║" + RESET);
                System.out.println(color + background + "║" + textColor + centerText("2. View Rules", 70) + RESET
                                + color + background + "║" + RESET);
                System.out.println(color + background
                                + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
                System.out.println();
        }

        public void displayRouletteWelcomeHeader() {
                flushScreen();
                System.out.println("  ");
                System.out.println("██████╗  ██████╗ ██╗   ██╗██╗     ███████╗████████╗████████╗███████╗██╗");
                System.out.println("██╔══██╗██╔═══██╗██║   ██║██║     ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██║");
                System.out.println("██████╔╝██║   ██║██║   ██║██║     █████╗     ██║      ██║   █████╗  ██║");
                System.out.println("██╔══██╗██║   ██║██║   ██║██║     ██╔══╝     ██║      ██║   ██╔══╝  ╚═╝");
                System.out.println("██║  ██║╚██████╔╝╚██████╔╝███████╗███████╗   ██║      ██║   ███████╗██╗");
                System.out.println("╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝");
                System.out.println("  ");
                System.out.println("  ");
                displayGameWelcomeHeader("Roulette", PURPLE);
        }

        public void displayPokerWelcomeHeader() {
                flushScreen();
                System.out.println("  ");
                System.out.println("         ██████╗  ██████╗ ██╗  ██╗███████╗██████╗     ██╗");
                System.out.println("         ██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝██╔══██╗    ██║");
                System.out.println("         ██████╔╝██║   ██║█████╔╝ █████╗  ██████╔╝    ██║");
                System.out.println("         ██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ██╔══██╗    ╚═╝");
                System.out.println("         ██║     ╚██████╔╝██║  ██╗███████╗██║  ██║    ██╗");
                System.out.println("         ╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝    ╚═╝");
                System.out.println("  ");
                System.out.println("  ");
                displayGameWelcomeHeaderSinglePlayer("Poker", PURPLE);
        }

        public void displayCrapsWelcomeHeader() {
                flushScreen();
                System.out.println("  ");
                System.out.println("          █████╗██████╗  █████╗ ██████╗ ███████╗    ██╗");
                System.out.println("        ██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝    ██║");
                System.out.println("        ██║     ██████╔╝███████║██████╔╝███████╗    ██║");
                System.out.println("        ██║     ██╔══██╗██╔══██║██╔═══╝ ╚════██║    ╚═╝");
                System.out.println("        ╚██████╗██║  ██║██║  ██║██║     ███████║    ██╗");
                System.out.println("         ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚══════╝    ╚═╝");
                System.out.println("  ");
                System.out.println("  ");
                displayGameWelcomeHeader("Craps", PURPLE);
        }

        public void displayTriviaWelcomeHeader() {
                flushScreen();
                System.out.println("  ");
                System.out.println("        ████████╗██████╗ ██╗██╗   ██╗██╗ █████╗     ██╗");
                System.out.println("        ╚══██╔══╝██╔══██╗██║██║   ██║██║██╔══██╗    ██║");
                System.out.println("           ██║   ██████╔╝██║██║   ██║██║███████║    ██║");
                System.out.println("           ██║   ██╔══██╗██║╚██╗ ██╔╝██║██╔══██║    ╚═╝");
                System.out.println("           ██║   ██║  ██║██║ ╚████╔╝ ██║██║  ██║    ██╗");
                System.out.println("           ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚═╝╚═╝  ╚═╝    ╚═╝");
                System.out.println("  ");
                System.out.println("  ");
                displayGameWelcomeHeader("Trivia", PURPLE);
        }

        public void displayNumberGuessWelcomeHeader() {
                flushScreen();
                System.out.println("  ");
                System.out.println("       ███╗   ██╗██╗   ██╗███╗   ███╗██████╗ ███████╗██████╗ ");
                System.out.println("       ████╗  ██║██║   ██║████╗ ████║██╔══██╗██╔════╝██╔══██╗");
                System.out.println("       ██╔██╗ ██║██║   ██║██╔████╔██║██████╔╝█████╗  ██████╔╝");
                System.out.println("       ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══██╗██╔══╝  ██╔══██╗");
                System.out.println("       ██║ ╚████║╚██████╔╝██║ ╚═╝ ██║██████╔╝███████╗██║  ██║");
                System.out.println("       ╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═╝");
                System.out.println("  ");
                System.out.println("        ██████╗ ██╗   ██╗███████╗███████╗███████╗    ██╗");
                System.out.println("       ██╔════╝ ██║   ██║██╔════╝██╔════╝██╔════╝    ██║");
                System.out.println("       ██║  ███╗██║   ██║█████╗  ███████╗███████╗    ██║");
                System.out.println("       ██║   ██║██║   ██║██╔══╝  ╚════██║╚════██║    ╚═╝");
                System.out.println("       ╚██████╔╝╚██████╔╝███████╗███████║███████║    ██╗");
                System.out.println("        ╚═════╝  ╚═════╝ ╚══════╝╚══════╝╚══════╝    ╚═╝");
                System.out.println("  ");
                System.out.println("  ");
                displayGameWelcomeHeaderSinglePlayer("Number Guess", PURPLE);
        }

        public void displayMultiplayerHeader(String gameName, String hostName) {
                flushScreen();
                String borderColor = PURPLE;
                String titleColor = YELLOW;
                String hostColor = GREEN;
                String background = BLACK; // Use static BLACK for background

                System.out.println(borderColor
                                + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(borderColor + "║" + background + titleColor
                                + centerText("  MULTIPLAYER SETUP  ", 70)
                                + RESET + borderColor + "║" + RESET);
                System.out.println(borderColor + "║" + background + hostColor
                                + centerText("Host: " + hostName.toUpperCase(), 70)
                                + RESET + borderColor + "║" + RESET);
                System.out.println(borderColor
                                + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
                System.out.println();
        }

        public void displayGoodbyeMessage() {
                String borderColor = RED;
                String titleColor = YELLOW;
                String background = BLACK;
                String messageColor = WHITE;

                displayWelcomeHeader();
                System.out.println(borderColor
                                + "╔══════════════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(
                                borderColor + "║" + background + titleColor + centerText("THANK YOU FOR PLAYING!", 70)
                                                + RESET + borderColor + "║" + RESET);
                System.out.println(borderColor
                                + "╠══════════════════════════════════════════════════════════════════════╣" + RESET);
                System.out.println(borderColor + "║" + background + messageColor
                                + centerText("We hope you enjoyed your time at the casino!", 70) + RESET
                                + borderColor + "║" + RESET);
                System.out.println(borderColor
                                + "╚══════════════════════════════════════════════════════════════════════╝" + RESET);
        }

}
