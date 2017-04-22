package com.rombosaur.ludumdare38.player;

import com.badlogic.gdx.Gdx;
import org.flixel.FlxG;
import org.flixel.FlxSprite;

/**
 * Created by rombus on 21/04/17.
 */
public class Hero extends FlxSprite {
    private int oriWidth = 40, oriHeight = 15;
    private static final int ANGLE_UP = 270, ANGLE_DOWN = 90, ANGLE_LEFT = 180, ANGLE_RIGHT = 360;
    private static final float BOOST_FACTOR = 2.5f, NO_BOOST = 1;
    private int maxSpeedRegular = 5;

    private float accel = 0.1f;
    private float finalSpeed = 0, speedX, speedY;
    private boolean stopped = false, boosting = false;
    private boolean goUp, goDown, goLeft, goRight;
    float boostFactor = NO_BOOST;


    public Hero(){
        loadGraphic("bus.png", true, true, oriWidth, oriHeight, true);
        centerOffsets();
        speedX = speedY = 0;
    }

    @Override
    public void reset(float X, float Y) {
        super.reset(X, Y);
        speedX = speedY = 0;
        stopped = false;
    }

    @Override
    public void update() {
//        regularControls();
//        debugAngles();

        alwaysMoving();


    }

    private void alwaysMoving(){
        if(stopped) {
            return;
        }

        finalSpeed = maxSpeedRegular; //(finalSpeed < maxSpeedRegular) ? finalSpeed + accel : maxSpeedRegular;

        if(FlxG.keys.justPressed("SPACE")){
            boosting = true;
            boostFactor = BOOST_FACTOR;
        }
        if (FlxG.keys.justReleased("SPACE")) {
            boosting = false;
            boostFactor = NO_BOOST;
        }

        goUp    = (FlxG.keys.justPressed("UP"))? true: false;
        goDown  = (FlxG.keys.justPressed("DOWN"))? true: false;
        goLeft  = (FlxG.keys.justPressed("LEFT"))? true: false;
        goRight = (FlxG.keys.justPressed("RIGHT"))? true: false;


        // Make pacman-like movement
        if (x > FlxG.width) {
            x = 0;
        } else if ( x < 0) {
            x = FlxG.width;
        }
        if (y > FlxG.height) {
            y = 0;
        } else if (y < 0) {
            y = FlxG.height;
        }


        // MOVEMENT
        if (goUp) {
            angle = ANGLE_UP;
            speedX = 0;
            speedY = -finalSpeed;

            // Bounding box
            width = oriHeight;
            height = oriWidth;
            offset.x = 13;
            offset.y = -11;
        }
        else if (goDown) {
            angle = ANGLE_DOWN;
            speedX = 0;
            speedY = finalSpeed;

            // Bounding box
            width = oriHeight;
            height = oriWidth;
            offset.x = 13;
            offset.y = -11;
        }

        if (goLeft) {
            angle = ANGLE_LEFT;
            speedX = -finalSpeed;
            speedY = 0;

            // Bounding box
            width = oriWidth;
            height = oriHeight;
            centerOffsets();
        }
        else if (goRight) {
            angle = ANGLE_RIGHT;
            speedX = finalSpeed;
            speedY = 0;

            // Bounding box
            width = oriWidth;
            height = oriHeight;
            centerOffsets();
        }

        x += speedX * boostFactor;
        y += speedY * boostFactor;
    }


    private void debugAngles(){
        if (FlxG.keys.justPressed("R")) {
            angle += 90;
            Gdx.app.log("DEBUG", "ANGLE: "+angle);
        }



        if(FlxG.keys.justPressed("Q")){
            angle = 360;
            Gdx.app.log("DEBUG", "ANGLE 360: "+angle);
        }
        if(FlxG.keys.justPressed("W")){
            angle = 90;
            Gdx.app.log("DEBUG", "ANGLE 90: "+angle);
        }
        if(FlxG.keys.justPressed("E")){
            angle = 180;
            Gdx.app.log("DEBUG", "ANGLE 180: "+angle);
        }
        if(FlxG.keys.justPressed("R")){
            angle = 270;
            Gdx.app.log("DEBUG", "ANGLE 270: "+angle);
        }
    }
}
