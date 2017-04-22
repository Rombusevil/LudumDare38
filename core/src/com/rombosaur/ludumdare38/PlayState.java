package com.rombosaur.ludumdare38;

import com.badlogic.gdx.Gdx;
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

    @Override
    public void create() {
        // FLIXEL CONFIG
        FlxG.debug = GameConfig.DEBUG;
        FlxG.mouse.show();

        // GROUPS
        buildings = new FlxGroup();
        passengers = new FlxGroup();

        // MANAGERS
        passengerMan = new PassengerMan(passengers, buildings);

        // SETUP
        hero = new Hero();
        hero.x = 150;
        hero.y = 150;

        createBuildings();

        passengerMan.showNewPassenger();

        // STAGE
        add(hero);
        add(buildings);
        add(passengers);
        hud = new Hud(this);

        // CALLBACKS
        heroBuildingsCallback =  new IFlxCollision() {
            @Override
            public void callback(FlxObject hero, FlxObject building) {
                hero.kill();
                hud.gameOver();
                passengerMan.stop();
                // TODO: set building on fire
                // TODO SOUND: die sound
            }
        };

        heroPassengersCallback = new IFlxCollision() {
            @Override
            public void callback(FlxObject hero, FlxObject passenger) {
                ((Passenger)passenger).pickUp();
                hud.pickedUpPassenger();
                passengerMan.passengerCollected();
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

        FlxG.collide(hero, buildings, heroBuildingsCallback);
        FlxG.overlap(hero, passengers, heroPassengersCallback);
    }

    /**
     * Creates the world
     */
    private void createBuildings(){
        buildings.add(new com.rombosaur.ludumdare38.level.Building(10, 4, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(630, 220, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(406, 352, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(406, 486, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(788, 369, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(325, 90, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(58, 539, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(1020, 581, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(844, 134, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(1084, 11, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(516, 22, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(130, 253, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(638, 643, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(220, 600, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(991, 285, 100, 50));
        buildings.add(new com.rombosaur.ludumdare38.level.Building(581, 491, 100, 50));
    }




/** DEVELOPMENT UTILS ------------------------------------- **/
    /**
     * Prints a Building constructor on the position of the mouse click
     */
    private void printNewBuildingPos(){
        if(FlxG.mouse.justPressed()){
            Gdx.app.log("DEBUG", "buildings.add(new Building("+FlxG.mouse.screenX+", "+FlxG.mouse.screenY+", 100, 50));");
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
