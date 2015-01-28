/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import kylevedder.com.github.controlls.CustomMouseListener;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.teams.SinglePlayerMatch;
import kylevedder.com.github.teams.SinglePlayerMatchGenerator;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class GameEngine
{

    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;

    public static final int TILE_SIZE = 64;

    final float PLAYER_START_X = 500f;
    final float PLAYER_START_Y = 500f;
    final float PLAYER_START_ANGLE = 0f;

    private float tankAngleAppend = 0;
    private float tankSpeed = 0;

    public Camera camera = null;
    public ObjectRegister register = null;
    public GroundHolder ground = null;
    public SinglePlayerMatchGenerator spMatch = null;
    public SinglePlayerMatch match = null;

    public UserTankEntity tankUser = null;
    public TankEntity tankDummy = null;

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

        tankUser = new UserTankEntity(PLAYER_START_X, PLAYER_START_Y, PLAYER_START_ANGLE);
        register.add(tankUser);
        register.add(tankDummy);

        ground = new GroundHolder(WORLD_HEIGHT, WORLD_WIDTH);
        register.addGround(ground);

        camera = new Camera(tankUser.getHitBox(), 1f);
        match = new SinglePlayerMatch("Team 1", "Team 2");
        match.addToYourTeam(tankUser);
        spMatch = new SinglePlayerMatchGenerator(1, match, tankUser, register);

        gc.getInput().addMouseListener(new CustomMouseListener());

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

        spMatch.update(gc.getInput(), deltaTime);
//            tankUser.update(gc.getInput(), deltaTime);
//            tankDummy.update(gc.getInput(), deltaTime);            

        camera.update(tankUser.getHitBox(), gc.getInput());

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
        g.translate(-camera.getRenderOffsetX(), -camera.getRenderOffsetY());
        g.scale(MainApp.gameEngine.camera.getZoom(), MainApp.gameEngine.camera.getZoom());
        ground.render(g, camera.getRenderOffsetX(), camera.getRenderOffsetY());
//        tankDummy.render();
//        tankDummy.renderHelpers(g);
//        tankUser.render();
//        tankUser.renderHelpers(g);
//        match.render(g);
        spMatch.render(g);
    }
}
