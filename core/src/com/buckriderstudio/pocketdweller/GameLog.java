package com.buckriderstudio.pocketdweller;

import com.badlogic.gdx.graphics.Color;
import com.buckriderstudio.pocketdweller.systems.TimeSystem;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class GameLog {

    private Set<Message> messageLog = new HashSet();

    public void addMessage(Message message){
        messageLog.add(message);
    }

    public static class Message{
        public Color color;
        public String message;
        public ZonedDateTime dateTime;

        public Message() {
        }

        public Message(Color color, String message) {
            dateTime = TimeSystem.CURRENT_TIME;
            this.color = color;
            this.message = message;
        }

        public Message(Color color, String message, ZonedDateTime dateTime) {
            this.color = color;
            this.message = message;
            this.dateTime = dateTime;
        }
    }
}
