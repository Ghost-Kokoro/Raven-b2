package net.bplaced.azoq.gui.click.settings;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.gui.click.settings.Setting;
import net.bplaced.azoq.module.Module;

public class SettingsManager {
    private ArrayList<Setting> settings;
    
    public SettingsManager() {
        this.settings = new ArrayList<Setting>();
    }
    
    public void rSetting(final Setting in) {
        this.settings.add(in);
    }
    
    public ArrayList<Setting> getSettings() {
        return this.settings;
    }
    
    public ArrayList<Setting> getSettingsByMod(final Module mod) {
        final ArrayList<Setting> out = new ArrayList<Setting>();
        for (final Setting s : this.getSettings()) {
            if (s.getParentMod().equals(mod)) {
                out.add(s);
            }
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    public Setting getSettingByName(final String name) {
        for (final Setting set : this.getSettings()) {
            if (set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        final PrintStream err = System.err;
        final StringBuilder append = new StringBuilder().append("[");
        err.println(append.append("\u00a72R\u00a7aa\u00a7bv\u00a73e\u00a7ln ").append("] Error Setting NOT found: '").append(name).append("'!").toString());
        return null;
    }
}
