/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public class UserTankEntity extends TankEntity
{

    private final float TURN_RATE = 2f;
    private final float DRIVE_SPEED = 5f;
    private final float DRIVE_SPEED_MULTIPLIER = 2f;
    
    /**
     * User controlled tank entity.
     * @param x
     * @param y
     * @param angle 
     */
    public UserTankEntity(float x, float y, float angle)
    {
        super(x, y, angle);
    }

    @Override
    public void update(Input input)
    {
        super.update(input);
        this.updateDrive(input);
        this.hitBox.updateAbs(this.hitBox.getCenterX() + this.vector.getXComp(), this.hitBox.getCenterY() - this.vector.getYComp(), this.vector.getRotation());
        
    }
    
    private void updateDrive(Input input)
    {
        if (input != null)
        {
            float tankAngleAppend = 0;
            float tankSpeed = 0;
            //drive forward
            if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
            {
                tankSpeed += this.getDriveSpeed();
            }
            //drive forward
             if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
            {
                tankSpeed -= this.getDriveSpeed();
            }
            //turn left
            if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
            {
                tankAngleAppend -= this.getTurnRate();
            }
            //turn right
            if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
            {
                tankAngleAppend += this.getTurnRate();
            }

            //speed multiplier
            if (input.isKeyDown(Input.KEY_LSHIFT))
            {
                tankSpeed *= this.getDriveSpeedMultiplier();                
            }
            
            this.vector.setSpeed(tankSpeed);
            this.vector.addAngle(tankAngleAppend);
            this.vector.setRotation(this.vector.getAngle());
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
