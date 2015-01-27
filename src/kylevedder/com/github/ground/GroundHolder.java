/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import java.util.ArrayList;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Kyle
 */
public class GroundHolder
{

    private int width;
    private int height;
    BaseGround[][] ground = null;

    public GroundHolder(int width, int height)
    {
        this.width = width;
        this.height = height;
        ground = new BaseGround[width][height];
        initGround();
    }

    private void initGround()
    {
        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.width; y++)
            {
                if (x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1)
                {
                    ground[x][y] = new CollideGround(x * BaseGround.GROUND_SIZE, y * BaseGround.GROUND_SIZE);

                }
                else
                {
                    ground[x][y] = new NonCollideGround(x * BaseGround.GROUND_SIZE, y * BaseGround.GROUND_SIZE);
                }
            }
        }
    }

    /**
     * Converts a given float x and y into ground tile coords. Then takes all
     * the tiles that can be collided with in the given radius and returns those
     * tiles.
     *
     * @param x
     * @param y
     * @param radius
     * @return
     */
    public ArrayList<BaseGround> getCollidableGroundTiles(float x, float y, int radius)
    {
        ArrayList<BaseGround> pieces = new ArrayList<>();
        if (ground != null)
        {
            int tileX = Utils.clampInt((int) (x - x % BaseGround.GROUND_SIZE) / BaseGround.GROUND_SIZE, 0, this.width - 1);
            int tileY = Utils.clampInt((int) (y - y % BaseGround.GROUND_SIZE) / BaseGround.GROUND_SIZE, 0, this.height - 1);
            for (int getx = Utils.clampInt(tileX - radius, 0, this.width - 1); getx <= Utils.clampInt(tileX + radius, 0, this.width - 1); getx++)
            {
                for (int gety = Utils.clampInt(tileY - radius, 0, this.height - 1); gety <= Utils.clampInt(tileY + radius, 0, this.width - 1); gety++)
                {
                    if (this.ground[getx][gety].isCollidable())
                    {
                        pieces.add(this.ground[getx][gety]);
                    }
                }
            }
        }
        return pieces;
    }

    /**
     * Renders all the appropriate ground tiles.
     */
    public void render(Graphics g, float renderOffsetX, float renderOffsetY)
    {
        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.width; y++)
            {
                if (Utils.isVisible(ground[x][y].getHitBox(), renderOffsetX, renderOffsetY))
                {
                    ground[x][y].render();
                }
            }
        }
    }

}
