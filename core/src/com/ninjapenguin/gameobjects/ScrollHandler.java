package com.ninjapenguin.gameobjects;

import com.ninjapenguin.gameworld.GameWorld;
import com.ninjapenguin.helpers.AssetLoader;

import java.util.Random;

/**
 * Created by David on 6/24/16.
 */
public class ScrollHandler {
    private Random r;
    private Background background1, background2;
    private Sarah sarah1, sarah2, sarah3;
    public static final int MIN_DISTANCE = 300, MAX_DISTANCE = 1000;

    private GameWorld world;

    public ScrollHandler(GameWorld world, float groundY) {
        this.world = world;
        r = new Random();
        background1 = new Background(AssetLoader.bg1.getRegionWidth()/2, AssetLoader.bg1.getRegionHeight()/2);
        background2 = new Background(background1.getTailX() + AssetLoader.bg2.getRegionWidth()/2, AssetLoader.bg2.getRegionHeight()/2);
        sarah1 = new Sarah(world.getWidth() + AssetLoader.sarahDouble.getRegionWidth()/2, groundY);
        int gap = MIN_DISTANCE + r.nextInt(MAX_DISTANCE - MIN_DISTANCE);
        sarah2 = new Sarah(sarah1.getTailX() + gap + AssetLoader.sarahDouble.getRegionWidth()/2, groundY);
        gap = MIN_DISTANCE + r.nextInt(MAX_DISTANCE - MIN_DISTANCE);
        sarah3 = new Sarah(sarah2.getTailX() + gap + AssetLoader.sarahDouble.getRegionWidth()/2, groundY);
    }

    public void update(float delta) {
        background1.update(delta);
        background2.update(delta);
        sarah1.update(delta);
        sarah2.update(delta);
        sarah3.update(delta);

        // Check if anything is off-screen and reset it
        if (background1.isScrolledLeft()) {
            background1.reset(background2.getTailX() + background1.getWidth()/2);
        } else if (background2.isScrolledLeft()) {
            background2.reset(background1.getTailX() + background2.getWidth()/2);
        }

        if (sarah1.isScrolledLeft() || sarah2.isScrolledLeft() || sarah3.isScrolledLeft()) {
            int gap = MIN_DISTANCE + r.nextInt(MAX_DISTANCE - MIN_DISTANCE);
            if (sarah1.isScrolledLeft()) {
                sarah1.reset(sarah3.getTailX() + gap + sarah1.getWidth()/2);
            } else if (sarah2.isScrolledLeft()) {
                sarah2.reset(sarah1.getTailX() + gap + sarah2.getWidth()/2);
            } else {
                sarah3.reset(sarah2.getTailX() + gap + sarah3.getWidth()/2);
            }
        }
    }

    public void stop() {
        background1.stop();
        background2.stop();
        sarah1.stop();
        sarah2.stop();
        sarah3.stop();
    }

    public boolean collides(Aaron player) {
        return sarah1.collides(player) || sarah2.collides(player) || sarah3.collides(player);
    }

    public void onRestart() {
        background1.onRestart(background1.getWidth()/2);
        background2.onRestart(background1.getTailX() + background2.getWidth()/2);
        sarah1.onRestart(world.getWidth() + AssetLoader.sarahDouble.getRegionWidth()/2);
        int gap = MIN_DISTANCE + r.nextInt(MAX_DISTANCE - MIN_DISTANCE);
        sarah2.onRestart(sarah1.getTailX() + gap + AssetLoader.sarahDouble.getRegionWidth()/2);
        gap = MIN_DISTANCE + r.nextInt(MAX_DISTANCE - MIN_DISTANCE);
        sarah3.onRestart(sarah2.getTailX() + gap + AssetLoader.sarahDouble.getRegionWidth()/2);
    }

    public Background getBackground1() {
        return background1;
    }

    public Background getBackground2() {
        return background2;
    }

    public Sarah[] getSarahs() {
        return new Sarah[]{sarah1, sarah2, sarah3};
    }
}
