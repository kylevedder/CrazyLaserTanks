/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kylevedder.com.github.main;

import kylevedder.com.github.physics.CenteredRectangle;
import org.newdawn.slick.Image;

/**
 *
 * @author Kyle
 */
public abstract class ObjectBoilerplate
{

    protected Image image = null;
    protected CenteredRectangle hitBox = null;
    
    abstract void update(long delta);
    abstract void render(float renderOffsetX, float renderOffsetY);

    public ObjectBoilerplate()
    {               
    }

    public Image getImage()
    {
        return image;
    }
    
    public CenteredRectangle getRectangle()
    {
        return hitBox;
    }        
}
