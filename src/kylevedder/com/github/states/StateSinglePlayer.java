/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.states;

import kylevedder.com.github.controlls.SinglePlayerKeyListener;
import kylevedder.com.github.controlls.SinglePlayerMouseListener;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.gui.FontLoader;
import kylevedder.com.github.main.Camera;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.menu.InGameMenu;
import kylevedder.com.github.music.MusicPlayer;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.reference.Reference;
import kylevedder.com.github.teams.SinglePlayerMatchData;
import kylevedder.com.github.teams.SinglePlayerMatch;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class StateSinglePlayer implements BasicState
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
    public SinglePlayerMatch spMatch = null;
    public SinglePlayerMatchData match = null;

    public UserTankEntity tankUser = null;
    public TankEntity tankDummy = null;

    private MusicPlayer musicPlayer = null;
    private FontLoader fontLoader = null;

    public boolean paused = false;

    private InGameMenu menu = null;

    private SinglePlayerMouseListener singlePlayerMouseListener = null;
    private SinglePlayerKeyListener singlePlayerKeyListener = null;

    public StateSinglePlayer()
    {
    }

    @Override
    public void init(GameContainer gc, StateManager stateManager, MusicPlayer musicPlayer) throws SlickException
    {
        this.paused = false;
        menu = new InGameMenu(gc, stateManager, "Paused");
        this.fontLoader = new FontLoader(Reference.MAIN_FONT, 32f);
        this.musicPlayer = musicPlayer;
        this.musicPlayer.startGameMusic();
        camera = new Camera(PLAYER_START_X, PLAYER_START_Y, 1f, MainApp.gameEngine.screenManager);
        register = new ObjectRegister();

        tankUser = new UserTankEntity(PLAYER_START_X, PLAYER_START_Y, PLAYER_START_ANGLE, register, camera);
        register.add(tankUser);
        register.add(tankDummy);

        ground = new GroundHolder(WORLD_HEIGHT, WORLD_WIDTH);
        register.addGround(ground);

        match = new SinglePlayerMatchData("Team 1", "Team 2", tankUser, camera);
        match.addToYourTeam(tankUser);
        spMatch = new SinglePlayerMatch(TEAM_SIZE, match, tankUser, register);

        //setup mouse and keyboard
        singlePlayerMouseListener = new SinglePlayerMouseListener(camera);
        singlePlayerKeyListener = new SinglePlayerKeyListener(this);

        gc.getInput().addMouseListener(singlePlayerMouseListener);
        gc.getInput().addKeyListener(singlePlayerKeyListener);
    }

    public void togglePaused()
    {
        this.paused = !this.paused;
    }

    @Override
    public void update(GameContainer gc, int deltaTime) throws SlickException
    {
        if (!paused)
        {
            spMatch.update(gc.getInput(), deltaTime);
        }
        else
        {
            menu.update();
        }
        camera.update(tankUser.getHitBox());
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //clears
        g.clear();
        //backgrond
        g.setBackground(Color.black);
        g.translate(-camera.getRenderOffsetX(), -camera.getRenderOffsetY());
        g.scale(camera.getZoom(), camera.getZoom());
        ground.render(g, camera);
        spMatch.render(g);
        g.resetTransform();
        menu.render(g, paused);
    }

    @Override
    public void cleanup(GameContainer gc) throws SlickException
    {   
        if (singlePlayerMouseListener != null)
        {
            gc.getInput().removeMouseListener(singlePlayerMouseListener);
        }

        if (singlePlayerKeyListener != null)
        {
            gc.getInput().removeKeyListener(singlePlayerKeyListener);
        }
    }

}
