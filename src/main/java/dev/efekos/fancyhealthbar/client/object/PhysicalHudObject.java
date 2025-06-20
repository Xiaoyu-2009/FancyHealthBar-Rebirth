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

package dev.efekos.fancyhealthbar.client.object;

import dev.efekos.fancyhealthbar.client.utils.HudLocation;

public abstract class PhysicalHudObject implements HudObject {
    private HudLocation location;
    private HudLocation velocity;
    private int lifetime;

    public PhysicalHudObject(HudLocation location, HudLocation velocity) {
        this.location = location;
        this.velocity = velocity;
    }

    public abstract int getGravity();

    public abstract double getSlipperiness();

    @Override
    public void tick() {
        location.add(velocity.getX(), -velocity.getY());

        int velocityX = velocity.getX();
        int velocityY = velocity.getY();

        velocityX = (int) (velocityX * getSlipperiness() * Math.max(0.8d, Math.random() * 1.5d));
        velocityY -= getGravity();

        setVelocity(new HudLocation(velocityX, velocityY));
        lifetime++;
    }

    @Override
    public int getLifetime() {
        return lifetime;
    }

    @Override
    public HudLocation getLocation() {
        return location;
    }

    public void setLocation(HudLocation location) {
        this.location = location;
    }

    public HudLocation getVelocity() {
        return velocity;
    }

    public void setVelocity(HudLocation velocity) {
        this.velocity = velocity;
    }

    public void addVelocity(HudLocation velocity) {
        this.velocity.add(velocity);
    }

    public void addVelocity(int x, int y) {
        this.velocity.add(x, y);
    }
} 