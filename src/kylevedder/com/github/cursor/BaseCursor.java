/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.cursor;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class BaseCursor
{
    Image cursorImage = null;
    Input input;

    float posX;
    float posY;
    /**
     * Base object for a cursor.
     * @throws SlickException 
     */
    public BaseCursor(Input input) throws SlickException
    {
        cursorImage = new Image("kylevedder/com/github/resources/images/crosshairs.png");
        this.input = input;
        update();
    }
    
    /**
     * Updates the cursor position
     */
    public void update()
    {
        posX = input.getMouseX();
        posY = input.getMouseY();
    }
    
    /**
     * Render the cursor
     */
    public void render()
    {
        cursorImage.drawCentered(posX, posY);
    }
    
    
}
