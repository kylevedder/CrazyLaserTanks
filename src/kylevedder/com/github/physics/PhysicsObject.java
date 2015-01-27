/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.physics;

import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import org.newdawn.slick.Image;

/**
 *
 * @author Kyle
 */
public abstract class PhysicsObject
{    
    protected CenteredRectangle hitBox = null;
    protected Vector vector;                  

    public PhysicsObject()
    {
    }

    @Override
    public String toString()
    {
        return vector.toString();
    }
    
    
         
}