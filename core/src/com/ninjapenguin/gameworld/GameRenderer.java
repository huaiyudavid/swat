package com.ninjapenguin.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ninjapenguin.gameobjects.Aaron;
import com.ninjapenguin.gameobjects.Background;
import com.ninjapenguin.gameobjects.Sarah;
import com.ninjapenguin.gameobjects.ScrollHandler;
import com.ninjapenguin.helpers.AssetLoader;

/**
 * Created by David on 6/24/16.
 */
public class GameRenderer {
    private GameWorld world;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;
    private GlyphLayout glyphLayout;

    // GameObjects
    private Aaron player;
    private ScrollHandler scroller;
    private Background background1, background2;
    private Sarah[] sarahs;

    // Game Assets
    private TextureRegion run3, aaronSplat, bg1, bg2, sarahBig, sarahSmall, sarahDouble;
    private Animation aaron;

    // Logging
    private FPSLogger fpsLogger;

    // Tween Stuff

    // Buttons

    public GameRenderer(GameWorld world, int width, int height) {
        this.world = world;
        //menu buttons?

        cam = new OrthographicCamera();
        //cam.setToOrtho(true, width, height);
        cam.setToOrtho(false, width, height);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        glyphLayout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        fpsLogger = new FPSLogger();

        //setupTweens();

    }

    private void initGameObjects() {
        player = world.getPlayer();
        scroller = world.getScroller();
        background1 = scroller.getBackground1();
        background2 = scroller.getBackground2();
        sarahs = scroller.getSarahs();
    }

    private void initAssets() {
        run3 = AssetLoader.run3;
        aaronSplat = AssetLoader.aaronSplat;
        bg1 = AssetLoader.bg1;
        bg2 = AssetLoader.bg2;
        sarahBig = AssetLoader.sarahBig;
        sarahSmall = AssetLoader.sarahSmall;
        sarahDouble = AssetLoader.sarahDouble;
        aaron = AssetLoader.aaron;
    }

    private void drawBackground() {
        //batcher.draw(bg1, 0, 400);
        batcher.draw(bg1, background1.getX() - background1.getWidth()/2, background1.getY() - background1.getHeight()/2);
        //Gdx.app.log("GameRenderer", "" + background2.getX());
        batcher.draw(bg2, background2.getX() - background2.getWidth()/2, background2.getY() - background2.getHeight()/2);
    }

    private void drawSarahs() {
        for (Sarah sarah : sarahs) {
            switch(sarah.getType()) {
                case BIG:
                    batcher.draw(sarahBig, sarah.getX() - sarah.getWidth()/2, sarah.getY() - sarah.getHeight()/2);
                    break;
                case SMALL:
                    batcher.draw(sarahSmall, sarah.getX() - sarah.getWidth()/2, sarah.getY() - sarah.getHeight()/2);
                    break;
                case DOUBLE:
                    batcher.draw(sarahDouble, sarah.getX() - sarah.getWidth()/2, sarah.getY() - sarah.getHeight()/2);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawPlayer(float runTime) {
        if (!player.isAlive()) {
            batcher.draw(aaronSplat, player.getX() - player.getWidth()/2, player.getY() - player.getHeight()/2);
        } else if (player.isJumping() || world.isReady()) {
            batcher.draw(run3, player.getX() - player.getWidth()/2, player.getY() - player.getHeight()/2);
        } else {
            batcher.draw(aaron.getKeyFrame(runTime), player.getX() - player.getWidth()/2, player.getY() - player.getHeight()/2);
        }
    }

    private void drawScore() {
        AssetLoader.comicsans.getData().setScale(.5f, .5f);
//        String score = "Score: " + leftPad("" + world.getScore(), 6);
        String score = "Score: " + world.getScore();
        AssetLoader.comicsans.draw(batcher, score, 950, 668);
    }

    private void drawGameOver() {
        AssetLoader.comicsans.getData().setScale(.75f, .75f);
        String gameOver = "GAME OVER";
        String replay = "Tap to Restart";
//        String highScore = "High Score: " + leftPad("" + AssetLoader.getHighScore(), 6);
        String highScore = "High Score: " + AssetLoader.getHighScore();
        glyphLayout.setText(AssetLoader.comicsans, gameOver);
        AssetLoader.comicsans.draw(batcher, glyphLayout, world.getWidth()/2 - glyphLayout.width/2, 600);
        glyphLayout.setText(AssetLoader.comicsans, replay);
        AssetLoader.comicsans.draw(batcher, glyphLayout, world.getWidth()/2 - glyphLayout.width/2, 500);
        glyphLayout.setText(AssetLoader.comicsans, highScore);
        AssetLoader.comicsans.draw(batcher, glyphLayout, world.getWidth()/2 - glyphLayout.width/2, 400);
    }

    private void drawInstructions() {
        String instructions = "Tap to Start";
        AssetLoader.comicsans.getData().setScale(.75f, .75f);
        glyphLayout.setText(AssetLoader.comicsans, instructions);
        AssetLoader.comicsans.draw(batcher, glyphLayout, world.getWidth()/2 - glyphLayout.width/2, world.getHeight()/2 + 100);
    }

    public void render(float delta, float runTime) {
        fpsLogger.log();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        batcher.disableBlending();

        drawBackground();

        batcher.enableBlending();

        drawSarahs();
        drawPlayer(runTime);
        drawScore();

        if (world.isGameOver()) {
            drawGameOver();
        } else if (world.isReady()) {
            drawInstructions();
        }

        batcher.end();

//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(new Color(255, 255, 0, 0.5f));
//
//        shapeRenderer.rect(player.getBoundingRect().getX(), player.getBoundingRect().getY(),
//                player.getBoundingRect().getWidth(), player.getBoundingRect().getHeight());
//
//        shapeRenderer.setColor(new Color(255, 0, 0, 0.5f));
//        for (Sarah sarah : sarahs) {
//            shapeRenderer.rect(sarah.getTopHalf().getX(), sarah.getTopHalf().getY(),
//                    sarah.getTopHalf().getWidth(), sarah.getTopHalf().getHeight());
//            shapeRenderer.rect(sarah.getBottomHalf().getX(), sarah.getBottomHalf().getY(),
//                    sarah.getBottomHalf().getWidth(), sarah.getBottomHalf().getHeight());
//        }
//
//        shapeRenderer.end();
//        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

//    private String leftPad(String str, int digits) {
//        String prefix = "";
//        int length = digits - str.length();
//        for (int i = 0; i < length; i++) {
//            prefix += "0";
//        }
//        return prefix + str;
//    }
}
