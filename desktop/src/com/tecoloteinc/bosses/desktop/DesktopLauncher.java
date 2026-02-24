package com.tecoloteinc.bosses.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tecoloteinc.bosses.BossesMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(720, 480);
		config.setForegroundFPS(60);
		config.setTitle("Bosses");
		new Lwjgl3Application(new BossesMain(), config);
	}
}
