/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.states;

import kylevedder.com.github.gui.FontLoader;
import kylevedder.com.github.gui.GUIButton;
import kylevedder.com.github.gui.GUIMouseOverContent;
import kylevedder.com.github.main.GameEngine;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.music.MusicPlayer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class StateMainMenu implements BasicState
{

    FontLoader fontLoader = null;
    GUIButton button;
    private StateManager stateManager = null;
    private MusicPlayer musicPlayer = null;

    public void init(GameContainer gc, StateManager stateManager, MusicPlayer musicPlayer) throws SlickException
    {        
        this.musicPlayer = musicPlayer;        
        this.musicPlayer.startMenuMusic();
        this.stateManager = stateManager;
        fontLoader = new FontLoader("font/youre-gone/YoureGone.ttf", 128f);
        fontLoader.setColor(java.awt.Color.red);

        GUIMouseOverContent content = new GUIMouseOverContent("Hello", "World", "images/buttons/normal.png", "images/buttons/hover.png", "images/buttons/hover.png");

        button = new GUIButton(gc, MainApp.gameEngine.screenManager.getCurrentResWidth() / 2, MainApp.gameEngine.screenManager.getCurrentResHeight()/ 2, content.getBaseImage().getWidth(), content.getBaseImage().getHeight(), content);
        button.setFont(fontLoader.getSizedFont(32f), fontLoader.getSizedFont(32f));
        button.setFontColors(java.awt.Color.magenta, java.awt.Color.magenta);
        button.setTextPadding(0, -5);
    }

    public void update(GameContainer gc, int deltaTime) throws SlickException
    {
        if (button.isButtonClicked())
        {
            button.resetButtonClicked();
            stateManager.setState(State.EXIT);
        }
    }

    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //clears
        g.clear();
        //backgrond
        g.setBackground(Color.black);
        g.setColor(Color.red);
        button.render(g);
    }    
}
