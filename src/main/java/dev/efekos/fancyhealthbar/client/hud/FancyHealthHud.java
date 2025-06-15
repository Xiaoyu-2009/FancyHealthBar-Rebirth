/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

public class FancyHealthHud implements IGuiOverlay {

    public static final VelocityProvider HEART_VELOCITY_PROVIDER = (random -> {
        double multiplier = FancyHealthBarConfig.getVelocityMultiplier();
        return new HudLocation((int) ((random.nextInt(21) - 10) * multiplier), (int) (Math.max(random.nextInt(16) - 5, 0) * multiplier));
    });
    public static List<HudObject> OBJECTS = new ArrayList<>();
    private static int gameTicks = 0;
    private int lastHeartStartX;
    private float lastHealth;
    private int lastHeartStartY;

    private void add(List<HudObject> objects){
        for (HudObject o : objects)
            if(OBJECTS.size()<FancyHealthBarConfig.getMaximumObjects()) OBJECTS.add(o);
    }

    public void onDamage(float oldHeart, float newHeart) {
        if (gameTicks < 40) return;

        double difference = Mth.clamp(oldHeart - newHeart, 0, 20);

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;

        boolean hardcore = client.level.getLevelData().isHardcore();
        boolean poison = player.hasEffect(MobEffects.POISON);
        boolean wither = player.hasEffect(MobEffects.WITHER);
        boolean frozen = player.getTicksFrozen() >= 140;

        HeartGenerator spawner = HeartTypes.get(hardcore, poison, frozen, wither);

        for (int i = 0; i < (int) (difference / 2); i++) {
            int heartsRemaining = (int) (newHeart / 2);
            int rowIndex = heartsRemaining / 10;
            int columnIndex = heartsRemaining % 10;
            
            int heartX = lastHeartStartX + (columnIndex * 8);
            int heartY = lastHeartStartY - (rowIndex * 10);

            for (int j = 0; j < FancyHealthBarConfig.getCountMultiplier(); j++)
                add(spawner.spawnFull(heartX, heartY, HEART_VELOCITY_PROVIDER));
        }

        if (difference % 2 != 0) {
            int heartsRemaining = (int) (newHeart / 2);
            int rowIndex = heartsRemaining / 10;
            int columnIndex = heartsRemaining % 10;
            
            int heartX = lastHeartStartX + (columnIndex * 8);
            int heartY = lastHeartStartY - (rowIndex * 10);

            if (Math.round(newHeart) % 2 == 0)
                for (int i = 0; i < FancyHealthBarConfig.getCountMultiplier(); i++)
                    add(spawner.spawnStartHalf(heartX, heartY));
            else
                for (int i = 0; i < FancyHealthBarConfig.getCountMultiplier(); i++)
                    add(spawner.spawnEndHalf(heartX, heartY));
        }
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft client = Minecraft.getInstance();
        boolean notPaused = !client.isPaused();

        lastHeartStartY = screenHeight - 38;
        lastHeartStartX = (screenWidth / 2) - 90;
        float health = client.player.getHealth();
        if (health < lastHealth) onDamage(lastHealth, health);
        lastHealth = health;

        for (HudObject object : new ArrayList<>(OBJECTS)) {
            if (gameTicks % FancyHealthBarConfig.getUpdateInterval() == 0 && notPaused) object.tick();

            if (object.getLocation().getX() > screenWidth + 16 || object.getLocation().getY() > screenHeight + 16 ||
                    object.getLocation().getX() < -16 || object.getLocation().getY() < -128 || object.getLifetime() >= FancyHealthBarConfig.getMaximumTicks()) {
                OBJECTS.remove(object);
                continue;
            }

            object.draw(guiGraphics);
        }

        gameTicks++;
    }
} 