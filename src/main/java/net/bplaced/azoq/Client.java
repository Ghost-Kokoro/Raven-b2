package net.bplaced.azoq;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import net.bplaced.azoq.config.ConfigManager;
import net.bplaced.azoq.file.FileManager;
import net.bplaced.azoq.friend.FriendManager;
import net.bplaced.azoq.gui.click.ClickGUI;
import net.bplaced.azoq.gui.click.settings.SettingsManager;
import net.bplaced.azoq.module.Module;
import net.bplaced.azoq.module.ModuleManager;
import net.bplaced.azoq.module.modules.render.ModuleClickGui;
import net.bplaced.azoq.utils.LoginUtils;
import net.minecraft.util.Util;

// Code fixed & ready by Ghost
public class Client {
    public static Client INSTANCE;
    public static final String CLIENT_NAME = "Sativa";
    public static final String CLIENT_VERSION = "v10";
    public static final String CLIENT_CODER = "azoq";
    public static final String PATH;
    private ConfigManager configManager;
    private SettingsManager settingsManager;
    private ModuleManager moduleManager;
    private FriendManager friendManager;
    private FileManager fileManager;
    private ClickGUI clickGUI;
    
    public void startGame() {
        this.createFolder();
        this.configManager = new ConfigManager();
        this.settingsManager = new SettingsManager();
        (this.moduleManager = new ModuleManager()).setup();
        this.friendManager = new FriendManager();
        (this.fileManager = new FileManager()).setup();
        this.clickGUI = new ClickGUI();
        this.startShit();
    }
    
    private void createFolder() {
        final File path = new File(Client.PATH);
        if (!path.exists()) {
            path.mkdir();
            try {
                Files.setAttribute(FileSystems.getDefault().getPath(Client.PATH, new String[0]), "dos:hidden", true, new LinkOption[0]);
            }
            catch (Exception ex) {}
        }
    }
    
    private void startShit() {
        if (this.getModuleManager().getModule(ModuleClickGui.class).getKeyBind() == -1) {
            this.getModuleManager().getModule(ModuleClickGui.class).setKeyBind(54);
        }
        for (final Module m : this.getModuleManager().getModules()) {
            if (m.getCategory() == Module.Category.SETTINGS && m.isEnabled()) {
                m.setEnabled(false);
            }
        }
        if (this.isDebug()) {
            LoginUtils.login("zaindadabhoy@hotmail.co.uk", "Something123"); // DO NOT put your account into the mod next time :)
        }
    }
    
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    public SettingsManager getSettingsManager() {
        return this.settingsManager;
    }
    
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public FriendManager getFriendManager() {
        return this.friendManager;
    }
    
    public FileManager getFileManager() {
        return this.fileManager;
    }
    
    public ClickGUI getClickGUI() {
        return this.clickGUI;
    }
    
    public boolean isDebug() {
        try {
            Class.forName("net.bplaced.azoq.utils.DebugUtils");
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLabyModActive() {
        try {
            Class.forName("LabyMod");
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    static {
        Client.INSTANCE = new Client();
        PATH = ((Util.getOSType() == Util.EnumOS.WINDOWS) ? (System.getProperty("user.home") + "/AppData/Local/Microsoft/Windows/" + "scba") : (System.getProperty("user.home") + "/" + "scba"));
    }
}
