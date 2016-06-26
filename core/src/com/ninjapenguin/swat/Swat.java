package com.ninjapenguin.swat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ninjapenguin.helpers.AssetLoader;
import com.ninjapenguin.screens.SplashScreen;

public class Swat extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
