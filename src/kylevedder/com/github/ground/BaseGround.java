/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import kylevedder.com.github.interfaces.BaseObject;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Kyle
 */
public abstract class BaseGround extends BaseObject
{

    public static final int GROUND_SIZE = 64;
    Image image = null;

    protected void init(float x, float y, float scale, Image image)
    {
        this.image = image.getScaledCopy(scale);
        this.hitBox = new CenteredRectangle(x, y, this.image.getWidth(), this.image.getHeight(), 0);
    }

    /**
     * Get the width of this object.
     *
     * @return
     */
    public float getWidth()
    {
        return this.image.getWidth();
    }

    /**
     * Get the width of this object.
     *
     * @return
     */
    public float getHeight()
    {
        return this.image.getHeight();
    }

    /**
     * Gets the scaled image of this object.
     *
     * @return
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * Gets the hitbox of the ground in the form of a CenteredRectangle
     *
     * @return
     */
    public CenteredRectangle getHitBox()
    {
        return this.hitBox;
    }

    @Override
    public void render()
    {
        this.image.drawCentered(this.hitBox.getCenterX(), this.hitBox.getCenterY());
    }

    public void renderHelpers(Graphics g)
    {
        g.drawRect(this.hitBox.getCenterX() - this.hitBox.getWidth() / 2, this.hitBox.getCenterY() - this.hitBox.getHeight() / 2, this.hitBox.getWidth(), this.hitBox.getHeight());
    }
}
