/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import kylevedder.com.github.interfaces.BaseObject;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import org.newdawn.slick.Image;

/**
 *
 * @author Kyle
 */
public abstract class BaseEntity extends BaseObject
{

    protected Vector vector = null;
    protected Image image = null;
    protected float health = 10f;
    protected boolean destroyed = false;

    protected void init(float x, float y, float angle, Image image)
    {
        this.hitBox = new CenteredRectangle(x, y, image.getWidth(), image.getHeight(), angle);
        this.image = image;
        vector = new Vector(0, angle);
        health = 10f;
        destroyed = false;
    }

    /**
     * Checks to see if this entity is destroyed.
     * @return 
     */
    public boolean isDestroyed()
    {
        return destroyed;
    }   
    
    /**
     * Gets the X value of this object's center.
     *
     * @return
     */
    public float getCenterX()
    {
        return this.hitBox.getCenterX();
    }

    /**
     * Gets the Y value of this object's center.
     *
     * @return
     */
    public float getCenterY()
    {
        return this.hitBox.getCenterY();
    }

    /**
     * Gets the angle of this object.
     *
     * @return
     */
    public float getAngle()
    {
        return this.vector.getAngle();
    }

    /**
     * Gets the hitbox of this object
     *
     * @return
     */
    public CenteredRectangle getHitBox()
    {
        return hitBox;
    }

    /**
     * Adds damage to the entity, reducing its health
     *
     * @param damage
     */
    public void addDamage(float damage)
    {
        if(!this.destroyed)
        this.health -= damage;
    }

    /**
     * Gets the rotation of this object.
     *
     * @return
     */
    public float getRotation()
    {
        return this.hitBox.getAngle();
    }

    @Override
    public void render()
    {
        this.image.setRotation(this.hitBox.getAngle());
        this.image.drawCentered(this.hitBox.getCenterX(), this.hitBox.getCenterY());
    }

    @Override
    public String toString()
    {
        return this.vector.toString();
    }

}
