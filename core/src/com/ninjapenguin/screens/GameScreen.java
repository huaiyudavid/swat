package com.ninjapenguin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.ninjapenguin.gameworld.GameRenderer;
import com.ninjapenguin.gameworld.GameWorld;
import com.ninjapenguin.helpers.InputHandler;

/**
 * Created by David on 6/24/16.
 */
public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 1280f;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        world = new GameWorld((int)gameWidth, (int)gameHeight);
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(world, (int) gameWidth, (int) gameHeight);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
