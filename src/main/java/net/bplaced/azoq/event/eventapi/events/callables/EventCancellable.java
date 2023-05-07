package net.bplaced.azoq.event.eventapi.events.callables;

import net.bplaced.azoq.event.eventapi.events.*;

public abstract class EventCancellable implements Event, Cancellable {
    private boolean cancelled;
    
    protected EventCancellable() {
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}
