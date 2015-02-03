/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class CollideGround extends BaseGround
{

    /**
     * Ground object that will allow collisions with itself.
     *
     * @param x - x position float
     * @param y - y position float
     */
    public CollideGround(float x, float y)
    {
        try
        {
            this.collidable = true;
            this.image = new Image("./kylevedder/com/github/resources/images/NewGround.png");            
            //at the given X,Y and a scale set so that the image will always be this.GROUND_SIZE
            this.init(x, y, (float)BaseGround.GROUND_SIZE / (float)this.image.getWidth(), image);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(CollideGround.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Input input, int delta)
    {
        //ignored
    }
    
    public void renderBB(Graphics g)
    {
        g.draw(this.hitBox.getPolygon());
    }
}
