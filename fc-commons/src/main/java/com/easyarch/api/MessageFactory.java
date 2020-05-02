package com.easyarch.api;

import com.easyarch.model.Message;

public interface MessageFactory {

    public Message handle(Message msg);
}
