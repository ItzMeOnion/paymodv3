package com.donutpayfx;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class PayFXScreen extends Screen {
    private int tab = 0;

    public PayFXScreen() { super(Text.literal("Donut Pay FX")); }

    @Override
    protected void init() {
        addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(Text.literal("PREVIEW"), b -> tab = 0)
            .dimensions(width/2 - 150, 25, 145, 20).build());
        addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(Text.literal("TOP PAYERS"), b -> tab = 1)
            .dimensions(width/2 + 5, 25, 145, 20).build());

        String[] labels = {"1M Clean", "5M Fireworks", "10M Blast", "100M JACKPOT", "1B LEGENDARY"};
        PayEffectManager.Tier[] tiers = {
            PayEffectManager.Tier.MILLION, PayEffectManager.Tier.FIVE_M,
            PayEffectManager.Tier.TEN_M, PayEffectManager.Tier.HUNDRED_M,
            PayEffectManager.Tier.BILLION
        };
        for (int i=0;i<labels.length;i++) {
            final int n=i;
            addDrawableChild(net.minecraft.client.gui.widget.ButtonWidget.builder(Text.literal(labels[i]), b ->
                DonutPayFXClient.EFFECTS.preview(tiers[n]))
                .dimensions(width/2 - 120, 70 + i*28, 240, 22).build());
        }
    }

    @Override
    public void render(DrawContext c, int mouseX, int mouseY, float delta) {
        renderBackground(c, mouseX, mouseY, delta);
        c.drawCenteredTextWithShadow(textRenderer, "DONUT PAY FX", width/2, 8, 0xFFFFFF);

        if (tab == 1) {
            c.fill(width/2-150, 60, width/2+150, height-25, 0xCC10131A);
            c.drawCenteredTextWithShadow(textRenderer, "TOP PAYERS • ALL TIME", width/2, 68, 0xFFD84D);
            int y=92, rank=1;
            for (var e : DonutPayFXClient.STORE.leaderboard()) {
                String line = "#" + rank++ + "  " + e.getKey() + "   $" + format(e.getValue());
                c.drawCenteredTextWithShadow(textRenderer, line, width/2, y, 0xEAF2FF);
                y += 18;
                if (y > height-35) break;
            }
            if (rank == 1) c.drawCenteredTextWithShadow(textRenderer, "No payments tracked yet.", width/2, 105, 0xAAAAAA);
        }
        super.render(c, mouseX, mouseY, delta);
    }

    private static String format(long n) {
        if (n >= 1_000_000_000L) return String.format("%.2fB", n/1_000_000_000.0);
        if (n >= 1_000_000L) return String.format("%.2fM", n/1_000_000.0);
        if (n >= 1_000L) return String.format("%.1fK", n/1_000.0);
        return Long.toString(n);
    }
}
