/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Kyle
 */
public class TiledGround extends BaseGround
{

    public TiledGround(int x, int y, TiledMap map)
    {
        this.image = map.getTileImage(x, y, 0);        
        this.collidable = map.getTileProperty(map.getTileId(x, y, 0), "collideable", "false").equals("true");
        this.pathable = map.getTileProperty(map.getTileId(x, y, 0), "pathable", "false").equals("true");
        this.init(x, y, (float)BaseGround.GROUND_SIZE / (float)this.image.getWidth(), this.image);
    }

    
    
    @Override
    public void update(Input input, int delta)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
