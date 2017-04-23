package com.rombosaur.ludumdare38.passenger;

import com.badlogic.gdx.utils.Array;
import com.rombosaur.ludumdare38.level.Building;
import org.flixel.*;
import org.flixel.event.IFlxTimer;

/**
 * Manager for passengers.
 * Keeps references to passengers for recycling.
 *
 * Created by rombus on 22/04/17.
 */
public class PassengerMan {
    private static final float REGULAR_INTERVAL = 10f, REGULAR_INTERVAL2 = 7f, SHORT_DELAY = 0.4f;
    private FlxGroup passengers, buildings;
    private static final float SIDES_OFFSETS = 50, VERTICAL_OFFSETS = 50; // Offsets for positioning passengers on the screen
    private boolean timer1Available = true;
    private FlxTimer timer1, scheduler, scheduler2;
    private IFlxTimer timer1Callback, schedulerCallback, schedulerCallback2;


    public PassengerMan(FlxGroup passengers, FlxGroup buildings){
        // INIT
        this.passengers = passengers;
        this.buildings = buildings;
        this.timer1 = new FlxTimer();
        this.scheduler = new FlxTimer();
        this.scheduler2 = new FlxTimer();

        // SETUP
        passengers.add(new Passenger());
        passengers.add(new Passenger());
        passengers.add(new Passenger());
        passengers.add(new Passenger());
        passengers.add(new Passenger());

        // CALBACKS
        timer1Callback = new IFlxTimer() {
            @Override
            public void callback(FlxTimer Timer) {
                showNewPassenger();
                timer1Available = true;
            }
        };

        schedulerCallback = new IFlxTimer() {
            @Override
            public void callback(FlxTimer Timer) {
                showNewPassenger();
                scheduler.start(REGULAR_INTERVAL, 1, schedulerCallback);
            }
        };

        schedulerCallback2 = new IFlxTimer() {
            @Override
            public void callback(FlxTimer Timer) {
                showNewPassenger();
                scheduler2.start(REGULAR_INTERVAL2, 1, schedulerCallback);
            }
        };

        scheduler.start(1f, 1, schedulerCallback);
        scheduler2.start(1.5f, 1, schedulerCallback);
    }

    public void passengerCollected(){
        if(timer1Available){
            timer1Available = false;
            timer1.start(SHORT_DELAY, 1, timer1Callback);
        }
    }

    public Passenger showNewPassenger(){
        Passenger passenger = (Passenger) passengers.getFirstAvailable();

        float tmpX, tmpY;
        do {
            // Randomize position

//            This are the limits that I have hardcoded on Hero class, I'll use the same here, sort of...
//            int xLeftOffset = 19, xRightOffset = FlxG.width -(FlxG.width - 1149), yTopOffset = 4, yBottomOffset=  FlxG.height - (FlxG.height - 591);
            int xRightOffset = FlxG.width - (FlxG.width - 1149);
            int yBottomOffset = FlxG.height - (FlxG.height - 591);
            tmpX = FlxG.random() * xRightOffset;
            tmpY = FlxG.random() * yBottomOffset;

            // Make sure it's not too close to the screen borders
            if(tmpX >= (FlxG.width - SIDES_OFFSETS)){
                tmpX -= SIDES_OFFSETS;
            } else if(tmpX <= SIDES_OFFSETS){
                tmpX += SIDES_OFFSETS;
            }

            if(tmpY >= (FlxG.height - VERTICAL_OFFSETS)){
                tmpY -= VERTICAL_OFFSETS;
            } else if(tmpY <= VERTICAL_OFFSETS){
                tmpY += VERTICAL_OFFSETS;
            }
        } while (overlapsWithBuilding(tmpX, tmpY));

        if (passenger != null) {
            passenger.spawn(tmpX, tmpY);
        }

        return passenger;
    }

    public void stop() {
        timer1.stop();
        scheduler.stop();
        scheduler2.stop();
    }

    private boolean overlapsWithBuilding(float x, float y){
        boolean result = false;

        // Create a point of each corner of the rectangle that bounds the passenger
        FlxPoint pointA = new FlxPoint(x, y);
        FlxPoint pointB = new FlxPoint(x, y+Passenger.HEIGHT);
        FlxPoint pointC = new FlxPoint(x+Passenger.WIDTH, y);
        FlxPoint pointD = new FlxPoint(x+Passenger.WIDTH, y+Passenger.HEIGHT);

        // Checks if any of the 4 points of the bounding box overlaps with a building
        Array<FlxBasic> buildingsArr = buildings.members;
        for (FlxBasic buildingBasic: buildingsArr) {
            Building building = (Building) buildingBasic;

            if( building.overlapsPoint(pointA) ||
                building.overlapsPoint(pointB) ||
                building.overlapsPoint(pointC) ||
                building.overlapsPoint(pointD)){
                    result =   true;
                    break;
            }
        }

        return result;
    }
}
