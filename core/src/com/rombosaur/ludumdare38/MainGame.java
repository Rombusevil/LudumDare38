package com.rombosaur.ludumdare38;

import flash.events.Event;
import org.flixel.FlxGame;

public class MainGame extends FlxGame {
	public MainGame() {
		super(GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT, PlayState.class, GameConfig.VIEWPORT_ZOOM);
	}

	@Override
	protected void onFocusLost(Event FlashEvent) {
		// Keep the game playing even when the window loses focus
		_lostFocus = false;
	}
}
