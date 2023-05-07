package net.bplaced.azoq.module.modules.player;

import java.util.Iterator;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventTarget;
import net.bplaced.azoq.event.events.EventMouse;
import net.bplaced.azoq.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@Module.Information(moduleName = "MiddleClickFriend", displayName = "MiddleClickFriend", category = Module.Category.PLAYER)
public class ModuleMiddleClickFriend extends Module {
    @EventTarget
    public void onMiddleClick(final EventMouse event) {
        if (event.getButton() == EventMouse.Button.Middle && ModuleMiddleClickFriend.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY) {
            final Entity e = ModuleMiddleClickFriend.mc.objectMouseOver.entityHit;
            final String name = e.getName();
            if (Client.INSTANCE.getFriendManager().getFriends().size() == 0) {
                Client.INSTANCE.getFriendManager().addFriend(name);
            }
            else {
                for (final String s : Client.INSTANCE.getFriendManager().getFriends()) {
                    if (s.equalsIgnoreCase(name)) {
                        Client.INSTANCE.getFriendManager().removeFriend(name);
                        return;
                    }
                }
                Client.INSTANCE.getFriendManager().addFriend(name);
            }
        }
    }
}
