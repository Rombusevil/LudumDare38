package com.rombosaur.ludumdare38.states;

import com.rombosaur.ludumdare38.GameConfig;
import org.flixel.*;
import org.flixel.event.IFlxButton;

/**
 * Created by rombus on 22/04/17.
 */
public class MenuState extends FlxState {
    private FlxSound intro;

    @Override
    public void create() {
        FlxG.playMusic("intro.ogg", 0.5f);

        FlxG.mouse.show();

        FlxText title = new FlxText(0, 0, FlxG.width, GameConfig.TITLE);
        title.setAlignment("center");
        title.scale.x = 10;
        title.scale.y = 10;

        FlxText controls = new FlxText(0, 150, FlxG.width, "Use arrows to steer; space bar to boost");
        controls.scale.x = 3;
        controls.scale.y = 3;
        controls.setAlignment("center");

        FlxText credits = new FlxText(0, FlxG.height-80, FlxG.width, "A game by Iber Parodi Siri - April 2017");
        credits.scale.x = 2;
        credits.scale.y = 2;
        credits.setAlignment("center");


        FlxButton start = new FlxButton(FlxG.width / 2 -40 , FlxG.height / 2 , "Play", new IFlxButton() {
            @Override
            public void callback() {
            FlxG.switchState(new PlayState());
            FlxG.playMusic("bikedayinthe80s.ogg");
            }
        });
        start.centerOffsets();
        start.labelOffset.y = -5;

        start.scale.x = 2;
        start.scale.y = 2;


        add(title);
        add(controls);
        add(credits);
        add(start);
    }

    @Override
    public void update() {
        super.update();
        if (FlxG.keys.justPressedAny()) {
            FlxG.switchState(new PlayState());
            FlxG.playMusic("bikedayinthe80s.ogg");
        }
    }
}
