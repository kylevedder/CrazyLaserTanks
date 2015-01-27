/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import kylevedder.com.github.main.MainApp;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.GroundHolder;
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

    public ObjectRegister register = null;
    public GroundHolder ground = null;

    public UserTankEntity tank = null;

    public GameEngine()
    {

    }

    /**
     * Sets up the game engine for use
     *
     * @param gc
     * @throws SlickException
     */
    public void init(GameContainer gc) throws SlickException
    {
        register = new ObjectRegister();
        tank = new UserTankEntity(PLAYER_START_X, PLAYER_START_Y, PLAYER_START_ANGLE);
        register.add(tank);
        ground = new GroundHolder(WORLD_HEIGHT, WORLD_WIDTH);
        register.addGround(ground);
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
        tank.update(gc.getInput(), deltaTime);
        renderOffsetX = tank.getCenterX() - (MainApp.SCREEN_WIDTH / 2);
        renderOffsetY = tank.getCenterY() - (MainApp.SCREEN_HEIGHT / 2);
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
        g.setBackground(new Color(103, 194, 240));
        g.translate(-renderOffsetX, -renderOffsetY);
        ground.render(g, renderOffsetX, renderOffsetY);
        tank.render();
        tank.renderHelpers(g);
    }
}
