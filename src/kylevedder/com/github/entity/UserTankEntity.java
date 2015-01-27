/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public class UserTankEntity extends TankEntity
{

    private final float TURN_RATE = 90f;
    private final float DRIVE_SPEED = 100f;//100f;
    private final float DRIVE_SPEED_MULTIPLIER = 2f;

    /**
     * User controlled tank entity.
     *
     * @param x
     * @param y
     * @param angle
     */
    public UserTankEntity(float x, float y, float angle)
    {
        super(x, y, angle);
    }

    @Override
    public void update(Input input, int delta)
    {
        super.update(input, delta);
        this.updateDrive(input, delta);
        Object[] objects = MainApp.gameEngine.register.updateCollision(this.hitBox, this.vector, MainApp.NUM_COLLISION_UPDATES, delta);
        this.vector = (Vector) objects[1];
        this.hitBox = (CenteredRectangle) objects[0];
    }

    private void updateDrive(Input input, int delta)
    {
        if (input != null)
        {
            //adds to both the angle of the vector and the rotation of the vector
            float tankAngleAppend = 0;
            //sets the speed of the vector
            float tankSpeed = 0;
            //drive forward
            if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
            {
                tankSpeed += this.getDriveSpeed() / (1000 / delta);
            }
            //drive forward
            if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
            {
                tankSpeed -= this.getDriveSpeed() / (1000 / delta);
            }
            //turn left
            if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
            {
                tankAngleAppend -= this.getTurnRate() / (1000 / delta);
            }
            //turn right
            if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
            {
                tankAngleAppend += this.getTurnRate() / (1000 / delta);
            }

            //speed multiplier
            if (input.isKeyDown(Input.KEY_LSHIFT))
            {
                tankSpeed *= this.getDriveSpeedMultiplier();
            }

            //sets the vector's speed
            this.vector.setSpeed(tankSpeed);
            //adds to the vector's angle
            this.vector.addAngle(tankAngleAppend);
        }
    }

    /**
     * Gets the turn rate for the tank.
     *
     * @return
     */
    public float getTurnRate()
    {
        return TURN_RATE;
    }

    /**
     * Get the base drive speed for the tank.
     *
     * @return
     */
    public float getDriveSpeed()
    {
        return DRIVE_SPEED;
    }

    /**
     * Gets the turbo drive speed multiplier for the tank.
     *
     * @return
     */
    public float getDriveSpeedMultiplier()
    {
        return DRIVE_SPEED_MULTIPLIER;
    }

}
