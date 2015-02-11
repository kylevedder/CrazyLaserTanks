/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.ground;

import kylevedder.com.github.pathfinding.PathFindingMap;
import java.util.ArrayList;
import kylevedder.com.github.main.Camera;
import kylevedder.com.github.utils.Utils;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 *
 * @author Kyle
 */
public class GroundHolder
{

    private int width;
    private int height;
    BaseGround[][] ground = null;
    private PathFindingMap pathFindingMap;

    public GroundHolder(int width, int height)
    {
        this.width = width;
        this.height = height;
        ground = new BaseGround[width][height];
        initGround();
        pathFindingMap = new PathFindingMap(this);
    }

    /**
     * Generates a new ground for the game to use.
     */
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
        for (int i = 0; i < 10; i++)
        {
            ground[20][i] = new CollideGround(20 * BaseGround.GROUND_SIZE, i * BaseGround.GROUND_SIZE);
        }

    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public PathFindingMap getPathFindingMap()
    {
        return pathFindingMap;
    }

    /**
     * Gets the base array for the ground
     *
     * @return
     */
    public BaseGround[][] getGroundArray()
    {
        return ground;
    }

    /**
     * Converts an entity X to a ground X
     *
     * @param x
     * @return
     */
    public int entityXtoGroundX(float x)
    {
        //x % BaseGround.GROUND_SIZE + BaseGround.GROUND_SIZE/2
        return Utils.clampInt((int) (x + BaseGround.GROUND_SIZE / 2) / BaseGround.GROUND_SIZE, 0, this.width - 1);
    }

    /**
     * Converts an entity Y to a ground Y
     *
     * @param y
     * @return
     */
    public int entityYtoGroundY(float y)
    {
        //
        return Utils.clampInt((int) (y + BaseGround.GROUND_SIZE / 2) / BaseGround.GROUND_SIZE, 0, this.height - 1);
    }

    public float groundYtoEntityY(int y)
    {
        return ground[0][y].getHitBox().getCenterY();
    }

    public float groundXtoEntityX(int x)
    {
        return ground[x][0].getHitBox().getCenterX();
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
    public void render(Graphics g, Camera camera)
    {
        for (int x = 0; x < this.width; x++)
        {
            for (int y = 0; y < this.width; y++)
            {
                if (Camera.isVisible(ground[x][y].getHitBox(), camera))
                {
                    ground[x][y].render();
                    ground[x][y].renderHelpers(g);
                }
            }
        }
    }

}
