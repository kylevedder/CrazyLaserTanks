/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.gui;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

/**
 *
 * @author Kyle
 */
public class GUIRectangle
{
    private Rectangle rectangle = null;

    /**
     * Centered rectangle for creating buttons.
     * @param centerX
     * @param centerY
     * @param width
     * @param height 
     */
    public GUIRectangle(float centerX, float centerY, float width, float height)
    {
        rectangle = new Rectangle(centerX - width / 2, centerY - height / 2, width, height);        
    }

    /**
     * Gets the rectangle.
     * @return 
     */
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    
    /**
     * Shifts the center of the rectangle by the given amount.
     * @param shiftX
     * @param shiftY 
     */
    public void shiftCenter(float shiftX, float shiftY)
    {
        this.rectangle = new Rectangle(rectangle.getMinX() + shiftX, rectangle.getMinY() + shiftY, rectangle.getWidth(), rectangle.getHeight()); 
    }
    
    /**
     * Sets the center of the rectangle to the given amount.
     * @param setX
     * @param setY 
     */
    public void setCenter(float setX, float setY)
    {
        this.rectangle = new Rectangle(setX - rectangle.getWidth() / 2, setY - rectangle.getHeight() / 2, rectangle.getWidth(), rectangle.getHeight()); 
    }
    
    /**
     * Gets the centerX of the rectangle.
     * @return 
     */
    public float getCenterX()
    {
        return this.rectangle.getCenterX();
    }
    
    /**
     * Gets the centerY of the rectangle.
     * @return 
     */
    public float getCenterY()
    {
        return this.rectangle.getCenterY();
    }
}
