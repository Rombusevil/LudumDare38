package com.rombosaur.ludumdare38.desktop;

import com.rombosaur.ludumdare38.GameConfig;
import com.rombosaur.ludumdare38.MainGame;
import org.flixel.FlxDesktopApplication;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new FlxDesktopApplication(new MainGame(), GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
	}
}
