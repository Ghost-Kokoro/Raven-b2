package net.bplaced.azoq.event.eventapi.events;

public interface Cancellable {
    boolean isCancelled();
    
    void setCancelled(final boolean p0);
}
