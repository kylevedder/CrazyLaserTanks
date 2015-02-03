/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class NonCollideGround extends BaseGround
{

    /**
     * Ground object that will allow collisions with itself.
     *
     * @param x - x position float
     * @param y - y position float
     */
    public NonCollideGround(float x, float y)
    {
        try
        {
            this.collidable = false;
            this.image = new Image("./kylevedder/com/github/resources/images/Ground.png");            
            //at the given X,Y and a scale set so that the image will always be this.GROUND_SIZE
            this.init(x, y, (float)BaseGround.GROUND_SIZE / (float)this.image.getWidth(), image);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(NonCollideGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Input input, int delta)
    {
        //ignored
    }
}
