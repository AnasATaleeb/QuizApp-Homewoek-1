package com.example.quizapp.Models;

public class QustionModel {
    private String qustion,optionA,optionB,OptionC,OptionD,correctAnswer;

    public QustionModel(String qustion, String optionB, String optionD, String optionC, String optionA, String correctAnswer) {
        this.qustion = qustion;
        this.optionA = optionA;
        this.optionB = optionB;
        OptionC = optionC;
        OptionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public QustionModel(){

    }

    public String getQustion() {
        return qustion;
    }

    public void setQustion(String qustion) {
        this.qustion = qustion;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
