package net.bplaced.azoq.event.events;

import net.bplaced.azoq.event.eventapi.events.Event;

public class EventMouse implements Event {
    private Button button;
    
    public EventMouse(final Button button) {
        this.button = button;
    }
    
    public Button getButton() {
        return this.button;
    }
    
    public enum Button {
        Left, 
        Right, 
        Middle;
    }
}
