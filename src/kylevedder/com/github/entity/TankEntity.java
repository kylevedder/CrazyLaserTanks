/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Kyle
 */
public class TankEntity extends BaseEntity
{

    private final int TILE_WIDTH = 25;
    private final int TILE_HEIGHT = 30;
    
    private final float SCALE = 2f;
    
    public TankEntity(float x, float y, float angle)
    {
        try
        {
            Image image = new SpriteSheet(new Image("images/tank.png"), TILE_WIDTH, TILE_HEIGHT).getSprite(0, 0);
            image = image.getScaledCopy(SCALE);
            this.init(x, y, angle, image);
        }
        catch (SlickException ex)
        {
            Logger.getLogger(TankEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @Override
    public void update(Input input, int delta)
    {
        
    }
    
    /**
     * Renders helpers, such as hitbox, to aid visualization
     */
    public void renderHelpers(Graphics g)
    {
        g.draw(this.hitBox.getPolygon());
        this.vector.render(g, this.hitBox.getCenterX(), this.hitBox.getCenterY());
    }
    
}
