/*
Homework 3
Trivia Application
Bhanu Teja Sriram
Tejaswini Naredla
*/

package com.example.cherr.triviaapplication;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by cherr on 16-02-2018.
 */

public class Question implements Serializable {
    private int question_id;
    private String question;
    private String image_url;
    private ArrayList<String> options;
    private ArrayList<Question> questions;

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    private int answer_index;
    private int selected_index;

    public Question() {
    }

    public int getQuestion_id() {

        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getAnswer_index() {
        return answer_index;
    }

    public void setAnswer_index(int answer_index) {
        this.answer_index = answer_index;
    }

    public int getSelected_index() {
        return selected_index;
    }

    public void setSelected_index(int selected_index) {
        this.selected_index = selected_index;
    }

    public Question(int question_id, String question, String image_url, ArrayList<String> options, int answer_index) {
        this.question_id = question_id;
        this.question = question;
        this.image_url = image_url;
        this.options = options;
        this.answer_index = answer_index;

    }
}
