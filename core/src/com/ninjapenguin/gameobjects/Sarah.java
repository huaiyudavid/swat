package com.ninjapenguin.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.ninjapenguin.helpers.AssetLoader;

import java.util.Random;

/**
 * Created by David on 6/24/16.
 */
public class Sarah extends Scrollable {
    private Random r;

    public enum Type {
        BIG, SMALL, DOUBLE
    }

    private float groundY;

    private Type type;
    private Rectangle topHalf, bottomHalf;

    public Sarah(float x, float groundY, Type type) {
        super(x, groundY + getSpriteHeight(type)/2, getSpriteWidth(type), getSpriteHeight(type));
        Gdx.app.log("Sarah", "Y: " + getHeight());
        this.type = type;
        this.groundY = groundY;
        r = new Random();
        bottomHalf = new Rectangle(x - 3*width/8, position.y - height/2, 3*width/4, 3*height/4);
        topHalf = new Rectangle(x - 3*width/32, position.y + height/4, 3*width/8, height/4);
    }

    public Sarah(float x, float groundY) {
        this(x, groundY, Type.BIG);
        changeType();
    }

    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);

        // Create bounding boxes
        topHalf.setCenter(position.x, position.y + 3*height/8);
        bottomHalf.setCenter(position.x, position.y - height/8);
    }

    @Override
    public void reset(float x) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(x);

        // Change to a Random Type
        changeType();
    }

    public void onRestart(float x) {
        super.onRestart(x);
        reset(x);
    }

    public void changeType() {
        int choice = r.nextInt(3);
        switch(choice) {
            case 0:
                type = Type.BIG;
                break;
            case 1:
                type = Type.SMALL;
                break;
            case 2:
                type = Type.DOUBLE;
                break;
            default:
                type = Type.BIG;
        }

        // Reset new sizes
        position.y = groundY + getSpriteHeight(type)/2;
        this.width = getSpriteWidth(type);
        this.height = getSpriteHeight(type);

        // Reset bounding boxes
        bottomHalf.set(position.x - 3*width/8, position.y - height/2, 3*width/4, 3*height/4);
        topHalf.set(position.x - 3*width/32, position.y + height/4, 3*width/8, height/4);
    }

    public Rectangle getTopHalf() {
        return topHalf;
    }

    public Rectangle getBottomHalf() {
        return bottomHalf;
    }

    public Type getType() {
        return type;
    }

    public boolean collides(Aaron player) {
        if (position.x - width/2 < player.getX() + player.getWidth()) {
            return (Intersector.overlaps(player.getBoundingRect(), topHalf) ||
                    Intersector.overlaps(player.getBoundingRect(), bottomHalf));
        }
        return false;
    }

    private static int getSpriteWidth(Type type) {
        switch(type) {
            case BIG:
                return AssetLoader.sarahBig.getRegionWidth();
            case SMALL:
                return AssetLoader.sarahSmall.getRegionWidth();
            case DOUBLE:
                return AssetLoader.sarahDouble.getRegionWidth();
            default:
                return 0;
        }
    }

    private static int getSpriteHeight(Type type) {
        switch(type) {
            case BIG:
                return AssetLoader.sarahBig.getRegionHeight();
            case SMALL:
                return AssetLoader.sarahSmall.getRegionHeight();
            case DOUBLE:
                return AssetLoader.sarahDouble.getRegionHeight();
            default:
                return 0;
        }
    }
}
