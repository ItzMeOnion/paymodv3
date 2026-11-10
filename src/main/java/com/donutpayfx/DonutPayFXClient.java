
package com.donutpayfx;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class DonutPayFXClient implements ClientModInitializer {

    public static final PaymentStore STORE = new PaymentStore();

    public static final PayEffectManager EFFECTS =
        new PayEffectManager();

    private static KeyBinding menuKey;

    @Override
    public void onInitializeClient() {

        // Menu key: RIGHT SHIFT
        menuKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key.donutpayfx.menu",
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC
            )
        );

        // Detect incoming payment messages
        ClientReceiveMessageEvents.GAME.register(
            (message, overlay) -> {

                PaymentParser.parse(message.getString())
                    .ifPresent(payment -> {

                        STORE.add(payment);

                        EFFECTS.trigger(payment);

                    });
            }
        );

        // Client tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (menuKey.wasPressed()) {

                client.setScreen(new PayFXScreen());

            }

            EFFECTS.tick();

        });
    }
}
