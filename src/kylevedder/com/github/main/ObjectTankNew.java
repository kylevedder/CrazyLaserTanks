/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.util.Random;
import kylevedder.com.github.animation.CustomAnimation;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 *
 * @author Kyle
 */
public class ObjectTankNew extends ObjectEntityBoilerplate
{

    //variables
    private final float SCALE = 2;
    private final int TILE_WIDTH = 25;
    private final int TILE_HEIGHT = 30;
    private final int ANIMATION_DURATION = 100;
    private final float TURN_RATE = 2f;
    private final float DRIVE_SPEED = 15f;
    private final float DRIVE_SPEED_MULTIPLIER = 2f;

    //objects
    private CustomAnimation driveAnimation = null;
    private Random r = null;

    public ObjectTankNew(float posX, float posY, float angle)
    {
        System.out.println("X: " + posX + " Y: " + posY + " A: " + angle);
        this.vector = new Vector(0, angle, angle);//speed, angle, rotation
        System.out.println(this.vector);
        this.hitBox = new CenteredRectangle(posX, posY, TILE_WIDTH*SCALE, TILE_HEIGHT*SCALE, angle);        
        r = new Random(System.currentTimeMillis());
        SpriteSheet sheet = null;
        try
        {
            sheet = new SpriteSheet(new Image("images/tank.png"), TILE_WIDTH, TILE_HEIGHT);
            SpriteSheet sheetForward = new SpriteSheet(sheet.getSubImage(0, 0, TILE_WIDTH * 10, TILE_HEIGHT).getFlippedCopy(true, false), TILE_WIDTH, TILE_HEIGHT);
            driveAnimation = new CustomAnimation(sheetForward, ANIMATION_DURATION);
        }
        catch (SlickException ex)
        {
            System.err.println("Tank Sprite not found!");
        }

    }

    @Override
    void update(long delta)
    {
        this.update(delta, null);
    }

    public void update(long delta, Input input)
    {
        if (this.vector.getSpeed() == 0)
        {
            driveAnimation.freeze(delta);
        }
        else
        {
            driveAnimation.update(delta);
        }
        System.out.println(this.vector);
        updateDrive(input);
        this.hitBox.updateAbs(hitBox.getCenterX() + vector.getXComp(), hitBox.getCenterY() + vector.getYComp(), vector.getAngle());        
    }

    private void updateDrive(Input input)
    {
        if (input != null)
        {
            float tankAngleAppend = 0;
            float tankSpeed = 0;
            int duration = ANIMATION_DURATION;
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
                duration = (int) ((float) ANIMATION_DURATION / (this.getDriveSpeedMultiplier()));
            }

            this.driveAnimation.setDuration(duration);
            this.vector.setSpeed(tankSpeed);
            this.vector.addAngle(tankAngleAppend);
            this.vector.setRotation(this.vector.getAngle());
        }
    }

    @Override
    void render(float renderOffsetX, float renderOffsetY)
    {
        Image image = driveAnimation.getFrame(false).getScaledCopy(SCALE);
        image.rotate(this.vector.getAngle());        
        image.drawCentered(this.hitBox.getCenterX() - renderOffsetX, this.hitBox.getCenterY() - renderOffsetY);
    }

    @Override
    void renderBB(Graphics g, float renderOffsetX, float renderOffsetY)
    {        
        
        g.setColor(Color.red);
//        //draw centering "X"
//        g.drawLine(0, 0, MainApp.SCREEN_WIDTH, MainApp.SCREEN_HEIGHT);
//        g.drawLine(MainApp.SCREEN_WIDTH, 0, 0, MainApp.SCREEN_HEIGHT);
        //draw center circle        
        g.drawOval(this.hitBox.getCenterX() - renderOffsetX - 16, this.hitBox.getCenterY() - renderOffsetY - 16, 32, 32);

        g.setColor(Color.green);
        //draw shape
        Shape s = this.hitBox.getPolygon();
        g.draw(s.transform(Transform.createTranslateTransform(-renderOffsetX, -renderOffsetY)));
        
        g.setColor(Color.red);
        g.drawLine(
                this.hitBox.getCenterX() - renderOffsetX, 
                this.hitBox.getCenterY() - renderOffsetY, 
                this.hitBox.getCenterX() + 20 * (float) Math.sin(Math.toRadians(vector.getAngle())) - renderOffsetX, 
                this.hitBox.getCenterY() - 20 * (float) Math.cos(Math.toRadians(vector.getAngle())) - renderOffsetY);
        g.setColor(Color.black);
        g.drawLine(
                this.hitBox.getCenterX() - renderOffsetX, 
                this.hitBox.getCenterY() - renderOffsetY, 
                this.hitBox.getCenterX() + vector.getXComp() - renderOffsetX, 
                this.hitBox.getCenterY() - vector.getYComp() - renderOffsetY);        
    }

    /**
     * Gets the X coord of the image
     *
     * @return
     */
    public float getX()
    {
        return this.hitBox.getCenterX();
    }

    /**
     * Gets the Y coord of the image
     *
     * @return
     */
    public float getY()
    {
        return this.hitBox.getCenterY();
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
