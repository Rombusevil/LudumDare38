package com.rombosaur.ludumdare38.player;

import com.badlogic.gdx.Gdx;
import org.flixel.FlxG;
import org.flixel.FlxSound;
import org.flixel.FlxSprite;

/**
 * Created by rombus on 21/04/17.
 */
public class Hero extends FlxSprite {
    private int xLeftOffset = 19, xRightOffset = FlxG.width -(FlxG.width - 1149), yTopOffset = 4, yBottomOffset=  FlxG.height - (FlxG.height - 591);
    private int oriWidth = 78, oriHeight = 32, vertWidth = 25, vertHeight = 78;
    private static final int ANGLE_UP = 270, ANGLE_DOWN = 90, ANGLE_LEFT = 180, ANGLE_RIGHT = 360;
    private static final float BOOST_FACTOR = 2.5f, NO_BOOST = 1;
    private int maxSpeedRegular = 5;

//    private float accel = 0.1f;
    private float finalSpeed = 0, speedX, speedY;
    public boolean stopped = false;
    private boolean boosting = false;
    private boolean goUp, goDown, goLeft, goRight;
    float boostFactor = NO_BOOST;
    public int lives = 3;
    private FlxSound regularEngine, boostEngine;


    public Hero(){
        loadGraphic("bus_50x50.png", true, true, 50, 50, true);
        addAnimation("sides", new int[]{0}, 0, false);
        addAnimation("up", new int[]{1}, 0, false);
        addAnimation("down", new int[]{2}, 0, false);
        height = width = 40;
        centerOffsets();
        speedX = speedY = 0;
        regularEngine = FlxG.loadSound("regularEngine.ogg", 0.5f, true, false, false);
        boostEngine = FlxG.loadSound("boostEngine.ogg", 0.7f, true, false, false);
    }

    public void levelUp(int level){
        float k = 0.5f * level;
        maxSpeedRegular *= k;
    }

    @Override
    public void kill() {
        regularEngine.stop();
        boostEngine.stop();
        super.kill();
    }

    @Override
    public void reset(float X, float Y) {
        super.reset(X, Y);
        speedX = speedY = 0;
        stopped = false;
        lives = 3;
        boosting = false;
        boostFactor = NO_BOOST;
    }

    public void setStartingPosition(){
        x = 150;
        y = 170;
        boosting = false;
        boostFactor = NO_BOOST;
    }

    public void loseLife(){
        lives--;

        if (lives <= 0) {
            kill();
        } else {
            setStartingPosition();
            speedX = speedY = 0;
        }
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
            regularEngine.stop();
            boostEngine.play();
            boosting = true;
            boostFactor = BOOST_FACTOR;
        }
        if (FlxG.keys.justReleased("SPACE")) {
            boostEngine.stop();
            regularEngine.play();
            boosting = false;
            boostFactor = NO_BOOST;
        }

        goUp    = (FlxG.keys.justPressed("UP"))? true: false;
        goDown  = (FlxG.keys.justPressed("DOWN"))? true: false;
        goLeft  = (FlxG.keys.justPressed("LEFT"))? true: false;
        goRight = (FlxG.keys.justPressed("RIGHT"))? true: false;




        // Make pacman-like movement
//        if (x > FlxG.width) {
//            x = 0;
//        } else if ( x < 0) {
//            x = FlxG.width;
//        }
//        if (y > FlxG.height) {
//            y = 0;
//        } else if (y < 0) {
//            y = FlxG.height;
//        }

        // small world borders
        if (x + width > xRightOffset) {
            goRight = false;
            speedX = 0;
        } else if ( x - 5 < xLeftOffset) {
            goLeft = false;
            speedX = 0;
        }
        if (y + height  > yBottomOffset) {
            speedY = 0;
            goDown = false;
        } else if (y -5 < yTopOffset) {
            speedY = 0;
            goUp = false;
        }


        // MOVEMENT
        if (goUp) {
//            angle = ANGLE_UP;

            // Bounding box
//            width = vertWidth;
//            height = vertHeight;
//            loadGraphic("busUp_25x78.png", true, true, (int)width, (int)height, true);

            play("up");

            speedX = 0;
            speedY = -finalSpeed;


        }
        else if (goDown) {
//            angle = ANGLE_DOWN;

            // Bounding box
//            width = vertWidth;
//            height = vertHeight;
//            loadGraphic("busDown_25x78.png", true, true, (int)width, (int)height, true);

            play("down");

            speedX = 0;
            speedY = finalSpeed;


        }

        if (goLeft) {
//            angle = ANGLE_LEFT;
//            loadGraphic("busSides_78x32.png", true, true, oriWidth, oriHeight, true);
            play("sides");
            setFacing(FlxSprite.LEFT);
            speedX = -finalSpeed;
            speedY = 0;

            // Bounding box
//            width = oriWidth;
//            height = oriHeight;
//            centerOffsets();
        }
        else if (goRight) {
//            angle = ANGLE_RIGHT;
//            loadGraphic("busSides_78x32.png", true, true, oriWidth, oriHeight, true);
            play("sides");
            setFacing(FlxSprite.RIGHT);
            speedX = finalSpeed;
            speedY = 0;

            // Bounding box
//            width = oriWidth;
//            height = oriHeight;
//            centerOffsets();
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
