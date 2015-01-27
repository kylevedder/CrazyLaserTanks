/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import kylevedder.com.github.interfaces.BaseObject;
import kylevedder.com.github.physics.CenteredRectangle;
import org.newdawn.slick.Image;

/**
 *
 * @author Kyle
 */
public class BaseGround extends BaseObject
{

    Image image = null;
    
    protected void init(float x, float y, float scale, Image image)
    {
        this.image = image.getScaledCopy(scale);                
        this.hitBox = new CenteredRectangle(x, y, this.image.getWidth(), this.image.getHeight()* scale, 0);
    }
    
    /**
     * Get the width of this object. 
     * @return 
     */
    public float getWidth()
    {
        return this.image.getWidth();
    }
    
    /**
     * Get the width of this object. 
     * @return 
     */
    public float getHeight()
    {
        return this.image.getHeight();
    }
    
    
    @Override
    public void update()
    {
        //does nothing.
    }

    /**
     * Gets the scaled image of this object.
     * @return 
     */
    public Image getImage()
    {
        return image;
    }
    
    

    @Override
    public void render()
    {
        this.image.drawCentered(this.hitBox.getCenterX(), this.hitBox.getCenterY());
    }
}
