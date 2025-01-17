package com.ninjapenguin.swat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ninjapenguin.swat.SwatGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Swat!";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new SwatGame(), config);
	}
}
