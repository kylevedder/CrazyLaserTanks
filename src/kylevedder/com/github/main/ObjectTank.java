/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.util.Random;
import kylevedder.com.github.animation.CustomAnimation;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.PhysicsObject;
import kylevedder.com.github.physics.Vector;
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
public class ObjectTank extends ObjectEntityBoilerplate
{
    

    private CustomAnimation driveAnimation = null;
    
    private CenteredRectangle hitBox = null;
    private Vector vector = null;

    private final float SCALE = 2;
    private final int TILE_WIDTH = 25;
    private final int TILE_HEIGHT = 30;
    private final int ANIMATION_DURATION = 100;
    private final float TURN_RATE = 2f;
    private final float DRIVE_SPEED = 15f;
    private final float DRIVE_SPEED_MULTIPLIER = 2f;

    private Random r = null;

    public ObjectTank(float posX, float posY, float angle)
    {
        this.vector = new Vector(0, angle);
        this.hitBox = new CenteredRectangle(posX, posY, TILE_WIDTH, TILE_HEIGHT, angle);
        r = new Random(System.currentTimeMillis());
        hitBox = new CenteredRectangle(posX, posY, TILE_WIDTH * SCALE, TILE_HEIGHT * SCALE, 0);
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
        updateVector(input);
        if (this.vector.getSpeed() == 0)
        {
            driveAnimation.freeze(delta);
        }
        else
        {
            driveAnimation.update(delta);
        }
        
        Object[] objects = MainApp.gameEngine.register.updateCollision(this.hitBox, vector, MainApp.NUM_COLLISION_UPDATES, (int)delta);
        this.hitBox = (CenteredRectangle) objects[0];
        this.vector = (Vector) objects[1];        
        System.out.println(this.vector);
        
//        float oldX = this.hitBox.getCenterX();
//        float oldY = this.hitBox.getCenterY();
//        float oldAngle = this.hitBox.getAngle();
////        this.hitBox.setCenterX(this.hitBox.getCenterX() + driveForwardX(speed, angle));
////        this.hitBox.setCenterY(this.hitBox.getCenterY() + driveForwardY(speed, angle));
////        this.hitBox.setAngle(angle);
//        this.hitBox.
////        System.out.println("Colliding: " + MainApp.gameEngine.physics.isColliding(newRect));
//        if (MainApp.gameEngine.physics.isColliding(this.hitBox))
//        {
//            this.hitBox.setCenterX(oldX);
//            this.hitBox.setCenterY(oldY);
//            this.hitBox.setAngle(angle);
//        }
    }

    @Override
    void render(float renderOffsetX, float renderOffsetY)
    {
        //gets the correct frame, reverses playback accordingly
        Image image = driveAnimation.getFrame(this.vector.getSpeed() < 0);
        image.setCenterOfRotation((((float) image.getWidth()) * SCALE / 2), (((float) image.getHeight()) * SCALE / 2));
        image.setRotation(vector.getAngle());
        image.draw(this.hitBox.getCenterX() - renderOffsetX - (image.getWidth() * SCALE / 2), this.hitBox.getCenterY() - renderOffsetY - (image.getHeight() * SCALE / 2), SCALE);
    }

    public void renderBoundingBox(Graphics g, float renderOffsetX, float renderOffsetY)
    {
        g.setColor(Color.red);
        //draw centering "X"
        g.drawLine(0, 0, MainApp.SCREEN_WIDTH, MainApp.SCREEN_HEIGHT);
        g.drawLine(MainApp.SCREEN_WIDTH, 0, 0, MainApp.SCREEN_HEIGHT);
        //draw center circle        
        g.drawOval(this.hitBox.getCenterX() - renderOffsetX - 16, this.hitBox.getCenterY() - renderOffsetY - 16, 32, 32);

        g.setColor(Color.green);
        //draw shape
        Shape s = this.hitBox.getPolygon();
        g.draw(s.transform(Transform.createTranslateTransform(-renderOffsetX, -renderOffsetY)));
        
        g.setColor(Color.red);
        g.drawLine(this.hitBox.getCenterX() - renderOffsetX, this.hitBox.getCenterY() - renderOffsetY, this.hitBox.getCenterX() + vector.getXComp() - renderOffsetX, this.hitBox.getCenterY() + vector.getYComp() - renderOffsetY);
        g.setColor(Color.red);
        g.drawLine(this.hitBox.getCenterX() - renderOffsetX, this.hitBox.getCenterY() - renderOffsetY, this.hitBox.getCenterX() + 20*(float)Math.cos(Math.toRadians(vector.getAngle())) - renderOffsetX, this.hitBox.getCenterY() + 20*(float)Math.sin(Math.toRadians(vector.getAngle())) - renderOffsetY);
    }
    
    @Override
    void renderBB(Graphics g, float renderOffsetX, float renderOffsetY)
    {
        renderBoundingBox(g, renderOffsetX, renderOffsetY);
    }

    /**
     * Updates the tank's vector based on User Input
     * @param input 
     */
    private void updateVector(Input input)
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
            this.vector.setAngle(wrapAngle(this.vector.getAngle(), tankAngleAppend));
        }
    }

    /**
     * Takes the append angle value and appends it to the angle.
     *
     * Wraps to [0 - 360]
     *
     * @param angle
     * @param appendValue
     * @return
     */
    private float wrapAngle(float angle, float appendValue)
    {
        return ((angle + appendValue) % 360f);
    }

    /**
     * Updates the X based upon dist to travel forward and angle
     *
     * @param dist
     * @param angle
     * @return
     */
    private float driveForwardX(float dist, float angle)
    {
        return (float) Math.sin(Math.toRadians(angle)) * dist;
    }

    /**
     * Updates the Y based upon dist to travel forward and angle
     *
     * @param dist
     * @param angle
     * @return
     */
    private float driveForwardY(float dist, float angle)
    {
        return -(float) Math.cos(Math.toRadians(angle)) * dist;
    }

    public float getPosX()
    {
        return this.hitBox.getCenterX();
    }

    public float getPosY()
    {
        return this.hitBox.getCenterY();
    }

    public float getTurnRate()
    {
        return TURN_RATE;
    }

    public float getDriveSpeed()
    {
        return DRIVE_SPEED;
    }

    public float getDriveSpeedMultiplier()
    {
        return DRIVE_SPEED_MULTIPLIER;
    }    

}
