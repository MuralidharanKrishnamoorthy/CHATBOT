package com.muralidharan.chatbot;

public class message {

    public static String sentbyme="me";
    public static String sentbybot="bot";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public message(String message, String sentby) {
        this.message = message;
        this.sentby=sentby;
    }

    String message,sentby;


}
