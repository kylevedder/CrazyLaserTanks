/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.animation.CustomAnimation;
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

    private final float SCALE = 2f;

    private final float TURRET_Y_OFFSET = -5 * SCALE;

    private Image turret = null;
    private Image base = null;
    private CustomAnimation leftTrack = null;
    private CustomAnimation rightTrack = null;

    public TankEntity(float x, float y, float angle)
    {
        try
        {
//            Image image = new SpriteSheet(new Image("images/tank.png"), TILE_WIDTH, TILE_HEIGHT).getSprite(0, 0);
            base = new Image("images/StaticBase.png").getScaledCopy(SCALE);
            turret = new Image("images/Turret.png").getScaledCopy(SCALE);
            leftTrack = new CustomAnimation(new SpriteSheet(new Image("images/tracks.png").getScaledCopy(SCALE), (int) (TRACK_WIDTH * SCALE), (int) (TRACK_HEIGHT * SCALE)), 100);
            rightTrack = new CustomAnimation(new SpriteSheet(new Image("images/tracks.png").getScaledCopy(SCALE), (int) (TRACK_WIDTH * SCALE), (int) (TRACK_HEIGHT * SCALE)), 100);
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
    }

    
    @Override
    public void render()
    {
        //Predone math
        float sin = (float) Math.sin(Math.toRadians(this.hitBox.getAngle()));
        float cos = (float) Math.cos(Math.toRadians(this.hitBox.getAngle()));

        //draw right track
        Image tempImage = this.rightTrack.getFrame(false);
        tempImage.setRotation(this.hitBox.getAngle());
        tempImage.drawCentered(this.hitBox.getCenterX() + cos * (this.base.getWidth() / 2 + tempImage.getWidth() / 2), this.hitBox.getCenterY() + sin * (this.base.getWidth() / 2 + tempImage.getWidth() / 2));

        //draw left track
        tempImage = this.leftTrack.getFrame(false);
        tempImage.setRotation(this.hitBox.getAngle());
        tempImage.drawCentered(this.hitBox.getCenterX() - cos * (this.base.getWidth() / 2 + tempImage.getWidth() / 2), this.hitBox.getCenterY() - sin * (this.base.getWidth() / 2 + tempImage.getWidth() / 2));

        //draw base
        this.base.setRotation(this.hitBox.getAngle());
        this.base.drawCentered(this.hitBox.getCenterX(), this.hitBox.getCenterY());

        //draw turret
        this.turret.setCenterOfRotation(this.turret.getWidth()/2, this.turret.getHeight()/2 - TURRET_Y_OFFSET);
        this.turret.setRotation(this.hitBox.getAngle());
        this.turret.drawCentered(this.hitBox.getCenterX() , this.hitBox.getCenterY() + TURRET_Y_OFFSET);
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
    }

    /**
     * Renders helpers, such as hitbox, to aid visualization
     */
    public void renderHelpers(Graphics g)
    {
        g.draw(this.hitBox.getPolygon());
        this.vector.render(g, this.hitBox.getCenterX(), this.hitBox.getCenterY(), 30/*Speed Scale*/);
    }

}
