package net.bplaced.azoq.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.bplaced.azoq.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class ReflectionUtils {
    public static void clickMouse() {
        try {
            String s;
            if (Client.INSTANCE.isDebug()) {
                s = "clickMouse";
            }
            else {
                s = "clickMouse";
            }
            final Minecraft mc = Minecraft.getMinecraft();
            final Class c = mc.getClass();
            final Method m = c.getDeclaredMethod(s, (Class[])new Class[0]);
            m.setAccessible(true);
            m.invoke(mc, new Object[0]);
        }
        catch (Exception ex) {}
    }
    
    public static void rightClickMouse() {
        try {
            String s;
            if (Client.INSTANCE.isDebug()) {
                s = "rightClickMouse";
            }
            else {
                s = "rightClickMouse";
            }
            final Minecraft mc = Minecraft.getMinecraft();
            final Class c = mc.getClass();
            final Method m = c.getDeclaredMethod(s, (Class[])new Class[0]);
            m.setAccessible(true);
            m.invoke(mc, new Object[0]);
        }
        catch (Exception ex) {}
    }
    
    public static void setLeftClickCounter(final int i) {
        try {
            String s;
            if (Client.INSTANCE.isDebug()) {
                s = "leftClickCounter";
            }
            else {
                s = "leftClickCounter";
            }
            final Minecraft mc = Minecraft.getMinecraft();
            final Class c = mc.getClass();
            final Field f = c.getDeclaredField(s);
            f.setAccessible(true);
            f.set(mc, i);
        }
        catch (Exception ex) {}
    }
    
    public static void setRightClickDelayTimer(final int i) {
        try {
            String s;
            if (Client.INSTANCE.isDebug()) {
                s = "rightClickDelayTimer";
            }
            else {
                s = "rightClickDelayTimer";
            }
            final Minecraft mc = Minecraft.getMinecraft();
            final Class c = mc.getClass();
            final Field f = c.getDeclaredField(s);
            f.setAccessible(true);
            f.set(mc, i);
        }
        catch (Exception ex) {}
    }
    
    public static void setSession(final Session session) {
        try {
            final String s = "session";
            final Minecraft mc = Minecraft.getMinecraft();
            final Class c = mc.getClass();
            final Field f = c.getDeclaredField(s);
            f.setAccessible(true);
            f.set(mc, session);
        }
        catch (Exception ex) {}
    }
}
