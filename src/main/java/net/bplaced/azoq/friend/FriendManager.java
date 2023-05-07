package net.bplaced.azoq.friend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.file.files.FileFriends;
import net.bplaced.azoq.module.modules.player.ModuleNoFriends;
import net.bplaced.azoq.module.modules.render.ModuleNotifications;
import net.bplaced.azoq.utils.ChatUtils;

public class FriendManager {
    private List<String> friends;
    
    public FriendManager() {
        this.friends = new ArrayList<String>();
    }
    
    public void addFriend(final String friend) {
        this.friends.add(friend);
        Client.INSTANCE.getFileManager().getFile((Class)FileFriends.class).save();
        if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Friends").getValBoolean()) {
            ChatUtils.sendChatMessage("Added '" + friend + "' as Friend");
        }
    }
    
    public void removeFriend(final String friend) {
        for (final String s : this.friends) {
            if (s.equalsIgnoreCase(friend)) {
                this.friends.remove(s);
                Client.INSTANCE.getFileManager().getFile((Class)FileFriends.class).save();
                if (!Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() || !Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Friends").getValBoolean()) {
                    continue;
                }
                ChatUtils.sendChatMessage("Removed '" + s + "' as Friend");
            }
        }
    }
    
    public boolean isFriend(final String player) {
        if (Client.INSTANCE.getModuleManager().getModule(ModuleNoFriends.class).isEnabled()) {
            return false;
        }
        for (final String s : this.friends) {
            if (s.equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }
    
    public List<String> getFriends() {
        return this.friends;
    }
}
