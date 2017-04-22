package com.rombosaur.ludumdare38.level;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

/**
 * Created by rombus on 22/04/17.
 */
public class Building extends FlxSprite{

    public Building(float x, float y, int width, int height){
        makeGraphic(width, height, FlxG.RED);
        centerOffsets();

        this.immovable = true;
        this.x = x;
        this.y = y;
    }

}
