/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.physics.CenteredRectangle;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class ObjectGroundDefault extends ObjectGroundBoilerplate
{

    private float scale = 8;

    private float width = 0;
    private float height = 0;

    public ObjectGroundDefault(int x, int y)
    {
        this.image = null;
        this.canCollide = false;
        try
        {
            this.image = new Image("images/Ground.png");
            width = this.image.getWidth();
            height = this.image.getHeight();
            this.scale = GameEngine.TILE_SIZE / width;
        }
        catch (SlickException ex)
        {
            Logger.getLogger(ObjectGroundDefault.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.hitBox = new CenteredRectangle(x * width * scale, y * height * scale, width * scale, height * scale, 0);
    }

    @Override
    void update(long delta)
    {

    }

    @Override
    void render(float renderOffsetX, float renderOffsetY)
    {
        if (Utils.isVisible(hitBox, image, scale, renderOffsetX, renderOffsetY))
        {
            image.draw(hitBox.getMinX() - renderOffsetX, hitBox.getMinY() - renderOffsetY, scale);
        }
    }

    @Override
    void renderBB(Graphics g, float renderOffsetX, float renderOffsetY)
    {
        g.setColor(Color.white);        
        g.drawRect(hitBox.getMinX() - renderOffsetX, hitBox.getMinY() - renderOffsetY, hitBox.getWidth(), hitBox.getHeight());
    }
}
