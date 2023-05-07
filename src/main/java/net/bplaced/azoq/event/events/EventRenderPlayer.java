package net.bplaced.azoq.event.events;

import net.bplaced.azoq.event.eventapi.events.Event;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderPlayer implements Event {
    private EntityLivingBase entity;
    private double x;
    private double y;
    private double z;
    
    public EventRenderPlayer(final EntityLivingBase entity, final double x, final double y, final double z) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
}
