/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/**
 *
 * @author Kyle
 */
public abstract class ObjectGroundBoilerplate extends ObjectBoilerplate
{
    protected boolean canCollide = false;
    
    public boolean canCollide()
    {
        return canCollide;
    }
    
    abstract void renderBB(Graphics g, float renderOffsetX, float renderOffsetY);    
}
