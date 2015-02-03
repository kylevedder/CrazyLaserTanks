/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kylevedder.com.github.menu;

import java.awt.Color;
import kylevedder.com.github.gui.GUIButton;
import kylevedder.com.github.gui.GUIButtonContent;
import kylevedder.com.github.gui.GUILabel;
import kylevedder.com.github.main.MainApp;
import kylevedder.com.github.slickstates.SlickStateMainMenu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kyle
 */
public class InGameMenuNew extends BaseMenu
{

    private GUIButton exitButton = null;
    private GUIButton mainMenuButton = null;
    private GUILabel label = null;
    
    private GameContainer gc;
    
    private StateBasedGame game;

    public InGameMenuNew(GameContainer gc, StateBasedGame game, String title) throws SlickException
    {
        super(gc, game);
        this.gc = gc;
        this.game = game;
        GUIButtonContent exitContent = new GUIButtonContent("Exit", "Exit", this.normalImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT), this.hovImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT));
        GUIButtonContent mainMenuContent = new GUIButtonContent("Main Menu", "Main Menu", this.normalImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT), this.hovImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT));

        this.exitButton = new GUIButton(this.gc, gc.getWidth() / 2 - this.BUTTON_WIDTH / 2 - this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2, this.BUTTON_WIDTH / 2 - this.BUTTON_HORZ_SPACING / 2, this.BUTTON_HEIGHT, exitContent);
        this.mainMenuButton = new GUIButton(this.gc, gc.getWidth() / 2 + this.BUTTON_WIDTH / 2 + this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2, this.BUTTON_WIDTH / 2 - this.BUTTON_HORZ_SPACING / 2, this.BUTTON_HEIGHT, mainMenuContent);        

        fontLoader.setColor(Color.magenta);        
        
        this.label = new GUILabel(gc, gc.getWidth(), gc.getHeight() - this.LABEL_VERT_SPACING, fontLoader.getSizedFont(this.LABEL_TEXT_SIZE), title);

        this.exitButton.setFont(fontLoader.getFont(), fontLoader.getFont());
        this.mainMenuButton.setFont(fontLoader.getFont(), fontLoader.getFont());

        this.exitButton.setTextOffset(0, this.BUTTON_VERT_TEXT_OFFSET);
        this.mainMenuButton.setTextOffset(0, this.BUTTON_VERT_TEXT_OFFSET);

    }
    
    /**
     * Sets the label's text
     * @param text 
     */
    public void setText(String text)
    {
        this.label.setText(text);
    }

    /**
     * Updates the menu position.
     */
    @Override
    public void update() throws SlickException
    {
        this.exitButton.setCenter(gc.getWidth() / 2 - this.BUTTON_WIDTH / 4 - this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2);
        this.mainMenuButton.setCenter(gc.getWidth() / 2 + this.BUTTON_WIDTH / 4 + this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2);
        this.label.setCenter(gc.getWidth() / 2, gc.getHeight() / 2 - this.LABEL_VERT_SPACING);

        if (exitButton.isButtonClicked())
        {
            exitButton.resetButtonClicked();
            MainApp.app.exit();
        }

        if (mainMenuButton.isButtonClicked())
        {
            mainMenuButton.resetButtonClicked();
            game.enterState(SlickStateMainMenu.ID);
        }
    }

    /**
     * Renders the menu.
     *
     * @param g
     * @param render
     */
    @Override
    public void render(Graphics g, boolean render)
    {
        super.render(g, render);
        if (render)
        {            
            this.exitButton.render(g);
            this.mainMenuButton.render(g);
            this.label.render();
        }

    }   

}
