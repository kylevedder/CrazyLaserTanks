/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.physics;

import java.util.ArrayList;
import kylevedder.com.github.entity.BaseEntity;
import kylevedder.com.github.ground.BaseGround;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.physics.CenteredRectangle;

/**
 *
 * @author Kyle
 */
public class ObjectRegister
{

    ArrayList<BaseEntity> objectsList = null;
    GroundHolder ground = null;

    /**
     * Object Physics Registration.
     */
    public ObjectRegister()
    {
        objectsList = new ArrayList<>();
    }

    /**
     * Adds an object to the BaseEntity register.
     *
     * @param o
     */
    public void add(BaseEntity o)
    {
        this.objectsList.add(o);
    }

    /**
     * Adds a ground to the register.
     *
     * @param ground
     */
    public void addGround(GroundHolder ground)
    {
        this.ground = ground;
    }

    /**
     * Checks to see if an object collides.
     *
     * @param objectChecking
     * @return
     */
    public boolean checkCollision(BaseEntity objectChecking)
    {
        for (BaseEntity objectItem : objectsList)
        {
            if (objectChecking != objectItem && objectChecking.getHitBox().collides(objectItem.getHitBox()))
            {
                return true;
            }
        }
        ArrayList<BaseGround> tiles = ground.getCollidableGroundTiles(objectChecking.getCenterX(), objectChecking.getCenterY(), 4);
        for (BaseGround ground : tiles)
        {
            if (objectChecking.getHitBox().collides(ground.getHitBox()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if an object collides.
     * <p>
     * Takes the rectangle to check for coords and an object so that the rect
     * does not check itself
     * </p>
     *
     * @param objectChecking - rectangle to check against others.
     * @param pointer - pointer to the original hitbox.
     * @return if rect collides with others.
     */
    public boolean checkCollision(CenteredRectangle rect, Object pointer)
    {
        if (ground != null && rect != null)
        {
            for (BaseEntity objectItem : objectsList)
            {
//            System.out.println(objectItem);
                if (pointer != objectItem.getHitBox() && rect.collides(objectItem.getHitBox()))
                {
                    return true;
                }
            }
            ArrayList<BaseGround> tiles = ground.getCollidableGroundTiles(rect.getCenterX(), rect.getCenterY(), 4);
            for (BaseGround ground : tiles)
            {
                if (rect.collides(ground.getHitBox()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the rectangle position.
     *
     * @param tentativeHitBox
     * @param vector
     * @param delta
     *
     * @return <b>Object array containing:</b>
     * <ul><li>Slot 0 - hitBox</li>
     * <p>
     * <li>Slot 1 - vector</li></ul>
     */
    public Object[] updateCollision(CenteredRectangle hitBox, Vector vector, int subdivisions, int delta)
    {

        Vector finalVector = vector;
        //create tenativeHitBox
        CenteredRectangle tentativeHitBox = new CenteredRectangle(hitBox);

        float tenHitX = tentativeHitBox.getCenterX();
        float tenHitY = tentativeHitBox.getCenterY();
        float tenAngle = tentativeHitBox.getAngle();
        float tenRotation = finalVector.getAbsRotation();

        float vectX = finalVector.getXComp() / (1000 / delta);//dist/sec
        float vectY = finalVector.getYComp() / (1000 / delta);//dist/sec
        float vectAbsRotation = finalVector.getAbsRotation() / (1000 / delta);//deg/sec

        float vectXSubs = vectX / subdivisions;
        float vectYSubs = vectY / subdivisions;
        float vectRotationSubs = vectAbsRotation / subdivisions;

        //cursory collision check
//        updateAbs(this.hitBox.getCenterX() + this.vector.getXComp(), this.hitBox.getCenterY() - this.vector.getYComp(), this.vector.getRotation())
        tentativeHitBox.updateAbs(tenHitX + (vectX), tenHitY - (vectY), tenAngle);
        if (this.checkCollision(tentativeHitBox, hitBox))
        {
            System.out.println("COLLIDE!");
            //cursory collision check failed, must be hitting somewhere.
            subdivisionLoop:
            for (int i = 0; i < subdivisions; i++)
            {
                //update using the given vector
                tentativeHitBox.updateAbs(tenHitX + (vectX - vectXSubs * i), tenHitY - (vectY - vectYSubs * i), tenAngle);

                //check the tenative
//            System.out.println(Main.register.checkCollision(tentativeHitBox, this.hitBox));
                if (!this.checkCollision(tentativeHitBox, hitBox))
                {
                    //if no collision with other boxes
                    hitBox.updateAbs(tenHitX + (vectX - vectXSubs * i), tenHitY - (vectY - vectYSubs * i), tenAngle);
//                    finalVector = Vector.add(Vector.gravityVector(delta), vector);
//                    finalVector = Vector.flipAxis(finalVector, false, true);
                    finalVector.setSpeed(0);
                    System.out.println("Collision resolved");
                    return new Object[]
                    {
                        hitBox, finalVector
                    };
                }
            }
            //not one of the subdivisions, reverting to last position
            hitBox.updateAbs(tenHitX, tenHitY, tenAngle);
//            finalVector = Vector.add(Vector.gravityVector(delta), vector);
//            finalVector = Vector.flipAxis(finalVector, false, true);            
            finalVector.setSpeed(0);
            System.out.println("collision unresolved");
            return new Object[]
            {
                hitBox, finalVector
            };
        }
        //no collision
        else
        {
            System.out.println("CLEAR " + vectAbsRotation);
            hitBox.updateAbs(tenHitX + vectX, tenHitY-vectY, finalVector.getAbsRotation());
//            finalVector = Vector.add(Vector.gravityVector(delta), vector);
            return new Object[]
            {
                hitBox, finalVector
            };
        }

    }
}
