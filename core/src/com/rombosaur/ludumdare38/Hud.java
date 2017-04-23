package com.rombosaur.ludumdare38;

import com.badlogic.gdx.Gdx;
import com.rombosaur.ludumdare38.player.Hero;
import org.flixel.*;
import org.flixel.event.IFlxTimer;

/**
 * Created by rombus on 22/04/17.
 */
public class Hud {
    private FlxState state;
    private FlxText pointsTxt;
    private final String PASSENGERS_TEXT = "Passengers: ";
    public int passengers = 0;
    private FlxSprite wrench1, wrench2, wrench3;
    private int curLevel = 1;
    private FlxTimer textTimer, diedTimer;
    private Hero hero;

    public Hud(FlxState state, Hero hero) {
        this.hero = hero;
        this.state = state;
        textTimer = new FlxTimer();
        diedTimer = new FlxTimer();

        pointsTxt = new FlxText(10, FlxG.height - 50, FlxG.width, PASSENGERS_TEXT + "0");
        pointsTxt.scale.x = 3;
        pointsTxt.scale.y = 3;

        state.add(pointsTxt);

        wrench1 = new FlxSprite(FlxG.width - 10 - (80 * 1), FlxG.height - 60, "wrench_55x45.png");
        wrench2 = new FlxSprite(FlxG.width - 10 - (80 * 2), FlxG.height - 60, "wrench_55x45.png");
        wrench3 = new FlxSprite(FlxG.width - 10 - (80 * 3), FlxG.height - 60, "wrench_55x45.png");

        state.add(wrench1);
        state.add(wrench2);
        state.add(wrench3);
    }

    /**
     * Deletes  life wrenches from screen
     *
     * @param lives
     */
    public void loseLife(int lives){
        if(lives == 0){
            wrench1.kill();
            return;
        }
        else if(lives == 1){
            wrench2.kill();
        }
        else if (lives == 2){
            wrench3.kill();
        }

        final FlxText youDied = new FlxText(0, 100, FlxG.width, "YOU CRASHED THE BUS!! D:");
        youDied.setAlignment("center");
        youDied.scale.x = 3;
        youDied.scale.y = 3;
        state.add(youDied);
        hero.stopped = true;


        diedTimer.start(1.5f, 1, new IFlxTimer() {
            @Override
            public void callback(FlxTimer Timer) {
                youDied.kill();
                hero.stopped = false;
            }
        });

    }

    public void gameOver(){
        FlxText gameOverTxt = new FlxText(0, FlxG.height/ 2 - 80, FlxG.width, "GAME OVER");
        gameOverTxt.setAlignment("center");
        gameOverTxt.scale.x = 6;
        gameOverTxt.scale.y = 6;

        FlxText restartTxt = new FlxText(0, FlxG.height/ 2 + 10, FlxG.width, "Press 'R' to restart\n'M' for main menu");
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

        int points = ((int)(passengers / 10)) +1;
        Gdx.app.log("DEBUG", "PICKED UP. Points. "+points+", curLevel "+curLevel);
        if(  points > curLevel ) {
            FlxG.play("levelCleared.ogg", 2f, false, false);
            curLevel = points;
            final FlxText levelCleared = new FlxText(0, FlxG.height/ 2 + 10, FlxG.width, "Level Cleared!\nStarting level "+curLevel+"\nSPEED INCREASED!!!");
            levelCleared.setAlignment("center");
            levelCleared.scale.x = 1;
            levelCleared.scale.y = 1;
            state.add(levelCleared);

            hero.levelUp(curLevel);

            textTimer.start(1f, 1, new IFlxTimer() {
                @Override
                public void callback(FlxTimer Timer) {
                    levelCleared.kill();
                }
            });
        }
    }

}
