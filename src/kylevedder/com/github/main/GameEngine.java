/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.physics.ObjectRegister;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Kyle
 */
public class GameEngine
{

    public final int WORLD_WIDTH = 100;
    public final int WORLD_HEIGHT = 100;
    
    public static final int TILE_SIZE = 64;

    final float PLAYER_START_X = 500f;
    final float PLAYER_START_Y = 500f;
    final float PLAYER_START_ANGLE = 0f;

    public float renderOffsetX = PLAYER_START_X;
    public float renderOffsetY = PLAYER_START_Y;

    private float tankAngleAppend = 0;
    private float tankSpeed = 0;

    ObjectTankNew tank = null;
    
    public ObjectRegister register = null;    
    
    public ObjectGroundBoilerplate[][] tileArray = null;

    public GameEngine()
    {
        tileArray = new ObjectGroundBoilerplate[WORLD_WIDTH][WORLD_HEIGHT];
        register = new ObjectRegister();
    }

    /**
     * Sets up the game engine for use
     *
     * @param gc
     * @throws SlickException
     */
    public void init(GameContainer gc) throws SlickException
    {
        genGround();        
        tank = new ObjectTankNew(PLAYER_START_X, PLAYER_START_Y, PLAYER_START_ANGLE);
        System.out.println("Game Loaded...");
    }

    /**
     * Called every update cycle for updating movement
     *
     * @param gc
     * @param deltaTime
     * @throws SlickException
     */
    public void update(GameContainer gc, int deltaTime) throws SlickException
    {
        tank.update(deltaTime, gc.getInput());
        renderOffsetX = tank.getX() - (MainApp.SCREEN_WIDTH / 2);
        renderOffsetY = tank.getY() - (MainApp.SCREEN_HEIGHT / 2);
    }

    /**
     *
     * @param gc
     * @param g
     * @throws SlickException
     */
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //clears
        g.clear();
        //backgrond
        g.setColor(new Color(103, 194, 240));
        g.fillRect(0, 0, MainApp.SCREEN_WIDTH, MainApp.SCREEN_HEIGHT);

        //draws every object
        ObjectBoilerplate object;

        ObjectGroundBoilerplate grnd;
        for (int x = 0; x < WORLD_WIDTH; x++)
        {
            for (int y = 0; y < WORLD_HEIGHT; y++)
            {
                if ((grnd = tileArray[x][y]) != null)
                {
                    grnd.render(renderOffsetX, renderOffsetY);
                    grnd.renderBB(g, renderOffsetX, renderOffsetY);
                }
            }
        }
        tank.render(renderOffsetX, renderOffsetY);
        tank.renderBB(g, renderOffsetX, renderOffsetY);

    }

    private void genGround()
    {
        for (int x = 0; x < WORLD_WIDTH; x++)
        {
            for (int y = 0; y < WORLD_HEIGHT; y++)
            {
                if (x == 0 || x == WORLD_WIDTH - 1 || y == 0 || y == WORLD_HEIGHT - 1)
                {
                    tileArray[x][y] = new ObjectGroundBarrier(x, y);
                }
                else
                {
                    tileArray[x][y] = new ObjectGroundDefault(x, y);
                }
            }
        }
    }
}
