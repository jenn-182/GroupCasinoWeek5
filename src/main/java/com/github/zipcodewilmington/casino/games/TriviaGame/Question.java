package com.github.zipcodewilmington.casino.games.TriviaGame;

public class Question {
    private final String category;
    private final String question;
    private final String answerOne;   // A
    private final String answerTwo;   // B
    private final String answerThree; // C
    private final String answerFour;  // D
    private final String correctAnswer;

    public Question(String category, String question, String answerOne, String answerTwo,
                    String answerThree, String answerFour, String correctAnswer) {
        this.category = category;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;

        // Safe null handling for unit tests
        this.correctAnswer = (correctAnswer == null) 
            ? null 
            : correctAnswer.toUpperCase();
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

