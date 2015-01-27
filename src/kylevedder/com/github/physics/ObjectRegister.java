/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.physics;

import java.util.ArrayList;
import kylevedder.com.github.physics.CenteredRectangle;

/**
 *
 * @author Kyle
 */
public class ObjectRegister
{

    ArrayList<PhysicsObject> objectsList = null;

    /**
     * Object Physics Registration.
     */
    public ObjectRegister()
    {
        objectsList = new ArrayList<>();
    }

    /**
     * Adds an object to the PhysicsObject register.
     *
     * @param o
     */
    public void add(PhysicsObject o)
    {
        this.objectsList.add(o);
    }

    /**
     * Checks to see if an object collides.
     *
     * @param objectChecking
     * @return
     */
    public boolean checkCollision(PhysicsObject objectChecking)
    {
        for (PhysicsObject objectItem : objectsList)
        {
            if (objectChecking != objectItem && objectChecking.hitBox.collides(objectItem.hitBox))
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
        for (PhysicsObject objectItem : objectsList)
        {
//            System.out.println(objectItem);
            if (pointer != objectItem.hitBox && rect.collides(objectItem.hitBox))
            {
                return true;
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

        float vectX = finalVector.getXComp()/(1000/delta);//dist/sec
        float vectY = finalVector.getYComp()/(1000/delta);//dist/sec
        float vectRotation = finalVector.getRotation()/(1000/delta);//deg/sec

        float vectXSubs = vectX / subdivisions;
        float vectYSubs = vectY / subdivisions;
        float vectRotationSubs = vectRotation / subdivisions;

        //cursory collision check
        tentativeHitBox.updateAbs(tenHitX + (vectX), tenHitY + (vectY), tenAngle + (vectRotation));
        if (this.checkCollision(tentativeHitBox, hitBox))
        {
            //cursory collision check failed, must be hitting somewhere.
            subdivisionLoop:
            for (int i = 0; i < subdivisions; i++)
            {
                //update using the given vector
                tentativeHitBox.updateAbs(tenHitX + (vectX - vectXSubs * i), tenHitY + (vectY - vectYSubs * i), tenAngle + (vectRotation - vectRotationSubs * i));

                //check the tenative
//            System.out.println(Main.register.checkCollision(tentativeHitBox, this.hitBox));
                if (!this.checkCollision(tentativeHitBox, hitBox))
                {
                    //if no collision with other boxes
                    hitBox.updateAbs(tenHitX + (vectX - vectXSubs * i), tenHitY + (vectY - vectYSubs * i), tenAngle + (vectRotation - vectRotationSubs*i));
//                    finalVector = Vector.add(Vector.gravityVector(delta), vector);
//                    finalVector = Vector.flipAxis(finalVector, false, true);
                    finalVector.setRotation(0);
                    finalVector.setSpeed(0);
                    return new Object[]
                    {
                        hitBox, finalVector
                    };
                }
            }
            //not one of the subdivisions, reverting to last position
            hitBox.updateAbs(tenHitX , tenHitY, tenAngle);
//            finalVector = Vector.add(Vector.gravityVector(delta), vector);
//            finalVector = Vector.flipAxis(finalVector, false, true);
            finalVector.setRotation(0);
            finalVector.setSpeed(0);
            return new Object[]
            {
                hitBox, finalVector
            };
        }
        //no collision
        else
        {
            hitBox.updateDelta(vectX, vectY, vectRotation);
//            finalVector = Vector.add(Vector.gravityVector(delta), vector);
            return new Object[]
            {
                hitBox, finalVector
            };
        }

    }
}
