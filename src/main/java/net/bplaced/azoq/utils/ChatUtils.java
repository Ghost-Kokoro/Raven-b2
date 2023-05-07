package net.bplaced.azoq.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
    private static Minecraft mc;
    
    public static void sendChatMessage(final String message) {
        ChatUtils.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
    
    static {
        ChatUtils.mc = Minecraft.getMinecraft();
    }
}
