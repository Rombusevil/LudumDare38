package com.rombosaur.ludumdare38;

import com.badlogic.gdx.Gdx;
import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;

/**
 * Created by rombus on 22/04/17.
 */
public class Hud {
    private FlxState state;
    private FlxText pointsTxt;
    private final String PASSENGERS_TEXT = "Passengers: ";
    public int passengers = 0;

    public Hud(FlxState state) {
        this.state = state;



        pointsTxt = new FlxText(10, 3, FlxG.width, PASSENGERS_TEXT + "0");
        pointsTxt.scale.x = 3;
        pointsTxt.scale.y = 3;

        state.add(pointsTxt);

        Gdx.app.log("DEBUG", "CONSTRUCTOR DE HUD");
    }

    public void gameOver(){
        FlxText gameOverTxt = new FlxText(0, FlxG.height/ 2 - 80, FlxG.width, "GAME OVER");
        gameOverTxt.setAlignment("center");
        gameOverTxt.scale.x = 6;
        gameOverTxt.scale.y = 6;

        FlxText restartTxt = new FlxText(0, FlxG.height/ 2 + 10, FlxG.width, "Press 'R' to restart");
        restartTxt.setAlignment("center");
        restartTxt.scale.x = 1;
        restartTxt.scale.y = 1;

        state.add(gameOverTxt);
        state.add(restartTxt);
    }

    public void reset(){
        passengers = 0;
    }

    public void pickedUpPassenger(){
        passengers++;
        pointsTxt.setText(PASSENGERS_TEXT+passengers);
    }
}
