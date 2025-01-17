package com.ninjapenguin.helpers;

import com.badlogic.gdx.InputProcessor;
import com.ninjapenguin.gameobjects.Aaron;
import com.ninjapenguin.gameworld.GameWorld;

/**
 * Created by David on 6/24/16.
 */
public class InputHandler implements InputProcessor {
    private GameWorld world;
    private Aaron player;

    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.player = world.getPlayer();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Gdx.app.log("InputHandler", "Touch Down");

        if (world.isReady()) {
            world.start();
        } else if (world.isRunning()) {
            player.onClick();
        } else {    // State = Game Over
            world.restart();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }
}
