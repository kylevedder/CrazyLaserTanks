/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.interfaces;

import kylevedder.com.github.physics.CenteredRectangle;

/**
 *
 * @author Kyle
 */
public abstract class BaseObject implements Renderable
{
    protected CenteredRectangle hitBox = null;    
    protected boolean collidable = true;

    /**
     * Checks if this object can be collided with.
     * @return 
     */
    public boolean isCollidable()
    {
        return collidable;
    }
    
    
}
