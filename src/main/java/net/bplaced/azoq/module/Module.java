package net.bplaced.azoq.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.event.eventapi.EventManager;
import net.bplaced.azoq.file.files.FileModules;
import net.bplaced.azoq.module.modules.settings.ModulePanic;
import net.minecraft.client.Minecraft;

public abstract class Module {
    public static Minecraft mc;
    public final String DISPLAY_START = " \u00a77[";
    public final String DISPLAY_END = "]\u00a7r";
    private String moduleName;
    private String displayName;
    private Category category;
    private int keyBind;
    private boolean visible;
    private boolean enabled;
    
    public Module() {
        this.moduleName = this.getClass().getAnnotation(Information.class).moduleName();
        this.displayName = this.getClass().getAnnotation(Information.class).displayName();
        this.category = this.getClass().getAnnotation(Information.class).category();
        this.keyBind = this.getClass().getAnnotation(Information.class).keyBind();
        this.visible = this.getClass().getAnnotation(Information.class).visible();
        this.enabled = this.getClass().getAnnotation(Information.class).enabled();
    }
    
    public String getModuleName() {
        return this.moduleName;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getKeyBind() {
        return this.keyBind;
    }
    
    public void setKeyBind(final int keyBind) {
        this.keyBind = keyBind;
        if (!Client.INSTANCE.getModuleManager().getModule(ModulePanic.class).isEnabled()) {
            Client.INSTANCE.getFileManager().getFile((Class)FileModules.class).save();
        }
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            this.onEnable();
            EventManager.register((Object)this);
        }
        else {
            this.onDisable();
            EventManager.unregister((Object)this);
        }
        if (!Client.INSTANCE.getModuleManager().getModule(ModulePanic.class).isEnabled()) {
            Client.INSTANCE.getFileManager().getFile((Class)FileModules.class).save();
        }
    }
    
    public void toggleModule() {
        this.setEnabled(!this.enabled);
    }
    
    public void setup() {
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
    
    public enum Category
    {
        COMBAT, 
        MOVEMENT, 
        RENDER, 
        PLAYER, 
        BEDWARS, 
        SETTINGS;
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Information {
        String moduleName();
        
        String displayName();
        
        Category category();
        
        int keyBind() default -1;
        
        boolean visible() default true;
        
        boolean enabled() default false;
    }
}
