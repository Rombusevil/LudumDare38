package com.rombosaur.ludumdare38.level;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

/**
 * Created by rombus on 22/04/17.
 */
public class Building extends FlxSprite{
    public enum Graphics { BUILDING1, BUILDING2, BUILDING3;

        public String getGraphic(){
            String imgPath;
            switch (this){
                default:
                case BUILDING1:
                    imgPath = "casa1_100x55.png";
                    break;
                case BUILDING2:
                    imgPath = "casa2_100x55.png";
                    break;
                case BUILDING3:
                    imgPath = "casa3_71x55.png";
                    break;
            }

            return imgPath;
        }
    }

    public Building(float x, float y, int width, int height, Graphics graphic){
        loadGraphic(graphic.getGraphic());
//        makeGraphic(width, height, FlxG.RED);
        centerOffsets();

        this.immovable = true;
        this.x = x;
        this.y = y;
    }

}
