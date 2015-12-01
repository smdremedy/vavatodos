package com.soldiersofmobile.todoekspert;

import java.io.Serializable;

public class Todo implements Serializable {

    public String content;
    public boolean done;

    @Override
    public String toString() {
        return "Todo{" +
                "content='" + content + '\'' +
                ", done=" + done +
                '}';
    }
}
