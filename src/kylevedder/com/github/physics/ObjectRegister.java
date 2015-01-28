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
import org.newdawn.slick.geom.Shape;

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
        if(o != null)
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
     * Checks to see if an object collides.
     * <p>
     * Note: This method has no way of checking if the passed in object collides
     * with itself; therefore, this function should only be called by unregistered
     * items.
     * </p>
     *
     * @param shape - Slick shape to collide with.
     * @param pointer - Pointer to the parent spawner to avoid collisions with.
     * @return - BaseEntity of the collision, or null if none found
     */
    public BaseEntity checkCollisionWithEntity(Shape shape, BaseEntity pointer)
    {        
        if (shape != null)
        {
            for (BaseEntity objectItem : objectsList)
            {
                if (objectItem != pointer && (objectItem.getHitBox().getPolygon()).contains(shape))
                {
                    return objectItem;
                }
            }
        }
        return null;
    }

    /**
     * Checks if object collides with the ground
     * @param shape
     * @return - boolean stating if collides
     */
    public boolean checkCollisionWithGround(Shape shape)
    {
        if (ground != null && shape != null)
        {
            ArrayList<BaseGround> tiles = ground.getCollidableGroundTiles(shape.getCenterX(), shape.getCenterY(), 4);
            for (BaseGround ground : tiles)
            {
                if ((ground.getHitBox().getPolygon()).contains(shape))
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

        //create vector which is to be passed back
        Vector finalVector = vector;
        //create tenativeHitBox which is to be passed back
        CenteredRectangle tentativeHitBox = new CenteredRectangle(hitBox);

        float tenHitX = tentativeHitBox.getCenterX();
        float tenHitY = tentativeHitBox.getCenterY();
        float tenAbsAngle = tentativeHitBox.getAngle();

        //sets the x comp to be a rate
        float vectX = finalVector.getXComp();//dist/sec
        //sets the y comp to be a rate
        float vectY = finalVector.getYComp();//dist/sec        
        //sets the delta of the angle to a rate
        float vectAngle = (finalVector.getAngle() - tenAbsAngle);//to keep up with the angle       

        //split the x component into subdivisions
        float vectXSubs = vectX / subdivisions;
        //split the y component into subdivisions
        float vectYSubs = vectY / subdivisions;
        //split the delta rotation into subdivisions
        float vectAngleSubs = vectAngle / subdivisions;

        //cursory collision check
        tentativeHitBox.updateAbs(tenHitX + (vectX), tenHitY - (vectY), tenAbsAngle + vectAngle);//updates the tentativeHitBox to be at its final position for this update (check if will collide essentially) 
        if (this.checkCollision(tentativeHitBox, hitBox))//use the updated tentativeHitBox to check if the tentativeHitBox will collide
        {
            //
            //THERE IS A COLLISION!!!
            //cursory collision check failed, must be hitting somewhere.
            //

            //because there is a collision, set the vector speed to zero
            finalVector.setSpeed(0);

            //loop that iterates over each subdivision, stepping backwards
            //each loop until there is no more collision
            subdivisionLoop:
            for (int i = 0; i <= subdivisions; i++)
            {
                //update tentativeHitBox to the new position to begin check
                tentativeHitBox.updateAbs(tenHitX + (vectX - vectXSubs * i), tenHitY - (vectY - vectYSubs * i), tenAbsAngle + vectAngle - (vectAngleSubs * i));

                //update finalVector to reflect tentativeHitBox angle
                finalVector.setAngle(tenAbsAngle + vectAngle - (vectAngleSubs * i));

                //check tentativeHitBox to see if there is no collision
                if (!this.checkCollision(tentativeHitBox, hitBox))
                {
                    //
                    //No collision with other boxes!!! Yay!
                    //

                    //Using the current tentativeHitBox coords as the new position
                    //return the object
                    return new Object[]
                    {
                        tentativeHitBox, finalVector
                    };
                }
            }
            //
            //If code arrives here, that means no subdivisions were appropriate
            //AS WELL AS the original position of the hitbox colliding. Bummer...
            //Best course of action is to leave the hitbox be.            
            //          
            //
            //If code arrives here, that means no subdivisions were appropriate
            //AS WELL AS the original position of the hitbox colliding. Bummer...
            //Best course of action is to leave the hitbox be.            
            //          
            //
            //If code arrives here, that means no subdivisions were appropriate
            //AS WELL AS the original position of the hitbox colliding. Bummer...
            //Best course of action is to leave the hitbox be.            
            //          
            //
            //If code arrives here, that means no subdivisions were appropriate
            //AS WELL AS the original position of the hitbox colliding. Bummer...
            //Best course of action is to leave the hitbox be.            
            //

            //The tentativeHitBox is at its initial position thanks to the aboveloop, therefore
            //the tentativeHitBox can be left as it lies and returned immeditly
            return new Object[]
            {
                tentativeHitBox, finalVector
            };
        }
        //no collision
        else
        {
            //
            //There is no collision, YAY!
            //Carry on adding the vectors as normal.
            //            

            //however, in the initial check, the hitbox was already at its final position.
            //Therefore, no update is needed            
            return new Object[]
            {
                tentativeHitBox, finalVector
            };
        }

    }
}
