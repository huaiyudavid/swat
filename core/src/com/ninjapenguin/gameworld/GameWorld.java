package com.ninjapenguin.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.ninjapenguin.gameobjects.Aaron;
import com.ninjapenguin.gameobjects.ScrollHandler;
import com.ninjapenguin.helpers.AssetLoader;

/**
 * Created by David on 6/24/16.
 */
public class GameWorld {
    public static float SCORE_MULTIPLIER = 1;

    private Aaron player;
    private ScrollHandler scroller;
    private Rectangle ground;
    private float score = 0;
    //private float runTime = 0;

    private int width, height;

    private GameState currentState;

    public enum GameState {
        READY, RUNNING, GAMEOVER
    }

    public GameWorld(int width, int height) {
        this.width = width;
        this.height = height;
        //currentState = GameState.RUNNING; //for testing ONLY
        currentState = GameState.READY;
        // The only exception to general rule of (x, y) being at center
        // ground has (x, y) at bottom left corner
        ground = new Rectangle(0, 0, width, height - 666);
        player = new Aaron(188, ground.getHeight() + AssetLoader.run1.getRegionHeight()/2, AssetLoader.run1.getRegionWidth(), AssetLoader.run1.getRegionHeight());
        scroller = new ScrollHandler(this, ground.getHeight());
        AssetLoader.music.play();
    }

    public void update(float delta) {
        //runTime += delta;

        switch(currentState) {
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    public void updateReady(float delta) {
        //nothing yet, perhaps some buttons
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        player.update(delta);
        //Gdx.app.log("GameWorld", "updating");
        scroller.update(delta);

        if (scroller.collides(player) && player.isAlive()){
            AssetLoader.music.stop();
            AssetLoader.splat.play();
            scroller.stop();
            player.die();
            currentState = GameState.GAMEOVER;
            if ((int)score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore((int) score);
            }
        }

        if (Intersector.overlaps(player.getBoundingRect(), ground)) {
            player.land((int)ground.getHeight());
        }

        //do score shenanigans
        score += delta*SCORE_MULTIPLIER;
    }

    public Aaron getPlayer() {
        return player;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return (int)score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void restart() {
        System.gc();
        currentState = GameState.RUNNING;
        score = 0;
        player.onRestart((int)ground.getHeight() + AssetLoader.run1.getRegionHeight()/2);
        scroller.onRestart();
        AssetLoader.music.play();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }
}
