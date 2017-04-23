package com.rombosaur.ludumdare38.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rombosaur.ludumdare38.GameConfig;
import com.rombosaur.ludumdare38.MainGame;
import org.flixel.FlxDesktopApplication;
import org.flixel.FlxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("ricon128.png", Files.FileType.Internal);
		config.width = GameConfig.GAME_WIDTH;
		config.height = GameConfig.GAME_HEIGHT;
		config.title = GameConfig.TITLE;

		FlxGame g = new MainGame();
		new LwjglApplication((ApplicationListener) g.stage, config);
//		new FlxDesktopApplication(new MainGame(), GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
	}
}
