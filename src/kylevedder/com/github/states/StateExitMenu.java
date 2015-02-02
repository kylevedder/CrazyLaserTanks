/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.states;

import kylevedder.com.github.gui.FontLoader;
import kylevedder.com.github.gui.GUIButton;
import kylevedder.com.github.gui.GUIButtonContent;
import kylevedder.com.github.main.GameEngine;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.music.MusicPlayer;
import kylevedder.com.github.reference.Reference;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class StateExitMenu implements BasicState
{

    FontLoader fontLoader = null;
    GUIButton exitButton;
    GUIButton mainMenuButton;
    private StateManager stateManager = null;
    private MusicPlayer musicPlayer = null;

    private final float BUTTON_SEPERATOR_WIDTH = 10f;

    public void init(GameContainer gc, StateManager stateManager, MusicPlayer musicPlayer)
    {
        this.stateManager = stateManager;
        this.musicPlayer = musicPlayer;        
        this.musicPlayer.startGameMusic();
        
        fontLoader = new FontLoader(Reference.MAIN_FONT, 128f);        

        GUIButtonContent exitContent = new GUIButtonContent("Exit", "Exit", "images/buttons/normal.png", "images/buttons/hover.png", "images/buttons/hover.png");
        GUIButtonContent mainMenuContent = new GUIButtonContent("Main Menu", "Main Menu", "images/buttons/normal.png", "images/buttons/hover.png", "images/buttons/hover.png");

        exitButton = new GUIButton(gc, MainApp.gameEngine.screenManager.getCurrentResWidth() / 2 - exitContent.getBaseImage().getWidth() / 2 - BUTTON_SEPERATOR_WIDTH / 2, MainApp.gameEngine.screenManager.getCurrentResHeight()/ 2, exitContent.getBaseImage().getWidth(), exitContent.getBaseImage().getHeight(), exitContent);
        exitButton.setFont(fontLoader.getSizedFont(32f), fontLoader.getSizedFont(32f));
        exitButton.setFontColors(java.awt.Color.magenta, java.awt.Color.magenta);
        exitButton.setTextOffset(0, -5);

        mainMenuButton = new GUIButton(gc, MainApp.gameEngine.screenManager.getCurrentResWidth() / 2 + mainMenuContent.getBaseImage().getWidth() / 2 + BUTTON_SEPERATOR_WIDTH / 2, MainApp.gameEngine.screenManager.getCurrentResHeight() / 2, mainMenuContent.getBaseImage().getWidth(), mainMenuContent.getBaseImage().getHeight(), mainMenuContent);
        mainMenuButton.setFont(fontLoader.getSizedFont(32f), fontLoader.getSizedFont(32f));
        mainMenuButton.setFontColors(java.awt.Color.magenta, java.awt.Color.magenta);
        mainMenuButton.setTextOffset(0, -5);
    }

    public void update(GameContainer gc, int deltaTime) throws SlickException
    {
        if (exitButton.isButtonClicked())
        {
            System.exit(0);
        }
        if (mainMenuButton.isButtonClicked())
        {
            mainMenuButton.resetButtonClicked();
            stateManager.setState(State.MENU);
        }
    }

    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //clears
        g.clear();
        //backgrond
        g.setBackground(Color.black);
        g.setColor(Color.red);
        exitButton.render(g);
        mainMenuButton.render(g);
    }    

    @Override
    public void cleanup(GameContainer gc) throws SlickException
    {
        
    }

    
}
