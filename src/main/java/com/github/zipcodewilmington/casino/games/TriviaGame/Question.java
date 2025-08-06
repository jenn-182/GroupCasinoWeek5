package com.github.zipcodewilmington.casino.games.TriviaGame;

public class Question {
    // You can add methods or fields here to manage trivia questions as needed.
    public String category;
    public String question;
    public String answerOne;
    public String answerTwo;
    public String answerThree;
    public String answerFour;
    public String correctAnswer;

    public Question(String category, String question, String answerOne,
                    String answerTwo, String answerThree, String answerFour,
                    String correctAnswer) {
        this.category = category;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correctAnswer = correctAnswer;
    }
}

