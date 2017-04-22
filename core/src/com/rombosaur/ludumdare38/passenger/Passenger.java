package com.rombosaur.ludumdare38.passenger;

import com.badlogic.gdx.Gdx;
import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxTimer;
import org.flixel.event.IFlxTimer;

/**
 * Created by rombus on 22/04/17.
 */
public class Passenger extends FlxSprite {
    public static final int HEIGHT = 20, WIDTH =10;
    private float timeToDie = 8f;               // Time of existence if the bus doesn't pick this passenger up
    private float deltaToFlicker = 0.5f;        //  timeToDie - deltaToFlicker = timeToFlicker
    private FlxTimer deathTimer, flickerTimer;
    private IFlxTimer deathTimerCallback, flickerTimerCallback;

    public Passenger() {
        // INIT
        deathTimer = new FlxTimer();
        flickerTimer = new FlxTimer();

        // SETUP
        makeGraphic(WIDTH, HEIGHT, FlxG.PINK, true);
        kill();


        // CALBACKS
        deathTimerCallback = new IFlxTimer() {
            @Override
            public void callback(FlxTimer Timer) {
                kill();
            }
        };
        flickerTimerCallback = new IFlxTimer() {
            @Override
            public void callback(FlxTimer Timer) {
                flicker(deltaToFlicker);
            }
        };
    }

    public void spawn(float x, float y) {
        reset(x, y);
        flickerTimer.start(timeToDie - deltaToFlicker, 1, flickerTimerCallback);
        deathTimer.start(timeToDie, 1, deathTimerCallback);
        // TODO: add deathTimer for the timeToDie
    }

    /**
     * If the bus runs over this passenger you get points
     */
    public void pickUp() {
        Gdx.app.log("DEBUG", "PICKUP según passenger");
        deathTimer.stop();
        flickerTimer.stop();
        kill();
    }

    @Override
    public void update() {
        super.update();

    }
}
