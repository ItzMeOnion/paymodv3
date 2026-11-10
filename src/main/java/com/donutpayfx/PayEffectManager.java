package com.donutpayfx;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public final class PayEffectManager {
    private Payment active;
    private int age;
    private int duration;
    private Tier tier = Tier.NONE;

    enum Tier { NONE, MILLION, FIVE_M, TEN_M, HUNDRED_M, BILLION }

    public void trigger(Payment payment) {
        active = payment;
        age = 0;
        tier = tierFor(payment.amount());
        duration = switch (tier) {
            case MILLION -> 80;
            case FIVE_M -> 110;
            case TEN_M -> 140;
            case HUNDRED_M -> 190;
            case BILLION -> 260;
            default -> 0;
        };
        MinecraftClient c = MinecraftClient.getInstance();
        if (c.player != null) {
            var sound = switch (tier) {
                case MILLION -> SoundEvents.BLOCK_NOTE_BLOCK_PLING.value();
                case FIVE_M -> SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH;
                case TEN_M -> SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST;
                case HUNDRED_M -> SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
                case BILLION -> SoundEvents.ENTITY_ENDER_DRAGON_GROWL;
                default -> SoundEvents.BLOCK_NOTE_BLOCK_HAT.value();
            };
            c.player.playSound(sound, SoundCategory.MASTER, 0.8f, tier == Tier.BILLION ? 1.15f : 1.0f);
        }
    }

    public void preview(Tier previewTier) {
        trigger(new Payment("PreviewPlayer", amountFor(previewTier), System.currentTimeMillis()));
    }

    public void tick() { if (age < duration) age++; }
    public boolean active() { return active != null && age < duration; }
    public Payment payment() { return active; }
    public int age() { return age; }
    public int duration() { return duration; }
    public Tier tier() { return tier; }

    public static Tier tierFor(long amount) {
        if (amount >= 1_000_000_000L) return Tier.BILLION;
        if (amount >= 100_000_000L) return Tier.HUNDRED_M;
        if (amount >= 10_000_000L) return Tier.TEN_M;
        if (amount >= 5_000_000L) return Tier.FIVE_M;
        if (amount >= 1_000_000L) return Tier.MILLION;
        return Tier.NONE;
    }

    private static long amountFor(Tier t) {
        return switch (t) {
            case MILLION -> 1_000_000L;
            case FIVE_M -> 5_000_000L;
            case TEN_M -> 10_000_000L;
            case HUNDRED_M -> 100_000_000L;
            case BILLION -> 1_000_000_000L;
            default -> 0;
        };
    }
}
