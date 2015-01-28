/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.bullet.Bullet;
import kylevedder.com.github.controlls.CustomMouseListener;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Kyle
 */
public class UserTankEntity extends TankEntity
{

    private final float TURN_RATE = 90f;
    private final float TURRET_TURN_RATE = 90f;
    private final float DRIVE_SPEED = 500f;//100f;
    private final float DRIVE_SPEED_MULTIPLIER = 2f;

    private final float ANIMATION_BASE_SPEED = 100f;    

    private float mouseX = 0;
    private float mouseY = 0;

    /**
     * User controlled tankUser entity.
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

        float prevAngle = this.hitBox.getAngle();
        this.updateDrive(input, delta);
        Object[] objects = MainApp.gameEngine.register.updateCollision(this.hitBox, this.vector, MainApp.NUM_COLLISION_UPDATES, delta);
        this.vector = (Vector) objects[1];
        this.hitBox = (CenteredRectangle) objects[0];

        this.updateTurret(input, delta);
        this.updateAnimation(Utils.wrapAngleDelta(this.hitBox.getAngle() - prevAngle), delta);

        this.updateShoot(input, delta);
        super.update(input, delta);
    }

    /**
     * Updates the tracks to animate correctly
     *
     * @param deltaAngle
     * @param deltaTime
     */
    private void updateAnimation(float deltaAngle, int deltaTime)
    {

        if (deltaAngle == 0 && this.vector.getSpeed() == 0)
        {
            this.leftTrack.freeze(deltaTime);
            this.rightTrack.freeze(deltaTime);
        }
        else if (this.vector.getSpeed() == 0f && deltaAngle != 0)
        {
            if (deltaAngle > 0)//clockwise
            {
                this.leftTrack.setDuration((int) ANIMATION_BASE_SPEED);
                this.rightTrack.setDuration(-(int) ANIMATION_BASE_SPEED);
            }
            else
            {
                this.leftTrack.setDuration(-(int) ANIMATION_BASE_SPEED);
                this.rightTrack.setDuration((int) ANIMATION_BASE_SPEED);
            }
            this.leftTrack.update(deltaTime);
            this.rightTrack.update(deltaTime);
        }
        else
        {
            float animationDivisor = (this.speedMuliplied) ? this.getDriveSpeedMultiplier() : 1;
            this.leftTrack.setDuration((int) (ANIMATION_BASE_SPEED / animationDivisor));
            this.rightTrack.setDuration((int) (ANIMATION_BASE_SPEED / animationDivisor));
            this.leftTrack.update(deltaTime);
            this.rightTrack.update(deltaTime);
        }
    }

    private void updateShoot(Input input, int delta)
    {
        if (input != null)
        {
            if (input.isKeyDown(Input.KEY_SPACE) || input.isMouseButtonDown(CustomMouseListener.BUTTON_LEFT))
            {
                this.fire();
            }            
        }
    }

    /**
     * Updates the turret angle
     *
     * @param input
     * @param delta
     */
    private void updateTurret(Input input, int delta)
    {
        this.mouseX = Utils.verifyFloat((input.getMouseX() + MainApp.gameEngine.camera.getRenderOffsetX()) / MainApp.gameEngine.camera.getZoom());
        this.mouseY = Utils.verifyFloat((input.getMouseY() + MainApp.gameEngine.camera.getRenderOffsetY()) / MainApp.gameEngine.camera.getZoom());
        float desiredTurretAngle = Utils.verifyFloat((float) Math.toDegrees(Math.atan2(mouseY - this.hitBox.getCenterY(), mouseX - this.hitBox.getCenterX())) + 90f - this.hitBox.getAngle());
        float turretRateCap = this.TURRET_TURN_RATE / (1000 / delta);
        float deltaTurret = Utils.wrapAngleDelta(desiredTurretAngle - this.turretAngle);
        //append and wrap the angle
        this.turretAngle = Utils.wrapAngle(this.turretAngle, Utils.clampFloat(deltaTurret, -turretRateCap, turretRateCap));
    }

    /**
     * Updates the driving of the tankUser via userinput
     *
     * @param input
     * @param delta
     */
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
            if (this.speedMuliplied = input.isKeyDown(Input.KEY_LSHIFT))
            {
                tankSpeed *= this.getDriveSpeedMultiplier();
            }

            //sets the vector's speed
            this.vector.setSpeed(tankSpeed);
            //adds to the vector's angle
            this.vector.addAngle(tankAngleAppend);
        }
    }

    @Override
    public void renderHelpers(Graphics g)
    {
        super.renderHelpers(g); //To change body of generated methods, choose Tools | Templates.        
        g.drawOval(mouseX - 8, mouseY - 8, 16, 16);
        g.drawOval(mouseX - 12, mouseY - 12, 24, 24);
    }

    /**
     * Gets the turn rate for the tankUser.
     *
     * @return
     */
    public float getTurnRate()
    {
        return TURN_RATE;
    }

    /**
     * Get the base drive speed for the tankUser.
     *
     * @return
     */
    public float getDriveSpeed()
    {
        return DRIVE_SPEED;
    }

    /**
     * Gets the turbo drive speed multiplier for the tankUser.
     *
     * @return
     */
    public float getDriveSpeedMultiplier()
    {
        return DRIVE_SPEED_MULTIPLIER;
    }

}
