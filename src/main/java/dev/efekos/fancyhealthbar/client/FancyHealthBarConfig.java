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

package dev.efekos.fancyhealthbar.client;

import dev.efekos.fancyhealthbar.FancyHealthBar;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = FancyHealthBar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FancyHealthBarConfig {

    public static final KeyMapping CONFIG_KEY = new KeyMapping("key.fancyhealthbar.config", GLFW.GLFW_KEY_L, "key.category.fancyhealthbar");
    
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private static final ForgeConfigSpec.IntValue MINIMUM_PIXEL_SIZE = BUILDER
            .comment("Determines minimum size of a pixel object")
            .defineInRange("minimumPixelSize", 1, 1, 8);
            
    private static final ForgeConfigSpec.IntValue MAXIMUM_PIXEL_SIZE = BUILDER
            .comment("Determines maximum size of a pixel object")
            .defineInRange("maximumPixelSize", 2, 1, 8);
            
    private static final ForgeConfigSpec.DoubleValue VELOCITY_MULTIPLIER = BUILDER
            .comment("Controls how fast particles can come out")
            .defineInRange("velocityMultiplier", 1.0, 0.0, 3.0);
            
    private static final ForgeConfigSpec.DoubleValue SLIPPERINESS = BUILDER
            .comment("Controls how fast particles will go in horizontal direction")
            .defineInRange("slipperiness", 1.0, 0.0, 5.0);
            
    private static final ForgeConfigSpec.IntValue GRAVITY = BUILDER
            .comment("Controls how fast particles will fall")
            .defineInRange("gravity", 1, -12, 12);
            
    private static final ForgeConfigSpec.IntValue COUNT_MULTIPLIER = BUILDER
            .comment("Controls how many particles will pop out")
            .defineInRange("countMultiplier", 2, 2, 16);
            
    private static final ForgeConfigSpec.IntValue MAXIMUM_OBJECTS = BUILDER
            .comment("Maximum amount of objects that can exist at the same time")
            .defineInRange("maximumObjects", 1024, 64, 4096);
            
    private static final ForgeConfigSpec.IntValue MAXIMUM_TICKS = BUILDER
            .comment("Controls maximum time that a particle can stay in the screen")
            .defineInRange("maximumTicks", 100, 20, 240);
            
    private static final ForgeConfigSpec.IntValue UPDATE_INTERVAL = BUILDER
            .comment("Controls the time between two particle updates")
            .defineInRange("updateInterval", 5, 2, 30);
    
    public static final ForgeConfigSpec SPEC = BUILDER.build();
    
    public static void load() {}
    
    public static float getSlipperiness() {
        return SLIPPERINESS.get().floatValue();
    }

    public static int getGravity() {
        return GRAVITY.get();
    }

    public static double getVelocityMultiplier() {
        return VELOCITY_MULTIPLIER.get();
    }

    public static int getMinimumPixelSize() {
        return MINIMUM_PIXEL_SIZE.get();
    }

    public static int getMaximumPixelSize(){
        return MAXIMUM_PIXEL_SIZE.get();
    }

    public static int getCountMultiplier() {
        return COUNT_MULTIPLIER.get();
    }

    public static int getMaximumObjects() {
        return MAXIMUM_OBJECTS.get();
    }

    public static int getMaximumTicks() {
        return MAXIMUM_TICKS.get();
    }

    public static int getUpdateInterval() {
        return UPDATE_INTERVAL.get();
    }
} 