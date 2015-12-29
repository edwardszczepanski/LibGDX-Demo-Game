package com.edwardszczepanski.ninecircles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.edwardszczepanski.ninecircles.NineCircles;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = NineCircles.V_WIDTH;
		config.width = NineCircles.V_WIDTH;
		new LwjglApplication(new NineCircles(), config);
	}
}
