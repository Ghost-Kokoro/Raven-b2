package net.bplaced.azoq.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.modules.render.ModuleNotifications;
import net.bplaced.azoq.utils.ChatUtils;

public class ConfigManager {
	
    private String configFolder;
    private List<String> configs;
    
    public ConfigManager() {
        final StringBuilder sb = new StringBuilder();
        final Client instance = Client.INSTANCE;
        this.configFolder = sb.append(Client.PATH).append("/").append("configs").toString();
        this.configs = new ArrayList<String>();
        this.start();
    }
    
    public List<String> getConfigs() {
        return this.configs;
    }
    
    private void start() {
        final File f = new File(this.configFolder);
        if (!f.isDirectory()) {
            f.mkdir();
        }
        if (f.listFiles() != null) {
            for (final File f2 : f.listFiles()) {
                this.configs.add(f2.getName().replace(".txt", ""));
            }
        }
    }
    
    public void createConfig(final String configName) {
        try {
            this.configs.add(configName);
            final String fileName = this.configFolder + "/" + configName + ".txt";
            final File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
                final BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
                for (final Setting s : Client.INSTANCE.getSettingsManager().getSettings()) {
                    if (s.isCombo()) {
                        bw.write(s.getName() + ":" + s.getValString());
                        bw.newLine();
                    } else if (s.isCheck()) {
                        bw.write(s.getName() + ":" + s.getValBoolean());
                        bw.newLine();
                    } else {
                        if (!s.isSlider()) {
                            continue;
                        }
                        bw.write(s.getName() + ":" + s.getValDouble());
                        bw.newLine();
                    }
                }
                bw.flush();
                bw.close();
                if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Config").getValBoolean()) {
                    ChatUtils.sendChatMessage("Config was saved as '" + configName + "'");
                }
            } else if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Config").getValBoolean()) {
                ChatUtils.sendChatMessage("Config existiert bereits !");
            }
        } catch (Exception ex) {}
    }
    
    public void removeConfig(final String configName) {
        try {
            final File f = new File(this.configFolder);
            for (final File f2 : f.listFiles()) {
                if (f2.getName().equalsIgnoreCase(configName + ".txt")) {
                    f2.delete();
                    if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Config").getValBoolean()) {
                        ChatUtils.sendChatMessage("Config wurde gel\u00f6scht !");
                    }
                    return;
                }
            }
            if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled() && Client.INSTANCE.getSettingsManager().getSettingByName("ModuleNotifications:Config").getValBoolean()) {
                ChatUtils.sendChatMessage("Config existiert nicht !");
            }
        } catch (Exception ex) {}
    }
    
    public void loadConfig(final String configName) {
        try {
            final String fileName = this.configFolder + "/" + configName + ".txt";
            final BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                final String[] args = line.split(":");
                for (final Setting s : Client.INSTANCE.getSettingsManager().getSettings()) {
                    if (s.getName().equalsIgnoreCase(args[0] + ":" + args[1])) {
                        if (s.isCombo()) {
                            s.setValString(args[2]);
                        } else if (s.isCheck()) {
                            s.setValBoolean(Boolean.valueOf(args[2]));
                        } else {
                            if (!s.isSlider()) {
                                continue;
                            }
                            s.setValDouble(Double.valueOf(args[2]));
                        }
                    }
                }
            }
            br.close();
            if (Client.INSTANCE.getModuleManager().getModule(ModuleNotifications.class).isEnabled()) {
                ChatUtils.sendChatMessage("Loaded Config '" + configName + "'");
            }
        } catch (Exception ex) {}
    }
}
