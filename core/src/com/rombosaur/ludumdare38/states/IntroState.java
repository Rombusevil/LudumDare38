package com.rombosaur.ludumdare38.states;

import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;

/**
 * Created by rombus on 22/04/17.
 */
public class IntroState extends FlxState {
    @Override
    public void create() {
        FlxSprite johnny = new FlxSprite(0, 0, "johnnycocaine.png");

        FlxText texto = new FlxText(700, FlxG.height/2 - 100, FlxG.width - 700, "Being a bus driver is important...\n\nEspecially when you are...\n\nTHE ONLY ONE IN THE WORLD!!!");
        texto.setAlignment("center");
        texto.scale.x = 3;
        texto.scale.y = 3;


        FlxText texto2 = new FlxText(700, FlxG.height - 30, FlxG.width - 700, "Ludum Dare #38");
        texto2.setAlignment("center");
        texto2.scale.x = 2;
        texto2.scale.y = 2;

        add(texto);
        add(texto2);
        add(johnny);
    }

    @Override
    public void update() {
        super.update();
        if(FlxG.keys.justPressedAny()){
            FlxG.switchState(new MenuState());
        }
    }
}
