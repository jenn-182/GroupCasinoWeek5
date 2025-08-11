"\nTRIVIA GAME RULES";
   ("===================================================================================================================");
   ("Trivia Game is a question-and-answer game where players test their knowledge across various categories.");
    ("Players can play solo or in multiplayer mode, answering questions to earn points.");
    ("The game consists of menu navigation, question rounds, and score tracking.\n");

    ("MENU OPTIONS:");
   ("===================================================================================================================");
    ("- Single Player: One player answers a set number of questions for the highest score.");
    System.out.println("- Multiplayer: Two players take turns answering questions. Highest score wins.");
    ("- Rules: Displays the instructions for how to play the game.\n");

    ("GAMEPLAY:");
    ("===================================================================================================================");
   ("- Players select their desired game mode from the menu.");
    ("- Questions are loaded from a 'questions.txt' file with categories (General, Science, History, etc.).");
   ("- Each question displays four answer choices: A, B, C, D.");
    ("- Players type the letter of their answer.\n");

    n("SCORING:");
   ("===================================================================================================================");
    ("- Correct Answer: +1 point.");
    ("- Incorrect Answer: 0 points.");
   ("- Multiplayer mode tracks scores separately for each player.\n");

   ("WIN CONDITIONS:");
   ("===================================================================================================================");
    ("- Single Player: Maximize your score. Final score is displayed at the end.");
  ("- Multiplayer: Player with the highest score wins. A tie results in a draw.\n");

   ("CATEGORIES:");
   ("===================================================================================================================");
    ("- Questions may come from:");
   ("  - General Knowledge");
    ("  - Science");
    ("  - History");
   ("  - Sports");
    ("  - Geography\n");

    ("SPECIAL FEATURES:");
    ("===================================================================================================================");
    ("- Random Question Order: Questions are shuffled each game.");
    ("- Category Filtering: Play from a single category or mixed categories.");
    ("- Replay Option: Start another round immediately after finishing.");
   ("- Score Saving: Scores are saved to 'scores.txt'.");
   "===========================================================================