package com.samibey.cheat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class SamiMod implements ModInitializer {

    private static boolean killAura = false;
    private static boolean fly = false;
    private static boolean noFall = true;
    private static boolean esp = true;

    private static KeyBinding keyKillAura;
    private static KeyBinding keyFly;

    @Override
    public void onInitialize() {
        keyKillAura = KeyBindingHelper.registerKeyBinding(new KeyBinding("KillAura Ac/Kapat", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "SamiMod"));
        keyFly = KeyBindingHelper.registerKeyBinding(new KeyBinding("Ucus Ac/Kapat", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "SamiMod"));
        keyEsp = KeyBindingHelper.registerKeyBinding(new KeyBinding("Esp Ac/Kapat", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "SamiMod"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (keyKillAura.wasPressed()) {
                killAura = !killAura;
                client.player.sendMessage(new LiteralText("§bKillAura: " + (killAura ? "§aACIK" : "§cKAPALI")), true);
            }
            while (keyFly.wasPressed()) {
                fly = !fly;
                client.player.sendMessage(new LiteralText("§bUcus: " + (fly ? "§aACIK" : "§cKAPALI")), true);
            }
            while (keyFly.wasPressed()) {
                esp = !esp;
                client.player.sendMessage(new LiteralText("§bEsp: " + (esp ? "§aACIK" : "§cKAPALI")), true);
            }
            

            if (killAura) {
                for (Entity entity : client.world.getEntities()) {
                    if (entity instanceof HostileEntity && client.player.distanceTo(entity) < 4.5f) {
                        client.interactionManager.attackEntity(client.player, entity);
                        client.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }

            client.player.getAbilities().allowFlying = fly;
            if (fly) client.player.getAbilities().flying = true;

            if (noFall && client.player.fallDistance > 2.0f) {
                client.player.getAbilities().flying = true;
                client.player.fallDistance = 0;
            }

            for (Entity entity : client.world.getEntities()) {
                if (entity instanceof HostileEntity) {
                    entity.setGlowing(esp);
                }
            }
        });
    }
}