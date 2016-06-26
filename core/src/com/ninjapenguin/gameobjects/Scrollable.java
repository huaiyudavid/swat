package com.ninjapenguin.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by David on 6/24/16.
 */
public class Scrollable {
    public static final float SCROLL_SPEED = -400;
    public static final float MAX_SCROLL_SPEED = -1000;
    public static final float SCROLL_FACTOR = 4;

    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledLeft;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
    }

    public Scrollable(float x, float y, int width, int height) {
        this(x, y, width, height, SCROLL_SPEED);
    }

    public void update(float delta) {
        position.x += velocity.x*delta;
        position.y += velocity.y*delta;

        // If the Scrollable object is no longer visible:
        if (position.x + width/2 < 0) {
            isScrolledLeft = true;
        }

        velocity.x -= delta*SCROLL_FACTOR;
        Gdx.app.log("Scrollable", "Scroll Speed: " + velocity.x);

        if (velocity.x < MAX_SCROLL_SPEED) {
            velocity.x = MAX_SCROLL_SPEED;
        }
    }

    // Reset: Should Override in subclass for more specific behavior.
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public float getTailX() {
        return position.x + width/2;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void onRestart(float x) {
        velocity.x = SCROLL_SPEED;
    }
}

