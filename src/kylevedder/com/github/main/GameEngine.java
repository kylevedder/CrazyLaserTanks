/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.main;

import java.util.HashMap;
import kylevedder.com.github.controlls.CustomMouseListener;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.music.MusicPlayer;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.states.BasicState;
import kylevedder.com.github.states.State;
import kylevedder.com.github.states.StateExitMenu;
import kylevedder.com.github.states.StateMainMenu;
import kylevedder.com.github.states.StateManager;
import kylevedder.com.github.states.StateSinglePlayer;
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

    public static final int WORLD_WIDTH = 40;
    public static final int WORLD_HEIGHT = 40;

    public static final int TILE_SIZE = 64;

    final float PLAYER_START_X = 500f;
    final float PLAYER_START_Y = 500f;
    final float PLAYER_START_ANGLE = 0f;
    
    final int TEAM_SIZE = 4;

    private float tankAngleAppend = 0;
    private float tankSpeed = 0;

    public Camera camera = null;
    public ObjectRegister register = null;
    public GroundHolder ground = null;
    public SinglePlayerMatchGenerator spMatch = null;
    public SinglePlayerMatch match = null;

    public UserTankEntity tankUser = null;
    public TankEntity tankDummy = null;
    
    
    public StateManager stateManager = null;
    
    public StateMainMenu mainMenu = null;
    public StateExitMenu exitMenu = null;
    public StateSinglePlayer singlePlayer = null;
    public MusicPlayer musicPlayer = null;
    public ScreenManager screenManager = null;

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
        screenManager = new ScreenManager(gc, false);
        
        musicPlayer = new MusicPlayer("music/Ouroboros.ogg", "music/Club_Diver.ogg");        
        mainMenu = new StateMainMenu();
        exitMenu = new StateExitMenu();
        singlePlayer = new StateSinglePlayer();
        HashMap<State, BasicState> map = new HashMap<State, BasicState>();
        map.put(State.MENU, mainMenu);
        map.put(State.EXIT, exitMenu);
        map.put(State.SINGLE_PLAYER, singlePlayer);
        stateManager = new StateManager(State.MENU, gc, map, musicPlayer);                

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
        screenManager.update(gc.getInput());
        stateManager.updateCurrent(gc, deltaTime);
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
        g.setBackground(Color.black);
        stateManager.renderCurrent(gc, g);
    }
}
