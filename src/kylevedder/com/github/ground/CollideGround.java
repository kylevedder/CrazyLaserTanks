/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class CollideGround extends BaseGround
{

    public CollideGround(float x, float y, float scale)
    {
        try
        {
            this.image = new Image("images/NewGround.png");
            this.init(x, y, scale, image);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(CollideGround.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    
}
