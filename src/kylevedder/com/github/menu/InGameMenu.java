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
import kylevedder.com.github.reference.Reference;
import kylevedder.com.github.states.State;
import kylevedder.com.github.states.StateManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kyle
 */
public class InGameMenu extends BaseMenu
{

    private GUIButton exitButton = null;
    private GUIButton mainMenuButton = null;

    private GameContainer gc;
    private StateManager stateManager = null;

    public InGameMenu(GameContainer gc, StateManager stateManager) throws SlickException
    {
        super();
        this.stateManager = stateManager;
        this.gc = gc;

        GUIButtonContent exitContent = new GUIButtonContent("Exit", "Exit", this.normalImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT), this.hovImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT));
        GUIButtonContent mainMenuContent = new GUIButtonContent("Main Menu", "Main Menu", this.normalImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT), this.hovImage.getScaledCopy(BUTTON_WIDTH / 2, BUTTON_HEIGHT));

        this.exitButton = new GUIButton(this.gc, gc.getWidth() / 2 - this.BUTTON_WIDTH / 2 - this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2, this.BUTTON_WIDTH / 2 - this.BUTTON_HORZ_SPACING / 2, this.BUTTON_HEIGHT, exitContent);
        this.mainMenuButton = new GUIButton(this.gc, gc.getWidth() / 2 + this.BUTTON_WIDTH / 2 + this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2, this.BUTTON_WIDTH / 2 - this.BUTTON_HORZ_SPACING / 2, this.BUTTON_HEIGHT, mainMenuContent);

        fontLoader.setColor(Color.magenta);

        this.exitButton.setFont(fontLoader.getFont(), fontLoader.getFont());
        this.mainMenuButton.setFont(fontLoader.getFont(), fontLoader.getFont());

        this.exitButton.setTextOffset(0, this.BUTTON_VERT_TEXT_OFFSET);
        this.mainMenuButton.setTextOffset(0, this.BUTTON_VERT_TEXT_OFFSET);

    }

    /**
     * Updates the menu position.
     */

    @Override
    public void update() throws SlickException
    {
        this.exitButton.setCenter(gc.getWidth() / 2 - this.BUTTON_WIDTH / 4 - this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2);
        this.mainMenuButton.setCenter(gc.getWidth() / 2 + this.BUTTON_WIDTH / 4 + this.BUTTON_HORZ_SPACING / 2, gc.getHeight() / 2);

        if (exitButton.isButtonClicked())
        {
            exitButton.resetButtonClicked();
            MainApp.app.exit();
        }

        if (mainMenuButton.isButtonClicked())
        {
            mainMenuButton.resetButtonClicked();
            stateManager.setState(State.MENU);
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
        }

    }

}
