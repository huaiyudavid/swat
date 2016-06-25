package com.ninjapenguin.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by David on 6/24/16.
 */
public class AssetLoader {
    public static TextureAtlas gameSprites;
    public static TextureRegion run1, run2, run3, aaronSplat, bg1, bg2, sarahBig, sarahSmall, sarahDouble;
    public static Animation aaron;
    public static Sound jump, splat;
    public static Music music;
    public static BitmapFont comicsans;

    private static Preferences prefs;

    public static void load() {
        gameSprites = new TextureAtlas(Gdx.files.internal("data/gameSprites.txt"));

        run1 = gameSprites.findRegion("run1");
        //run1.flip(false, true);
        run2 = gameSprites.findRegion("run2");
        //run2.flip(false, true);
        run3 = gameSprites.findRegion("run3");
        //run3.flip(false, true);
        TextureRegion[] running = {run1, run3, run2};
        aaron = new Animation(0.15f, running);
        aaron.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        sarahBig = gameSprites.findRegion("sarahbig");
        //sarahBig.flip(false, true);
        sarahSmall = gameSprites.findRegion("sarahsmall");
        //sarahSmall.flip(false, true);
        sarahDouble = gameSprites.findRegion("sarahdouble");
        //sarahDouble.flip(false, true);

        aaronSplat = gameSprites.findRegion("aaronsplat");
        //aaronSplat.flip(false, true);

        bg1 = gameSprites.findRegion("background1");
        //bg1.flip(false, true);
        bg2 = gameSprites.findRegion("background2");
        //bg2.flip(false, true);

        jump = Gdx.audio.newSound(Gdx.files.internal("data/jump.mp3"));
        splat = Gdx.audio.newSound(Gdx.files.internal("data/splat.mp3"));

        music = Gdx.audio.newMusic(Gdx.files.internal("data/getaway.mp3"));
        music.setLooping(true);

        comicsans = new BitmapFont(Gdx.files.internal("data/comicsans.fnt"));
        comicsans.getData().setScale(.5f, .5f);

        prefs = Gdx.app.getPreferences("Swat");
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        gameSprites.dispose();

        // Dispose sounds
        jump.dispose();
        splat.dispose();
        music.dispose();

        comicsans.dispose();
    }
}
