/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.physics.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/**
 *
 * @author Kyle
 */
public abstract class ObjectEntityBoilerplate extends ObjectBoilerplate
{
    protected boolean canCollide = true;
    protected Vector vector = null;
    
    public boolean canCollide()
    {
        return canCollide;
    }
    
    @Override
    public String toString()
    {
        return vector.toString();
    }
    
    abstract void renderBB(Graphics g, float renderOffsetX, float renderOffsetY);    
}
