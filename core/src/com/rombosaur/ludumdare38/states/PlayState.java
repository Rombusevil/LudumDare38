package com.rombosaur.ludumdare38.states;

import com.badlogic.gdx.Gdx;
import com.rombosaur.ludumdare38.GameConfig;
import com.rombosaur.ludumdare38.Hud;
import com.rombosaur.ludumdare38.level.Building;
import com.rombosaur.ludumdare38.passenger.Passenger;
import com.rombosaur.ludumdare38.passenger.PassengerMan;
import com.rombosaur.ludumdare38.player.Hero;
import org.flixel.*;
import org.flixel.event.IFlxCollision;

/**
 * Created by rombus on 21/04/17.
 */
public class PlayState extends FlxState {
    private Hero hero;
    private FlxGroup buildings, passengers;
    private PassengerMan passengerMan;
    private IFlxCollision heroBuildingsCallback, heroPassengersCallback;
    private Hud hud;

    private boolean editingLevel = true;
    private Building.Graphics selectedGraphic;
    private Building lastAddedBuilding;

    private FlxEmitter emitter;

    public void create() {
        // FLIXEL CONFIG
        FlxG.debug = GameConfig.DEBUG;
        FlxG.mouse.show();
        editingLevel = GameConfig.DEBUG;

        // GROUPS
        buildings = new FlxGroup();
        passengers = new FlxGroup();

        // MANAGERS
        passengerMan = new PassengerMan(passengers, buildings);

        // SETUP
        emitter = new FlxEmitter(0,0, 50);
        emitter.setXSpeed(-350, 350);
        emitter.setYSpeed(-100, 0);
        emitter.setRotation(-720, 720);
        emitter.gravity = 300;
        emitter.makeParticles("gibssmall.png", 50, 10, true, 0f);



        selectedGraphic = Building.Graphics.BUILDING1;
        hero = new Hero();
        hero.setStartingPosition();

        createBuildings();

        passengerMan.showNewPassenger();

        // STAGE
        add(new FlxSprite(0, 0, "land_1200x700.png"));
        add(hero);
        add(buildings);
        add(passengers);
        add(emitter);
        hud = new Hud(this, hero);

        // CALLBACKS
        heroBuildingsCallback =  new IFlxCollision() {
            @Override
            public void callback(FlxObject hero, FlxObject building) {
                emitter.at(hero);
                emitter.start(true, 3, 0, 30);


                ((Hero)hero).loseLife();
                int lives = ((Hero)hero).lives;
                hud.loseLife(lives);
                FlxG.play("explo2.ogg");

                if(lives == 0) {
                    hud.gameOver();
                    passengerMan.stop();
                }
                // TODO SOUND: die sound
            }
        };

        heroPassengersCallback = new IFlxCollision() {
            @Override
            public void callback(FlxObject hero, FlxObject passenger) {
                ((Passenger)passenger).pickUp();
                hud.pickedUpPassenger();
                passengerMan.passengerCollected();
                FlxG.play("tinu.ogg");
                // TODO SOUND: pickup sound
            }
        };
    }

    @Override
    public void update() {
        super.update();
        if(GameConfig.DEBUG) {
            printNewBuildingPos();
            testPassengerMan();
        }

        // Restart game
        if(FlxG.keys.justPressed("R")){
            FlxG.switchState(new PlayState());
        }
        else if(FlxG.keys.justPressed("M")){
            FlxG.switchState(new MenuState());
            FlxG.playMusic("intro.ogg", 0.5f);
        }

        FlxG.collide(hero, buildings, heroBuildingsCallback);
        FlxG.overlap(hero, passengers, heroPassengersCallback);
    }

    /**
     * Creates the world
     */
    private void createBuildings(){
        buildings.add(new Building(500, 220, 100,    50, Building.Graphics.BUILDING1));
        buildings.add(new Building(600, 220, 100, 50, Building.Graphics.BUILDING2));
        buildings.add(new Building(700, 220, 100, 50, Building.Graphics.BUILDING2));
        buildings.add(new Building(213, 326, 100, 50, Building.Graphics.BUILDING2));
//        buildings.add(new Building(213, 420, 100, 50, Building.Graphics.BUILDING2));
        buildings.add(new Building(29, 63,100, 55, Building.Graphics.BUILDING1));
        buildings.add(new Building(114, 14,71, 55, Building.Graphics.BUILDING3));
        buildings.add(new Building(532, 541,100, 55, Building.Graphics.BUILDING3));
        buildings.add(new Building(604, 541,100, 55, Building.Graphics.BUILDING1));
        buildings.add(new Building(708, 541,100, 55, Building.Graphics.BUILDING2));
        buildings.add(new Building(924, 342,71, 55, Building.Graphics.BUILDING3));
        buildings.add(new Building(704, 6,71, 55, Building.Graphics.BUILDING3));
        buildings.add(new Building(1069, 10,71, 55, Building.Graphics.BUILDING3));
        buildings.add(new Building(1078, 467,100, 55, Building.Graphics.BUILDING2));
        buildings.add(new Building(326, 106,100, 55, Building.Graphics.BUILDING1));
        buildings.add(new Building(1078, 196,100, 55, Building.Graphics.BUILDING1));
        buildings.add(new Building(882, 128,71, 55, Building.Graphics.BUILDING3));
        buildings.add(new Building(955, 128,71, 55, Building.Graphics.BUILDING2));
        buildings.add(new Building(1060, 129,100, 55, Building.Graphics.BUILDING1));
    }




/** DEVELOPMENT UTILS ------------------------------------- **/
    /**
     * Prints a Building constructor on the position of the mouse click
     */
    private void printNewBuildingPos(){
        if(editingLevel) {
            if (FlxG.keys.justPressed("Z")) {
                if (lastAddedBuilding != null) {
                    lastAddedBuilding.kill();
                    remove(lastAddedBuilding);
                }
            }
            if (FlxG.keys.justPressed("Q")) {
                selectedGraphic = Building.Graphics.BUILDING1;
            } else if (FlxG.keys.justPressed("W")) {
                selectedGraphic = Building.Graphics.BUILDING2;
            } else if (FlxG.keys.justPressed("E")) {
                selectedGraphic = Building.Graphics.BUILDING3;
            }

            if (FlxG.mouse.justPressed()) {
                int width, height;
                width = (selectedGraphic== Building.Graphics.BUILDING3)? 71: 100;
                height = 55;
                lastAddedBuilding = new Building(FlxG.mouse.screenX, FlxG.mouse.screenY, width, height, selectedGraphic);
                buildings.add(lastAddedBuilding);
                Gdx.app.log("DEBUG", "buildings.add(new Building(" + FlxG.mouse.screenX + ", " + FlxG.mouse.screenY + ","+width+", "+height+", Building.Graphics."+selectedGraphic+"));");
            }
        }
    }

    /**
     * Press P to kill and regenerate a passenger
     */
    private void testPassengerMan() {
        if(FlxG.keys.justPressed("P")){
            passengerMan.showNewPassenger();
        }
    }
}
