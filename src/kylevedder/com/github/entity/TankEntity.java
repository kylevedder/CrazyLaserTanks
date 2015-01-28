/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.animation.CustomAnimation;
import kylevedder.com.github.bullet.Bullet;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kyle
 */
public class TankEntity extends BaseEntity
{

    private final int TRACK_WIDTH = 5;
    private final int TRACK_HEIGHT = 27;

    private final int SHOT_SPEED = 300;
    private final int SHOT_COOLDOWN = 500;//millis

    protected float turretAngle = 0;
    protected int shotCounter = 0;
    protected boolean readyToFire = true;
    protected boolean speedMuliplied = false;

    private final float[] DESTROYED_COLORS = new float[]
    {
        0.5f, 0.5f, 0.5f
    };

    protected ArrayList<Bullet> bullets = null;

    private final float SCALE = 2f;

    private final float TURRET_Y_OFFSET = -4.8f * SCALE;
    private final float TURRET_END_Y_OFFSET = 12f * SCALE - TURRET_Y_OFFSET;

    protected Image turret = null;
    protected Image base = null;
    protected CustomAnimation leftTrack = null;
    protected CustomAnimation rightTrack = null;

    public TankEntity(float x, float y, float angle)
    {
        try
        {
//            Image image = new SpriteSheet(new Image("images/tank.png"), TILE_WIDTH, TILE_HEIGHT).getSprite(0, 0);
            base = new Image("images/StaticBase.png").getScaledCopy(SCALE);
            turret = new Image("images/Turret.png").getScaledCopy(SCALE);
            leftTrack = new CustomAnimation(new SpriteSheet(new Image("images/tracks.png").getScaledCopy(SCALE).getFlippedCopy(false, true), (int) (TRACK_WIDTH * SCALE), (int) (TRACK_HEIGHT * SCALE)), 100);
            rightTrack = new CustomAnimation(new SpriteSheet(new Image("images/tracks.png").getScaledCopy(SCALE).getFlippedCopy(false, true), (int) (TRACK_WIDTH * SCALE), (int) (TRACK_HEIGHT * SCALE)), 100);
            this.init(x, y, angle, base);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(TankEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void init(float x, float y, float angle, Image image)
    {

        this.hitBox = new CenteredRectangle(x, y, base.getWidth() + leftTrack.getFrame(false).getWidth() * 2, leftTrack.getFrame(false).getHeight(), angle);
        this.image = this.base;
        vector = new Vector(0, angle);
        this.turretAngle = 0;
        this.speedMuliplied = false;
        this.shotCounter = 0;
        this.readyToFire = true;
        this.bullets = new ArrayList<>();
    }

    protected void fire()
    {
        if (this.readyToFire)
        {
            this.readyToFire = false;
            this.shotCounter = 0;
            float sin = (float) Math.sin(Math.toRadians(this.turretAngle + this.hitBox.getAngle()));
            float cos = (float) Math.cos(Math.toRadians(this.turretAngle + this.hitBox.getAngle()));
            float barrelX = this.hitBox.getCenterX() + sin * TURRET_END_Y_OFFSET;
            float barrelY = this.hitBox.getCenterY() - cos * TURRET_END_Y_OFFSET;
            this.bullets.add(new Bullet(barrelX, barrelY, SHOT_SPEED, this.turretAngle + this.hitBox.getAngle(), this));
        }
    }

    @Override
    public void render()
    {
        //Predone math
        float sin = (float) Math.sin(Math.toRadians(this.hitBox.getAngle()));
        float cos = (float) Math.cos(Math.toRadians(this.hitBox.getAngle()));

        //draw right track
        Image tempImage = this.rightTrack.getFrame(this.vector.getSpeed() < 0f);
        if (this.destroyed)
        {
            tempImage.setImageColor(DESTROYED_COLORS[0], DESTROYED_COLORS[1], DESTROYED_COLORS[2], 1f);
        }
        tempImage.setRotation(this.hitBox.getAngle());
        tempImage.drawCentered(this.hitBox.getCenterX() + cos * (this.base.getWidth() / 2 + tempImage.getWidth() / 2), this.hitBox.getCenterY() + sin * (this.base.getWidth() / 2 + tempImage.getWidth() / 2));

        //draw left track
        tempImage = this.leftTrack.getFrame(this.vector.getSpeed() < 0f);
        if (this.destroyed)
        {
            tempImage.setImageColor(DESTROYED_COLORS[0], DESTROYED_COLORS[1], DESTROYED_COLORS[2], 1f);
        }
        tempImage.setRotation(this.hitBox.getAngle());
        tempImage.drawCentered(this.hitBox.getCenterX() - cos * (this.base.getWidth() / 2 + tempImage.getWidth() / 2), this.hitBox.getCenterY() - sin * (this.base.getWidth() / 2 + tempImage.getWidth() / 2));

        //draw base
        tempImage = this.base;
        if (this.destroyed)
        {
            tempImage.setImageColor(DESTROYED_COLORS[0], DESTROYED_COLORS[1], DESTROYED_COLORS[2], 1f);
        }
        tempImage.setRotation(this.hitBox.getAngle());
        tempImage.drawCentered(this.hitBox.getCenterX(), this.hitBox.getCenterY());

        //draw turret
        tempImage = this.turret;
        if (this.destroyed)
        {
            tempImage.setImageColor(DESTROYED_COLORS[0], DESTROYED_COLORS[1], DESTROYED_COLORS[2], 1f);
        }
        tempImage.setCenterOfRotation(this.turret.getWidth() / 2, this.turret.getHeight() / 2 - TURRET_Y_OFFSET);
        tempImage.setRotation(this.hitBox.getAngle() + this.turretAngle);
        tempImage.drawCentered(this.hitBox.getCenterX(), this.hitBox.getCenterY() + TURRET_Y_OFFSET);

        //render all bullets
        for (Bullet b : bullets)
        {
            b.render();
        }
    }

    /**
     * Gets the hitbox for this object.
     *
     * @return
     */
    public CenteredRectangle getHitBox()
    {
        return this.hitBox;
    }

    @Override
    public void update(Input input, int delta)
    {
        if (!this.destroyed)
        {
            //count up if shot not ready
            if (!this.readyToFire)
            {
                this.shotCounter += delta;
            }

            //when ready, enable shoot
            if (this.shotCounter >= this.SHOT_COOLDOWN)
            {
                this.readyToFire = true;
                this.shotCounter = 0;
            }

            //remove dead bullets
            int i = 0;
            while (i < bullets.size())
            {
                Bullet b = bullets.get(i);
                if (!b.doesExist())
                {
                    bullets.remove(i);
                }
                else
                {
                    i++;
                }
            }

            //update each bullet
            for (Bullet b : bullets)
            {
                b.update(input, delta);
            }

            //check for destroy
            if (this.health <= 0)
            {
                this.destroyed = true;
            }
        }
    }

    /**
     * Renders helpers, such as hitbox, to aid visualization
     */
    public void renderHelpers(Graphics g)
    {
        g.draw(this.hitBox.getPolygon());
        this.vector.render(g, this.hitBox.getCenterX(), this.hitBox.getCenterY(), 30/*Speed Scale*/);
        g.drawString(String.valueOf(this.health), this.hitBox.getCenterX(), this.getCenterY() + 40);
    }

}
