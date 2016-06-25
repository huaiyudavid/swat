package com.ninjapenguin.gameobjects;

import com.ninjapenguin.helpers.AssetLoader;

/**
 * Created by David on 6/24/16.
 */
public class Background extends Scrollable {
    public Background(float x, float y) {
        super(x, y, AssetLoader.bg1.getRegionWidth(), AssetLoader.bg1.getRegionHeight());
    }

    public void onRestart(float x) {
        super.onRestart(x);
        position.x = x;
    }
}
