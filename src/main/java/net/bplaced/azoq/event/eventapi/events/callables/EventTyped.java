package net.bplaced.azoq.event.eventapi.events.callables;

import net.bplaced.azoq.event.eventapi.events.*;

public abstract class EventTyped implements Event, Typed {
    private final byte type;
    
    protected EventTyped(final byte eventType) {
        this.type = eventType;
    }
    
    @Override
    public byte getType() {
        return this.type;
    }
}
