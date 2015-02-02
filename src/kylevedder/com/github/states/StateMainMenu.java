/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.states;

import java.util.ArrayList;
import kylevedder.com.github.gui.FontLoader;
import kylevedder.com.github.gui.GUIButton;
import kylevedder.com.github.gui.GUIMouseOverContent;
import kylevedder.com.github.gui.GUIRotatingLogo;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.music.MusicPlayer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

/**
 *
 * @author Kyle
 */
public class StateMainMenu implements BasicState
{

    FontLoader fontLoader = null;
    GUIButton newGameButton;
    GUIButton exitButton;
    private StateManager stateManager = null;
    private MusicPlayer musicPlayer = null;

    private final int BUTTON_TEXT_WIDTH_PADDING = 20;
    private final int BUTTON_TEXT_HEIGHT_PADDING = 10;

    private final int BUTTON_WIDTH = 1000;
    private final int VERTICAL_SPACING = 50;
    private final int LOGO_Y_POS = 100;

    private GUIRotatingLogo logo = null;

    private ArrayList<GUIButton> buttonList = null;

    public void init(GameContainer gc, StateManager stateManager, MusicPlayer musicPlayer) throws SlickException
    {        
        this.stateManager = stateManager;
        this.musicPlayer = musicPlayer;

        fontLoader = new FontLoader("font/youre-gone/YoureGone.ttf", 128f);
        UnicodeFont buttonFont = fontLoader.getSizedFont(32f);

        GUIMouseOverContent newGameContent = new GUIMouseOverContent("Single Player", "Single Player", "images/buttons/normal.png", "images/buttons/hover.png", "images/buttons/hover.png");
        newGameContent.setBaseImage(newGameContent.getBaseImage().getScaledCopy(
                BUTTON_WIDTH,
                buttonFont.getHeight(newGameContent.getBaseText()) + BUTTON_TEXT_HEIGHT_PADDING));
        newGameContent.setHoverImage(newGameContent.getHoverImage().getScaledCopy(
                BUTTON_WIDTH,
                buttonFont.getHeight(newGameContent.getHoverText()) + BUTTON_TEXT_HEIGHT_PADDING));
        newGameContent.setClickImage(newGameContent.getClickImage().getScaledCopy(
                BUTTON_WIDTH,
                buttonFont.getHeight(newGameContent.getHoverText()) + BUTTON_TEXT_HEIGHT_PADDING));

        GUIMouseOverContent exitContent = new GUIMouseOverContent("Exit", "Exit", "images/buttons/normal.png", "images/buttons/hover.png", "images/buttons/hover.png");
        exitContent.setBaseImage(exitContent.getBaseImage().getScaledCopy(
                BUTTON_WIDTH,
                buttonFont.getHeight(exitContent.getBaseText()) + BUTTON_TEXT_HEIGHT_PADDING));
        exitContent.setHoverImage(exitContent.getHoverImage().getScaledCopy(
                BUTTON_WIDTH,
                buttonFont.getHeight(exitContent.getHoverText()) + BUTTON_TEXT_HEIGHT_PADDING));
        exitContent.setClickImage(exitContent.getClickImage().getScaledCopy(
                BUTTON_WIDTH,
                buttonFont.getHeight(exitContent.getHoverText()) + BUTTON_TEXT_HEIGHT_PADDING));

        newGameButton = new GUIButton(gc, MainApp.gameEngine.screenManager.getCurrentResWidth() / 2, MainApp.gameEngine.screenManager.getCurrentResHeight() / 2, BUTTON_WIDTH, newGameContent.getBaseImage().getHeight(), newGameContent);
        newGameButton.setFont(buttonFont, buttonFont);
        newGameButton.setFontColors(java.awt.Color.magenta, java.awt.Color.magenta);
        newGameButton.setTextOffset(0, -BUTTON_TEXT_HEIGHT_PADDING / 2);

        exitButton = new GUIButton(gc, MainApp.gameEngine.screenManager.getCurrentResWidth() / 2, MainApp.gameEngine.screenManager.getCurrentResHeight() / 2, BUTTON_WIDTH, exitContent.getBaseImage().getHeight(), exitContent);
        exitButton.setFont(buttonFont, buttonFont);
        exitButton.setFontColors(java.awt.Color.magenta, java.awt.Color.magenta);
        exitButton.setTextOffset(0, -BUTTON_TEXT_HEIGHT_PADDING / 2);

        buttonList = new ArrayList<GUIButton>();
        buttonList.add(newGameButton);
        buttonList.add(exitButton);

        this.logo = new GUIRotatingLogo("images/title/full.png", "images/title/wire_frame.png", 4, 2000, LOGO_Y_POS);
        
        this.musicPlayer.startMenuMusic();
    }

    public void update(GameContainer gc, int deltaTime) throws SlickException
    {
        this.logo.update(deltaTime);
        //ensure layout reflects screen size
        for (int i = 0; i < buttonList.size(); i++)
        {
            buttonList.get(i).setCenter(MainApp.gameEngine.screenManager.getCurrentResWidth() / 2, MainApp.gameEngine.screenManager.getCurrentResHeight() / 2 + i * VERTICAL_SPACING);
        }

        //perform updates
        if (newGameButton.isButtonClicked())
        {
            newGameButton.resetButtonClicked();
            stateManager.setState(State.SINGLE_PLAYER);
        }
        if (exitButton.isButtonClicked())
        {
            exitButton.resetButtonClicked();
            System.exit(0);
        }
    }

    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //clears
        g.clear();
        //backgrond
        g.setBackground(Color.black);
        newGameButton.render(g);
        exitButton.render(g);
        logo.render(gc);
    }
}
