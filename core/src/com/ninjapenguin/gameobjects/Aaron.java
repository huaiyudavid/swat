package com.ninjapenguin.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ninjapenguin.helpers.AssetLoader;

/**
 * Created by David on 6/24/16.
 */
public class Aaron {
    private static final float GRAVITY = -2700f;
    private static final float JUMP = 1500f;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private int width;
    private int height;

    private boolean isAlive;
    private boolean isJumping;

    private Rectangle boundingRect;

    public Aaron(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        boundingRect = new Rectangle(x - 7*width/16, y - height/2, 5*width/8, height);
        isAlive = true;
        isJumping = false;
    }

    public void update(float delta) {
        velocity.x += acceleration.x*delta;
        velocity.y += acceleration.y*delta;

        position.x += velocity.x*delta;
        position.y += velocity.y*delta;

        boundingRect.setCenter(position.x + width/8, position.y);
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean isAlive() {
        return isAlive;
    }

    private void jump() {
        if (isAlive && !isJumping) {
            AssetLoader.jump.play();
            velocity.y = JUMP;
            acceleration.y = GRAVITY;
            isJumping = true;
        }
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
        decelerate();
    }

    public void land(int groundY) {
        velocity.y = 0;
        decelerate();
        position.y = groundY + height/2;
        isJumping = false;
    }

    public void decelerate() {
        acceleration.y = 0;
    }

    public void onRestart(int y) {
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = GRAVITY;
        isAlive = true;
    }

    public void onClick() {
        jump();
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBoundingRect() {
        return boundingRect;
    }
}
