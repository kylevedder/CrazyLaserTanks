/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.slickstates;

import kylevedder.com.github.controlls.SinglePlayerKeyListener;
import kylevedder.com.github.controlls.SinglePlayerMouseListener;
import kylevedder.com.github.entity.TankEntity;
import kylevedder.com.github.entity.UserTankEntity;
import kylevedder.com.github.ground.GroundHolder;
import kylevedder.com.github.gui.FontLoader;
import kylevedder.com.github.main.Camera;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.menu.InGameMenuNew;
import kylevedder.com.github.music.MusicPlayer;
import kylevedder.com.github.physics.ObjectRegister;
import kylevedder.com.github.reference.Reference;
import kylevedder.com.github.teams.SinglePlayerMatch;
import kylevedder.com.github.teams.SinglePlayerMatchData;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kyle
 */
public class SlickStateSinglePlayer extends BasicGameState
{

    public static final int ID = 1;

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


    public boolean paused = false;

    private InGameMenuNew menu = null;

    private SinglePlayerMouseListener singlePlayerMouseListener = null;
    private SinglePlayerKeyListener singlePlayerKeyListener = null;

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException
    {
        System.out.println("Init SinglePlayer");
        this.paused = false;
        menu = new InGameMenuNew(container, game, "Paused");        
        camera = new Camera(PLAYER_START_X, PLAYER_START_Y, 1f, MainApp.screenManager);
        register = new ObjectRegister();

        tankUser = new UserTankEntity(PLAYER_START_X, PLAYER_START_Y, PLAYER_START_ANGLE, register, camera);
        register.add(tankUser);

        ground = new GroundHolder(WORLD_HEIGHT, WORLD_WIDTH);
        register.addGround(ground);

        match = new SinglePlayerMatchData("Team 1", "Team 2", tankUser, camera);
        match.addToYourTeam(tankUser);
        spMatch = new SinglePlayerMatch(TEAM_SIZE, match, tankUser, register);

        //setup mouse and keyboard
        singlePlayerMouseListener = new SinglePlayerMouseListener(camera);
        singlePlayerKeyListener = new SinglePlayerKeyListener(this);

        container.getInput().addMouseListener(singlePlayerMouseListener);
        container.getInput().addKeyListener(singlePlayerKeyListener);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
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
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {
        MainApp.musicPlayer.playGameMusic();
        if (!paused)
        {
            spMatch.update(container.getInput(), delta);
        }
        else
        {
            menu.update();
        }
        MainApp.screenManager.update(container.getInput());
        camera.update(tankUser.getHitBox());
    }

    /**
     * Toggles the state of the game.
     */
    public void togglePaused()
    {
        this.paused = !this.paused;
    }

}
