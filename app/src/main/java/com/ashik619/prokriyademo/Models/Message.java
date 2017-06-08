package com.ashik619.prokriyademo.Models;

/**
 * Created by ashik619 on 07-06-2017.
 */
public class Message {
    public String text;
    public String timestamp;
    public Message(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
