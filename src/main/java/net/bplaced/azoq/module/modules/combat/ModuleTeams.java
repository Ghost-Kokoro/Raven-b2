package net.bplaced.azoq.module.modules.combat;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.module.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

@Module.Information(moduleName = "Teams", displayName = "Teams", category = Module.Category.COMBAT)
public class ModuleTeams extends Module {
	
    public static boolean isTeam(final Entity e) {
        return Client.INSTANCE.getModuleManager().getModule((Class)ModuleTeams.class).isEnabled() && getTeamColor(e) == getTeamColor((Entity)ModuleTeams.mc.thePlayer);
    }
    
    private static int getTeamColor(final Entity e) {
        int color = 16777215;
        final ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)((EntityPlayer)e).getTeam();
        if (scoreplayerteam != null) {
            final String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
            if (s.length() >= 2) {
                color = ModuleTeams.mc.fontRendererObj.getColorCode(s.charAt(1));
            }
        }
        return color;
    }
}
