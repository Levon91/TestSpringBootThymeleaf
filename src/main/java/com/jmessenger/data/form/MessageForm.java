package com.jmessenger.data.form;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class MessageForm {

    @NotEmpty(message = "Message can not be blank.")
    private String message;

    @NotEmpty(message = "Please choose at least one user.")
    private List<String> channels;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }
}
