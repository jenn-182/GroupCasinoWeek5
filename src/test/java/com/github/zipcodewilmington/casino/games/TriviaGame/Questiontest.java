package com.github.zipcodewilmington.casino.games.TriviaGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Questiontest {

    @Test
    void testConstructorAndGetters() {
        Question question = new Question(
                "Science",
                "What is the chemical symbol for water?",
                "A) H2O",
                "B) O2",
                "C) CO2",
                "D) NaCl",
                "a" // lowercase to test auto-uppercase
        );

        assertEquals("Science", question.getCategory());
        assertEquals("What is the chemical symbol for water?", question.getQuestion());
        assertEquals("A) H2O", question.getAnswerOne());
        assertEquals("B) O2", question.getAnswerTwo());
        assertEquals("C) CO2", question.getAnswerThree());
        assertEquals("D) NaCl", question.getAnswerFour());
        assertEquals("A", question.getCorrectAnswer());
    }

    @Test
    void testCorrectAnswerIsUppercase() {
        Question question = new Question(
                "Math",
                "What is 2 + 2?",
                "A) 3",
                "B) 4",
                "C) 5",
                "D) 6",
                "b"
        );
        assertEquals("B", question.getCorrectAnswer());
    }

    @Test
    void testCorrectAnswerInvalidValue() {
        Question question = new Question(
                "Geography",
                "Which continent is Australia in?",
                "A) Africa",
                "B) Europe",
                "C) Asia",
                "D) Australia",
                "Z" // invalid answer
        );
        assertEquals("Z", question.getCorrectAnswer(), "Constructor should store invalid value as given (still uppercase)");
    }

    @Test
    void testCorrectAnswerNull() {
        Question question = new Question(
                "History",
                "Who was the first President of the USA?",
                "A) George Washington",
                "B) Abraham Lincoln",
                "C) Thomas Jefferson",
                "D) John Adams",
                null
        );
        assertNull(question.getCorrectAnswer(), "Null correctAnswer should remain null");
    }
}
