/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.controlls.TankMouseListener;
import kylevedder.com.github.main.Camera;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.ObjectRegister;
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

    private float mouseX = 0;
    private float mouseY = 0;
    
    private Camera camera = null;

    /**
     * User controlled tankUser entity.
     *
     * @param x
     * @param y
     * @param angle
     */
    public UserTankEntity(float x, float y, float angle, ObjectRegister register, Camera camera)
    {
        super(x, y, angle, register);
        this.camera = camera;
    }

    @Override
    public void update(Input input, int delta)
    {

        if (!this.isDestroyed())
        {
            float prevAngle = this.hitBox.getAngle();
            this.updateDrive(input, delta);
            Object[] objects = register.updateCollision(this.hitBox, this.vector, MainApp.NUM_COLLISION_UPDATES, delta);
            this.vector = (Vector) objects[1];
            this.hitBox = (CenteredRectangle) objects[0];

            this.updateTurret(input, delta);
            this.updateAnimation(Utils.wrapAngleDelta(this.hitBox.getAngle() - prevAngle), delta);

            this.updateShoot(input);
            
        }
        super.update(input, delta);
    }

    private void updateShoot(Input input)
    {
        if (input != null)
        {
            if (input.isKeyDown(Input.KEY_SPACE) || input.isMouseButtonDown(TankMouseListener.BUTTON_LEFT))
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
        this.mouseX = Utils.verifyFloat((input.getMouseX() + camera.getRenderOffsetX()) / camera.getZoom());
        this.mouseY = Utils.verifyFloat((input.getMouseY() +camera.getRenderOffsetY()) / camera.getZoom());
        float desiredTurretAngle = Utils.verifyFloat((float) Math.toDegrees(Math.atan2(mouseY - this.hitBox.getCenterY(), mouseX - this.hitBox.getCenterX())) + 90f - this.hitBox.getAngle());
        //append and wrap the angle
        this.addTurretAngle(desiredTurretAngle - this.turretAngle, delta);
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

}
