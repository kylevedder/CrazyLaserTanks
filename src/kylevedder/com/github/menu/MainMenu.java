/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.menu;

import java.awt.Color;
import kylevedder.com.github.gui.GUIButton;
import kylevedder.com.github.gui.GUIButtonContent;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.slickstates.SlickStateSinglePlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

/**
 *
 * @author Kyle
 */
public class MainMenu extends BaseMenu
{

    GameContainer gc;    
    StateBasedGame game;
    GUIButton newSPGameButton = null;
    GUIButton exitButton = null;

    public MainMenu(GameContainer gc, StateBasedGame game) throws SlickException
    {
        super(gc, game);
        this.gc = gc;
        this.game = game;
        GUIButtonContent newSPContent = new GUIButtonContent("New Game", "New Game", normalImage, hovImage);
        GUIButtonContent exitContent = new GUIButtonContent("Exit", "Exit", normalImage, hovImage);

        newSPGameButton = new GUIButton(gc, gc.getWidth() / 2, gc.getHeight() / 2, this.BUTTON_WIDTH, this.BUTTON_HEIGHT, newSPContent);
        exitButton = new GUIButton(gc, gc.getWidth() / 2, gc.getHeight() / 2 + this.BUTTON_HEIGHT + this.BUTTON_VERT_SPACING, this.BUTTON_WIDTH, this.BUTTON_HEIGHT, exitContent);

        fontLoader.setColor(Color.magenta);

        this.exitButton.setFont(fontLoader.getFont(), fontLoader.getFont());
        this.newSPGameButton.setFont(fontLoader.getFont(), fontLoader.getFont());

        this.exitButton.setTextOffset(0, this.BUTTON_VERT_TEXT_OFFSET);
        this.newSPGameButton.setTextOffset(0, this.BUTTON_VERT_TEXT_OFFSET);
    }

    @Override
    public void update() throws SlickException
    {
        super.update();
        this.newSPGameButton.setCenter(gc.getWidth()/2, gc.getHeight()/2);
        this.exitButton.setCenter(gc.getWidth()/2, gc.getHeight()/2 + this.BUTTON_HEIGHT + this.BUTTON_VERT_SPACING);
        
        if (exitButton.isButtonClicked())
        {
            exitButton.resetButtonClicked();
            MainApp.app.exit();
        }

        if (newSPGameButton.isButtonClicked())
        {
            newSPGameButton.resetButtonClicked();                
            game.enterState(SlickStateSinglePlayer.ID);
        }
    }

    @Override
    public void render(Graphics g, boolean render)
    {
        super.render(g, render);
        newSPGameButton.render(g);
        exitButton.render(g);
    }
    
    

}
