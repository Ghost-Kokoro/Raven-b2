package net.bplaced.azoq.utils;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.util.Session;

// Since the Microsoft has taken the login access, this utility was no longer useful.
public class LoginUtils {
    public static void login(final String username, final String password) {
        try {
            final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
            authentication.setUsername(username);
            authentication.setPassword(password);
            authentication.logIn();
            final GameProfile profile = authentication.getSelectedProfile();
            final Session session = new Session(profile.getName(), profile.getId().toString(), authentication.getAuthenticatedToken(), "MOJANG");
            ReflectionUtils.setSession(session);
        }
        catch (Exception ex) {}
    }
}
