/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.states;

import kylevedder.com.github.gui.GUIRotatingLogo;
import kylevedder.com.github.menu.MainMenu;
import kylevedder.com.github.music.MusicPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class StateMainMenu implements BasicState
{

    private final int LOGO_NUM_FRAMES = 4;
    private final int LOGO_UPDATE_MILLIS = 2000;
    private final int LOGO_Y_POS = 100;
    
    private StateManager stateManager = null;
    private MusicPlayer musicPlayer = null;
    
    private MainMenu menu = null;
    private GUIRotatingLogo logo = null;
    
    @Override
    public void init(GameContainer gc, StateManager stateManager, MusicPlayer musicPlayer) throws SlickException
    {
        this.stateManager = stateManager;
        this.musicPlayer = musicPlayer;
                
        this.logo = new GUIRotatingLogo("images/title/full.png", "images/title/wire_frame.png", LOGO_NUM_FRAMES, LOGO_UPDATE_MILLIS, LOGO_Y_POS);
        this.menu = new MainMenu(gc, stateManager);        
        
        this.musicPlayer.startMenuMusic();
    }

    @Override
    public void update(GameContainer gc, int deltaTime) throws SlickException
    {        
        logo.update(deltaTime);
        menu.update();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {        
        menu.render(g, true);
        logo.render(gc);
    }

    @Override
    public void cleanup(GameContainer gc) throws SlickException
    {
        
    }
    
}
