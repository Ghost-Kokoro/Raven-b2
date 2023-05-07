//Deobfuscated by Ghost with mappings "C:\Users\Administrator\Desktop\Minecraft\JByteMod\DecompSpace\Minecraft\mappings\methods.csv"!

//Deobfuscated by Ghost with mappings "C:\Users\Administrator\Desktop\Minecraft\JByteMod\DecompSpace\Minecraft\mappings\fields.csv"!

//Decompiled by Procyon!

package net.bplaced.azoq.module;

import net.bplaced.azoq.module.modules.combat.*;
import net.bplaced.azoq.module.modules.movement.*;
import net.bplaced.azoq.module.modules.render.*;
import net.bplaced.azoq.module.modules.player.*;
import net.bplaced.azoq.module.modules.bedwars.*;
import net.bplaced.azoq.module.modules.settings.*;
import net.bplaced.azoq.config.configs.*;
import java.util.*;
import net.bplaced.azoq.*;
import net.minecraft.client.*;

public class ModuleManager
{
    private List<Module> modules;
    
    public ModuleManager() {
        (this.modules = new ArrayList<Module>()).add(new ModuleAntiBots());
        this.modules.add(new ModuleAutoClicker());
        this.modules.add(new ModuleHitBox());
        this.modules.add(new ModuleRange());
        this.modules.add(new ModuleSmoothAimbot());
        this.modules.add(new ModuleTeams());
        this.modules.add(new ModuleTriggerBot());
        this.modules.add(new ModuleInvMove());
        this.modules.add(new ModuleKeepSprint());
        this.modules.add(new ModuleNoCobweb());
        this.modules.add(new ModuleParkour());
        this.modules.add(new ModuleSprint());
        this.modules.add(new ModuleVelocity());
        this.modules.add(new ModuleBlockOverlay());
        this.modules.add(new ModuleChestEsp());
        this.modules.add(new ModuleClickGui());
        this.modules.add(new ModuleFullBright());
        this.modules.add(new ModuleHud());
        this.modules.add(new ModuleNameTags());
        this.modules.add(new ModuleNoScoreBoard());
        this.modules.add(new ModuleNotifications());
        this.modules.add(new ModulePlayerEsp());
        this.modules.add(new ModuleXRay());
        this.modules.add(new ModuleAutoArmor());
        this.modules.add(new ModuleAutoSoupPot());
        this.modules.add(new ModuleChestStealer());
        this.modules.add(new ModuleFastRespawn());
        this.modules.add(new ModuleMiddleClickFriend());
        this.modules.add(new ModuleNoFriends());
        this.modules.add(new ModuleRefill());
        this.modules.add(new ModuleAutoMlg());
        this.modules.add(new ModuleEagle());
        this.modules.add(new ModuleFastPlace());
        this.modules.add(new ModuleArrayList());
        this.modules.add(new ModulePanic());
        this.modules.add((Module)new ModuleConfigs());
        this.modules.add((Module)new ModuleRemoveConfig());
        this.modules.add((Module)new ModuleSaveConfig());
    }
    
    public Module getModule(final Class module) {
        for (final Module m : this.modules) {
            if (m.getClass() == module) {
                return m;
            }
        }
        return null;
    }
    
    public List<Module> getModules() {
        return this.modules;
    }
    
    public List<Module> getModulesSorted() {
        final List<Module> modulesSorted = new ArrayList<Module>();
        modulesSorted.addAll(this.modules);
        modulesSorted.sort(new Comparator<Module>() {
            @Override
            public int compare(final Module m1, final Module m2) {
                if (Client.INSTANCE.getSettingsManager().getSettingByName("ArrayList:Info").getValBoolean()) {
                    return Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.getDisplayName()) - Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.getDisplayName());
                }
                return Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.getModuleName()) - Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.getModuleName());
            }
        });
        return modulesSorted;
    }
    
    public void setup() {
        for (final Module m : this.modules) {
            m.setup();
        }
    }
}
